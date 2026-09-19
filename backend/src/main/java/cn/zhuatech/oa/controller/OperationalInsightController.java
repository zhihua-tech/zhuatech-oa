/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;

import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.service.ApprovalSlaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/operations")
public class OperationalInsightController {
    private final ApprovalSlaService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public OperationalInsightController(ApprovalSlaService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/approval-sla")
    public ApiResponse<ApprovalSlaService.Assessment> assess(@Valid @RequestBody ApprovalSlaService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/approval-sla/portfolio")
    public ApiResponse<ApprovalSlaService.PortfolioResult> rebalance(
        @Valid @RequestBody ApprovalSlaService.PortfolioRequest request) {
        return ApiResponse.ok(service.rebalance(request));
    }
}
