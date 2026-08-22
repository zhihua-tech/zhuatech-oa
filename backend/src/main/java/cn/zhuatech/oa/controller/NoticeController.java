/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;
import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.dto.OaDto.*;
import cn.zhuatech.oa.model.Notice;
import cn.zhuatech.oa.repository.NoticeRepository;
import cn.zhuatech.oa.service.CurrentUserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/notices")
public class NoticeController {
    private final NoticeRepository notices; private final CurrentUserService current;
    public NoticeController(NoticeRepository notices, CurrentUserService current) { this.notices=notices; this.current=current; }
    @GetMapping public ApiResponse<List<NoticeView>> list() { return ApiResponse.ok(notices.findAllByOrderByPinnedDescPublishTimeDesc().stream().map(NoticeView::from).toList()); }
    @PostMapping @PreAuthorize("hasRole('ADMIN')") public ApiResponse<NoticeView> create(@Valid @RequestBody NoticeRequest req) { return ApiResponse.ok("公告已发布", NoticeView.from(notices.save(new Notice(req.title(), req.content(), current.get().getFullName(), req.pinned())))); }
}
