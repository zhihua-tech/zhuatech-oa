/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.oa.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfficialDocumentPublicationGovernanceService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.finalVersionFrozen()) blockers.add("待发布公文不是已冻结的最终版本");
        if (!request.classificationAssigned()) blockers.add("密级与公开范围尚未标定");
        if (!request.recipientScopeReviewed()) blockers.add("收文组织和人员范围未复核");
        if (!request.legalApproved()) blockers.add("法务或制度合规审查未完成");
        if (!request.sealOrSignatureVerified()) blockers.add("印章或电子签名未验证");
        if (!request.documentNumberIssued()) blockers.add("正式发文字号尚未签发");
        if (!request.attachmentsScanned()) blockers.add("正文或附件安全扫描未通过");
        if (!request.sensitiveDataReviewed()) blockers.add("个人信息及敏感数据未完成审查");
        if (!request.retentionScheduleAssigned()) blockers.add("归档分类和保管期限未配置");
        if (!request.businessOwnerApproved()) blockers.add("公文业务负责人尚未批准");
        if (!request.publisherApproverSeparated()) blockers.add("公文编制、审批与发布未职责分离");
        if (!request.auditReady()) blockers.add("版本、会签、签发及发布证据链不完整");
        if (!request.acknowledgementPlanReady()) actions.add("配置必读回执、催办和未读升级规则");
        if (!request.withdrawalPlanReady()) actions.add("准备误发撤回、更正及替代版本方案");
        if (!request.archivePackageReady()) actions.add("生成正文、附件、签名和审批归档包");
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED : !actions.isEmpty() ? Decision.REVIEW : Decision.PUBLISH;
        return new Assessment(request.documentNo(), request.versionNo(), request.pageCount(), decision,
                List.copyOf(blockers), List.copyOf(actions));
    }

    public record Request(@NotBlank String documentNo, @NotBlank String versionNo, @Positive int pageCount,
                          boolean finalVersionFrozen, boolean classificationAssigned,
                          boolean recipientScopeReviewed, boolean legalApproved,
                          boolean sealOrSignatureVerified, boolean documentNumberIssued,
                          boolean attachmentsScanned, boolean sensitiveDataReviewed,
                          boolean retentionScheduleAssigned, boolean businessOwnerApproved,
                          boolean publisherApproverSeparated, boolean auditReady,
                          boolean acknowledgementPlanReady, boolean withdrawalPlanReady,
                          boolean archivePackageReady) {}
    public record Assessment(String documentNo, String versionNo, int pageCount, Decision decision,
                             List<String> blockers, List<String> actions) {}
    public enum Decision { PUBLISH, REVIEW, BLOCKED }
}
