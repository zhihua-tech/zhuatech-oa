/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class SealUseGovernanceService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Assessment assess(Request r) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!r.finalDocumentApproved()) blockers.add("用印文件尚未完成定稿审批");
        if (!r.authorizationValid()) blockers.add("印章授权无效或已过期");
        if (r.requesterId().equals(r.custodianId())) blockers.add("申请人与印章保管人必须职责分离");
        if (!r.recipientScopeConfirmed()) blockers.add("用印对象及份数范围未确认");
        if (r.externalRecipient() && !r.legalReviewed()) blockers.add("对外用印必须完成法务复核");
        if (!r.evidenceAttached()) actions.add("补充审批单、定稿文件及用印影像证据");
        if (!r.postUseArchivePlanned()) actions.add("配置用印完成后的归档任务");
        if (r.physicalSeal() && !r.sealReturnPlanned()) blockers.add("实物印章未安排归还确认");
        if (!r.physicalSeal() && !r.electronicSealTamperProof()) blockers.add("电子印章缺少防篡改校验");
        RiskLevel risk = r.externalRecipient() || r.useCount() > 10 ? RiskLevel.HIGH : RiskLevel.NORMAL;
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED : !actions.isEmpty() ? Decision.REVIEW : Decision.APPROVE;
        String route = risk == RiskLevel.HIGH ? "部门负责人→法务→印章管理员" : "部门负责人→印章管理员";
        return new Assessment(r.requestId(), decision, risk, route, List.copyOf(blockers), List.copyOf(actions));
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotBlank String requestId, @NotBlank String sealType,
                          @NotBlank String requesterId, @NotBlank String custodianId,
                          @Min(1) int useCount, boolean finalDocumentApproved, boolean authorizationValid,
                          boolean legalReviewed, boolean recipientScopeConfirmed, boolean externalRecipient,
                          boolean evidenceAttached, boolean postUseArchivePlanned, boolean physicalSeal,
                          boolean sealReturnPlanned, boolean electronicSealTamperProof) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Assessment(String requestId, Decision decision, RiskLevel riskLevel, String approvalRoute,
                             List<String> blockers, List<String> actions) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Decision { APPROVE, REVIEW, BLOCKED }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum RiskLevel { NORMAL, HIGH }
}
