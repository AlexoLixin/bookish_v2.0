# don9cn_v2.0
version 2.0 of my blog application which based on SpringBoot<br>
基于SpringBoot的原don9cn重构版本

### 重构内容
* 基础架构改为Spring Boot
* 项目整体业务实现代码使用 Java8 重构<br>
default 特性精简dao层<br>
Optional 解决空指针异常<br>
Stream 流重构集合遍历操作<br>
CompletableFuture 利用多核处理IO及网络并发请求<br>
* 重构项目目录结构,更加简洁明了
* 数据库改为MongoDB
* 实现REST API
* 日志管理

### 新的项目架构
SpringBoot          基础架构<br>
Shiro               权限控制<br>
Redis               缓存实现<br>
MongoDB             数据库<br>
Kafka+WebSocket     消息推送<br>
