/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.repository;
import cn.zhuatech.oa.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByApplicantOrderByCreatedAtDesc(UserAccount applicant);
    List<LeaveRequest> findByStatusOrderByCreatedAtAsc(LeaveRequest.Status status);
    long countByApplicantAndStatus(UserAccount applicant, LeaveRequest.Status status);
}
