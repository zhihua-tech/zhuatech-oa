/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oa.model;

import jakarta.persistence.*;

@Entity @Table(name = "oa_department")
public class Department extends BaseEntity {
    @Column(nullable = false, unique = true, length = 32) private String code;
    @Column(nullable = false, length = 64) private String name;
    @Column(nullable = false) private Integer sortOrder = 0;
    protected Department() {}
    public Department(String code, String name, int sortOrder) { this.code = code; this.name = name; this.sortOrder = sortOrder; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public Integer getSortOrder() { return sortOrder; }
}
