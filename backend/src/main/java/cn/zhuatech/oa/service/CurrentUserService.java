/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;
import cn.zhuatech.oa.common.BusinessException;
import cn.zhuatech.oa.model.UserAccount;
import cn.zhuatech.oa.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class CurrentUserService {
    private final UserRepository users;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public CurrentUserService(UserRepository users) { this.users = users; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public UserAccount get() { String name = SecurityContextHolder.getContext().getAuthentication().getName(); return users.findByUsername(name).orElseThrow(() -> new BusinessException("登录状态已失效")); }
}
