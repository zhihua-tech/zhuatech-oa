/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "oa_notice")
public class Notice extends BaseEntity {
    @Column(nullable = false, length = 120) private String title;
    @Column(nullable = false, columnDefinition = "TEXT") private String content;
    @Column(nullable = false, length = 50) private String publisher;
    @Column(nullable = false) private LocalDateTime publishTime;
    @Column(nullable = false) private boolean pinned;
    protected Notice() {}
    public Notice(String title, String content, String publisher, boolean pinned) {
        this.title = title; this.content = content; this.publisher = publisher; this.pinned = pinned; this.publishTime = LocalDateTime.now();
    }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getPublisher() { return publisher; }
    public LocalDateTime getPublishTime() { return publishTime; }
    public boolean isPinned() { return pinned; }
}
