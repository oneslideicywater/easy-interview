## 处理yaml
使用[yq](https://mikefarah.gitbook.io/yq/commands/evaluate)

### 直接输出
对象
```bash
$ yq e -n '.a.name="steve" | .a.position="ceo"' 
a:
  name: steve
  position: ceo
```
数组
```bash
$ yq e -n '.a[0]=1 |  .a[1]=2 | .a[2]=3' 
a:
  - 1
  - 2
  - 3
```

