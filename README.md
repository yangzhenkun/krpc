![](https://img.shields.io/badge/Java-1.8-green.svg)
[![Build Status](https://travis-ci.org/yangzhenkun/krpc.svg?branch=master)](https://travis-ci.org/yangzhenkun/krpc)
[![license](https://raw.githubusercontent.com/yangzhenkun/krpc/master/MIT.png)](https://github.com/yangzhenkun/krpc/blob/master/LICENSE)

![Logo](https://raw.githubusercontent.com/yangzhenkun/krpc/master/logo.png)

### 如何使用

编译好的环境 https://github.com/yangzhenkun/krpc/blob/master/krpc.rar

#### 1.服务端
解压后server文件夹中就是服务端环境，如demo所示，server/service中有一个user文件，就是我们部署的user服务，下面有两个必须的文件夹conf（配置文件）

log4j.xml是该服务日志的标准的log4j配置文件，如果想修改日志路径
```java
<!-- 输出日志到文件  每天一个文件 -->
  	<appender name="dailyRollingFile"
  		class="org.apache.log4j.DailyRollingFileAppender">
  		<param name="Threshold" value="info"></param>
  		<param name="ImmediateFlush" value="true"></param>
  		<param name="File" value="D:/opt/krpc/log/user/krpc.log"></param>
  		<param name="DatePattern" value="'.'yyyy-MM-dd'.log'"></param>
  		<layout class="org.apache.log4j.PatternLayout">
  			<param name="ConversionPattern" value="[%d{yyyy-MM-dd HH:mm:ss\} %-5p] [%t] {%c:%L}-%m%n"></param>
  		</layout>
  	</appender> 
```
修改<param name="File" value="D:/opt/krpc/log/user/krpc.log"></param>值即可

server.xml文件为服务的配置文件

```xml
<configuration>


<!-- 连接相关参数 -->
	<property>
		<!-- 该服务监听的本机IP和tcp端口 -->
		<connection ip="127.0.0.1" port="17999" timeout="3000"/>
		<!-- 最大接受请求（字节,默认值是1024） -->
		<netty maxBuf="655350"/>
	</property>
	
	
	<!-- 所有能提供服务的接口 -->
	<services>
		<!-- name的值为接口名字，客户端通过代理工程生成接口时需要, impl值为具体实现类全路径  -->
		<service name="userService" impl="com.a123.service.user.impl.UserServiceImpl"/>
	</services>
	
	
	
	</configuration>

```
一个服务中所有对外的接口必须在services中配置,规则如上注释所示。

编写服务端代码会需要一个接口包和一个实现包，将源码生成jar包后，放在server/service/user/lib中,这里以user服务为例，你可以自己定义这个文件名的名字来标识这个服务。

**启动** 
启动在server/bin里面，执行
```java
java -jar com.krpc.server-0.0.1.jar 服务名
```
命令，查看日志，如果看到  启动成功，监听端口***  的日志，恭喜你，服务端启动成功。

#### 2.客户端
需要引入KRPC客户端，由于项目还没有发布到maven中央仓库，用户可以将client包发布到自己本地，或者直接将该com.krpc.client-0.0.1.jar包加入项目。

使用需要先调用KRPC.init("client配置文件")进行初始化
配置在client/client.xml中

```java

	<!-- 所连接的服务配置文件 name的值可以任意指定，只要在ProxyFactory.create的第二个参数值相同即可 -->
    <!--用户服务 -->
	<Service name="user" id="1" maxThreadCount="50">
		
        <Loadbalance>
			<!-- 请求超时时间(ms) -->
            <Server timeout="10000">
                <addr name="user1" host="127.0.0.1" port="17666" maxCurrentUser="50"/>
            </Server>
        </Loadbalance>
    </Service>
```

sercie的name的值必须
```java
 
	UserService service = ProxyFactory.create(UserService.class, "user", "userService");

```
跟create中第二个参数一直。第三个参数为服务端实现的名字，需要跟服务端的配置文件一直。


#### 3.未完成的事情
<del>3.1 (已解决) <del>接口包参数中目前不支持基本类型，必须使用其包装类

3.2 大访问量下压测  server端会发生dang住，或许是缓冲区相关问题

3.3 客户端大调用量下  需要提高client请求的效率,或许可以做连接池

3.4 增加安全SOA  扩展方案


#### 4.要注意的事情

krpc.rar中的krpc相关包均为上面源码中生成

配置文件模板：https://github.com/yangzhenkun/krpc/tree/master/config_file_template


**这个RPC框架是我自己造轮子学习的，并没有真实在生产环境中使用，所以看到这个项目，希望大家可以交流学习技术，提交pr,issue，完善其性能及功能,争取让它可以用于生产环境**

我个人博客中关于RPC及这个KRPC的源码原理解析会在这个专栏中写道
https://blog.csdn.net/column/details/22012.html
