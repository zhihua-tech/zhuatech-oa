/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.model;

import jakarta.persistence.*;
import java.time.*;

@Entity @Table(name = "oa_attendance", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "work_date"}))
public class Attendance extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id") private UserAccount user;
    @Column(name = "work_date", nullable = false) private LocalDate workDate;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    @Column(nullable = false, length = 20) private String status = "NORMAL";
    protected Attendance() {}
    public Attendance(UserAccount user, LocalDate workDate) { this.user = user; this.workDate = workDate; }
    public void checkIn() { this.checkInTime = LocalDateTime.now(); this.status = checkInTime.toLocalTime().isAfter(LocalTime.of(9, 15)) ? "LATE" : "NORMAL"; }
    public void checkOut() { this.checkOutTime = LocalDateTime.now(); }
    public LocalDate getWorkDate() { return workDate; }
    public LocalDateTime getCheckInTime() { return checkInTime; }
    public LocalDateTime getCheckOutTime() { return checkOutTime; }
    public String getStatus() { return status; }
}
