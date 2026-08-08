# ZhuaTech OA — 知华科技 OA 社区源码版

[![License](https://img.shields.io/badge/license-Community_Source_Noncommercial-orange.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21-ED8B00.svg)](backend/pom.xml)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-6DB33F.svg)](backend/pom.xml)
[![Vue](https://img.shields.io/badge/Vue-3-42b883.svg)](frontend/package.json)

ZhuaTech OA（知华 OA）是由 **[知华科技（上海如静知华信息科技有限公司）](https://www.zhuatech.cn/)** 开放源码的前后端分离移动办公系统。项目展示企业公告、移动考勤、请假审批、工作待办和组织通讯录等基础 OA 能力，可用于个人学习 Java OA、Vue OA 和 H5 移动办公系统的设计与实现。

> [!IMPORTANT]
> **使用限制：本工程仅允许个人用于非商业性的学习、研究与技术交流，不得用于任何商业用途。企业内部使用、生产部署、SaaS、项目交付、咨询实施、二次开发后销售或其他直接、间接商业使用，均须事先取得上海如静知华信息科技有限公司的书面商业授权。完整条款请阅读 [LICENSE](LICENSE)。**

> 本项目为“源码开放/社区源码版”，因包含非商业限制，不属于 OSI 定义的开源软件。

> 官方网站：[https://www.zhuatech.cn/](https://www.zhuatech.cn/) · 商业授权、深度开发、私有化部署与定制功能，请联系知华科技。

## 功能特性

- 账号登录：JWT 无状态认证，管理员与员工角色权限
- 移动工作台：待办、审批、公告和考勤状态概览
- 企业公告：公告列表、置顶与管理员发布
- 考勤管理：每日签到、签退、迟到识别和历史记录
- 请假审批：申请、状态跟踪与管理员审批
- 工作待办：创建、完成、删除和优先级管理
- 组织通讯录：部门、员工、职位与一键拨号
- 工程能力：MySQL 迁移、Docker Compose、健康检查与 GitHub Actions

## 技术架构

| 层级 | 技术 |
| --- | --- |
| H5 前端 | Vue 3、Vite、Vant、Pinia、Vue Router、Axios |
| Java 后端 | Java 21、Spring Boot、Spring Security、Spring Data JPA、Flyway |
| 数据库 | MySQL 8.4（测试环境可使用 H2） |
| 部署 | Docker、Docker Compose、Nginx |

后端采用 `cn.zhuatech.oa` 根包名，前后端通过 REST API 解耦。详细设计见 [架构文档](docs/ARCHITECTURE.md) 和 [API 文档](docs/API.md)。

## 5 分钟启动

前置条件：Docker Desktop / Docker Engine 24+ 与 Docker Compose v2。以下方式仅供个人非商业学习环境使用；商业或生产部署前须取得书面授权。

```bash
cp .env.example .env
docker compose up --build -d
```

浏览器访问：<http://localhost:8088>

| 类型 | 账号 | 密码 |
| --- | --- | --- |
| 员工体验 | `demo` | `Demo@2026` |
| 管理员 | `admin` | `ZhuaTech@2026` |

> 体验密码仅用于本地首次启动。部署到公网前必须修改初始化账号、数据库密码及 `JWT_SECRET`。

停止服务：

```bash
docker compose down
```

删除数据库卷会永久清除数据，仅在明确需要重置演示数据时执行：`docker compose down -v`。

## 本地开发

后端需要 JDK 21、Maven 3.9 和 MySQL 8：

```bash
cd backend
mvn spring-boot:run
```

前端需要 Node.js 24 与 npm 11：

```bash
cd frontend
npm install
npm run dev
```

默认开发地址为 <http://localhost:5173>，Vite 会将 `/api` 代理到 <http://localhost:8080>。环境变量说明见 [.env.example](.env.example)。

## 项目结构

```text
zhuatech-oa/
├── backend/        # cn.zhuatech.oa Java 后端
├── frontend/       # Vue 3 移动端 H5
├── deploy/         # 部署相关配置
├── docs/           # 架构、API 与开发文档
├── compose.yaml    # 一键启动编排
└── README.md
```

## 路线图

- [ ] 流程设计器与多级审批
- [ ] 加班、出差、报销、用章和会议室
- [ ] RBAC 菜单与数据权限
- [ ] 文件中心、消息通知与企业微信/钉钉集成
- [ ] PC 管理后台与多租户能力

欢迎按 [贡献指南](CONTRIBUTING.md) 提交 Issue 和 Pull Request。安全问题请不要公开披露，处理方式见 [安全策略](SECURITY.md)。

## 使用许可与商业授权

本项目版权归 **上海如静知华信息科技有限公司** 所有，并按照 [ZhuaTech OA 社区源码许可协议](LICENSE)提供源码：

- 允许自然人用于个人、非商业性的学习、研究、实验和技术交流。
- 允许为上述目的在个人设备上运行和修改，但必须保留许可证、版权与 NOTICE 声明。
- **未经我方事先书面授权，不得用于任何商业用途。** 企业内部使用、生产环境部署、SaaS、托管、项目交付、商业集成、收费或免费商业产品、咨询实施以及可产生直接或间接商业利益的使用，均属于商业使用。
- 商业使用、私有化部署或基于本工程进行商业二次开发，须联系知华科技取得书面商业授权。
- “知华科技”“ZhuaTech”相关名称及标识不因源码可见而授予商标许可。

如果你需要商业授权、OA 系统深度开发、业务流程定制、私有化部署、系统集成或技术支持，请访问 **[知华科技官网](https://www.zhuatech.cn/)** 联系上海如静知华信息科技有限公司。

### 微信咨询

扫描下方任一二维码添加微信，可咨询 ZhuaTech OA 部署、二次开发、功能定制及企业数字化解决方案。

<p align="center">
  <img src="docs/images/zhuatech-wechat-consulting.png" width="280" alt="知华科技微信咨询二维码一｜上海如静知华信息科技有限公司" />
  &nbsp;&nbsp;
  <img src="docs/images/zhuatech-wechat-consulting-2.png" width="280" alt="知华科技微信咨询二维码二｜上海如静知华信息科技有限公司" />
</p>

<p align="center">任选一个二维码扫码添加微信，联系知华科技</p>

### 相关关键词

OA 社区源码、Java OA 学习项目、Spring Boot OA、Vue OA、H5 移动办公、企业考勤系统、请假审批系统、协同办公系统、OA 商业授权、OA 私有化部署。

---

Copyright © 2026 上海如静知华信息科技有限公司（知华科技）

## 本次增强：审批 SLA 分诊

新增审批 SLA 评估接口，可依据已等待时长、流程时限、业务影响、关键申请人和材料完整度计算风险分、P0–P3 优先级、超期小时数及升级动作。它适合接入待办中心或定时任务，让流程负责人先处理真正影响交付的审批，而不是只按提交时间排队。

- 接口：`POST /api/operations/approval-sla`
- 输出：`ON_TRACK`、`AT_RISK`、`BREACHED` 状态与可执行处置建议
- 质量保障：新增鉴权集成测试，覆盖超期 P0 场景

### 审批组合负载路由

在单笔 SLA 判断之上，新增 `POST /api/operations/approval-sla/portfolio`。系统会按风险统一排序待办，结合审批团队的并发容量和当前负载给出承接团队、预计剩余容量与升级建议，帮助流程管理员减少“高风险事项排在队尾”和人工分单不均的问题。移动工作台已提供可直接运行的演示入口。
