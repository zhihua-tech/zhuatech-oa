/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;

import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.service.ApprovalDelegationGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enterprise/oa")
public class ApprovalDelegationGovernanceController {
    private final ApprovalDelegationGovernanceService service;

    public ApprovalDelegationGovernanceController(ApprovalDelegationGovernanceService service) {
        this.service = service;
    }

    @PostMapping("/approval-delegation")
    public ApiResponse<ApprovalDelegationGovernanceService.Assessment> assess(
            @Valid @RequestBody ApprovalDelegationGovernanceService.Request request) {
        return ApiResponse.ok("审批委托治理评估完成", service.assess(request));
    }
}
