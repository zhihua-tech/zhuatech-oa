/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oa.dto;

import cn.zhuatech.oa.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;

public final class OaDto {
    private OaDto() {}
    public record NoticeRequest(@NotBlank(message="请输入公告标题") @Size(max=120) String title, @NotBlank(message="请输入公告内容") String content, boolean pinned) {}
    public record NoticeView(Long id, String title, String content, String publisher, LocalDateTime publishTime, boolean pinned) {
        public static NoticeView from(Notice n) { return new NoticeView(n.getId(), n.getTitle(), n.getContent(), n.getPublisher(), n.getPublishTime(), n.isPinned()); }
    }
    public record AttendanceView(Long id, LocalDate workDate, LocalDateTime checkInTime, LocalDateTime checkOutTime, String status) {
        public static AttendanceView from(Attendance a) { return new AttendanceView(a.getId(), a.getWorkDate(), a.getCheckInTime(), a.getCheckOutTime(), a.getStatus()); }
    }
    public record LeaveCreateRequest(@NotBlank(message="请选择请假类型") String leaveType, @NotNull(message="请选择开始时间") LocalDateTime startTime, @NotNull(message="请选择结束时间") LocalDateTime endTime, @NotNull @DecimalMin(value="0.5", message="请假时长至少半天") BigDecimal durationDays, @NotBlank(message="请输入请假原因") @Size(max=500) String reason) {}
    public record LeaveApproveRequest(boolean approved, @Size(max=500) String comment) {}
    public record LeaveView(Long id, String applicantName, String leaveType, LocalDateTime startTime, LocalDateTime endTime, BigDecimal durationDays, String reason, String status, String approverComment, String approverName, LocalDateTime createdAt) {
        public static LeaveView from(LeaveRequest l) { return new LeaveView(l.getId(), l.getApplicant().getFullName(), l.getLeaveType(), l.getStartTime(), l.getEndTime(), l.getDurationDays(), l.getReason(), l.getStatus().name(), l.getApproverComment(), l.getApproverName(), l.getCreatedAt()); }
    }
    public record TaskCreateRequest(@NotBlank(message="请输入待办标题") @Size(max=120) String title, @Size(max=500) String description, LocalDate dueDate, @Pattern(regexp="LOW|MEDIUM|HIGH", message="优先级不正确") String priority) {}
    public record TaskStatusRequest(boolean completed) {}
    public record TaskView(Long id, String title, String description, LocalDate dueDate, String priority, boolean completed) {
        public static TaskView from(TodoTask t) { return new TaskView(t.getId(), t.getTitle(), t.getDescription(), t.getDueDate(), t.getPriority(), t.isCompleted()); }
    }
    public record DepartmentView(Long id, String code, String name) { public static DepartmentView from(Department d) { return new DepartmentView(d.getId(), d.getCode(), d.getName()); } }
    public record DashboardView(long pendingTasks, long pendingLeaves, long notices, boolean checkedIn, boolean checkedOut) {}
}
