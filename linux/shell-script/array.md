
## 数组
### 索引数组
```bash
declare -a arr=(ele1 ele2 ele3)

echo "arr length: ${#arr[*]}"
echo "arr content: ${arr[*]}"
for ele in ${arr[*]}
do
  echo $ele
done
```
### 追加元素

```bash
# 声明一个空数组
iplist=()

# 按行读取iplist.txt文件,每行一个元素
for ip in $(gawk '{ print $1}' iplist.txt)
do
  # 向数组尾部添加一个元素
   iplist+=($ip)
done

# 输出数组
for value in "${iplist[@]}"
do
     echo $value
done
```
### 合并数组

```bash
#!/bin/bash

# Declare two string arrays
arrVar1=("John" "Watson" "Micheal" "Lisa")
arrVar2=("Ella" "Mila" "Abir" "Hossain")

# Add the second array at the end of the first array
arrVar=(${arrVar1[@]} ${arrVar2[@]})
# 元素修改
arrVar[3]=oneslide
# Iterate the loop to read and print each array element
for value in "${arrVar[@]}"
do
     echo $value
done
```

## 关联数组

>  Indexed  arrays are referenced using integers (including arithmetic expressions)  and are zero-based; associative arrays are referenced using arbitrary strings.

声明数组使用`declare -a`, 但声明关联数组（可以理解为map）使用`declare -A`.

```bash
declare -A map

map["math"]=99
map['lang']=80
map["english"]=20

echo "map element count: ${#map[*]}"
for item in ${map[*]} ; do
    echo $item
done

```
输出：

```bash
map element count: 3
20
80
99
```




