/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;

import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.service.ApprovalRecallGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/enterprise/oa")
public class ApprovalRecallGovernanceController {
    private final ApprovalRecallGovernanceService service;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public ApprovalRecallGovernanceController(ApprovalRecallGovernanceService service) {
        this.service = service;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/approval-recall")
    public ApiResponse<ApprovalRecallGovernanceService.Assessment> assess(
            @Valid @RequestBody ApprovalRecallGovernanceService.Request request) {
        return ApiResponse.ok("审批撤回评估完成", service.assess(request));
    }
}
