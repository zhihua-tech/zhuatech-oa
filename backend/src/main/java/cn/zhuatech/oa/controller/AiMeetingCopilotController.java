/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;

import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.service.AiMeetingCopilotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/oa/ai")
public class AiMeetingCopilotController {
    private final AiMeetingCopilotService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public AiMeetingCopilotController(AiMeetingCopilotService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/meeting-copilot")
    public ApiResponse<AiMeetingCopilotService.Result> analyze(@Valid @RequestBody AiMeetingCopilotService.Request request) {
        return ApiResponse.ok(service.analyze(request));
    }
}
