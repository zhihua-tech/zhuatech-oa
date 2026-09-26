/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议资源预订前统一校验时间冲突、容量、设备、安全和跨地点协同条件。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class MeetingResourceGovernanceService {
    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (request.endMinute() <= request.startMinute()) blockers.add("结束时间必须晚于开始时间");
        if (request.attendeeCount() > request.roomCapacity()) blockers.add("参会人数超过会议室额定容量");
        if (request.overlapBookings() > 0 && !request.overrideApproved()) blockers.add("会议室在目标时段存在冲突预订");
        if (request.externalGuests() > 0 && !request.visitorListReady()) blockers.add("外部访客名单尚未备案");
        if (request.confidentialMeeting() && !request.secureRoom()) blockers.add("涉密会议必须使用安全级别匹配的会议室");
        if (request.videoConferenceRequired() && !request.videoEquipmentReady()) actions.add("会前完成视频会议设备联调");
        if (request.attendeeCount() * 100 >= request.roomCapacity() * 90) actions.add("座位使用率超过90%，准备备用会议室");
        if (request.crossSiteMeeting() && !request.remoteSiteConfirmed()) actions.add("确认远程场地、时区和现场支持人");
        if (!request.organizerAcknowledgedPolicy()) actions.add("由组织者确认会议室和信息安全规则");
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED
                : !actions.isEmpty() ? Decision.REVIEW : Decision.RESERVE;
        int durationMinutes = Math.max(0, request.endMinute() - request.startMinute());
        return new Assessment(request.bookingNo(), decision, durationMinutes,
                List.copyOf(blockers), List.copyOf(actions));
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public record Request(@NotBlank String bookingNo, @NotBlank String roomCode,
                          @Min(0) int startMinute, @Min(1) int endMinute,
                          @Min(1) int attendeeCount, @Min(1) int roomCapacity,
                          @Min(0) int overlapBookings, boolean overrideApproved,
                          @Min(0) int externalGuests, boolean visitorListReady,
                          boolean confidentialMeeting, boolean secureRoom,
                          boolean videoConferenceRequired, boolean videoEquipmentReady,
                          boolean crossSiteMeeting, boolean remoteSiteConfirmed,
                          boolean organizerAcknowledgedPolicy) {}

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public record Assessment(String bookingNo, Decision decision, int durationMinutes,
                             List<String> blockers, List<String> actions) {}

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public enum Decision { RESERVE, REVIEW, BLOCKED }
}
