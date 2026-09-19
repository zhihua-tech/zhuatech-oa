/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * 审批委托启用前的时间、权限、职责分离和循环委托检查。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class ApprovalDelegationGovernanceService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (request.principalId().equals(request.delegateId())) blockers.add("委托人与受托人不能相同");
        if (!request.effectiveFrom().isBefore(request.effectiveTo())) blockers.add("委托开始时间必须早于结束时间");
        if (request.effectiveTo().isBefore(request.evaluatedAt())) blockers.add("委托有效期已经结束");
        if (!request.delegateActive()) blockers.add("受托人账号未启用");
        if (!request.sameOrganization()) blockers.add("跨组织委托未获得授权");
        if (request.cycleDetected()) blockers.add("检测到循环或反向委托链");
        if (request.selfApprovalPossible()) blockers.add("委托后存在申请人自审风险");
        if (request.restrictedScopeIncluded() && !request.restrictedScopeApproved()) {
            blockers.add("印章、付款或人事等受限流程未获得专项授权");
        }
        if (request.delegateLoadPercent() >= 90) actions.add("受托人负载达到 90%，建议拆分委托范围");
        if (!request.notificationPlanned()) actions.add("通知委托人、受托人和流程管理员");
        if (!request.auditEvidenceAttached()) actions.add("归档委托依据、范围及审批证据");
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED
                : !actions.isEmpty() ? Decision.REVIEW : Decision.ACTIVATE;
        return new Assessment(request.delegationId(), decision, request.effectiveFrom(), request.effectiveTo(),
                List.copyOf(request.scopes()), List.copyOf(blockers), List.copyOf(actions));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotBlank String delegationId, @NotBlank String principalId,
                          @NotBlank String delegateId, @NotEmpty List<@NotBlank String> scopes,
                          @NotNull Instant effectiveFrom, @NotNull Instant effectiveTo,
                          @NotNull Instant evaluatedAt, boolean delegateActive,
                          boolean sameOrganization, boolean cycleDetected, boolean selfApprovalPossible,
                          boolean restrictedScopeIncluded, boolean restrictedScopeApproved,
                          @Min(0) @Max(100) int delegateLoadPercent,
                          boolean notificationPlanned, boolean auditEvidenceAttached) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Assessment(String delegationId, Decision decision, Instant effectiveFrom,
                             Instant effectiveTo, List<String> scopes, List<String> blockers,
                             List<String> actions) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Decision { ACTIVATE, REVIEW, BLOCKED }
}
