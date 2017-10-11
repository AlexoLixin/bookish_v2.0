# don9cn_v2.0
version 2.0 of my blog application which based on SpringBoot<br>
基于SpringBoot的原don9cn重构版本

### 重构内容
* 基础架构改为Spring Boot
* 项目整体业务实现代码使用 Java8 重构<br>
主要重构点:<br>
default 特性精简dao层<br>
Optional 解决空指针异常<br>
Stream 流重构集合遍历操作<br>
CompletableFuture 利用多核处理IO及网络并发请求<br>
* 重构项目目录结构,更加简洁明了