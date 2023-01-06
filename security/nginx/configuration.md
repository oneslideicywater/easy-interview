## nginx 安全配置基线

<!-- TOC -->

- [nginx 安全配置基线](#nginx-%E5%AE%89%E5%85%A8%E9%85%8D%E7%BD%AE%E5%9F%BA%E7%BA%BF)
    - [设置专门管理账户](#%E8%AE%BE%E7%BD%AE%E4%B8%93%E9%97%A8%E7%AE%A1%E7%90%86%E8%B4%A6%E6%88%B7)
    - [禁止指定User-Agent](#%E7%A6%81%E6%AD%A2%E6%8C%87%E5%AE%9Auser-agent)
    - [禁止特定URL](#%E7%A6%81%E6%AD%A2%E7%89%B9%E5%AE%9Aurl)
    - [方法限制](#%E6%96%B9%E6%B3%95%E9%99%90%E5%88%B6)
    - [禁止数据包中包含Nginx版本信息](#%E7%A6%81%E6%AD%A2%E6%95%B0%E6%8D%AE%E5%8C%85%E4%B8%AD%E5%8C%85%E5%90%ABnginx%E7%89%88%E6%9C%AC%E4%BF%A1%E6%81%AF)
    - [URL配置参数过滤关键字](#url%E9%85%8D%E7%BD%AE%E5%8F%82%E6%95%B0%E8%BF%87%E6%BB%A4%E5%85%B3%E9%94%AE%E5%AD%97)
    - [强制要求Referer](#%E5%BC%BA%E5%88%B6%E8%A6%81%E6%B1%82referer)
    - [限制客户端的请求带宽、请求频率以及请求连接数](#%E9%99%90%E5%88%B6%E5%AE%A2%E6%88%B7%E7%AB%AF%E7%9A%84%E8%AF%B7%E6%B1%82%E5%B8%A6%E5%AE%BD%E8%AF%B7%E6%B1%82%E9%A2%91%E7%8E%87%E4%BB%A5%E5%8F%8A%E8%AF%B7%E6%B1%82%E8%BF%9E%E6%8E%A5%E6%95%B0)
        - [使用ab测试工具验证](#%E4%BD%BF%E7%94%A8ab%E6%B5%8B%E8%AF%95%E5%B7%A5%E5%85%B7%E9%AA%8C%E8%AF%81)
    - [网站根目录权限限制](#%E7%BD%91%E7%AB%99%E6%A0%B9%E7%9B%AE%E5%BD%95%E6%9D%83%E9%99%90%E9%99%90%E5%88%B6)

<!-- /TOC -->

### 设置专门管理账户

设置专门管理账户。在nginx.conf配置文件中，修改User配置项，对Nginx应用设立专门的服务账户。

```bash
# nginx进程的启动用户为nginx
user nginx;
```


### 禁止指定User-Agent

禁止指定`User-Agent`,防止网络爬虫抓取数据。在配置文件中通过判断`Http_User_Agent`变量过滤`User-Agent`,禁止网络爬虫工具抓取网站数据

```bash
server {
        listen       80;
        listen       [::]:80;
        server_name  _;
        root         /usr/share/nginx/html;

        # Load configuration files for the default server block.
        include /etc/nginx/default.d/*.conf;


        #禁止Scrapy等工具的抓取
        if ($http_user_agent ~* (Scrapy|Curl|HttpClient)) {
                return 403;
        }
 
        #禁止指定UA及UA为空的访问
        if ($http_user_agent ~ "WinHttp|WebZIP|FetchURL|node-superagent|java/|
                FeedDemon|Jullo|JikeSpider|Indy Library|Alexa Toolbar|AskTbFXTV|AhrefsBot|
                CrawlDaddy|Java|Feedly|Apache-HttpAsyncClient|UniversalFeedParser|ApacheBench|
                Microsoft URL Control|Swiftbot|ZmEu|oBot|jaunty|Python-urllib|
                lightDeckReports Bot|YYSpider|DigExt|HttpClient|MJ12bot|heritrix|EasouSpider|Ezooms|BOT/0.1|
                YandexBot|FlightDeckReports|Linguee Bot|^$" ) {
                return 403;             
        }
    }
```

测试curl访问:

```bash
[root@localhost conf.d]# curl -iv localhost:80
* About to connect() to localhost port 80 (#0)
*   Trying ::1...
* Connected to localhost (::1) port 80 (#0)
> GET / HTTP/1.1
> User-Agent: curl/7.29.0  # curl访问被禁止，因为User-Agent不被允许
> Host: localhost
> Accept: */*
> 
< HTTP/1.1 403 Forbidden
HTTP/1.1 403 Forbidden
< Server: nginx/1.20.1
Server: nginx/1.20.1
< Date: Tue, 22 Nov 2022 08:24:20 GMT
Date: Tue, 22 Nov 2022 08:24:20 GMT
< Content-Type: text/html
Content-Type: text/html
< Content-Length: 153
Content-Length: 153
< Connection: keep-alive
Connection: keep-alive

< 
<html>
<head><title>403 Forbidden</title></head>
<body>
<center><h1>403 Forbidden</h1></center>
<hr><center>nginx/1.20.1</center>
</body>
</html>
```

### 禁止特定URL

禁止特定URL。修改配置文件中过滤特定URL,防止通过非法URL访问非授权文件。

```bash
        # 禁止访问以.sql为后缀的文件类型
        location ~* \.(bak|save|sh|sql|mdb|svn|git|old)$ {
                # 如果访问重定向到404
                rewrite ^/(.*)$ /404.html permanent;
        }
```

测试：

```bash
[root@localhost html]# ls
404.html  50x.html  en-US  hello.sh  hello.test  hello.txt  icons  img  index.html  nginx-logo.png  poweredby.png
[root@localhost html]# pwd
/usr/share/nginx/html
```
通过浏览器访问`localhost:80/hello.txt`可以，但是`localhost:80/hello.sh`不可以。

### 方法限制

指定特定Http方法。 一般网站只需开启Get和Post 方法，通过配置文件中通过Request   Method变量指定访问网站的Http方法。


```bash
        # only http method GET,HEAD,POST are allowed
        if ($request_method !~ ^(GET|HEAD|POST)$) {
                return 404;
        }
     
        location /httpbin/ {
            proxy_pass http://httpbin.org/;
        }
```

这里的配置中我们引入了httpbin作为测试服务器，它支持各种http测试接口。

为了对比出效果，请分别测试以下http请求：

```bash
PUT http://192.168.10.87/httpbin/get  # => 通过
GET http://httpbin.org/get            # => 通过
PUT http://192.168.10.87/httpbin/put  # => 未通过，404
PUT http://httpbin.org/put            # => 通过
```

### 禁止数据包中包含Nginx版本信息

禁止数据包中包含Nginx版本信息，防范针对特定版 本的恶意攻击。在配置文件中添加`Server_Tokens Off`项关闭版本显示。


更新配置项:

```bash
http {
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    # ...
    # 这里取消掉版本号输出
    server_tokens off;
    server {
        listen       80;
        listen       [::]:80;
        server_name  _;
        root         /usr/share/nginx/html;
    }
}   
```


nginx 获取版本可以有多种途径，比如刚刚我们在配置中禁止了curl访问，但是返回的403信息中仍然泄露了nginx的版本。

```bash
[root@localhost html]# curl localhost:80
<html>
<head><title>403 Forbidden</title></head>
<body>
<center><h1>403 Forbidden</h1></center>
<hr><center>nginx/1.20.1</center>
</body>
</html>
```



更新配置后发现虽然还是403，但是输出的信息中已经不包含nginx版本信息了。
```bash
[root@localhost html]# curl localhost:80
<html>
<head><title>403 Forbidden</title></head>
<body>
<center><h1>403 Forbidden</h1></center>
<hr><center>nginx</center>
</body>
</html>
```

### URL配置参数过滤关键字

URL配置参数过滤关键字，防范SQL注入攻击。在配 置文件中通过Request_Uri和Query_String变量过滤URL中关键字。

```bash
        if ($query_string ~* (union.*|select.*|insert.*|delete.*|create.*|drop.*)) {
                rewrite ^/(.*)$ /warning ;
        }


        location /warning {
            return 403 "sql injection inspected! $query_string";
        }
```

测试：
```bash
GET http://192.168.10.87/httpbin/anything?hello=drop database hello; 

#=> 403 sql injection inspected! hello=drop%20database%20hello;
```


### 强制要求Referer

强制要求Referer。禁止特定或空Referer访问网站资源，防止资源盗用和恶意请求， 在配置文件中通过Valid  Referer变量过滤掉指定Referer。

```bash
        if ($http_referer = "" ) {  

                return 403;
        }
```

- 白名单

只允许`Referer:https://*.hack.com`或`Referer:http//*.hack.com` 访问。

```bash

           valid_referers *.hack.com;
            
            if ($invalid_referer) {
                 return 403;
            }
```

- 黑名单

不允许黑名单中的访问。

黑名单列表：` *.hack.com *.example.com`



```bash
            # black list
            # by default, referer is in the blacklist
            set $is_black 1;
            valid_referers *.hack.com *.example.com;

            if ($invalid_referer) {
                # referer not in blacklist goes here 
                set $is_black 0;
            }
           # in the blacklist, reject the access
           if ($is_black){
                return 403;
           }
            # referer not in blacklist goes here 
```
测试：

```bash
Referer: http://test.hack.com => 拒绝
Referer: http://test.example.com => 拒绝
Referer: http://test.example2.com => 通过
```


使用Postman测试:

```bash
GET http://192.168.10.87/httpbin/anything #=> 不通过
GET -H "Referer=xx" http://192.168.10.87/httpbin/anything #=>通过
```

### 限制客户端的请求带宽、请求频率以及请求连接数

限制客户端的请求带宽、请求频率以及请求连接数，防范DDoS攻击。在配置文件中通过HttpLimitZoneModule变量限制并发访问数量和HttpLimitReqModule变量限制每秒
请求次数。

```bash
http {
    limit_conn_zone $binary_remote_addr zone=one:10m;

    server{
        # 业务系统限制每个IP连接数为1，传输速率为100k
        location /httpbin/ {
            limit_conn one 1;
            limit_rate 100k;
            proxy_pass http://httpbin.org/;
        }
    }
}    
```


#### 使用ab测试工具验证

- 并发数验证

设置并发数为1，没有错误返回
```bash
[root@localhost html]# ab -n 10 -c 1 http://localhost/httpbin/anything
Server Software:        nginx
Server Hostname:        localhost
Server Port:            80

Document Path:          /httpbin/anything
Document Length:        345 bytes

Concurrency Level:      1
Time taken for tests:   4.397 seconds
Complete requests:      10
Failed requests:        0
Write errors:           0
Total transferred:      5600 bytes
HTML transferred:       3450 bytes
Requests per second:    2.27 [#/sec] (mean)
Time per request:       439.676 [ms] (mean)
Time per request:       439.676 [ms] (mean, across all concurrent requests)
Transfer rate:          1.24 [Kbytes/sec] received
```
增大并发数：

```bash
[root@localhost html]# ab -n 10 -c 2 http://localhost/httpbin/anything
Server Software:        nginx
Server Hostname:        localhost
Server Port:            80

Document Path:          /httpbin/anything
Document Length:        345 bytes

Concurrency Level:      2
Time taken for tests:   0.942 seconds
Complete requests:      10
Failed requests:        8
   (Connect: 0, Receive: 0, Length: 8, Exceptions: 0)
Write errors:           0
Non-2xx responses:      8  #=> 因为有并发数限制，当超过1会出现错误的状态码。
Total transferred:      32168 bytes
```

- 传输速率限制

传输速率的限制并不精确，也就是说稳定在指定值左右。

```bash
[root@localhost html]# ab -n 3 -c 1 http://localhost/httpbin/bytes/200000
Transfer rate:          61.58 [Kbytes/sec] received # 无论怎么增加并发字节数量，传输速率始终在100k以下
```

### 网站根目录权限限制

网站根目录权限限制。按照网站需求开启指定目录上传权限，禁止上传脚本文件，在配置文件中通过Location变量进行过滤。


```bashW
        location ~* /(uploads|images)/.*\.(php|php2|php3|php4|php5|phpm|phpml|phtml|phtm|phps|pht|asp|aspx|asa|cer|cdx|ashx|exe|sh|bash|jsp|jspx|jspa|html|web.config|cfm|cgi|war|ashx|htaccess|py|pl)$
        {

                deny all;
        }
```

测试效果：

```bash
http://192.168.10.87/uploads/a.php  # => 403 forbidden
http://192.168.10.87/httpbin/uploads/a.html #=> 403 forbidden
```