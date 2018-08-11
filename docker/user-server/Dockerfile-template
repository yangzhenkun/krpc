from yangzhenkun/krpc:1.0.0
maintainer yasin 1334036616@qq.com
# 复制组织好的服务压缩包
add  user.tar.gz /opt/krpc/service/

workdir /opt/krpc/bin

expose 17666

# 最后一个参数是 服务名字
entrypoint ["java","-jar","com.krpc.server-0.0.1.jar","user"]

