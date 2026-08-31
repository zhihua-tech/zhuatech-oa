/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;

import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.service.ApprovalGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enterprise/approval-governance")
public class ApprovalGovernanceController {
    private final ApprovalGovernanceService service;
    public ApprovalGovernanceController(ApprovalGovernanceService service) { this.service = service; }

    @PostMapping("/evaluate")
    public ApiResponse<ApprovalGovernanceService.Assessment> evaluate(
            @Valid @RequestBody ApprovalGovernanceService.ApprovalCase request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
