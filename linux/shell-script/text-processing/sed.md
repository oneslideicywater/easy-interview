## sed

### 替换

普通的替换并不能改文件，要改文件，需要增加`-i`
```bash
#替换sample.txt全部被替换字段,/g代表全局替换，
sed -i 's/被替换字段/替换字段/g' sample.txt
```
如何替换特殊字符，比如字符串中含有斜杠“/”，实际上，sed不一定非使用“/”来分割,其他符号也可以(e.g.`@`)：

```bash
echo hello |sed 's@hello@world@'
```

Ref:
- [Sed - An Introduction and Tutorial by Bruce Barnett](https://www.grymoire.com/Unix/Sed.html)

### 删除带#注释行

```bash
$ cat hello 
# hello
# xx
hello
$ sed '/^#/d' hello 
hello
```