/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.controller;
import cn.zhuatech.oa.common.ApiResponse;
import cn.zhuatech.oa.dto.AuthDto.UserView;
import cn.zhuatech.oa.dto.OaDto.DepartmentView;
import cn.zhuatech.oa.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/organization")
public class OrganizationController {
    private final DepartmentRepository departments; private final UserRepository users;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public OrganizationController(DepartmentRepository departments, UserRepository users) { this.departments=departments; this.users=users; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping("/departments") public ApiResponse<List<DepartmentView>> departments() { return ApiResponse.ok(departments.findAllByOrderBySortOrderAsc().stream().map(DepartmentView::from).toList()); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping("/contacts") public ApiResponse<List<UserView>> contacts() { return ApiResponse.ok(users.findAll().stream().filter(u -> u.isEnabled()).map(UserView::from).toList()); }
}
