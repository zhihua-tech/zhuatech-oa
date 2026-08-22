/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;
import cn.zhuatech.oa.common.*;
import cn.zhuatech.oa.dto.OaDto.*;
import cn.zhuatech.oa.model.LeaveRequest;
import cn.zhuatech.oa.repository.LeaveRequestRepository;
import cn.zhuatech.oa.service.CurrentUserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/leaves")
public class LeaveController {
    private final LeaveRequestRepository leaves; private final CurrentUserService current;
    public LeaveController(LeaveRequestRepository leaves, CurrentUserService current) { this.leaves=leaves; this.current=current; }
    @GetMapping public ApiResponse<List<LeaveView>> mine() { return ApiResponse.ok(leaves.findByApplicantOrderByCreatedAtDesc(current.get()).stream().map(LeaveView::from).toList()); }
    @GetMapping("/pending") @PreAuthorize("hasRole('ADMIN')") public ApiResponse<List<LeaveView>> pending() { return ApiResponse.ok(leaves.findByStatusOrderByCreatedAtAsc(LeaveRequest.Status.PENDING).stream().map(LeaveView::from).toList()); }
    @PostMapping public ApiResponse<LeaveView> create(@Valid @RequestBody LeaveCreateRequest req) { if(!req.endTime().isAfter(req.startTime())) throw new BusinessException("结束时间必须晚于开始时间"); var leave=new LeaveRequest(current.get(), req.leaveType(), req.startTime(), req.endTime(), req.durationDays(), req.reason()); return ApiResponse.ok("申请已提交", LeaveView.from(leaves.save(leave))); }
    @PostMapping("/{id}/approve") @PreAuthorize("hasRole('ADMIN')") public ApiResponse<LeaveView> approve(@PathVariable Long id, @Valid @RequestBody LeaveApproveRequest req) { var leave=leaves.findById(id).orElseThrow(() -> new BusinessException("申请不存在")); if(leave.getStatus()!=LeaveRequest.Status.PENDING) throw new BusinessException("申请已经处理"); leave.approve(req.approved(), req.comment(), current.get().getFullName()); return ApiResponse.ok("审批完成", LeaveView.from(leaves.save(leave))); }
}
