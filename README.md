# cs6650-final-project

##TODO
- 视频做到P98 (问题记录文档https://docs.qq.com/doc/DT2JPQUVvb015RHVB) shenxi & yingtuo
- Project Executive Summary
  - Overview
  - Nacos: raft 画图+讲解 （https://blog.csdn.net/qq_34820803/article/details/107978204） yuqin
  - Microservice
  - 其他technical impression
- Demo 的slides (前端，后端，Nacos Raft) huixin
- 前端界面的更改 - 加入banner，加入NEU 元素  huixin


- code clean - 把中文comment翻译为英文 
- sql 数据库 把“campus”相关翻译为“campus”相关，并create sql初始化file （2file）
- code 中“campus”相关翻译为“campus”相关，如api路径 hosp/findAll

跟分布式有关系：Nacos, Microservice, Security(MD5 加密，[P75]), MongoDB(主从数据库，数据一致性)
网关 - Spring Cloud Gateway：跟Microservice 架构有关（看day10的笔记）
1、网关介绍
API网关出现的原因是微服务架构的出现，不同的微服务一般会有不同的网络地址，而外部客户端可能需要调用多个服务的接口才能完成一个业务需求，如果让客户端直接与各个微服务通信，会有以下的问题：
（1）客户端会多次请求不同的微服务，增加了客户端的复杂性。
（2）存在跨域请求，在一定场景下处理相对复杂。
（3）认证复杂，每个服务都需要独立认证。
（4）难以重构，随着项目的迭代，可能需要重新划分微服务。例如，可能将多个服务合并成一个或者将一个服务拆分成多个。如果客户端直接与微服务通信，那么重构将会很难实施。
（5）某些微服务可能使用了防火墙 / 浏览器不友好的协议，直接访问会有一定的困难。
以上这些问题可以借助 API 网关解决。API 网关是介于客户端和服务器端之间的中间层，所有的外部请求都会先经过API 网关这一层。也就是说，API 的实现方面更多的考虑业务逻辑，而安全、性能、监控可以交由 API 网关来做，这样既提高业务灵活性又不缺安全性

2、Spring Cloud Gateway介绍
Spring cloud gateway是spring官方基于Spring 5.0、Spring Boot2.0和Project Reactor等技术开发的网关，Spring Cloud Gateway旨在为微服务架构提供简单、有效和统一的API路由管理方式，Spring Cloud Gateway作为Spring Cloud生态系统中的网关，目标是替代Netflix Zuul，其不仅提供统一的路由方式，并且还基于Filer链的方式提供了网关基本的功能，例如：安全、监控/埋点、限流等

