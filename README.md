# booklish_v2.0
version 2.0 of my blog application which based on SpringBoot<br>
基于SpringBoot的原booklish_v1.0重构版本

### 重构内容
* 基础架构改为Spring Boot
* 消息中间件由Kafka改为ActiveMq
* 业务代码使用 Kotlin 重构,更加精简
* 重构项目目录结构,更加简洁明了
* 数据库改为MongoDB
* 实现REST API
* 日志管理

### 新的项目架构
基础架构:   SpringBoot          <br>
权限控制:   Shiro               <br>
缓存实现:   Redis               <br>
数据库:     MongoDB             <br>
消息推送:   ActiveMq+WebSocket     <br>
