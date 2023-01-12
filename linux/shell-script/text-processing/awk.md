
## gawk


### 删除带#注释行和空行
```c
cat /var/lib/pgsql/11/data/postgresql.conf  |gawk '$0 !~ /^#/ {print $0}' |gawk -F '#' '{print $1}' |gawk 'NF'
```
参考：[Awk to skip the blank lines](https://stackoverflow.com/questions/11687216/awk-to-skip-the-blank-lines)


