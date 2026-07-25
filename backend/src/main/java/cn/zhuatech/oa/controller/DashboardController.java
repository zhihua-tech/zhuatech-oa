/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oa.controller;
import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.dto.OaDto.DashboardView;
import cn.zhuatech.oa.model.LeaveRequest;
import cn.zhuatech.oa.repository.*;
import cn.zhuatech.oa.service.CurrentUserService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
@RestController @RequestMapping("/api/dashboard")
public class DashboardController {
    private final TodoTaskRepository tasks; private final LeaveRequestRepository leaves; private final NoticeRepository notices; private final AttendanceRepository attendance; private final CurrentUserService current;
    public DashboardController(TodoTaskRepository tasks, LeaveRequestRepository leaves, NoticeRepository notices, AttendanceRepository attendance, CurrentUserService current) { this.tasks=tasks; this.leaves=leaves; this.notices=notices; this.attendance=attendance; this.current=current; }
    @GetMapping public ApiResponse<DashboardView> dashboard() { var user=current.get(); var today=attendance.findByUserAndWorkDate(user, LocalDate.now()); return ApiResponse.ok(new DashboardView(tasks.countByAssigneeAndCompletedFalse(user), leaves.countByApplicantAndStatus(user, LeaveRequest.Status.PENDING), notices.count(), today.map(a -> a.getCheckInTime()!=null).orElse(false), today.map(a -> a.getCheckOutTime()!=null).orElse(false))); }
}
