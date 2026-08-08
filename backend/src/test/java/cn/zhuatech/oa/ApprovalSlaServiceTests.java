/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oa;

import cn.zhuatech.oa.service.ApprovalSlaService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

class ApprovalSlaServiceTests {
    @Test void triagesBreachedCriticalApproval() {
        var result = new ApprovalSlaService().assess(new ApprovalSlaService.Request(
            "采购合同审批", 30, 24, 85, true, true));
        assertEquals("BREACHED", result.status());
        assertEquals("P0", result.priority());
        assertEquals(6, result.overdueHours());
        assertTrue(result.escalationRequired());
    }

    @Test void rebalancesPortfolioByRiskAndAvailableCapacity() {
        var service = new ApprovalSlaService();
        var result = service.rebalance(new ApprovalSlaService.PortfolioRequest(List.of(
            new ApprovalSlaService.Request("合同审批", 30, 24, 90, true, false),
            new ApprovalSlaService.Request("差旅报销", 4, 24, 25, false, false),
            new ApprovalSlaService.Request("采购申请", 20, 24, 70, false, true)
        ), List.of(
            new ApprovalSlaService.TeamCapacity("综合审批组", 3, 2),
            new ApprovalSlaService.TeamCapacity("财务审批组", 2, 1)
        )));
        assertEquals(3, result.totalRequests());
        assertEquals(1, result.breachedRequests());
        assertEquals(1, result.unassignedRequests());
        assertEquals("合同审批", result.routings().getFirst().processName());
        assertTrue(result.recommendation().contains("容量不足"));
    }
}
