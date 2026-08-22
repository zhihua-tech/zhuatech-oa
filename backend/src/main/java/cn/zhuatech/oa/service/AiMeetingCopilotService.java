/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import cn.zhuatech.oa.ai.OpenAiCompatibleGateway;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AiMeetingCopilotService {
    private final OpenAiCompatibleGateway gateway;

    public AiMeetingCopilotService(OpenAiCompatibleGateway gateway) { this.gateway = gateway; }

    public Result analyze(Request request) {
        int qualityScore = 100;
        List<String> actions = new ArrayList<>();
        if (request.durationMinutes() > 90) { qualityScore -= 15; actions.add("拆分超长议题并限制单次会议时长"); }
        if (request.participants() > 12) { qualityScore -= 10; actions.add("缩小核心决策人范围"); }
        if (request.decisionCount() == 0) { qualityScore -= 25; actions.add("补充明确的会议结论和决策人"); }
        if (request.actionItemCount() == 0) { qualityScore -= 25; actions.add("形成负责人、截止日期明确的行动项"); }
        if (request.overdueActionCount() > 0) { qualityScore -= Math.min(25, request.overdueActionCount() * 8); actions.add("升级逾期行动项并重新确认截止时间"); }
        qualityScore = Math.max(0, qualityScore);

        List<String> highlights = Arrays.stream(request.transcript().split("[。！？\\n]+"))
            .map(String::trim)
            .filter(text -> !text.isBlank())
            .filter(text -> text.contains("决定") || text.contains("确认") || text.contains("负责")
                || text.contains("截止") || text.contains("风险"))
            .limit(5).toList();
        if (highlights.isEmpty()) highlights = List.of("未识别到明确决策语句，请人工复核会议记录");

        String localBrief = "%s：形成 %d 项决策、%d 项行动，会议质量分 %d。"
            .formatted(request.topic(), request.decisionCount(), request.actionItemCount(), qualityScore);
        String prompt = "会议主题：%s；时长：%d 分钟；参与人数：%d；转写：%s"
            .formatted(request.topic(), request.durationMinutes(), request.participants(), request.transcript());
        var enhanced = gateway.complete("你是企业会议纪要助手，请输出简洁摘要、决策、行动项和风险。", prompt);
        var metadata = gateway.metadata();
        return new Result(qualityScore, qualityScore >= 80 ? "HEALTHY" : qualityScore >= 60 ? "REVIEW" : "FOLLOW_UP",
            enhanced.orElse(localBrief), highlights, List.copyOf(actions),
            enhanced.isPresent() ? "EXTERNAL_MODEL" : "LOCAL_RULES", metadata.provider(), metadata.model());
    }

    public record Request(@NotBlank String topic, @Min(1) int durationMinutes, @Min(1) int participants,
                          @Min(0) int decisionCount, @Min(0) int actionItemCount,
                          @Min(0) int overdueActionCount, @NotBlank @Size(max = 12000) String transcript) {}
    public record Result(int qualityScore, String status, String brief, List<String> highlights,
                         List<String> actions, String aiMode, String provider, String model) {}
}
