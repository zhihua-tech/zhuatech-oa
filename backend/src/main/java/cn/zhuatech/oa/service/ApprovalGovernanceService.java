/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApprovalGovernanceService {
    public Assessment evaluate(ApprovalCase request) {
        int requiredLevels = request.amountCents() > 100_000_000L ? 3
                : request.amountCents() > 10_000_000L ? 2 : 1;
        List<String> blockers = new ArrayList<>();
        if (request.sameRequesterAndApprover()) blockers.add("申请人与审批人不得为同一人");
        if (request.delegated() && !request.delegateAuthorized()) blockers.add("委托审批未在授权范围内");
        if (!request.evidenceComplete()) blockers.add("审批证据不完整");
        boolean escalationNeeded = request.currentApprovalLevel() < requiredLevels;
        String decision = !blockers.isEmpty() ? "BLOCKED" : escalationNeeded ? "ESCALATE" : "APPROVED";
        List<String> tags = List.of("SOD_CHECKED", "DELEGATION_CHECKED", "AMOUNT_TIER_" + requiredLevels);
        return new Assessment(request.caseId(), decision, requiredLevels, request.currentApprovalLevel(),
                !request.sameRequesterAndApprover(), !request.delegated() || request.delegateAuthorized(),
                List.copyOf(blockers), tags);
    }

    public record ApprovalCase(@NotBlank String caseId, @Min(0) long amountCents,
                               @Min(1) @Max(5) int currentApprovalLevel, boolean delegated,
                               boolean delegateAuthorized, boolean sameRequesterAndApprover,
                               boolean evidenceComplete) {
        public ApprovalCase {
            if (caseId == null || caseId.isBlank()) throw new IllegalArgumentException("caseId is required");
            if (amountCents < 0) throw new IllegalArgumentException("amountCents must be non-negative");
            if (currentApprovalLevel < 1 || currentApprovalLevel > 5) throw new IllegalArgumentException("invalid approval level");
        }
    }

    public record Assessment(String caseId, String decision, int requiredApprovalLevels,
                             int currentApprovalLevel, boolean segregationPassed,
                             boolean delegationValid, List<String> blockers, List<String> auditTags) {}
}
