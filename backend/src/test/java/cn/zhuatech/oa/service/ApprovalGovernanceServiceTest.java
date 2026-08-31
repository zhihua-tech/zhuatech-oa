/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApprovalGovernanceServiceTest {
    private final ApprovalGovernanceService service = new ApprovalGovernanceService();

    @Test
    void approvesCompliantCaseAtRequiredLevel() {
        var result = service.evaluate(new ApprovalGovernanceService.ApprovalCase(
                "OA-2026-001", 20_000_000L, 2, true, true, false, true));
        assertEquals("APPROVED", result.decision());
        assertTrue(result.segregationPassed());
        assertTrue(result.blockers().isEmpty());
    }

    @Test
    void blocksSegregationAndDelegationViolations() {
        var result = service.evaluate(new ApprovalGovernanceService.ApprovalCase(
                "OA-2026-002", 200_000_000L, 1, true, false, true, false));
        assertEquals("BLOCKED", result.decision());
        assertEquals(3, result.requiredApprovalLevels());
        assertEquals(3, result.blockers().size());
    }
}
