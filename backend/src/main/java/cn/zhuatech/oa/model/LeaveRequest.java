/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "oa_leave_request")
public class LeaveRequest extends BaseEntity {
    public enum Status { PENDING, APPROVED, REJECTED, CANCELLED }
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "applicant_id") private UserAccount applicant;
    @Column(nullable = false, length = 20) private String leaveType;
    @Column(nullable = false) private LocalDateTime startTime;
    @Column(nullable = false) private LocalDateTime endTime;
    @Column(nullable = false, precision = 5, scale = 1) private BigDecimal durationDays;
    @Column(nullable = false, length = 500) private String reason;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status = Status.PENDING;
    @Column(length = 500) private String approverComment;
    @Column(length = 50) private String approverName;
    private LocalDateTime approvedAt;
    protected LeaveRequest() {}
    public LeaveRequest(UserAccount applicant, String leaveType, LocalDateTime startTime, LocalDateTime endTime, BigDecimal durationDays, String reason) {
        this.applicant = applicant; this.leaveType = leaveType; this.startTime = startTime; this.endTime = endTime; this.durationDays = durationDays; this.reason = reason;
    }
    public void approve(boolean approved, String comment, String approver) { this.status = approved ? Status.APPROVED : Status.REJECTED; this.approverComment = comment; this.approverName = approver; this.approvedAt = LocalDateTime.now(); }
    public UserAccount getApplicant() { return applicant; }
    public String getLeaveType() { return leaveType; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public BigDecimal getDurationDays() { return durationDays; }
    public String getReason() { return reason; }
    public Status getStatus() { return status; }
    public String getApproverComment() { return approverComment; }
    public String getApproverName() { return approverName; }
}
