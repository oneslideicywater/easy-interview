# Keepalive


Keepalive是一个轻量级的组件。

> Keepalived implements VRRP (Virtual Router Redundancy Protocol) on a Linux system as well as managing Linux Virtual Server configuration. Keepalived can implement High Availability (active/passive) and load balancing (active/active) setups that can be made responsive to several customisable factors.



## VRRP(Virtual Router Redundancy Protocol)

[VRRP协议](https://www.rfc-editor.org/rfc/rfc5798#section-1.1)定义了虚拟路由，通过路由冗余实现给路由高可用。

路由集群中通过选举机制选择主节点，主节点负责流量转发。集群会定期发送保活报文，当主节点挂掉，会通过重新选举，选出新的路由充当主节点。


## failover场景

首先ARP是节点通过IP查询MAC地址的协议。


Virtual Router会关联一个IP数组，任何VRRP路由器当成为主节点时，碰到ARP请求中包含数组中任一IP时，必须给出回应。


因此当主节点挂掉的情况下，客户端还是能够获得到来自新的主节点得arp响应。