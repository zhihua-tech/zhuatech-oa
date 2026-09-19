/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name = "oa_leave_request")
public class LeaveRequest extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
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
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected LeaveRequest() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LeaveRequest(UserAccount applicant, String leaveType, LocalDateTime startTime, LocalDateTime endTime, BigDecimal durationDays, String reason) {
        this.applicant = applicant; this.leaveType = leaveType; this.startTime = startTime; this.endTime = endTime; this.durationDays = durationDays; this.reason = reason;
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void approve(boolean approved, String comment, String approver) { this.status = approved ? Status.APPROVED : Status.REJECTED; this.approverComment = comment; this.approverName = approver; this.approvedAt = LocalDateTime.now(); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public UserAccount getApplicant() { return applicant; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getLeaveType() { return leaveType; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getStartTime() { return startTime; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getEndTime() { return endTime; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BigDecimal getDurationDays() { return durationDays; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getReason() { return reason; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Status getStatus() { return status; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getApproverComment() { return approverComment; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getApproverName() { return approverName; }
}
