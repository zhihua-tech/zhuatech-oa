/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oa.repository;
import cn.zhuatech.oa.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<UserAccount, Long> { Optional<UserAccount> findByUsername(String username); }
