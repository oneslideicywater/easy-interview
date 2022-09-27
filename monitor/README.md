## 监控系统

监控系统是提高应用的可观测性(observability)

### APM(Application Performance Management)

目前市面的系统基本都是参考 Google 的 Dapper（大规模分布式系统的跟踪系统）来做的, 参考[Dapper论文中文翻译](http://bigbully.github.io/Dapper-translation/).


开源的APM工具列表：

- [pinpoint](https://github.com/naver/pinpoint)

Pinpoint is an open source APM (Application Performance Management) tool for large-scale distributed systems written in Java.


- [skywalking](http://skywalking.org)

A distributed tracing system, and APM (Application Performance Monitoring).

- [zipkin](http://zipkin.io/)

Zipkin is a distributed tracing system. It helps gather timing data needed to troubleshoot latency problems in microservice architectures. It manages both the collection and lookup of this data. Zipkin’s design is based on the Google Dapper paper.
