/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oa.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApprovalSlaService {
    public Assessment assess(Request request) {
        double ratio = (double) request.pendingHours() / request.slaHours();
        int score = Math.min(100, (int) Math.round(request.businessImpact() * .4
            + ratio * 35 + (request.vipApplicant() ? 15 : 0) + (request.missingMaterials() ? 10 : 0)));
        int overdueHours = Math.max(0, request.pendingHours() - request.slaHours());
        String status = overdueHours > 0 ? "BREACHED" : ratio >= .8 ? "AT_RISK" : "ON_TRACK";
        String priority = score >= 80 ? "P0" : score >= 60 ? "P1" : score >= 35 ? "P2" : "P3";
        List<String> actions = new ArrayList<>();
        if (request.missingMaterials()) actions.add("联系申请人补齐审批材料");
        if (overdueHours > 0) actions.add("升级至流程负责人并设置处理时限");
        if (request.vipApplicant()) actions.add("通知业务负责人关注关键申请");
        if (actions.isEmpty()) actions.add("保持当前处理节奏并持续监控");
        return new Assessment(request.processName(), status, priority, score, overdueHours,
            "BREACHED".equals(status) || "P0".equals(priority), actions);
    }

    public record Request(@NotBlank String processName, @Min(0) int pendingHours,
                          @Positive int slaHours, @Min(0) @Max(100) int businessImpact,
                          boolean vipApplicant, boolean missingMaterials) {}
    public record Assessment(String processName, String status, String priority, int riskScore,
                             int overdueHours, boolean escalationRequired, List<String> actions) {}
}
