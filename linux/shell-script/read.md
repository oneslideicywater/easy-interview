## read命令从函数输出中读取数组变量

```bash
#!/bin/bash

# 接受一个数组(此处为变量arr), 并遍历输出
function iter(){
   local ele
   for ele; do
     echo $ele
   done
}

function test(){
   local -a binaries
   local -a arr=(ele,ele2,ele3)
   # 从函数iter输出中，按照空格分隔依次读取到临时变量binary, 然后追加到数组binaries中
   while IFS="" read -r binary; do binaries+=("$binary"); done < <(iter ${arr[*]})
   echo ${binaries[*]} # => [ele,ele2,ele3]
}
test
```