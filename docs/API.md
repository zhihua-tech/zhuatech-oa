# ZhuaTech OA REST API

Copyright © 2026 上海如静知华信息科技有限公司。

基础路径为 `/api`。除登录外，请在请求头中传入 `Authorization: Bearer <token>`。成功与失败均使用统一结构：

```json
{"success": true, "message": "操作成功", "data": {}, "timestamp": "2026-07-25T00:00:00Z"}
```

| 方法 | 路径 | 功能 | 权限 |
| --- | --- | --- | --- |
| POST | `/auth/login` | 登录并获取 JWT | 公开 |
| GET | `/auth/me` | 当前用户 | 登录 |
| GET | `/dashboard` | 首页统计 | 登录 |
| GET / POST | `/notices` | 公告列表 / 发布 | 登录 / 管理员 |
| GET | `/attendance/today` | 今日考勤 | 登录 |
| GET | `/attendance` | 最近 31 条考勤 | 登录 |
| POST | `/attendance/check-in` | 签到 | 登录 |
| POST | `/attendance/check-out` | 签退 | 登录 |
| GET / POST | `/leaves` | 我的申请 / 新建申请 | 登录 |
| GET | `/leaves/pending` | 待审批申请 | 管理员 |
| POST | `/leaves/{id}/approve` | 同意或拒绝 | 管理员 |
| GET / POST | `/tasks` | 待办列表 / 新建 | 登录 |
| PATCH / DELETE | `/tasks/{id}` | 完成状态 / 删除 | 本人 |
| GET | `/organization/departments` | 部门列表 | 登录 |
| GET | `/organization/contacts` | 通讯录 | 登录 |

时间使用 ISO 8601 本地时间，如 `2026-07-25T09:00:00`。接口扩展需保持向后兼容；破坏性变更应发布新的主版本。

## 审批 SLA 分诊

`POST /api/operations/approval-sla`：提交流程名称、等待小时、SLA 小时、业务影响分及关键标记，返回风险状态、优先级、超期小时、是否升级和建议动作。

## 工作日专注度

`POST /api/oa/insights/workday-focus`：依据会议时长、审批积压、逾期任务、打断次数和专注时长返回专注分、风险等级与行动建议。
