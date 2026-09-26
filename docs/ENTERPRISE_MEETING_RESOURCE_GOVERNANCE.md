# 会议资源预订治理

`POST /api/enterprise/oa/meeting-resource-governance` 用于会议室提交前的确定性校验。

- 检查时段冲突、参会容量、外部访客备案和涉密场地级别。
- 识别视频设备联调、高上座率备用场地和跨地点协同动作。
- 返回 `RESERVE / REVIEW / BLOCKED`，并附带阻断原因、待办动作和会议时长。

自动化测试覆盖无冲突预订、超员及外客阻断、设备待准备场景。
