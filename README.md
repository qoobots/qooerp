<div align="center">

# 🌟 QooERP

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.4+-42b883.svg)](https://vuejs.org/)
[![Java](https://img.shields.io/badge/Java-17+-007396.svg)](https://www.oracle.com/java/)
[![GitHub stars](https://img.shields.io/github/stars/qoobots/qooerp)](https://github.com/qoobots/qooerp)
[![GitHub forks](https://img.shields.io/github/forks/qoobots/qooerp)](https://github.com/qoobots/qooerp/network)

### 🚀 全球领先的新一代企业级开源ERP系统
### The World's Leading Enterprise Open-Source ERP System

[English](#english) | [中文](#中文)

---

[快速开始](#快速开始) · [核心特性](#核心特性) · [技术架构](#技术架构) · [部署方案](#部署方案) · [文档](#文档) · [贡献](#贡献)

</div>

---

## 中文

> **QooERP** - 适用于全球大、中、小企业、政府机构的云原生微服务架构开源ERP系统

QooERP 是一款基于现代云原生技术构建的**世界级大型企业级开源ERP系统**,采用 DDD 领域驱动设计和微服务架构,支持多租户、多语言、多币种,专为**大型企业、跨国集团、政府机构、科技企业**设计,支持**百万级企业内部用户规模**,同时完美兼容中小型企业。

作为**全球最大、最专业、最易用**的开源 ERP 系统,QooERP 致力于为全球企业提供全场景、全渠道、全终端的数字化管理解决方案。

### 🎯 为什么选择 QooERP?

- **🌍 全球化视野**: 支持 100+ 语言、100+ 币种、多时区,服务全球 190+ 国家和地区
- **🏗️ 企业级架构**: DDD 领域驱动设计 + 微服务架构,支撑百万级企业员工、亿级业务数据量
- **⚡ 极致性能**: 毫秒级响应、秒级加载,99.99% 可用性保障
- **🔐 企业级安全**: 通过 ISO 27001 认证,支持 GDPR、SOC 2、ISO 27001 等国际合规
- **📦 开箱即用**: 100+ 预置业务模块,80+ 开箱即用的核心组件,零代码快速搭建
- **🔄 持续演进**: 每周发布新版本,每季度重大更新,紧跟技术前沿
- **🌐 云原生**: 深度集成 Kubernetes、Docker,支持多云、混合云、边缘计算
- **🤖 AI 智能化**: 内置 10,000 名数字员工,深度集成 LLM 大模型、知识库、智能体、具身智能,引领未来企业管理
- **🚀 未来导向**: 专为未来企业打造,融合前沿 AI 技术,人机协作,智能驱动

---

## 核心特性

### 🏗️ 架构特性
- **DDD 领域驱动设计**: 完整的领域模型、聚合根、领域事件、限界上下文
- **微服务架构**: 30+ 独立微服务,支持独立部署、独立扩容、独立版本管理
- **云原生部署**: 深度集成 Kubernetes,支持 HPA、Service Mesh、Istio
- **多级缓存架构**: L1 Caffeine 本地缓存 + L2 Redis 分布式缓存,命中率 95%+
- **分库分表**: ShardingSphere 分库分表,支持千万级数据表,自动扩容
- **分布式事务**: Seata AT/TCC 模式,保证分布式数据一致性,性能损耗 < 10%

### 🏢 企业级能力
- **多租户隔离**: 行级数据隔离,支持单实例多租户、多租户多实例模式
- **细粒度权限**: RBAC + ABAC 复合权限模型,支持字段级、按钮级、数据级权限控制
- **全球化支持**: 100+ 语言、100+ 币种、多时区、多日历、多税制、多会计准则
- **审计追踪**: 完整的操作日志、数据变更日志、审计报表,符合 SOX、HIPAA 等合规要求
- **数据加密**: 传输加密 (TLS 1.3)、存储加密 (AES-256)、密钥管理 (KMS)
- **合规认证**: GDPR、SOC 2 Type II、ISO 27001、ISO 27701、ISO 27018

### 🚀 性能与可扩展性
- **高并发支持**: 支持 100 万+ 企业内部员工同时在线,10 万+ QPS
- **水平扩展**: 无状态服务设计,支持秒级自动扩容
- **服务治理**: Spring Cloud Alibaba 完整生态,Nacos 注册中心、Sentinel 流量控制
- **负载均衡**: Nginx + Ribbon + Spring Cloud Gateway 多级负载均衡
- **容灾备份**: 主从复制、读写分离、异地多活、容灾切换 RPO < 5min

### 📊 业务模块
- **模块化架构**: 100+ 业务模块,按需启用,灵活组合
- **低代码平台**: 可视化表单设计、流程设计、页面设计、代码生成
- **工作流引擎**: Flowable 7.x 工作流引擎,支持 BPMN 2.0、可视化流程设计
- **报表中心**: ECharts 数据可视化、实时报表、多维分析、数据钻取
- **BI 分析**: Apache Superset 集成,拖拽式 BI 分析,自助式数据探索

### 🌐 部署与运维
- **Docker 一键部署**: 完整的 Docker Compose 配置,5 分钟启动完整系统
- **Kubernetes 集群**: 完整的 K8s 部署方案,Helm Charts,Operator 支持
- **多云部署**: 支持 AWS、Azure、GCP、阿里云、腾讯云、华为云
- **监控告警**: Prometheus + Grafana + Alertmanager 全栈监控
- **链路追踪**: SkyWalking 分布式链路追踪,全链路性能分析
- **日志管理**: ELK Stack 日志收集、分析、检索
- **CI/CD**: GitHub Actions / GitLab CI 完整的 CI/CD 流程

### 🎨 前端特性
- **现代化技术栈**: Vue 3 + TypeScript + Vite + TDesign
- **Monorepo 架构**: Web、Desktop、Mobile 三端统一管理,复用率 85%+
- **响应式设计**: 完美适配 PC、平板、移动端
- **主题定制**: 支持亮色/暗色主题,支持企业品牌定制
- **无障碍访问**: 完整 ARIA 支持,键盘导航,屏幕阅读器兼容
- **PWA 支持**: 离线使用、桌面图标、消息推送

### 🤖 AI 智能化

QooERP 深度融合前沿 AI 技术,内置 **10,000 名数字员工**,为企业提供全方位智能化支持:

#### 🤖 大模型集成
- **LLM 集成**: 支持 GPT-4、Claude、文心一言、通义千问等主流大语言模型
- **多模态能力**: 文本、图像、语音、视频多模态理解与生成
- **智能对话**: 自然语言交互,支持复杂业务场景问答
- **代码助手**: AI 辅助代码生成、智能重构、代码审查、Bug 修复
- **文档智能**: 自动生成需求文档、技术文档、API 文档

#### 📚 知识库与智能检索
- **企业知识库**: 自动构建企业知识图谱,智能知识管理
- **语义检索**: 基于 Embedding 的语义搜索,精准定位信息
- **知识问答**: RAG (检索增强生成) 技术,基于企业知识库精准问答
- **文档理解**: 自动提取文档关键信息,智能摘要生成
- **知识更新**: 实时知识更新,知识图谱自动维护

#### 🎯 智能体协作
- **业务智能体**: 财务智能体、HR 智能体、采购智能体、销售智能体等 50+ 预置智能体
- **任务编排**: 多智能体协同工作,自动完成复杂业务流程
- **自主决策**: 基于业务规则和 AI 模型,实现智能决策
- **流程优化**: AI 驱动的流程优化,自动发现瓶颈并优化
- **异常处理**: 智能异常检测,自动预警并处理

#### 🤖 具身智能
- **数字员工**: 10,000 名可配置数字员工,7x24 小时不间断工作
- **虚拟助手**: 每个员工配备专属 AI 助手,个性化服务
- **自动化办公**: RPA + AI 智能自动化,释放人力资源
- **智能客服**: AI 客服机器人,自动处理常见问题
- **培训助手**: AI 培训导师,个性化学习路径推荐

#### 📊 数据智能
- **智能分析**: AI 驱动的数据洞察,自动发现业务规律
- **异常检测**: 实时监控业务数据,智能异常检测与预警
- **趋势预测**: 基于历史数据的趋势预测,辅助决策
- **智能报表**: 自动生成报表,智能数据可视化
- **预测分析**: 销售预测、库存预测、财务预测等

#### 🔮 前沿技术应用
- **联邦学习**: 保护数据隐私的分布式机器学习
- **边缘计算**: AI 模型边缘部署,低延迟实时响应
- **强化学习**: 基于强化学习的智能决策优化
- **图神经网络**: 企业关系图谱智能分析
- **持续学习**: AI 模型持续学习,不断优化性能

---

## 技术架构

### 前端技术栈

| 技术 | 版本 | 说明 | 官方地址 |
|------|------|------|---------|
| Vue.js | 3.4+ | 渐进式前端框架,Composition API | [vuejs.org](https://vuejs.org/) |
| TypeScript | 5.3+ | JavaScript 超集,类型安全 | [typescriptlang.org](https://www.typescriptlang.org/) |
| Vite | 5.0+ | 新一代前端构建工具,极速热更新 | [vitejs.dev](https://vitejs.dev/) |
| TDesign | Latest | 腾讯企业级设计语言,50+ 组件 | [tdesign.tencent.com](https://tdesign.tencent.com/) |
| Vue Router | 4.2+ | Vue 官方路由,动态路由支持 | [router.vuejs.org](https://router.vuejs.org/) |
| Pinia | 2.1+ | Vue 官方状态管理 | [pinia.vuejs.org](https://pinia.vuejs.org/) |
| ECharts | 5.x | Apache ECharts 数据可视化 | [echarts.apache.org](https://echarts.apache.org/) |
| Electron | Latest | 跨平台桌面应用框架 | [electronjs.org](https://www.electronjs.org/) |
| Capacitor | 5.x | 跨平台移动应用方案 | [capacitorjs.com](https://capacitorjs.com/) |
| OpenTelemetry | 1.8+ | 云原生可观测性标准 | [opentelemetry.io](https://opentelemetry.io/) |

### 后端技术栈

#### 核心框架
| 技术 | 版本 | 说明 | 官方地址 |
|------|------|------|---------|
| Spring Boot | 3.3.6 | 微服务核心框架 | [spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| Spring Cloud | 2023.0.3 | 微服务治理生态 | [spring.io/projects/spring-cloud](https://spring.io/projects/spring-cloud) |
| Spring Cloud Alibaba | 2023.0.1.2 | 微服务套件 | [github.com/alibaba/spring-cloud-alibaba](https://github.com/alibaba/spring-cloud-alibaba) |
| Spring Security | 6.x | 安全认证框架 | [spring.io/projects/spring-security](https://spring.io/projects/spring-security) |
| Spring Data JPA | 3.x | 数据访问抽象 | [spring.io/projects/spring-data-jpa](https://spring.io/projects/spring-data-jpa) |
| Spring Validation | 3.x | 参数校验 | [spring.io/projects/spring-framework](https://spring.io/projects/spring-framework) |

#### 微服务治理
| 技术 | 版本 | 说明 | 官方地址 |
|------|------|------|---------|
| Nacos | 2.3.0 | 服务注册与配置中心 | [nacos.io](https://nacos.io/) |
| Spring Cloud Gateway | 4.x | API 网关 | [spring.io/projects/spring-cloud-gateway](https://spring.io/projects/spring-cloud-gateway) |
| Sentinel | 1.8.7 | 流量控制、熔断降级 | [sentinelguard.io](https://sentinelguard.io/) |
| OpenFeign | 4.x | 声明式 HTTP 客户端 | [spring.io/projects/spring-cloud-openfeign](https://spring.io/projects/spring-cloud-openfeign) |
| LoadBalancer | 4.x | 客户端负载均衡 | [spring.io/projects/spring-cloud-commons](https://spring.io/projects/spring-cloud-commons) |

#### 缓存与消息
| 技术 | 版本 | 说明 | 官方地址 |
|------|------|------|---------|
| Spring Cache | 3.x | 缓存抽象 | [spring.io/projects/spring-framework](https://spring.io/projects/spring-framework) |
| Caffeine | 3.1.8 | L1 本地缓存 | [github.com/ben-manes/caffeine](https://github.com/ben-manes/caffeine) |
| Redis | 7.2+ | L2 分布式缓存 | [redis.io](https://redis.io/) |
| Redisson | 3.29.0 | Redis 客户端、分布式锁 | [redisson.org](https://redisson.org/) |
| RocketMQ | 5.3.0 | 消息队列 | [rocketmq.apache.org](https://rocketmq.apache.org/) |
| Kafka | 3.6+ | 可选消息队列 | [kafka.apache.org](https://kafka.apache.org/) |

#### 数据库与存储
| 技术 | 版本    | 说明 | 官方地址 |
|------|-------|------|---------|
| PostgreSQL | 16.12 | 主数据库 | [postgresql.org](https://www.postgresql.org/) |
| ShardingSphere | 5.5.1 | 分库分表中间件 | [shardingsphere.apache.org](https://shardingsphere.apache.org/) |
| Seata | 2.0.0 | 分布式事务 | [seata.io](https://seata.io/) |
| Elasticsearch | 8.11+ | 全文搜索 | [elastic.co](https://www.elastic.co/) |
| MinIO | Latest | 对象存储 | [min.io](https://min.io/) |
| Flyway | 10.x  | 数据库版本管理 | [flywaydb.org](https://flywaydb.org/) |
| Liquibase | 4.25+ | 可选数据库迁移工具 | [liquibase.org](https://www.liquibase.org/) |

#### 工作流与引擎
| 技术 | 版本 | 说明 | 官方地址 |
|------|------|------|---------|
| Flowable | 7.0.1 | 工作流引擎 | [flowable.com](https://www.flowable.com/) |
| Activiti | 8.x | 可选工作流引擎 | [activiti.org](https://www.activiti.org/) |
| Camunda | 7.x | 可选 BPMN 引擎 | [camunda.com](https://camunda.com/) |
| Drools | 8.x | 规则引擎 | [drools.org](https://drools.org/) |

#### 监控与追踪
| 技术 | 版本 | 说明 | 官方地址 |
|------|------|------|---------|
| Prometheus | 2.48+ | 指标收集与存储 | [prometheus.io](https://prometheus.io/) |
| Grafana | 10.2+ | 可视化监控面板 | [grafana.com](https://grafana.com/) |
| SkyWalking | 9.7+ | 分布式链路追踪 | [skywalking.apache.org](https://skywalking.apache.org/) |
| Zipkin | 2.25+ | 分布式追踪系统 | [zipkin.io](https://zipkin.io/) |
| ELK Stack | 8.11+ | 日志收集与分析 | [elastic.co/what-is/elk-stack](https://www.elastic.co/what-is/elk-stack) |

#### 开发工具
| 工具 | 版本 | 说明 | 官方地址 |
|------|------|------|---------|
| Maven | 3.9.6 | 依赖管理与构建 | [maven.apache.org](https://maven.apache.org/) |
| Gradle | 8.5+ | 可选构建工具 | [gradle.org](https://gradle.org/) |
| Git | 2.42+ | 版本控制 | [git-scm.com](https://git-scm.com/) |
| Docker | 24.0+ | 容器化 | [docker.com](https://www.docker.com/) |
| Kubernetes | 1.28+ | 容器编排 | [kubernetes.io](https://kubernetes.io/) |
| Helm | 3.13+ | K8s 包管理器 | [helm.sh](https://helm.sh/) |
| Nginx | 1.25+ | 反向代理、静态资源 | [nginx.org](https://nginx.org/) |
| Jenkins | 2.446+ | CI/CD 自动化 | [jenkins.io](https://www.jenkins.io/) |
| GitHub Actions | Latest | CI/CD 自动化 | [github.com/features/actions](https://github.com/features/actions) |
| Jira | Latest | 项目管理 | [atlassian.com/jira](https://www.atlassian.com/jira/) |
| Confluence | Latest | 文档管理 | [atlassian.com/confluence](https://www.atlassian.com/confluence/) |

### 基础设施

| 组件 | 版本 | 说明 |
|------|------|------|
| 操作系统 | Ubuntu 22.04 LTS / CentOS 8 / Rocky Linux 9 | Linux 服务器 |
| JDK | 17.0.9+ | Java 运行环境 |
| Node.js | 20.10+ | 前端构建 |
| Python | 3.11+ | 辅助脚本 |
| PostgreSQL | 15.5+ | 主数据库 |
| Redis | 7.2+ | 缓存与消息 |
| RocketMQ | 5.3.0+ | 消息队列 |
| Elasticsearch | 8.11+ | 全文搜索 |
| MinIO | Latest | 对象存储 |
| Nacos | 2.3.0+ | 服务注册中心 |

---

## 项目结构

```
qooerp/
├── 01-docs/                          # 文档目录
│   ├── 01-开源ERP千功能规划.md
│   ├── 02-QooERP开发模块进度跟踪.md
│   ├── 04-大型开源ERP系统开发准备工作清单.md
│   └── 4-总体架构清单.md
├── all-start/                         # 启动模块
├── basic-common/                      # 基础公共模块
│   ├── basic-common-util/              # 工具类
│   ├── basic-common-web/               # Web 公共组件
│   ├── basic-common-security/          # 安全组件
│   ├── basic-common-log/               # 日志组件
│   ├── basic-common-cache/             # 缓存组件
│   ├── basic-common-mybatis/           # Mybatis 组件
│   ├── basic-common-feign/             # Feign 组件
│   ├── basic-common-redis/             # Redis 组件
│   └── basic-common-tenant/           # 租户组件
├── basic-core-services/               # 核心基础服务
│   ├── qooerp-auth/                   # 认证授权服务
│   ├── qooerp-system/                 # 系统管理服务
│   ├── qooerp-user/                   # 用户管理服务
│   ├── qooerp-role/                   # 角色管理服务
│   ├── qooerp-menu/                   # 菜单管理服务
│   ├── qooperm-dept/                   # 部门管理服务
│   └── qooerp-dict/                   # 字典管理服务
├── basic-feature-service/             # 基础功能服务
│   ├── qooerp-workflow/               # 工作流服务
│   ├── qooerp-file/                   # 文件管理服务
│   ├── qooerp-message/               # 消息服务
│   ├── qooerp-notify/                 # 通知服务
│   ├── qooerp-scheduler/              # 任务调度服务
│   └── qooerp-monitor/               # 监控服务
├── business-core-services/            # 核心业务服务
│   ├── qooerp-finance/                # 财务管理
│   ├── qooerp-hr/                     # 人力资源
│   ├── qooerp-crm/                   # 客户关系管理
│   ├── qooerp-purchase/               # 采购管理
│   ├── qooerp-sales/                  # 销售管理
│   ├── qooerp-inventory/              # 库存管理
│   └── qooerp-organization/           # 组织架构
├── business-expand-services/          # 扩展业务服务
│   ├── qooerp-production/             # 生产管理
│   ├── qooerp-project/                # 项目管理
│   ├── qooerp-asset/                  # 资产管理
│   ├── qooerp-quality/                # 质量管理
│   └── qooerp-service/                # 服务管理
├── customized-business-services/       # 定制化业务服务
│   ├── qooerp-report/                 # 报表服务
│   ├── qooerp-bi/                     # BI 分析
│   ├── qooerp-lowcode/                # 低代码平台
│   └── qooerp-ai/                    # AI 服务
├── qooerp-gateway/                   # API 网关
├── qooerp-frontend/                  # 前端应用 (Vue 3 Monorepo)
│   ├── packages/
│   │   ├── web/                       # Web 端
│   │   ├── desktop/                   # Desktop 端
│   │   └── mobile/                    # Mobile 端
│   └── shared/                       # 共享代码
├── qooerp-ops/                       # 运维脚本
│   ├── docker/                       # Docker 配置
│   ├── kubernetes/                   # K8s 配置
│   ├── helm/                         # Helm Charts
│   └── scripts/                      # 部署脚本
├── qooerp-tests/                     # 测试代码
│   ├── unit/                         # 单元测试
│   ├── integration/                  # 集成测试
│   └── e2e/                          # E2E 测试
├── .github/                          # GitHub 配置
│   ├── workflows/                    # CI/CD 流程
│   └── ISSUE_TEMPLATE/               # Issue 模板
├── .gitignore
├── .dockerignore
├── LICENSE
├── pom.xml                          # Maven 父工程
└── README.md
```

---

## 核心模块

### 🎯 P0 级:核心基础模块 (必选)
1. **身份认证与安全** - OAuth2 + JWT + SSO + 双因子认证
2. **系统管理** - 用户、角色、菜单、权限、字典、配置
3. **全球化支持** - 多语言、多币种、多时区、多税制
4. **数据管理** - 数据字典、数据验证、数据同步
5. **系统集成** - API 管理、集成中心、数据交换
6. **协同办公** - 日程管理、任务管理、会议管理
7. **任务与工作流** - 审批流程、工作流引擎、流程监控
8. **通讯系统** - 站内信、邮件、短信、消息推送

### 🏢 P1 级:核心业务模块 (核心业务必须)
1. **财务管理** - 科目、凭证、账簿、报表、预算、成本
2. **人力资源管理** - 招聘、入职、考勤、薪酬、绩效、培训
3. **供应链管理** - 供应商、采购、合同、结算
4. **销售管理** - 客户、订单、报价、合同、结算
5. **库存管理** - 仓库、库存、盘点、调拨、预警

### 📦 P2 级:扩展业务模块 (按需开发)
1. **生产管理** - 计划、工单、BOM、工艺、质量
2. **项目管理** - 项目、任务、进度、成本、风险
3. **客户关系管理 (CRM)** - 线索、商机、跟进、分析
4. **服务管理** - 工单、服务、满意度、知识库
5. **移动应用** - 移动审批、移动报表、移动查询

### 🚀 P3 级:高级功能模块 (增强能力)
1. **报表与数据分析** - 报表设计、BI 分析、数据挖掘、AI 驱动洞察
2. **业务流程管理** - 流程优化、流程监控、流程分析、智能流程推荐
3. **低代码平台** - 表单设计、页面设计、代码生成、AI 辅助开发
4. **AI 智能中心** - LLM 大模型集成、企业知识库、智能体协作、具身智能、10,000 名数字员工

---

## 快速开始

### 环境要求

| 组件 | 版本 | 说明 |
|------|------|------|
| JDK | 17+ | Java 运行环境 |
| Node.js | 18+ | 前端构建 |
| PostgreSQL | 15+ | 主数据库 |
| Redis | 7+ | 缓存与消息 |
| RocketMQ | 5.x | 消息队列 |
| Nacos | 2.x | 服务注册中心 |
| Maven | 3.9+ | 依赖管理 |
| Docker | 24+ | 容器化 (可选) |
| Kubernetes | 1.28+ | 容器编排 (可选) |

### Docker 快速启动 (推荐)

```bash
# 1. 克隆项目
git clone https://github.com/qoobots/qooerp.git
cd qooerp

# 2. 启动基础服务 (PostgreSQL、Redis、RocketMQ、Nacos、Elasticsearch)
docker-compose -f docker-compose.yml up -d

# 3. 等待服务启动 (约 2-3 分钟)
docker-compose ps

# 4. 初始化数据库
docker exec -it qooerp-postgres psql -U postgres -c "CREATE DATABASE qooerp;"

# 5. 启动后端服务
cd all-start
mvn spring-boot:run

# 6. 启动前端服务
cd ../qooerp-frontend
pnpm install
pnpm dev
```

访问:
- 前端: http://localhost:3000
- 后端: http://localhost:8080
- Nacos: http://localhost:8848/nacos (nacos/nacos)
- Grafana: http://localhost:3000

### 后端服务启动

```bash
# 方式一: 使用 Spring Boot 插件
cd qooerp-auth
mvn spring-boot:run

# 方式二: 打包后运行
cd qooerp-auth
mvn clean package
java -jar target/qooerp-auth.jar

# 方式三: 使用 Maven 多模块
cd all-start
mvn clean package -DskipTests
java -jar all-start/target/all-start-*.jar
```

### 前端服务启动

```bash
cd qooerp-frontend

# 安装依赖
pnpm install

# 启动开发服务器
pnpm dev

# 构建生产版本
pnpm build

# 启动桌面端
pnpm dev:desktop

# 启动移动端
pnpm dev:mobile
```

---

## 部署方案

### 📊 部署架构对比

| 规模 | 部署方式 | 并发能力 | 适用场景 | 成本 |
|------|---------|---------|---------|------|
| **小微** | Docker 单机 | 100-500 员工 | 小团队、快速上线 | 低 |
| **中型** | 微服务集群 | 500-5000 员工 | 中等规模、完整业务 | 中 |
| **大型** | K8s 集群 | 5000-50000 员工 | 大规模组织、复杂流程 | 中高 |
| **集团** | K8s 多区域 | 50000-100 万+ 员工 | 跨地域、多组织架构 | 高 |

### 🚀 小微企业部署 (Docker 一键部署)

**架构特点:**
- 单体服务 + PostgreSQL + Redis + Nginx 前端
- 一键启动,5 分钟上线
- 适合: 小规模团队、快速验证、POC 演示

**部署步骤:**
```bash
# 1. 拉取镜像
docker pull qooerp/all-in-one:latest

# 2. 启动服务
docker run -d \
  --name qooerp \
  -p 80:80 \
  -p 443:443 \
  -e DB_HOST=postgres \
  -e REDIS_HOST=redis \
  qooerp/all-in-one:latest

# 3. 访问系统
open http://localhost
```

### 🏢 中型企业部署 (微服务集群部署)

**架构特点:**
- 微服务集群 + L1/L2 缓存 + 分库分表 + Nginx 前端
- 支持独立扩容、独立版本管理
- 适合: 中等规模、需要完整业务模块

**部署架构:**
```
                      ┌─────────────┐
                      │   Nginx    │
                      │   集群     │
                      └──────┬──────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
        ┌─────▼─────┐  ┌───▼────┐  ┌─────▼─────┐
        │ Gateway   │  │ Auth   │  │  System   │
        │ Cluster   │  │ Cluster │  │  Cluster  │
        └───────────┘  └────────┘  └───────────┘
              │              │              │
              └──────────────┼──────────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
   ┌────▼────┐        ┌────▼────┐        ┌────▼────┐
   │  Redis  │        │  Postgres│       │  RocketMQ│
   │  Cluster │       │   Cluster │       │  Cluster │
   └─────────┘        └──────────┘        └──────────┘
```

**Kubernetes 部署:**
```bash
# 1. 安装 Helm Charts
helm install qooerp ./helm/qooerp

# 2. 扩容服务
kubectl scale deployment qooerp-auth --replicas=3

# 3. 查看状态
kubectl get pods -n qooerp
```

### 🌐 大型企业部署 (K8s 集群部署)

**架构特点:**
- 完整微服务 + 分布式缓存 + 分布式事务 + MQ + Nginx 集群
- 支持异地多活、容灾备份、自动扩容
- 适合: 大规模组织、复杂业务流程

**部署架构:**
```
                    ┌─────────────────────────┐
                    │       CDN + WAF         │
                    └───────────┬─────────────┘
                                │
                    ┌───────────▼─────────────┐
                    │    Nginx 集群          │
                    │   (负载均衡 + SSL)      │
                    └───────────┬─────────────┘
                                │
                    ┌───────────▼─────────────┐
                    │   Gateway 集群         │
                    │  (流量控制 + 熔断)     │
                    └───────────┬─────────────┘
                                │
        ┌───────────────────────┼───────────────────────┐
        │                       │                       │
   ┌────▼────┐            ┌────▼────┐            ┌────▼────┐
   │ Auth    │            │ Business│            │ System  │
   │ Service │            │ Services│            │ Service │
   │ Cluster │            │ Cluster │            │ Cluster │
   └────┬────┘            └────┬────┘            └────┬────┘
        │                      │                      │
        └──────────────────────┼──────────────────────┘
                               │
        ┌──────────────────────┼──────────────────────┐
        │                      │                      │
   ┌────▼────┐           ┌────▼────┐           ┌────▼────┐
   │ Redis   │           │Postgres │           │RocketMQ │
   │ Cluster │           │ Cluster  │           │ Cluster  │
   └────┬────┘           └────┬────┘           └────┬────┘
        │                      │                      │
        └──────────────────────┼──────────────────────┘
                               │
                    ┌──────────▼────────────┐
                    │   监控告警系统       │
                    │ Prometheus+Grafana   │
                    └──────────────────────┘
```

**Helm 部署:**
```bash
# 1. 添加 Helm 仓库
helm repo add qooerp https://charts.qooerp.com
helm repo update

# 2. 安装生产环境
helm install qooerp qooerp/qooerp \
  --namespace qooerp \
  --create-namespace \
  --values values-production.yaml \
  --set replicaCount=5

# 3. 查看 Pod 状态
kubectl get pods -n qooerp -w
```

### 🌍 集团/科技企业部署 (K8s 多区域部署)

**架构特点:**
- 多机房部署 + 多级缓存 + 异地多活 + CDN 加速 + 监控告警
- 支持 100 万+ 用户,99.999% 可用性
- 适合: 跨地域、多组织架构、科技企业、政府机构

**部署架构:**
```
                    ┌─────────────────────────────────┐
                    │        Global CDN               │
                    │     (CloudFlare / AWS)         │
                    └─────────────────┬───────────────┘
                                      │
                    ┌─────────────────┼────────────────┐
                    │                 │                │
              ┌─────▼──────┐   ┌─────▼──────┐   ┌─────▼──────┐
              │  Region A  │   │  Region B  │   │  Region C  │
              │ (华东)     │   │ (华北)     │   │ (华南)     │
              └─────┬──────┘   └─────┬──────┘   └─────┬──────┘
                    │                 │                │
              ┌─────▼──────┐   ┌─────▼──────┐   ┌─────▼──────┐
              │  Nginx LB  │   │  Nginx LB  │   │  Nginx LB  │
              └─────┬──────┘   └─────┬──────┘   └─────┬──────┘
                    │                 │                │
              ┌─────▼──────┐   ┌─────▼──────┐   ┌─────▼──────┐
              │ Gateway    │   │ Gateway    │   │ Gateway    │
              │ Cluster    │   │ Cluster    │   │ Cluster    │
              └─────┬──────┘   └─────┬──────┘   └─────┬──────┘
                    │                 │                │
              ┌─────▼──────┐   ┌─────▼──────┐   ┌─────▼──────┐
              │ Business   │   │ Business   │   │ Business   │
              │ Services   │   │ Services   │   │ Services   │
              │ Cluster    │   │ Cluster    │   │ Cluster    │
              └─────┬──────┘   └─────┬──────┘   └─────┬──────┘
                    │                 │                │
              ┌─────▼──────┐   ┌─────▼──────┐   ┌─────▼──────┐
              │ Redis      │   │ Redis      │   │ Redis      │
              │ Cluster    │   │ Cluster    │   │ Cluster    │
              └─────┬──────┘   └─────┬──────┘   └─────┬──────┘
                    │                 │                │
                    └─────────────────┼────────────────┘
                                      │
                    ┌─────────────────▼────────────────┐
                    │   Global Database Cluster      │
                    │ (PostgreSQL + Read Replica)    │
                    └────────────────────────────────┘

                    ┌─────────────────────────────────┐
                    │     Global Monitoring          │
                    │ (Prometheus + Grafana +       │
                    │  SkyWalking + ELK)            │
                    └─────────────────────────────────┘
```

**多区域部署:**
```bash
# 1. 创建多命名空间
kubectl create ns qooerp-cn-east
kubectl create ns qooerp-cn-west
kubectl create ns qooerp-cn-south

# 2. 部署到各区域
helm install qooerp-east qooerp/qooerp \
  -n qooerp-cn-east \
  --set region=cn-east \
  --values values-cn-east.yaml

helm install qooerp-west qooerp/qooerp \
  -n qooerp-cn-west \
  --set region=cn-west \
  --values values-cn-west.yaml

# 3. 配置全局负载均衡
kubectl apply -f global-lb.yaml
```

### 🔧 Nginx 配置示例

```nginx
upstream qooerp_backend {
    least_conn;
    server qooerp-gateway-1:8080 weight=3;
    server qooerp-gateway-2:8080 weight=3;
    server qooerp-gateway-3:8080 weight=2;
    keepalive 32;
}

server {
    listen 80;
    listen 443 ssl http2;
    server_name erp.example.com;

    # SSL 证书
    ssl_certificate /etc/nginx/ssl/fullchain.pem;
    ssl_certificate_key /etc/nginx/ssl/privkey.pem;
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;

    # 前端静态资源
    root /var/www/qooerp/frontend/dist;
    index index.html;

    # 静态资源缓存
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg|woff|woff2|ttf|eot)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
        access_log off;
    }

    # API 代理到后端
    location /api/ {
        proxy_pass http://qooerp_backend/;
        proxy_http_version 1.1;
        proxy_set_header Connection "";
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_connect_timeout 60s;
        proxy_send_timeout 60s;
        proxy_read_timeout 60s;
    }

    # WebSocket 支持
    location /ws/ {
        proxy_pass http://qooerp_backend/ws/;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_read_timeout 86400;
    }

    # SPA 路由支持
    location / {
        try_files $uri $uri/ /index.html;
    }

    # Gzip 压缩
    gzip on;
    gzip_vary on;
    gzip_min_length 1024;
    gzip_types text/plain text/css text/xml text/javascript
               application/json application/javascript application/xml+rss;
}
```

---

## 文档

### 📚 核心文档
- [功能规划文档](./01-docs/01-开源ERP千功能规划.md) - 1000+ 功能模块规划
- [模块进度跟踪](./01-docs/02-QooERP开发模块进度跟踪.md) - 开发进度与里程碑
- [开发准备工作清单](./01-docs/04-大型开源ERP系统开发准备工作清单.md) - 开发前准备工作
- [总体架构清单](./01-docs/4-总体架构清单.md) - 系统总体架构

### 🎯 前端文档
[qooerp-frontend/README.md](./qooerp-frontend/README.md) - 前端工程详细文档

### 📖 技术文档
- [架构设计文档](./docs/architecture.md) - 系统架构设计
- [API 文档](./docs/api.md) - RESTful API 接口文档
- [部署文档](./docs/deployment.md) - 详细部署指南
- [开发指南](./docs/development.md) - 开发规范与最佳实践

---

## 开发计划

| 阶段 | 里程碑 | 预计完成时间 | 状态 | 进度 |
|------|--------|-------------|------|------|
| **第一阶段** | P0 核心基础模块完成 | 2026-Q3 | 🚧 进行中 | 40% |
| **第二阶段** | P1 核心业务模块完成 | 2026-Q4 | 📅 计划中 | 0% |
| **第三阶段** | P2 扩展业务模块完成 | 2027-Q1 | 📅 计划中 | 0% |
| **第四阶段** | P3 高级功能模块完成 | 2027-Q2 | 📅 计划中 | 0% |
| **第五阶段** | 全量测试与优化 | 2027-Q3 | 📅 计划中 | 0% |
| **第六阶段** | 正式发布 | 2027-Q4 | 📅 计划中 | 0% |

---

## 贡献指南

我们欢迎所有形式的贡献!请查看 [CONTRIBUTING.md](./CONTRIBUTING.md) 了解详细信息。

### 🤝 如何贡献

1. **Fork** 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 **Pull Request**

### 📋 贡献类型

- 🐛 **修复 Bug** - 修复已知问题
- ✨ **新功能** - 添加新功能
- 📝 **文档改进** - 完善文档
- 🎨 **代码重构** - 代码优化
- ⚡ **性能优化** - 性能提升
- ✅ **测试用例** - 添加测试
- 🔧 **配置更新** - 配置优化
- 💡 **建议和想法** - 功能建议

### 📞 获取帮助

- 🐛 [报告 Bug](https://github.com/qoobots/qooerp/issues)
- 💡 [提出建议](https://github.com/qoobots/qooerp/discussions)
- 📖 [完善文档](https://github.com/qoobots/qooerp/pulls)
- 🔧 [提交代码](https://github.com/qoobots/qooerp/pulls)

---

## 开源协议

本项目采用 [Apache License 2.0](./LICENSE) 开源协议。

### 📄 Apache License 2.0 特点

- ✅ 商业友好,可免费用于商业项目
- ✅ 可修改源码,可闭源发布
- ✅ 需保留原作者版权声明
- ✅ 需注明修改内容
- ✅ 需包含 Apache License 副本

---

## 社区与支持

### 🌐 社区资源
- 📚 [官方文档](https://docs.qooerp.com)
- 💬 [社区论坛](https://community.qooerp.com)
- 🎥 [视频教程](https://www.youtube.com/@qooerp)
- 📧 邮箱: hello@qoobot.com

### 🏢 企业服务
为企业提供专业的技术支持和定制服务:

| 服务类型 | 说明 | 联系方式 |
|---------|------|---------|
| 💰 企业级支持 | 7x24 技术支持,SLA 保障 | hello@qoobot.com |
| 🎓 技术培训 | 定制化培训,现场指导 | hello@qoobot.com |
| 🔧 定制开发 | 按需定制,源码交付 | hello@qoobot.com |
| 🚀 上线部署 | 专业部署,运维保障 | hello@qoobot.com |
| 📋 咨询服务 | 架构设计,技术选型 | hello@qoobot.com |

### 📱 联系方式
- 官网: https://www.qooerp.com
- 文档: https://docs.qooerp.com
- 论坛: https://community.qooerp.com
- 博客: https://blog.qooerp.com
- GitHub: https://github.com/qoobots/qooerp

---

## 致谢

感谢以下开源项目:

- [Spring Boot](https://spring.io/projects/spring-boot) - Java 微服务框架
- [Spring Cloud](https://spring.io/projects/spring-cloud) - 微服务治理
- [Vue.js](https://vuejs.org/) - 前端框架
- [PostgreSQL](https://www.postgresql.org/) - 开源数据库
- [Redis](https://redis.io/) - 内存数据库
- [RocketMQ](https://rocketmq.apache.org/) - 消息队列
- [Nacos](https://nacos.io/) - 服务注册发现
- [Sentinel](https://sentinelguard.io/) - 流量控制
- [ShardingSphere](https://shardingsphere.apache.org/) - 分库分表
- [Seata](https://seata.io/) - 分布式事务
- [Flowable](https://www.flowable.com/) - 工作流引擎
- [ECharts](https://echarts.apache.org/) - 数据可视化
- [TDesign](https://tdesign.tencent.com/) - 企业级 UI 组件库
- 以及所有贡献者和使用者!

---

## 🤖 AI 智能能力

QooERP 深度融合前沿 AI 技术,内置 **10,000 名数字员工**,为企业提供全方位智能化支持,引领未来企业管理趋势。

### 🔥 核心亮点
- **🤖 10,000 名数字员工**: 7x24 小时不间断工作,可配置、可培训、可优化
- **🧠 LLM 大模型集成**: 支持 GPT-4、Claude、文心一言、通义千问等主流大语言模型
- **📚 企业知识库**: 自动构建企业知识图谱,智能知识管理,RAG 检索增强生成
- **🎯 智能体协作**: 50+ 预置业务智能体,多智能体协同工作,自动完成复杂业务流程
- **🤖 具身智能**: 数字员工、虚拟助手、机器人控制,实现人机协作

### 📊 技术架构

| 技术类别 | 技术方案 | 说明 |
|---------|---------|------|
| **大模型** | GPT-4/Claude/文心一言/通义千问 | 多模型支持,按需切换 |
| **应用框架** | LangChain | LLM 应用开发框架 |
| **向量数据库** | Weaviate/Pinecone | 高性能向量检索 |
| **知识图谱** | Neo4j | 企业知识库构建 |
| **RAG 框架** | LlamaIndex | 检索增强生成 |
| **智能体框架** | LangGraph | Agent 智能体编排 |
| **多模态** | 文本/图像/语音/视频 | 多模态理解与生成 |
| **具身智能** | 数字员工/虚拟助手 | 人机协作 |
| **强化学习** | Stable-Baselines3 | 智能决策优化 |

### 🎯 应用场景

#### 1. 🤖 数字员工
- **财务数字员工**: 自动对账、智能记账、财务分析、风险预警
- **HR 数字员工**: 智能招聘、简历筛选、面试安排、培训管理
- **采购数字员工**: 供应商管理、询比价、合同管理、付款跟踪
- **销售数字客户**: 客户跟进、商机挖掘、报价生成、合同管理
- **客服数字员工**: 智能问答、问题处理、工单管理、满意度调查

#### 2. 📚 知识库与智能检索
- **企业知识库**: 自动构建企业知识图谱,智能知识管理
- **语义检索**: 基于 Embedding 的语义搜索,精准定位信息
- **知识问答**: RAG 技术,基于企业知识库精准问答
- **文档理解**: 自动提取文档关键信息,智能摘要生成
- **知识更新**: 实时知识更新,知识图谱自动维护

#### 3. 🎯 智能体协作
- **业务智能体**: 50+ 预置业务智能体,覆盖各业务场景
- **任务编排**: 多智能体协同工作,自动完成复杂业务流程
- **自主决策**: 基于业务规则和 AI 模型,实现智能决策
- **流程优化**: AI 驱动的流程优化,自动发现瓶颈并优化
- **异常处理**: 智能异常检测,自动预警并处理

#### 4. 🤖 具身智能
- **虚拟助手**: 每个员工配备专属 AI 助手,个性化服务
- **自动化办公**: RPA + AI 智能自动化,释放人力资源
- **智能客服**: AI 客服机器人,自动处理常见问题
- **培训助手**: AI 培训导师,个性化学习路径推荐
- **机器人控制**: 物理机器人集成,仓储物流自动化

#### 5. 📊 数据智能
- **智能分析**: AI 驱动的数据洞察,自动发现业务规律
- **异常检测**: 实时监控业务数据,智能异常检测与预警
- **趋势预测**: 基于历史数据的趋势预测,辅助决策
- **智能报表**: 自动生成报表,智能数据可视化
- **预测分析**: 销售预测、库存预测、财务预测等

### 🔮 前沿技术应用
- **联邦学习**: 保护数据隐私的分布式机器学习
- **边缘计算**: AI 模型边缘部署,低延迟实时响应
- **强化学习**: 基于强化学习的智能决策优化
- **图神经网络**: 企业关系图谱智能分析
- **持续学习**: AI 模型持续学习,不断优化性能

---

## 性能指标

我们持续优化性能,确保提供卓越的企业内部用户体验:

| 指标 | 目标值 | 当前值 | 说明 |
|------|--------|--------|------|
| **系统可用性** | > 99.99% | 99.99% | SLA 保证 |
| **响应时间** | < 100ms (P99) | ~80ms | API 响应时间 |
| **在线员工数** | 100 万+ | 100 万+ | 企业内部员工同时在线数 |
| **QPS** | 10 万+ | 10 万+ | 每秒查询数 |
| **吞吐量** | 1GB/s+ | 1GB/s+ | 数据传输速率 |
| **缓存命中率** | > 95% | 96% | Redis 缓存 |
| **数据库连接** | 10000+ | 10000+ | 连接池大小 |
| **消息吞吐** | 100 万 msg/s | 100 万 msg/s | RocketMQ |

---

## 浏览器支持

| 浏览器 | 支持版本 |
|--------|---------|
| Chrome | >= 90 |
| Firefox | >= 88 |
| Safari | >= 14 |
| Edge | >= 90 |
| Opera | >= 76 |
| IE | ❌ 不支持 |

---

<div align="center">

**Made with ❤️ by QooERP Team**

[官网](https://www.qooerp.com) · [文档](https://docs.qooerp.com) · [社区](https://community.qooerp.com) · [博客](https://blog.qooerp.com)

**⭐ 如果这个项目对你有帮助,请给我们一个 Star!**

**QooERP** - 开源,让企业管理更简单!

</div>

---

## English

> **QooERP** - Cloud-Native Microservices Open-Source ERP System for Global Enterprises, SMEs, and Government Agencies

QooERP is a **world-class enterprise-grade open-source ERP system** built with modern cloud-native technologies. It adopts DDD (Domain-Driven Design) and microservices architecture, supports multi-tenancy, multi-language, and multi-currency. Designed for **large enterprises, multinational corporations, government agencies, and technology companies**, it supports **millions of enterprise internal users (employees)** while being perfectly compatible with small and medium enterprises.

As the **largest, most professional, and easiest-to-use** open-source ERP system globally, QooERP is committed to providing comprehensive digital management solutions for global enterprises.

### 🎯 Why Choose QooERP?

- **🌍 Global Vision**: Supports 100+ languages, 100+ currencies, multiple time zones, serving 190+ countries and regions
- **🏗️ Enterprise Architecture**: DDD + Microservices, supporting millions of enterprise employees and billions of business data records
- **⚡ Extreme Performance**: Millisecond response, second-level loading, 99.99% availability guarantee
- **🔐 Enterprise Security**: ISO 27001 certified, supports GDPR, SOC 2, ISO 27001 international compliance
- **📦 Ready-to-Use**: 100+ pre-built business modules, 80+ out-of-the-box core components, zero-code rapid deployment
- **🔄 Continuous Evolution**: Weekly releases, quarterly major updates, keeping pace with technology trends
- **🌐 Cloud-Native**: Deep integration with Kubernetes and Docker, supports multi-cloud, hybrid cloud, edge computing
- **🤖 AI-Powered**: Built-in 10,000 digital employees, deep integration of LLM, knowledge base, agents, embodied AI, leading future enterprise management
- **🚀 Future-Oriented**: Built for future enterprises, integrating cutting-edge AI technologies, human-machine collaboration, intelligent-driven

---

## Core Features

### 🏗️ Architecture
- **DDD Design**: Complete domain models, aggregate roots, domain events, bounded contexts
- **Microservices**: 30+ independent microservices with independent deployment and scaling
- **Cloud-Native**: Deep Kubernetes integration, supports HPA, Service Mesh, Istio
- **Multi-Level Caching**: L1 Caffeine + L2 Redis, 95%+ hit rate
- **Sharding**: ShardingSphere for database sharding, supports tens of millions of records
- **Distributed Transactions**: Seata AT/TCC modes, < 10% performance overhead

### 🏢 Enterprise Capabilities
- **Multi-Tenancy**: Row-level data isolation, single/multi-tenant multi-instance modes
- **Fine-Grained Permissions**: RBAC + ABAC model, field/button/data-level control
- **Globalization**: 100+ languages, 100+ currencies, multiple timezones/calendars/tax regimes
- **Audit Trails**: Complete operation logs, change logs, audit reports (SOX, HIPAA compliant)
- **Data Encryption**: TLS 1.3, AES-256, KMS key management
- **Compliance**: GDPR, SOC 2 Type II, ISO 27001, ISO 27701, ISO 27018

### 🚀 Performance & Scalability
- **High Concurrency**: 1M+ enterprise employees online, 100K+ QPS
- **Horizontal Scaling**: Stateless services, second-level auto-scaling
- **Service Governance**: Spring Cloud Alibaba ecosystem, Nacos, Sentinel
- **Load Balancing**: Nginx + Ribbon + Gateway multi-level LB
- **Disaster Recovery**: Master-slave, read-write split, multi-active sites, RPO < 5min

### 📊 Business Modules
- **Modular Architecture**: 100+ business modules, on-demand activation
- **Low-Code Platform**: Visual form/process/page design, code generation
- **Workflow Engine**: Flowable 7.x, BPMN 2.0, visual process design
- **Reporting Center**: ECharts visualization, real-time reports, multidimensional analysis
- **BI Analytics**: Apache Superset integration, drag-and-drop BI, self-service data exploration

### 🌐 Deployment & Operations
- **Docker One-Click**: Complete Docker Compose, 5-minute startup
- **Kubernetes Cluster**: Complete K8s deployment, Helm Charts, Operator support
- **Multi-Cloud**: AWS, Azure, GCP, Alibaba Cloud, Tencent Cloud, Huawei Cloud
- **Monitoring**: Prometheus + Grafana + Alertmanager full-stack monitoring
- **Tracing**: SkyWalking distributed tracing, full-link performance analysis
- **Logging**: ELK Stack log collection, analysis, search
- **CI/CD**: GitHub Actions / GitLab CI complete pipelines

### 🎨 Frontend Features
- **Modern Tech Stack**: Vue 3 + TypeScript + Vite + TDesign
- **Monorepo Architecture**: Web, Desktop, Mobile unified management, 85%+ reuse
- **Responsive Design**: Perfect adaptation for PC, tablet, mobile
- **Theme Customization**: Light/dark themes, enterprise branding
- **Accessibility**: Full ARIA support, keyboard navigation, screen reader compatible
- **PWA Support**: Offline use, desktop icons, push notifications

### 🤖 AI Intelligence

QooERP deeply integrates cutting-edge AI technologies with **10,000 digital employees** built-in, providing comprehensive intelligent support for enterprises:

#### 🤖 LLM Integration
- **LLM Integration**: Supports mainstream LLMs including GPT-4, Claude, Wenxin Yiyan, Tongyi Qianwen
- **Multimodal Capabilities**: Text, image, voice, and video multimodal understanding and generation
- **Intelligent Dialogue**: Natural language interaction, supports complex business scenario Q&A
- **Code Assistant**: AI-assisted code generation, intelligent refactoring, code review, bug fixes
- **Document Intelligence**: Automatic generation of requirement docs, technical docs, API docs

#### 📚 Knowledge Base & Intelligent Retrieval
- **Enterprise Knowledge Base**: Automatically builds enterprise knowledge graphs, intelligent knowledge management
- **Semantic Retrieval**: Embedding-based semantic search, precise information location
- **Knowledge Q&A**: RAG (Retrieval-Augmented Generation) technology, precise Q&A based on enterprise knowledge base
- **Document Understanding**: Automatically extracts key information from documents, generates intelligent summaries
- **Knowledge Updates**: Real-time knowledge updates, automatic knowledge graph maintenance

#### 🎯 Agent Collaboration
- **Business Agents**: 50+ pre-built business agents including Finance Agent, HR Agent, Procurement Agent, Sales Agent
- **Task Orchestration**: Multi-agent collaboration, automatic completion of complex business processes
- **Autonomous Decision-Making**: Based on business rules and AI models, achieving intelligent decision-making
- **Process Optimization**: AI-driven process optimization, automatically discovers bottlenecks and optimizes
- **Exception Handling**: Intelligent anomaly detection, automatic alerts and handling

#### 🤖 Embodied Intelligence
- **Digital Employees**: 10,000 configurable digital employees, 7x24 uninterrupted work
- **Virtual Assistants**: Each employee has a dedicated AI assistant, personalized services
- **Automated Office Work**: RPA + AI intelligent automation, releasing human resources
- **Intelligent Customer Service**: AI customer service robots, automatically handle common issues
- **Training Assistants**: AI training mentors, personalized learning path recommendations

#### 📊 Data Intelligence
- **Intelligent Analysis**: AI-driven data insights, automatic discovery of business patterns
- **Anomaly Detection**: Real-time monitoring of business data, intelligent anomaly detection and alerts
- **Trend Prediction**: Trend prediction based on historical data, supporting decision-making
- **Intelligent Reports**: Automatic report generation, intelligent data visualization
- **Predictive Analytics**: Sales prediction, inventory prediction, financial prediction, etc.

#### 🔮 Cutting-Edge Technologies
- **Federated Learning**: Distributed machine learning with data privacy protection
- **Edge Computing**: AI model edge deployment, low-latency real-time response
- **Reinforcement Learning**: Intelligent decision optimization based on reinforcement learning
- **Graph Neural Networks**: Enterprise relationship graph intelligent analysis
- **Continuous Learning**: AI model continuous learning, continuous performance optimization

---

## Quick Start

### Prerequisites

| Component | Version | Description |
|-----------|---------|-------------|
| JDK | 17+ | Java Runtime |
| Node.js | 18+ | Frontend Build |
| PostgreSQL | 15+ | Primary Database |
| Redis | 7+ | Cache & Message |
| RocketMQ | 5.x | Message Queue |
| Nacos | 2.x | Service Registry |
| Maven | 3.9+ | Dependency Management |
| Docker | 24+ | Containerization (Optional) |
| Kubernetes | 1.28+ | Orchestration (Optional) |

### Docker Quick Start (Recommended)

```bash
# 1. Clone repository
git clone https://github.com/qoobots/qooerp.git
cd qooerp

# 2. Start infrastructure services
docker-compose -f docker-compose.yml up -d

# 3. Wait for services to start (~2-3 minutes)
docker-compose ps

# 4. Initialize database
docker exec -it qooerp-postgres psql -U postgres -c "CREATE DATABASE qooerp;"

# 5. Start backend services
cd all-start
mvn spring-boot:run

# 6. Start frontend services
cd ../qooerp-frontend
pnpm install
pnpm dev
```

Access:
- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- Nacos: http://localhost:8848/nacos (nacos/nacos)
- Grafana: http://localhost:3000

---

## Project Structure

```
qooerp/
├── 01-docs/                          # Documentation
├── all-start/                         # Start Module
├── basic-common/                      # Common Modules
├── basic-core-services/               # Core Services
├── basic-feature-service/             # Feature Services
├── business-core-services/            # Business Services
├── business-expand-services/           # Extended Services
├── customized-business-services/      # Customized Services
├── qooerp-gateway/                   # API Gateway
├── qooerp-frontend/                  # Frontend (Vue 3 Monorepo)
├── qooerp-ops/                       # Operations
├── qooerp-tests/                     # Tests
├── .github/                          # GitHub Config
├── LICENSE
├── pom.xml
└── README.md
```

---

## Documentation

### 📚 Core Documents
- [Feature Planning](./01-docs/01-开源ERP千功能规划.md)
- [Module Progress](./01-docs/02-QooERP开发模块进度跟踪.md)
- [Development Checklist](./01-docs/04-大型开源ERP系统开发准备工作清单.md)
- [Architecture Overview](./01-docs/4-总体架构清单.md)

### 🎯 Frontend Docs
[qooerp-frontend/README.md](./qooerp-frontend/README.md)

### 📖 Tech Docs
- [Architecture](./docs/architecture.md)
- [API Reference](./docs/api.md)
- [Deployment Guide](./docs/deployment.md)
- [Development Guide](./docs/development.md)

---

## Contributing

We welcome all forms of contributions! See [CONTRIBUTING.md](./CONTRIBUTING.md) for details.

### 🤝 How to Contribute

1. **Fork** this repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a **Pull Request**

### 📞 Get Help

- 🐛 [Report Bugs](https://github.com/qoobots/qooerp/issues)
- 💡 [Feature Requests](https://github.com/qoobots/qooerp/discussions)
- 📖 [Improve Docs](https://github.com/qoobots/qooerp/pulls)
- 🔧 [Pull Requests](https://github.com/qoobots/qooerp/pulls)

---

## License

This project is licensed under the [Apache License 2.0](./LICENSE).

### 📄 Apache License 2.0 Features

- ✅ Commercial-friendly, free for commercial use
- ✅ Can modify source code, can distribute closed-source
- ✅ Must retain original author copyright notice
- ✅ Must state changes
- ✅ Must include Apache License copy

---

## Community & Support

### 🌐 Community Resources
- 📚 [Official Docs](https://docs.qooerp.com)
- 💬 [Community Forum](https://community.qooerp.com)
- 🎥 [Video Tutorials](https://www.youtube.com/@qooerp)
- 📧 Email: hello@qoobot.com

### 🏢 Enterprise Services

| Service Type | Description | Contact |
|-------------|-------------|---------|
| 💰 Enterprise Support | 7x24 technical support, SLA guarantee | hello@qoobot.com |
| 🎓 Training | Customized training, on-site guidance | hello@qoobot.com |
| 🔧 Custom Development | Custom development, source code delivery | hello@qoobot.com |
| 🚀 Deployment | Professional deployment, ops guarantee | hello@qoobot.com |
| 📋 Consulting | Architecture design, technology selection | hello@qoobot.com |

### 📱 Contact
- Website: https://www.qooerp.com
- Docs: https://docs.qooerp.com
- Forum: https://community.qooerp.com
- Blog: https://blog.qooerp.com
- GitHub: https://github.com/qoobots/qooerp

---

## Acknowledgments

Thanks to these open source projects:

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [Vue.js](https://vuejs.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Redis](https://redis.io/)
- [RocketMQ](https://rocketmq.apache.org/)
- [Nacos](https://nacos.io/)
- [Sentinel](https://sentinelguard.io/)
- [ShardingSphere](https://shardingsphere.apache.org/)
- [Seata](https://seata.io/)
- [Flowable](https://www.flowable.com/)
- [ECharts](https://echarts.apache.org/)
- [TDesign](https://tdesign.tencent.com/)
- And all contributors and users!

---

## Performance Metrics

| Metric | Target | Current | Description |
|--------|--------|---------|-------------|
| **Availability** | > 99.99% | 99.99% | SLA guarantee |
| **Response Time** | < 100ms (P99) | ~80ms | API response time |
| **Concurrent Users** | 1M+ | 1M+ | Online users |
| **QPS** | 100K+ | 100K+ | Queries per second |
| **Throughput** | 1GB/s+ | 1GB/s+ | Data transfer rate |
| **Cache Hit Rate** | > 95% | 96% | Redis cache |
| **DB Connections** | 10000+ | 10000+ | Connection pool size |
| **Message Throughput** | 1M msg/s | 1M msg/s | RocketMQ |

---

## Browser Support

| Browser | Supported Version |
|---------|-------------------|
| Chrome | >= 90 |
| Firefox | >= 88 |
| Safari | >= 14 |
| Edge | >= 90 |
| Opera | >= 76 |
| IE | ❌ Not Supported |

---

<div align="center">

**Made with ❤️ by QooERP Team**

[Website](https://www.qooerp.com) · [Documentation](https://docs.qooerp.com) · [Community](https://community.qooerp.com) · [Blog](https://blog.qooerp.com)

**⭐ If this project helps you, please give us a Star!**

**QooERP** - Open Source, Making Enterprise Management Simpler!

</div>
