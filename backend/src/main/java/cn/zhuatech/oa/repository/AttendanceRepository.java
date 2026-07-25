/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oa.repository;
import cn.zhuatech.oa.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.*;
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserAndWorkDate(UserAccount user, LocalDate workDate);
    List<Attendance> findTop31ByUserOrderByWorkDateDesc(UserAccount user);
}
