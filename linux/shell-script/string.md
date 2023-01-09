## 字符串

### 常用例子

字符串操作包含判空，前缀，后缀，子字符串等操作。

1. 判空

```bash
if [ -z "$1" ]
then 
	echo "empty"
else 
	echo $1
fi
```
2. 判非空

```bash
# 如果 $args变量非空
if [ -n "$args" ]; then echo "args变量存在"; fi
```


3. 截取固定前缀

```bash
$ DOCKER_HOST='unix:///var/run/docker.sock'
# 意思是把前面的"unix://"去掉
$ echo ${DOCKER_HOST#unix://}
/var/run/docker.sock
```

4. 移除前后空格

```bash
output="    This is a test    "
echo "${output}" | awk '{gsub(/^ +| +$/,"")} {print $0}' // => This is a test
```


5. 多行文本

使用`"`划分多行。
```bash
echo "
service-ctl [batch-start|version] ...

service-ctl is utility tools for services which obey the convention.
"
```


6. 默认值

- https://unix.stackexchange.com/questions/286335/how-variables-inside-braces-are-evaluated
- https://unix.stackexchange.com/questions/30470/what-does-mean-in-a-shell-script/30472


### 美化输出

### 颜色列表


color|code
------|---------------
Black       | 0;30    
Dark Gray  |   1;30
$\color{red}{Red}$        |  0;31     
Light Red   |  1;31
$\color{green}{Green}$        |0;32     
Light Green  | 1;32
$\color{brown}{Brown/Orange}$ |0;33     
Yellow       | 1;33
$\color{blue}{Blue}$        | 0;34     
Light Blue  |  1;34
$\color{purple}{Purple}$      | 0;35    
$\color{purple}{Light Purple}$| 1;35
$\color{cyan}{Cyan}$        | 0;36    
Light Cyan  |  1;36
Light Gray  | 0;37    
White       |  1;37


### 输出颜色字符串

```bash
RED='\033[0;31m'
NC='\033[0m' # No Color
echo -e  "I ${RED}love${NC} Stack Overflow\n"
```
###  vim
批量缩进 

```bash
# 第10行到20行正向缩进
:10,20> 
# 反向缩进
:10,20< 
```