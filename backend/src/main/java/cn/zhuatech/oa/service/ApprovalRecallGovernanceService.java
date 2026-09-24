/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批撤回前校验流程状态、申请人权限、不可逆业务动作和外部系统同步状态。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class ApprovalRecallGovernanceService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.applicantId().equals(request.requestedById()) && !request.processAdmin()) {
            blockers.add("只有申请人或流程管理员可以发起撤回");
        }
        if (request.status() != ProcessStatus.PENDING) blockers.add("仅审批中的流程允许撤回");
        if (request.irreversibleBusinessActionExecuted()) blockers.add("付款、用印、发文或入账等不可逆动作已经执行");
        if (request.downstreamRecordLocked()) blockers.add("下游业务单据已锁定，必须先执行冲销或解锁");
        if (request.completedApprovalNodes() > 0 && !request.approverNotificationPlanned()) {
            actions.add("通知所有已审批节点并保留原审批意见");
        }
        if (request.downstreamRecordCreated() && !request.downstreamRecordLocked()) {
            actions.add("撤销或作废已创建的下游业务单据");
        }
        if (request.externalSystemSynced()) actions.add("向外部系统发送撤销事件并确认回执");
        if (!request.resubmissionPlanReady()) actions.add("记录修改项和预计重新提交时间");
        if (!request.recallReasonRecorded()) actions.add("填写结构化撤回原因");
        if (!request.auditEvidenceAttached()) actions.add("归档撤回申请、审批轨迹及外部回执");
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED
                : !actions.isEmpty() ? Decision.REVIEW : Decision.RECALL;
        boolean compensationRequired = request.externalSystemSynced() || request.downstreamRecordCreated();
        String nextStatus = decision == Decision.RECALL ? "WITHDRAWN" : request.status().name();
        return new Assessment(request.processInstanceId(), decision, nextStatus, compensationRequired,
                List.copyOf(blockers), List.copyOf(actions));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotBlank String processInstanceId, @NotBlank String applicantId,
                          @NotBlank String requestedById, @NotNull ProcessStatus status,
                          boolean processAdmin, @Min(0) int completedApprovalNodes,
                          boolean irreversibleBusinessActionExecuted, boolean downstreamRecordCreated,
                          boolean downstreamRecordLocked, boolean externalSystemSynced,
                          boolean approverNotificationPlanned, boolean resubmissionPlanReady,
                          boolean recallReasonRecorded, boolean auditEvidenceAttached) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Assessment(String processInstanceId, Decision decision, String nextStatus,
                             boolean compensationRequired, List<String> blockers, List<String> actions) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum ProcessStatus { DRAFT, PENDING, APPROVED, REJECTED, WITHDRAWN }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Decision { RECALL, REVIEW, BLOCKED }
}
