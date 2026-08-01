/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oa.controller;

import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.service.ApprovalSlaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operations")
public class OperationalInsightController {
    private final ApprovalSlaService service;
    public OperationalInsightController(ApprovalSlaService service) { this.service = service; }

    @PostMapping("/approval-sla")
    public ApiResponse<ApprovalSlaService.Assessment> assess(@Valid @RequestBody ApprovalSlaService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
