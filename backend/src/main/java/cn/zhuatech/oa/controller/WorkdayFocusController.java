/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oa.controller;

import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.service.WorkdayFocusService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oa/insights")
public class WorkdayFocusController {
    private final WorkdayFocusService service;
    public WorkdayFocusController(WorkdayFocusService service) { this.service = service; }

    @PostMapping("/workday-focus")
    public ApiResponse<WorkdayFocusService.Result> assess(@Valid @RequestBody WorkdayFocusService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
