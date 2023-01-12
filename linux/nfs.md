# NFS

NFS是Linux常用的远程文件系统。

## 1. 服务端

```bash
yum -y install nfs-utils
```

### 安装nfs+rpc

```bash
yum -y install nfs-utils rpcbind
```

在服务端创建一个共享目录 /data/share(目录可自定义)

```bash
mkdir -p /data/share
chmod 666 /data/share
```

然后，修改 NFS 配置文件 /etc/exports

```bash
vim /etc/exports
/data/share 172.16.0.1/24(rw,sync,insecure,no_subtree_check,no_root_squash)
```

### 重载数据

```bash
exportfs -rv
```

> 参数说明：这里配置后边有很多参数，每个参数有不同的含义，具体可以参考下边。此处，我配置了将 `/data/share` 文件目录设置为允许 IP
> 为该 `10.222.77.0/24` 区间的客户端挂载，当然，如果客户端 IP 不在该区间也想要挂载的话，可以设置 IP 区间更大或者设置为
> * 即允许所有客户端挂载，例如：`/home *(ro,sync,insecure,no_root_squash)` 设置 /home 目录允许所有客户端只读挂载。

接下来，我们先启动 RPC 服务
###  启动rpc

```bash
systemctl start rpcbind
```

#设置开机启动

```bash
systemctl enable rpcbind
```

检查启动是否成功：`rpcinfo -p localhost` ,如果显示rpc 服务器注册的端口列表（端口：111），则启动成功。

接着我们来启动 NFS 服务

```bash
systemctl start nfs
systemctl enable nfs
```

## 2. 客户端安装
1、安装NFS
客户端上不需要启动nfs服务，只是为了使用`showmount`工具

安装nfs

```bash
yum -y install nfs-utils
```

检测rpc服务

```bash
rpcinfo -p
```

### 查看服务器端挂在目录

```bash
showmount -e 192.168.4.32
```

2、挂载目录
将服务器端目录挂在到本地

```bash
mount -t nfs 192.168.4.32:/data/share /data/share
```

验证：在服务器端 `/data/share`路径下新建文件后去客户端挂载目录查看是否同步。

