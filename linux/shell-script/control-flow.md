## 流程控制



### if-else

```shell
#注意空格[ x ],而不是[x]
if [ $variableName -gt 5 ]
then 
   # something to do   
else
   # something to do
fi
```

下面的表格给出条件判断使用的表达式：

比较条件 | 描述
------|-------
n1 -eq n2 | n1 = n2
n1 -ge n2 | n1 >=n2
n1 -gt n2 | n1 > n2
n1 -le n2 | n1 <=n2
n1 -lt n2 | n1 < n2
n1 -ne n2 | n1 !=n2


### For

#### for-each

> command substitution --------`$()` or ``   将命令的输出赋值到一个变量

```shell
#查看root下的文件，file代表每个文件名
files=$(ls /root)
for file in $files
do
  echo $file
done
```
上面的`files=$(ls /root)`也可以写成：

```bash
files=`ls /root`
```

#### C风格遍历

```shell
for ((i=1; i<=10; i++))
do
	echo "The next number is $i"
done

```

## 命令执行的多种姿势

### 单行执行多条命令

使用`;`分割命令
```bash
ls;echo hello
```

### 多行命令

在每行后面加`\`。
```bash
[root@localhost ~]# echo "what's your \
> name ? \
> " 
what's your name ? 
```
