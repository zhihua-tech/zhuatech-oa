/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.oa.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingLoadService {
    public Result assess(Request request) {
        BigDecimal loadRate = request.meetingHours().divide(request.weeklyCapacityHours(), 4, RoundingMode.HALF_UP);
        int optionalMeetings = Math.max(0, request.meetingCount() - request.requiredMeetings());
        BigDecimal optionalRate = request.meetingCount() == 0 ? BigDecimal.ZERO
            : BigDecimal.valueOf(optionalMeetings).divide(BigDecimal.valueOf(request.meetingCount()), 4, RoundingMode.HALF_UP);

        int score = 0;
        if (loadRate.compareTo(new BigDecimal("0.40")) >= 0) score += 40;
        else if (loadRate.compareTo(new BigDecimal("0.25")) >= 0) score += 20;
        if (request.meetingCount() >= 12) score += 20;
        if (optionalRate.compareTo(new BigDecimal("0.35")) >= 0) score += 15;
        score += Math.min(20, request.conflictCount() * 10);
        if (request.focusBlockCount() < 3) score += 15;
        score = Math.min(100, score);

        String riskLevel = score >= 60 ? "HIGH" : score >= 30 ? "MEDIUM" : "LOW";
        List<String> actions = new ArrayList<>();
        if (loadRate.compareTo(new BigDecimal("0.40")) >= 0) actions.add("合并例会并设置无会专注时段");
        if (optionalRate.compareTo(new BigDecimal("0.35")) >= 0) actions.add("将非必要参会人调整为纪要知会");
        if (request.conflictCount() > 0) actions.add("处理日程冲突并为关键会议预留缓冲");
        if (request.focusBlockCount() < 3) actions.add("每周至少安排三个两小时专注时间块");
        if (actions.isEmpty()) actions.add("维持当前会议节奏并按周复盘");
        return new Result(request.employeeNo(), loadRate, optionalRate, score, riskLevel, actions);
    }

    public record Request(@NotBlank String employeeNo,
                          @DecimalMin("1.0") BigDecimal weeklyCapacityHours,
                          @DecimalMin("0") BigDecimal meetingHours,
                          @Min(0) int meetingCount, @Min(0) int requiredMeetings,
                          @Min(0) int conflictCount, @Min(0) int focusBlockCount) {}

    public record Result(String employeeNo, BigDecimal meetingLoadRate, BigDecimal optionalMeetingRate,
                         int loadScore, String riskLevel, List<String> actions) {}
}
