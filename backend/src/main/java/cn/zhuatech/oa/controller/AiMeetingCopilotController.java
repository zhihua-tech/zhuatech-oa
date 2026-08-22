/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;

import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.service.AiMeetingCopilotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oa/ai")
public class AiMeetingCopilotController {
    private final AiMeetingCopilotService service;
    public AiMeetingCopilotController(AiMeetingCopilotService service) { this.service = service; }

    @PostMapping("/meeting-copilot")
    public ApiResponse<AiMeetingCopilotService.Result> analyze(@Valid @RequestBody AiMeetingCopilotService.Request request) {
        return ApiResponse.ok(service.analyze(request));
    }
}
