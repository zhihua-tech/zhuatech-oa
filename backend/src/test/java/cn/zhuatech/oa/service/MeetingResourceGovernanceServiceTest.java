/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
class MeetingResourceGovernanceServiceTest {
    private final MeetingResourceGovernanceService service = new MeetingResourceGovernanceService();

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void reservesConflictFreePreparedRoom() {
        var result = service.assess(request(0, 8, 20, false, true, true));
        assertThat(result.decision()).isEqualTo(MeetingResourceGovernanceService.Decision.RESERVE);
        assertThat(result.durationMinutes()).isEqualTo(60);
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void blocksCapacityConflictAndUnregisteredVisitors() {
        var result = service.assess(request(1, 25, 20, false, false, true));
        assertThat(result.decision()).isEqualTo(MeetingResourceGovernanceService.Decision.BLOCKED);
        assertThat(result.blockers()).hasSizeGreaterThanOrEqualTo(3);
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void reviewsMeetingNeedingEquipmentPreparation() {
        var result = service.assess(request(0, 8, 20, true, true, false));
        assertThat(result.decision()).isEqualTo(MeetingResourceGovernanceService.Decision.REVIEW);
        assertThat(result.actions()).anyMatch(item -> item.contains("设备联调"));
    }

    private MeetingResourceGovernanceService.Request request(int overlaps, int attendees, int capacity,
                                                               boolean videoRequired, boolean visitorsReady,
                                                               boolean videoReady) {
        return new MeetingResourceGovernanceService.Request("MTG-100", "ROOM-A", 540, 600,
                attendees, capacity, overlaps, false, 2, visitorsReady,
                false, true, videoRequired, videoReady, false, true, true);
    }
}
