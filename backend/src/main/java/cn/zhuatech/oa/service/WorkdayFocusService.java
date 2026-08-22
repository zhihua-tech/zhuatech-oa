/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkdayFocusService {
    public Result assess(Request request) {
        int score = 100;
        score -= Math.min(30, request.meetingHours().multiply(BigDecimal.valueOf(6)).intValue());
        score -= Math.min(20, request.pendingApprovals() * 2);
        score -= Math.min(25, request.overdueTasks() * 5);
        score -= Math.min(20, request.interruptionCount() * 2);
        score += Math.min(15, request.focusHours().multiply(BigDecimal.valueOf(5)).intValue());
        score = Math.max(0, Math.min(100, score));

        String riskLevel = score < 40 ? "HIGH" : score < 70 ? "MEDIUM" : "LOW";
        int recommendedFocusBlocks = score < 40 ? 3 : score < 70 ? 2 : 1;
        List<String> actions = new ArrayList<>();
        if (request.overdueTasks() > 0) actions.add("优先清理逾期任务并明确责任人和截止时间");
        if (request.pendingApprovals() >= 6) actions.add("集中设置两个审批处理窗口，减少任务切换");
        if (request.meetingHours().compareTo(new BigDecimal("3")) >= 0) actions.add("合并非必要会议并使用异步纪要同步");
        if (request.interruptionCount() >= 6) actions.add("开启免打扰时段并统一收集临时事项");
        if (actions.isEmpty()) actions.add("维持当前节奏并在下班前完成次日优先级排序");
        return new Result(request.employeeNo(), score, riskLevel, recommendedFocusBlocks, actions);
    }

    public record Request(@NotBlank String employeeNo,
                          @DecimalMin("0") BigDecimal meetingHours,
                          @Min(0) int pendingApprovals, @Min(0) int overdueTasks,
                          @Min(0) int interruptionCount,
                          @DecimalMin("0") BigDecimal focusHours) {}

    public record Result(String employeeNo, int focusScore, String riskLevel,
                         int recommendedFocusBlocks, List<String> actions) {}
}
