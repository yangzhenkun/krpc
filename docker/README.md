### 0.文件解释
krpc目录下是krpc容器镜像构建所需的文件(krpc.tar.gz是krpc服务端以来端的jar)

user-server 是以user服务为例，发布docker images

### 1.准备数据 服务压缩包
以user服务为例
```
--user
    --lib
        服务所有的jar包
    --conf
        log4j.xml
        service.xml
```
任何服务以上面的目录为例，顶级目录以服务名字命名，下面有固定的两个文件，lib和conf。lib中放的是改服务所有的jar包。conf下是krpc提供的两个配置文件。

将整理好的文件目录 打成对一个的压缩包，例如user.tar.gz

### 2.编写dockerfile

```
from yangzhenkun/krpc:1.0.0
maintainer ****  ***@**.com
# 复制组织好的服务压缩包
add  user.tar.gz /opt/krpc/service/

workdir /opt/krpc/bin

expose 17666

# 最后一个参数是 服务名字
entrypoint ["java","-jar","com.krpc.server-0.0.1.jar","user"]

```
上面是dockerfile的模板

针对不同的服务，需要改动两处。

1.将add 第一个参数换成对应符合的压缩包。

2.entrypoint 最后一个参数 "user" 换成对应的服务名字即刻

### 3.编译 启动

```
编译
dokcer buld -t 服务名:版本号 .

启动
docker run -d -p 要监听的端口:17666 服务名
```
