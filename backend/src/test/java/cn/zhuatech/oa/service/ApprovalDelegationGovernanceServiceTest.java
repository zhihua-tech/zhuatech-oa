/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class ApprovalDelegationGovernanceServiceTest {
    private final ApprovalDelegationGovernanceService service = new ApprovalDelegationGovernanceService();
    private final Instant now = Instant.parse("2026-09-19T00:00:00Z");

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void activatesControlledDelegation() {
        var result = service.assess(request(false, false, 45, true, true));
        assertThat(result.decision()).isEqualTo(ApprovalDelegationGovernanceService.Decision.ACTIVATE);
        assertThat(result.blockers()).isEmpty();
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void reviewsOverloadedDelegateAndMissingEvidence() {
        var result = service.assess(request(false, false, 95, false, false));
        assertThat(result.decision()).isEqualTo(ApprovalDelegationGovernanceService.Decision.REVIEW);
        assertThat(result.actions()).hasSize(3);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void blocksCycleAndRestrictedScopeWithoutApproval() {
        var result = service.assess(request(true, true, 40, true, true));
        assertThat(result.decision()).isEqualTo(ApprovalDelegationGovernanceService.Decision.BLOCKED);
        assertThat(result.blockers()).contains("检测到循环或反向委托链", "印章、付款或人事等受限流程未获得专项授权");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void blocksSelfDelegationAndInvalidWindow() {
        var result = service.assess(new ApprovalDelegationGovernanceService.Request("D-2", "u1", "u1",
                List.of("采购审批"), now.plusSeconds(3600), now, now, true, true,
                false, false, false, true, 10, true, true));
        assertThat(result.decision()).isEqualTo(ApprovalDelegationGovernanceService.Decision.BLOCKED);
        assertThat(result.blockers()).hasSize(2);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private ApprovalDelegationGovernanceService.Request request(boolean cycle, boolean restricted,
                                                                 int load, boolean notice, boolean evidence) {
        return new ApprovalDelegationGovernanceService.Request("D-1", "u1", "u2",
                List.of("合同审批", "费用审批"), now, now.plusSeconds(86400), now,
                true, true, cycle, false, restricted, false, load, notice, evidence);
    }
}
