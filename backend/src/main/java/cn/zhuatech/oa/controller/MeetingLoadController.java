/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;

import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.service.MeetingLoadService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oa/insights")
public class MeetingLoadController {
    private final MeetingLoadService service;
    public MeetingLoadController(MeetingLoadService service) { this.service = service; }

    @PostMapping("/meeting-load")
    public ApiResponse<MeetingLoadService.Result> assess(@Valid @RequestBody MeetingLoadService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
