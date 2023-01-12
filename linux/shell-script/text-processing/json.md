## 处理JSON
处理json使用jq

### 安装jq
```bash
yum install epel-release
yum install jq
```
### 常用举例

```bash
[root@bogon ~]# jq "." sample.json 
{
  "name": "johey",
  "address": {
    "city": "St.Paul",
    "province": "Minnesota"
  },
  "infantName": [
    "LiGouDan",
    "LiDanDan"
  ]
}
[root@bogon ~]# jq ".name" sample.json 
"johey"
[root@bogon ~]# jq ".address" sample.json 
{
  "city": "St.Paul",
  "province": "Minnesota"
}
[root@bogon ~]# jq ".infantName" sample.json 
[
  "LiGouDan",
  "LiDanDan"
]
[root@bogon ~]# jq ".infantName[1]" sample.json 
"LiDanDan"
[root@bogon ~]# jq ".infantName|length" sample.json 
2

```
