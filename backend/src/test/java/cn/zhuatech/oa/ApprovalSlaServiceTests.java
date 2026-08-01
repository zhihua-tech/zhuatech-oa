/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oa;

import cn.zhuatech.oa.service.ApprovalSlaService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApprovalSlaServiceTests {
    @Test void triagesBreachedCriticalApproval() {
        var result = new ApprovalSlaService().assess(new ApprovalSlaService.Request(
            "采购合同审批", 30, 24, 85, true, true));
        assertEquals("BREACHED", result.status());
        assertEquals("P0", result.priority());
        assertEquals(6, result.overdueHours());
        assertTrue(result.escalationRequired());
    }
}
