# find 实例


## 删除临时文件

删除`/`下所有以`.tmp`为后缀名的文件。

```bash
find / -name "*.tmp" -exec rm -f {} \;
```
## 批量修改配置文件


修改当前目录下所有`.yml`为后缀的文件的内容,将`127.0.0.1`改成`localhost`.
```bash
#!/bin/bash

for file in $(find . -name "*.yml")
do
   sed -i "s/127.0.0.1/localhost/g" $file  
done
```