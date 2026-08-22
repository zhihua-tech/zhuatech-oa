/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "oa_todo_task")
public class TodoTask extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "assignee_id") private UserAccount assignee;
    @Column(nullable = false, length = 120) private String title;
    @Column(length = 500) private String description;
    private LocalDate dueDate;
    @Column(nullable = false, length = 20) private String priority;
    @Column(nullable = false) private boolean completed;
    protected TodoTask() {}
    public TodoTask(UserAccount assignee, String title, String description, LocalDate dueDate, String priority) { this.assignee = assignee; this.title = title; this.description = description; this.dueDate = dueDate; this.priority = priority; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getDueDate() { return dueDate; }
    public String getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
}
