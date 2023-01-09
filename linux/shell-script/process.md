# 进程管理


## 查看进程

`ps`命令可以查看OS上运行的进程。

bash命令会新创建一个子shell进程执行命令的。子shell进程的父进程号和交互式shell的进程号一致。

```bash
UID         PID   PPID  C STIME TTY          TIME CMD
root       [7175] 7171  0 03:14 pts/0    00:00:00 -bash
root       7228  [7175] 0 03:32 pts/0    00:00:00 ps -ef
```
这里`PPID`代表父进程的ID,看方括号括起来的部分是`ps -ef`的父进程是`-bash`。

## 如何停止进程

比如使用`sleep`开启一个后台进程，如何干掉它？

使用`kill`或者`killall`
```bash
[root@localhost ~]# sleep 100 &
[1] 7431
# 使用kill -s signal_name pid来杀死进程
[root@localhost ~]# kill -s INT 7431
```

执行后没什么输出，得通过`ps`命令看进程是否被成功干掉。

一般进程遵守接受的信号规范，这些信号有着特定的语义：


|Signal| Name |Description
----|-----|-----
|1 |HUP | Hangs up
2  |INT | Interrupts
3 | QUIT  |Stops running
9  |KILL | Unconditionally terminates
11 | SEGV  |Produces segment violation
15  |TERM  |Terminates if possible **(kill的默认信号，比如kill pid发送的是TERM信号)**
17  |STOP | Stops unconditionally, but doesn’t terminate
18  |TSTP | Stops or pauses, but continues to run in background
19  |CONT |Resumes execution after STOP or TSTP