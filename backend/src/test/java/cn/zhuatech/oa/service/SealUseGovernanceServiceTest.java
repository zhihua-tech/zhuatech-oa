/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class SealUseGovernanceServiceTest {
    private final SealUseGovernanceService service = new SealUseGovernanceService();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void approvesControlledInternalElectronicSeal() {
        var a = service.assess(new SealUseGovernanceService.Request("SEAL-1", "合同章", "u1", "u2", 2,
            true, true, true, true, false, true, true, false, false, true));
        assertThat(a.decision()).isEqualTo(SealUseGovernanceService.Decision.APPROVE);
        assertThat(a.approvalRoute()).contains("印章管理员");
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void reviewsWhenArchiveEvidenceIsIncomplete() {
        var a = service.assess(new SealUseGovernanceService.Request("SEAL-2", "公章", "u1", "u2", 1,
            true, true, true, true, false, false, false, false, false, true));
        assertThat(a.decision()).isEqualTo(SealUseGovernanceService.Decision.REVIEW);
        assertThat(a.actions()).hasSize(2);
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void blocksExternalSelfCustodiedSealRequest() {
        var a = service.assess(new SealUseGovernanceService.Request("SEAL-3", "公章", "u1", "u1", 20,
            true, true, false, true, true, true, true, true, true, false));
        assertThat(a.decision()).isEqualTo(SealUseGovernanceService.Decision.BLOCKED);
        assertThat(a.riskLevel()).isEqualTo(SealUseGovernanceService.RiskLevel.HIGH);
        assertThat(a.blockers()).hasSizeGreaterThanOrEqualTo(2);
    }
}
