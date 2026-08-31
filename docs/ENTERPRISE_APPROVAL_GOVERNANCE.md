# 企业级审批治理升级

本次升级为 OA 增加金额分级审批、职责分离、委托授权校验、证据完整性和审计标签。接口 `POST /api/enterprise/approval-governance/evaluate` 可在流程提交前给出 `APPROVED`、`ESCALATE` 或 `BLOCKED` 决策。

该能力适合费用、采购、合同和行政审批，并为后续接入统一身份、电子签章及流程引擎保留标准 REST 边界。
