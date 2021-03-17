# [原项目参考](https://github.com/guangee/demo_upload)

1. 原生上传
2. Minio上传
3. s3上传
## 修改新增功能
* 增加一个前端HTML页面
* 上传功能新增用户验证
* 上传结果返回形式分为HTML页面格式和JSON格式（可以用作Android应用的数据返回格式）

## 文件上传下载
1. SpringBoot原生文件上传下载，图片在线浏览
2. 使用[Minio](https://docs.min.io/cn/)作为文件服务器
3. 学习使用S3的[Java SDK](https://docs.aws.amazon.com/zh_cn/AmazonS3/latest/dev/ObjectOperations.html)上传
> MinIO 是一个基于Apache License v2.0开源协议的对象存储服务。它兼容亚马逊S3云存储服务接口，非常适合于存储大容量非结构化的数据，例如图片、视频、日志文件、备份数据和容器/虚拟机镜像等，而一个对象文件可以是任意大小，从几kb到最大5T不等。
> MinIO是一个非常轻量的服务,可以很简单的和其他应用的结合，类似 NodeJS, Redis 或者 MySQL。

```
//docker安装minio
 docker pull minio/minio
// docker启动文件服务器
 docker run -d -p 9000:9000 -e MINIO_ACCESS_KEY=admin -e MINIO_SECRET_KEY=admin123 -v /mnt/data/oss:/data minio/minio server /data 
// 简易启动方式
 docker run -p 9000:9000 minio/minio server /data 
```
## 配置说明
> + 端口：8081
> + 上传文件保存的文件夹：files
> + 日志保存的路径：log日志/文件服务器日志.log
> ### 账户设置
> + application.properties(全局配置文件)

> ```properties
> # 账户设置
> user.username = zhangheng
> user.password = 123456
> 
> # Minio的连接配置
> s3.s3Url = http://192.168.194.205:9000
> s3.accessKey = minioadmin
> s3.secretKey = minioadmin
> ```
> 可以在jar包外部创建一个新的application.properties(全局配置文件)修改其中的数据，并和jar包放在同一个文件夹内；在启动运行时，外部的application.properties(全局配置文件)会覆盖掉jar内部的配置文件，从而实现修改账户的目的

> #### 文件上传
> * HTML访问文件上传的访问地址：localhost:8081/
>   * 上传参数（post）
>       + username
>       + password
>       + file文件
>   + HTML页面提交地址：localhost:8081/uploadhtml/files
>   + 返回格式Model
> * 网址访问文件上传的访问地址：localhost:8081/uploadjson/files
>   * 上传参数（post）
>       + username
>       + password
>       + file文件
>   + 返回格式Json对象
> #### 文件下载
> + 文件展示及下载地址：localhost:8081/downloads/show/文件的全路径
>    + 这种直接在地址后面拼接即可
>    + 示例：`http://localhost:8081/downloads/show/image/星曦向荣网c4462@错误码.png`
> + 另一个下载方式：`http://localhost:8081/downloads/download`（这种方式下载时的文件名陈是download，有点bug）
>      + 参数（get）：
>           + name :输入文件的全路径，例：image/星曦向荣网c4462@错误码.png
> ##### 文件遍历(将上传文件夹的files遍历)
> + 以json数据返回， 访问路径：·localhost:8081/filelist/jsonlist/{type}·
>      * `@PathVariable("type") String type,
          @Nullable @RequestParam("username") String username,
          @Nullable @RequestParam("password") String password`
>           + 路径参数type有：all/image/audio/text/video/application/other这几种，不同参数对应遍历files文件夹里的不同文件夹
>           + username：账户名称
>           + password：账户密码
>      



### 日志的[logback-spring.xml文件](https://www.cnblogs.com/sxdcgaq8080/p/7885340.html)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="60 seconds" debug="false">

    <contextName>logback</contextName>
    <!--定义日志文件的存储地址 勿在 LogBack 的配置中使用相对路径-->
    <property name="log.path" value="E:\\log日志\\logback.log" />

    <!--输出到控制台-->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>
        <withJansi>true</withJansi>
        <encoder>
            <!--<pattern>%d %p (%file:%line\)- %m%n</pattern>-->
            <!--格式化输出：%d:表示日期    %thread:表示线程名     %-5level:级别从左显示5个字符宽度  %msg:日志消息    %n:是换行符-->
            <pattern>%black(控制台-) %red(%d{yyyy-MM-dd HH:mm:ss}) %green([%thread]) %highlight(%-5level) %boldMagenta(%logger) - %cyan(%msg%n)</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!--输出到文件-->
    <appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log.path}</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>log日志/%d{yyyy-MM-dd}_%i.log</fileNamePattern>
            <maxHistory>30</maxHistory>
            <totalSizeCap>1GB</totalSizeCap>
        </rollingPolicy>
        <encoder>
            <!--格式化输出：%d:表示日期    %thread:表示线程名     %-5level:级别从左显示5个字符宽度  %msg:日志消息    %n:是换行符-->
            <pattern>文件记录-%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
            <charset>GBK</charset>
        </encoder>
    </appender>

    <root level="info">
        <appender-ref ref="console" />
        <appender-ref ref="file" />
    </root>

    <logger name="com.sxd.controller"/>
    <logger name="com.sxd.util.LogTestController" level="WARN" additivity="false">
        <appender-ref ref="console" />
    </logger>

</configuration>
```
