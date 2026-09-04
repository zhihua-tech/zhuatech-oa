/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OfficialDocumentPublicationGovernanceServiceTest {
    private final OfficialDocumentPublicationGovernanceService service = new OfficialDocumentPublicationGovernanceService();

    @Test void publishesControlledOfficialDocument() {
        var result = service.assess(request(true, true, true));
        assertEquals(OfficialDocumentPublicationGovernanceService.Decision.PUBLISH, result.decision());
        assertTrue(result.blockers().isEmpty());
        assertTrue(result.actions().isEmpty());
    }

    @Test void reviewsDocumentWithOperationalActions() {
        var result = service.assess(request(false, false, false));
        assertEquals(OfficialDocumentPublicationGovernanceService.Decision.REVIEW, result.decision());
        assertEquals(3, result.actions().size());
    }

    @Test void blocksUncontrolledOfficialDocument() {
        var result = service.assess(new OfficialDocumentPublicationGovernanceService.Request("DOC-003", "V3", 8,
                false, false, false, false, false, false, false, false, false, false, false, false,
                true, true, true));
        assertEquals(OfficialDocumentPublicationGovernanceService.Decision.BLOCKED, result.decision());
        assertEquals(12, result.blockers().size());
    }

    private OfficialDocumentPublicationGovernanceService.Request request(boolean acknowledgement,
                                                                          boolean withdrawal,
                                                                          boolean archive) {
        return new OfficialDocumentPublicationGovernanceService.Request("DOC-001", "V1", 8,
                true, true, true, true, true, true, true, true, true, true, true, true,
                acknowledgement, withdrawal, archive);
    }
}
