/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;
import cn.zhuatech.oa.common.*;
import cn.zhuatech.oa.dto.OaDto.AttendanceView;
import cn.zhuatech.oa.model.*;
import cn.zhuatech.oa.repository.AttendanceRepository;
import cn.zhuatech.oa.service.CurrentUserService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RestController @RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceRepository attendance; private final CurrentUserService current;
    public AttendanceController(AttendanceRepository attendance, CurrentUserService current) { this.attendance=attendance; this.current=current; }
    @GetMapping("/today") public ApiResponse<AttendanceView> today() { return ApiResponse.ok(attendance.findByUserAndWorkDate(current.get(), LocalDate.now()).map(AttendanceView::from).orElse(null)); }
    @GetMapping public ApiResponse<List<AttendanceView>> history() { return ApiResponse.ok(attendance.findTop31ByUserOrderByWorkDateDesc(current.get()).stream().map(AttendanceView::from).toList()); }
    @PostMapping("/check-in") public ApiResponse<AttendanceView> checkIn() { UserAccount user=current.get(); Attendance a=attendance.findByUserAndWorkDate(user, LocalDate.now()).orElseGet(() -> new Attendance(user, LocalDate.now())); if(a.getCheckInTime()!=null) throw new BusinessException("今天已经签到"); a.checkIn(); return ApiResponse.ok("签到成功", AttendanceView.from(attendance.save(a))); }
    @PostMapping("/check-out") public ApiResponse<AttendanceView> checkOut() { Attendance a=attendance.findByUserAndWorkDate(current.get(), LocalDate.now()).orElseThrow(() -> new BusinessException("请先签到")); if(a.getCheckOutTime()!=null) throw new BusinessException("今天已经签退"); a.checkOut(); return ApiResponse.ok("签退成功", AttendanceView.from(attendance.save(a))); }
}
