## sudo 

### su
需要输入root密码，这明确告诉系统我就是简单的切换成root用户。其他两种凡是不能访问root的家目录和root环境变量信息

### sudo su
唯一的区别是输入当前用户的密码，可以保护root密码。可以访问root的HOME目录和环境信息。

### sudo -i

> With sudo su you’re using more than one root `setuid` commands.
这个看不懂

### sudo -s
不会去读root的环境信息，no-login风格shell,通常只是为了方便执行某些信息（比如安装软件）。


### 无密码sudo

更改`/etc/sudoers`


```bash
test	ALL=(ALL)	NOPASSWD: ALL
```

