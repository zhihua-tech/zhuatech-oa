/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa;

import cn.zhuatech.oa.ai.OpenAiCompatibleGateway;
import cn.zhuatech.oa.service.AiMeetingCopilotService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AiMeetingCopilotServiceTests {
    private final AiMeetingCopilotService service = new AiMeetingCopilotService(
        new OpenAiCompatibleGateway("local", "https://api.deepseek.com", "deepseek-chat", ""));

    @Test void extractsDecisionsWithoutExternalModel() {
        var result = service.analyze(new AiMeetingCopilotService.Request("季度经营会", 60, 8, 2, 3, 0,
            "会议决定提前启动回款专项。张经理负责本周五提交方案。其他事项下周确认。"));
        assertThat(result.aiMode()).isEqualTo("LOCAL_RULES");
        assertThat(result.highlights()).hasSizeGreaterThanOrEqualTo(2);
        assertThat(result.status()).isEqualTo("HEALTHY");
    }
}
