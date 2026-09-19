/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.repository;
import cn.zhuatech.oa.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<LeaveRequest> findByApplicantOrderByCreatedAtDesc(UserAccount applicant);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<LeaveRequest> findByStatusOrderByCreatedAtAsc(LeaveRequest.Status status);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    long countByApplicantAndStatus(UserAccount applicant, LeaveRequest.Status status);
}
