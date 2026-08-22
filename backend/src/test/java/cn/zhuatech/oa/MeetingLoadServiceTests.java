/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa;

import cn.zhuatech.oa.service.MeetingLoadService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingLoadServiceTests {
    private final MeetingLoadService service = new MeetingLoadService();

    @Test void identifiesHighMeetingLoad() {
        var result = service.assess(new MeetingLoadService.Request("E1008", new BigDecimal("40"),
            new BigDecimal("19"), 16, 8, 2, 1));
        assertThat(result.riskLevel()).isEqualTo("HIGH");
        assertThat(result.meetingLoadRate()).isEqualByComparingTo("0.4750");
        assertThat(result.actions()).hasSizeGreaterThanOrEqualTo(3);
    }

    @Test void keepsHealthyScheduleLowRisk() {
        var result = service.assess(new MeetingLoadService.Request("E1021", new BigDecimal("40"),
            new BigDecimal("6"), 6, 5, 0, 4));
        assertThat(result.riskLevel()).isEqualTo("LOW");
        assertThat(result.loadScore()).isZero();
    }
}
