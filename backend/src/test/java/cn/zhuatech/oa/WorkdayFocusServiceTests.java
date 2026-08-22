/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa;

import cn.zhuatech.oa.service.WorkdayFocusService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class WorkdayFocusServiceTests {
    private final WorkdayFocusService service = new WorkdayFocusService();

    @Test void identifiesFragmentedHighRiskWorkday() {
        var result = service.assess(new WorkdayFocusService.Request("E1008", new BigDecimal("5"), 9, 4, 8, new BigDecimal("0.5")));
        assertThat(result.riskLevel()).isEqualTo("HIGH");
        assertThat(result.recommendedFocusBlocks()).isEqualTo(3);
        assertThat(result.actions()).hasSizeGreaterThanOrEqualTo(3);
    }

    @Test void rewardsProtectedFocusTime() {
        var result = service.assess(new WorkdayFocusService.Request("E1021", new BigDecimal("1"), 1, 0, 1, new BigDecimal("3")));
        assertThat(result.riskLevel()).isEqualTo("LOW");
        assertThat(result.focusScore()).isEqualTo(100);
    }
}
