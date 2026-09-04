/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;

import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.service.OfficialDocumentPublicationGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/oa")
public class OfficialDocumentPublicationGovernanceController {
    private final OfficialDocumentPublicationGovernanceService service;
    public OfficialDocumentPublicationGovernanceController(OfficialDocumentPublicationGovernanceService service) { this.service = service; }

    @PostMapping("/official-document-publication")
    public ApiResponse<OfficialDocumentPublicationGovernanceService.Assessment> assess(
            @Valid @RequestBody OfficialDocumentPublicationGovernanceService.Request request) {
        return ApiResponse.ok("公文发布评估完成", service.assess(request));
    }
}
