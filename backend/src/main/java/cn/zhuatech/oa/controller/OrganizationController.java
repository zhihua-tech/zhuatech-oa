/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;
import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.dto.AuthDto.UserView;
import cn.zhuatech.oa.dto.OaDto.DepartmentView;
import cn.zhuatech.oa.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/organization")
public class OrganizationController {
    private final DepartmentRepository departments; private final UserRepository users;
    public OrganizationController(DepartmentRepository departments, UserRepository users) { this.departments=departments; this.users=users; }
    @GetMapping("/departments") public ApiResponse<List<DepartmentView>> departments() { return ApiResponse.ok(departments.findAllByOrderBySortOrderAsc().stream().map(DepartmentView::from).toList()); }
    @GetMapping("/contacts") public ApiResponse<List<UserView>> contacts() { return ApiResponse.ok(users.findAll().stream().filter(u -> u.isEnabled()).map(UserView::from).toList()); }
}
