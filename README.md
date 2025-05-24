# 快速了解

- 充电桩管理系统是专为中小型充电桩运营商、企业和个人开发者设计的一套高效、灵活的管理平台。系统基于Spring Cloud微服务架构开发，采用模块化设计，支持单机部署与集群部署，能够根据业务需求动态扩展。系统前端使用uniapp框架，可同时编译到微信小程序、H5、Android App、iOS App等多个平台，帮助中小型充电桩运营商降低开发与维护成本，快速实现业务扩展。

#### 社区

- QQ讨论群：687754307 (进群前，请在网页右上角点star)
- 数据库初始化sql文件，请进入讨论交流群，群文件自行下载，欢迎讨论与交流

[🔥 充电平台源码](https://gitee.com/guo-yanping-1991/kxcharge)

---

### Java 后端服务

- **kxcharge-dependencies**  
  功能：用于管理项目的依赖版本，统一管理 Maven 或 Gradle 依赖。  
  说明：包含项目的父 POM 文件，定义所有子模块的公共依赖版本，确保依赖一致性。

- **kxcharge-framework**  
  功能：项目核心框架模块，提供公共工具类、通用配置、基础组件等。  
  说明：包含全局异常处理、日志配置、工具类（如日期处理、字符串处理）、基础实体类等。

- **kxcharge-gateway**  
  功能：API 网关模块，负责请求路由、负载均衡、权限校验等。  
  说明：基于 Spring Cloud Gateway 实现，作为系统的统一入口，处理所有外部请求。

- **kxcharge-module-infra**  
  功能：基础设施模块，提供数据库、缓存、消息队列等基础服务。  
  说明：包含数据库连接配置、Redis 缓存配置、RabbitMQ 消息队列配置等。

- **kxcharge-module-interflow**  
  功能：交互流模块，负责与外部系统或第三方服务的交互。  
  说明：包含与支付网关、地图服务、短信服务等第三方服务的集成。

- **kxcharge-module-member**  
  功能：会员管理模块，负责用户注册、登录、权限管理等功能。  
  说明：包含用户信息管理、角色权限分配、登录认证等功能。

- **kxcharge-module-operations**  
  功能：运营管理模块，负责充电桩的日常运营管理。  
  说明：包含充电桩状态监控、故障处理、运维任务分配等功能。

- **kxcharge-module-pay**  
  功能：支付管理模块，负责充电订单的支付与退款处理。  
  说明：支持多种支付方式（微信、支付宝等），包含支付状态回调、对账等功能。

- **kxcharge-module-pile**  
  功能：充电桩管理模块，负责充电桩的信息管理与状态监控。  
  说明：包含充电桩信息录入、状态监控（空闲、充电中、故障等）、远程控制等功能。

- **kxcharge-module-report**  
  功能：报表管理模块，负责生成各类数据分析报表。  
  说明：包含充电桩使用率统计、用户充电行为分析、收益统计等功能。

- **kxcharge-module-system**  
  功能：系统管理模块，负责系统配置与基础数据管理。  
  说明：包含系统参数配置、字典管理、日志管理等功能。

---

### Vue 前端页面

- **kxcharge-ui-admin**  
  功能：系统管理模块，负责系统配置与基础数据管理。  
  说明：包含系统参数配置、字典管理、日志管理等功能。

- **kxcharge-uniapp**  
  功能：系统管理模块，负责系统配置与基础数据管理。  
  说明：包含系统参数配置、字典管理、日志管理等功能。

---

### 🔨 项目全景图

![PC端](https://kxcharge.oss-cn-beijing.aliyuncs.com/pc-jiagou.jpg)

![PC端](https://kxcharge.oss-cn-beijing.aliyuncs.com/app-jiagou.jpg)


### ⭐ 系统亮点

1. 支持云快充通信协议 ｜ 支持分时电价 ｜ 支持停车系统(目前支持海康道闸、其余需对接)

2. 支持设备监控预警 ｜ 支持中电联互联互通 ｜ 支持各地区市政平台对接

3. 集群服务架构 ｜ 系统-业务模块分离 ｜ 多租户 ｜ 高可用 ｜ 动态伸缩 ｜ 无感升级

4. 采用 Spring + Mybatis-Plus + Redis + RabbitMQ + Netty  高并发方案

5. 系统内置“模拟桩”模块，可进行仿真充电，方便业务调试

```
1.易维护：基于Ruoyi脚手架搭建，脚手架与业务模块分离，易于升级更新；
2.前后端分离：后端采用Spring，关联端采用vue-admin-element，移动端采用uni-app；
3.权限管理：基于ruoyi体系，支持多租户；
4.二次开发能力：业务模块独立，方便业务扩展；
5.快速上手：完备的用户使用文档、可基于docker编排10分钟快速搭建仿真使用环境；
6.系统安全：支持接口数据加密、全局操作日志等；
7.高性能：基于Netty通信架构
8.互通能力：支持对接特来电、快电、新电途、e充电、星星充电等各家充电平台。
```

### 🧭 参考文档

- [👉 充电桩在线文档](https://cdzdoc.kxmall.vip)

### 💿 客户端系统演示

 - 下面是微信小程序真机模式调试的界面，可Android安装Apk,也可同时支持苹果。 在这基础上，还增加了H5。可内置到微信公众号上，变成公众号客户端！尽情体验！

   - 客户端（可打包成小程序、APP）
   - 默认使用微信一键登录即可
   - 客户端由于调用地图需要https
   - 模拟支付模块，可正常体验操作流程

|                                    充电桩                                    |                                                                充电桩                                                                | 充电桩 |
|:-----------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------------:| :----: |
| ![充电桩](https://kxcharge.oss-cn-beijing.aliyuncs.com/home.png) |                                    ![充电桩](https://kxcharge.oss-cn-beijing.aliyuncs.com/my.png)                                    | ![充电桩](https://kxcharge.oss-cn-beijing.aliyuncs.com/station.png) |
| ![充电桩](https://kxcharge.oss-cn-beijing.aliyuncs.com/gitee/3b60bba077c908369f28a73f07d7bc70_6f519c4cda4147f88f244ab051a2ec2d.png) | ![充电桩](https://kxcharge.oss-cn-beijing.aliyuncs.com/gitee/cbf250df37eeae56eb63237f6b25f255_d27b264d968944df8d98c51d6c58bb5c.png)  | ![充电桩](https://kxcharge.oss-cn-beijing.aliyuncs.com/gitee/b597a98446b96f2c0bc476ddb386f229_ef0f993baf01482e8234c395010ffdfa.png) |

|                                                              充电桩订单                                                               |                                                              充电桩订单                                                               |
|:--------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------:|
| ![充电桩](https://kxcharge.oss-cn-beijing.aliyuncs.com/gitee/737ef31732507bad96c40fe4a02f0162_639ed798827c4cc7acdbc783ae3005a2.png) | ![充电桩](https://kxcharge.oss-cn-beijing.aliyuncs.com/gitee/9f6c2e63a95617d3323cb11758e80a72_7fbff2ce9bf74dfb870f4768eceee39c.png) 


### 💿 后台系统演示


- 使用免费开源框架vue-element-admin，基于element-ui的后台管理页面！尽情体验！

  - Admin后台
  - 演示地址: https://cdz.kxmall.vip
  - 登录名、密码: 已默认 (仅有只读权限，无写配置权限)

![河禾生鲜](https://kxcharge.oss-cn-beijing.aliyuncs.com/gitee/748436e1411c4a172467189e2e61e022_84daa7af17894fcc92b9f4d37a8fc3c7.png)  
![河禾生鲜](https://kxcharge.oss-cn-beijing.aliyuncs.com/gitee/c2255015b4cd4332ae16fb112978db3b.png)  
![河禾生鲜](https://kxcharge.oss-cn-beijing.aliyuncs.com/gitee/20c13af821744626a95494cbf98c16b6.png)   
![河禾生鲜](https://kxcharge.oss-cn-beijing.aliyuncs.com/gitee/388013ca67ed462e905126ac522e6955.png)


### 🌈 核心功能

- 系统基于互联互通协议，充电运营服务支持对接多个自有/第三方平台（如特来电、快电、新电途、e充电）和市政充电平台；
- 系统基础服务采用ruoyi框架，独立与业务模块，可无干扰同步升级系统非业务功能；
- 充电基础设施服务支持多类充电桩，支持一站多种不同协议混合使用；
- 系统支持动态伸缩，服务增减对业务0影响；
 
### 📖 支持中电联互联互通协议

- T／CEC 102.1—2016 电动汽车充换电服务信息交换 第1部分：总则
- T／CEC 102.2—2016 电动汽车充换电服务信息交换 第2部分：公共信息交换规范
- T／CEC 102.3—2016 电动汽车充换电服务信息交换 第3部分：业务信息交换规范
- T／CEC 102.4—2016 电动汽车充换电服务信息交换 第4部分：数据传输及安全

