## 函数
### 声明 & 调用

```bash
#声明
function hello(){
	echo "hello"
}
#调用
hello
```
###  传参

```bash
function hello(){
	#代表脚本名称 如*.sh
	echo $0
 	#function parameter number 传入参数数量
	echo $#
	#first parameter 
	echo $1
	#second parameter
	echo $2
} 
hello hello1 hello2
```
###  参数值


```bash
function hello(){
	# function name	
	echo $0
 	# the count of the function parameters 
	echo $#
	#first parameter
	echo $1
	#second parameter
	echo $2
	# multiple parameter into single variable,same for $@ and $*
	all=$* 
	all=$@  
	# all="hello1 hello2"
	echo $all
} 
#把函数当成小脚本，这里使用command substitution-----$()
result=$(hello hello1 hello2) 
echo $result
```


|变量| 描述 |
|--|--|
| $0 | 第一个位置参数（脚本文件名或函数名） 
| $1| 第二个位置参数
|$?| 上个命令执行的返回值
| $* $@ | 全部参数，空格分隔
| $# | 参数数量 




### 返回值

```bash
function hello(){
    # 这里跟函数的返回值不一样，这里代表函数对应进程的状态返回值。非0代表异常退出
  	return 1
} 
hello 
# $?代表上一个脚本执行状态结果不是输出output，返回结果是0 成功
echo $?
```

