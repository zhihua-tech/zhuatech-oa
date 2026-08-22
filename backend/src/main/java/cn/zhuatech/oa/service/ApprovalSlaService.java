/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /** 按风险排序审批队列，并根据各审批组的剩余容量给出分流建议。 */
    public PortfolioResult rebalance(PortfolioRequest request) {
        List<Assessment> queue = request.requests().stream().map(this::assess)
            .sorted(Comparator.comparingInt(Assessment::riskScore).reversed()
                .thenComparing(Assessment::processName))
            .toList();
        Map<String, Integer> remaining = new HashMap<>();
        request.teams().forEach(team -> remaining.put(team.teamName(),
            Math.max(0, team.dailyCapacity() - team.activeLoad())));
        List<Routing> routings = new ArrayList<>();
        for (Assessment item : queue) {
            String target = request.teams().stream()
                .filter(team -> remaining.get(team.teamName()) > 0)
                .max(Comparator.comparingInt(team -> remaining.get(team.teamName())))
                .map(TeamCapacity::teamName).orElse("待人工分派");
            if (!"待人工分派".equals(target)) remaining.computeIfPresent(target, (key, value) -> value - 1);
            routings.add(new Routing(item.processName(), item.priority(), item.riskScore(), target,
                item.escalationRequired()));
        }
        long breached = queue.stream().filter(item -> "BREACHED".equals(item.status())).count();
        long unassigned = routings.stream().filter(item -> "待人工分派".equals(item.targetTeam())).count();
        String recommendation = unassigned > 0 ? "审批容量不足，建议临时扩容或启用代理审批"
            : breached > 0 ? "优先处理已超时审批并通知流程负责人" : "当前审批容量可以覆盖待办队列";
        return new PortfolioResult(queue.size(), breached, unassigned, List.copyOf(routings),
            Map.copyOf(remaining), recommendation);
    }

    public record Request(@NotBlank String processName, @Min(0) int pendingHours,
                          @Positive int slaHours, @Min(0) @Max(100) int businessImpact,
                          boolean vipApplicant, boolean missingMaterials) {}
    public record Assessment(String processName, String status, String priority, int riskScore,
                             int overdueHours, boolean escalationRequired, List<String> actions) {}
    public record TeamCapacity(@NotBlank String teamName, @Min(1) int dailyCapacity,
                               @Min(0) int activeLoad) {}
    public record PortfolioRequest(@NotEmpty List<@Valid Request> requests,
                                   @NotEmpty List<@Valid TeamCapacity> teams) {}
    public record Routing(String processName, String priority, int riskScore,
                          String targetTeam, boolean escalationRequired) {}
    public record PortfolioResult(int totalRequests, long breachedRequests, long unassignedRequests,
                                  List<Routing> routings, Map<String, Integer> remainingCapacity,
                                  String recommendation) {}
}
