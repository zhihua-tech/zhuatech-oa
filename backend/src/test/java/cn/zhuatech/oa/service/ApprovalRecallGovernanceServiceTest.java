/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class ApprovalRecallGovernanceServiceTest {
    private final ApprovalRecallGovernanceService service = new ApprovalRecallGovernanceService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void recallsPendingWorkflowBeforeAnySideEffect() {
        var result = service.assess(request(false, false, false, true, true));
        assertThat(result.decision()).isEqualTo(ApprovalRecallGovernanceService.Decision.RECALL);
        assertThat(result.nextStatus()).isEqualTo("WITHDRAWN");
        assertThat(result.compensationRequired()).isFalse();
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void reviewsRecallThatRequiresExternalCompensation() {
        var result = service.assess(request(false, true, false, true, true));
        assertThat(result.decision()).isEqualTo(ApprovalRecallGovernanceService.Decision.REVIEW);
        assertThat(result.compensationRequired()).isTrue();
        assertThat(result.actions()).anyMatch(item -> item.contains("外部系统"));
        assertThat(result.actions()).anyMatch(item -> item.contains("下游业务单据"));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void blocksRecallAfterIrreversibleExecution() {
        var result = service.assess(request(true, false, true, true, true));
        assertThat(result.decision()).isEqualTo(ApprovalRecallGovernanceService.Decision.BLOCKED);
        assertThat(result.blockers()).anyMatch(item -> item.contains("不可逆"));
        assertThat(result.blockers()).anyMatch(item -> item.contains("锁定"));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private ApprovalRecallGovernanceService.Request request(boolean irreversible, boolean synced,
                                                             boolean locked, boolean notify,
                                                             boolean evidence) {
        return new ApprovalRecallGovernanceService.Request("PI-100", "employee-1", "employee-1",
                ApprovalRecallGovernanceService.ProcessStatus.PENDING, false, 2, irreversible,
                synced, locked, synced, notify, true, true, evidence);
    }
}
