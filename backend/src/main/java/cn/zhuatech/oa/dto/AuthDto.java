/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oa.dto;
import cn.zhuatech.oa.model.UserAccount;
import jakarta.validation.constraints.NotBlank;

public final class AuthDto {
    private AuthDto() {}
    public record LoginRequest(@NotBlank(message="请输入用户名") String username, @NotBlank(message="请输入密码") String password) {}
    public record UserView(Long id, String username, String fullName, String email, String phone, String position, String role, Long departmentId, String departmentName) {
        public static UserView from(UserAccount u) { return new UserView(u.getId(), u.getUsername(), u.getFullName(), u.getEmail(), u.getPhone(), u.getPosition(), u.getRole().name(), u.getDepartment() == null ? null : u.getDepartment().getId(), u.getDepartment() == null ? null : u.getDepartment().getName()); }
    }
    public record LoginResponse(String token, UserView user) {}
}
