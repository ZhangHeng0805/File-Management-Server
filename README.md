# [原项目参考](https://github.com/guangee/demo_upload)
## [文件管理系统成品下载(无需数据库)](https://github.com/ZhangHeng0805/File-Management-Server/releases/download/V1.0/default.zip)
## [该项目的最新jar包](https://github.com/ZhangHeng0805/File-Management-Server/releases/download/V1.3/My-Server.jar)
1. 原生上传
2. Minio上传
3. s3上传
## 修改新增功能
* 增加一个前端HTML页面
* 上传功能新增用户验证
* 上传结果返回形式分为HTML页面格式和JSON格式（可以用作Android应用的数据返回格式）
* 新增定位信息共享管理
* 新增利用Netty创建的聊天室[项目链接](https://github.com/ZhangHeng0805/Netty)

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
> + 上传文件保存的文件夹（根目录）：files/
>   + 根据文件类型的不同，会分为不同的文件夹进行保存
>       + /image/：图像文件夹
>       + /audio/：音频文件夹
>       + /video/：视频文件夹
>       + /text/：文本文件夹
>       + /application/：应用文件夹
>       + /other/：其他类型文件夹
> + 日志保存的路径：log日志/
> + 软件更新的路径：update/软件名/软件安装包
> ### 账户设置
> + application.properties(全局配置文件)
> ```properties
> # 账户设置
> user.username = zhangheng
> user.password = 123456
> 
> # Minio的连接配置（此处可以不用配置）
> s3config.s3Url = http://192.168.194.205:9000
> s3config.accessKey = minioadmin
> s3config.secretKey = minioadmin
> ```
> 可以在jar包外部创建一个新的application.properties(全局配置文件)修改其中的数据，并和jar包放在同一个文件夹内；在启动运行时，外部的application.properties(全局配置文件)会覆盖掉jar内部的配置文件，从而实现修改账户的目的
> #### 文件上传
> * HTML访问文件上传的访问地址：localhost:8081/files
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
> + 文件展示及下载地址(Get)：`localhost:8081/downloads/show/文件的全路径`
>    + 这种直接在地址后面拼接即可
>    + 示例：`http://localhost:8081/downloads/show/image/星曦向荣网c4462@错误码.png`
> + 另一个下载方式：`http://localhost:8081/downloads/download`（这种方式下载时的文件名陈是download，有点bug）
>      + 参数（get）：
>           + name :输入文件的全路径，例：image/星曦向荣网c4462@错误码.png
> #### 文件遍历(将上传文件夹files的进行遍历)
> + 以json数据返回(Post) 访问路径：`localhost:8081/filelist/jsonlist/{type}`
>      * 参数：
`
            @PathVariable("type") String type,
            @Nullable @RequestParam("username") String username,
            @Nullable @RequestParam("password") String password,
`
>         + 路径参数type有：`all/image/audio/text/video/application/other`这几种，不同参数对应遍历files文件夹里的不同文件夹
>         + username：账户名称
>         + password：账户密码
> + 以Model形式返回给前端页面(Post) 访问路径：`localhost:8081/filelist/htmllist`
>   + 参数：
`
            @Nullable @RequestParam("type") String type,
            @Nullable @RequestParam("username") String username,
            @Nullable @RequestParam("password") String password,        
`
>       + type:`all/image/audio/text/video/application/other`这几种，不同参数对应遍历files文件夹里的不同文件夹
>       + username：账户名称
>       + password：账户密码
### 项目数据库连接配置
```properties
#数据库配置
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://localhost:3306/jpa?serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
### 本项目的数据库jpa的建表SQL语句
```sql
/*
Navicat MySQL Data Transfer

Source Server         : 张恒
Source Server Version : 50535
Source Host           : localhost:3306
Source Database       : jpa

Target Server Type    : MYSQL
Target Server Version : 50535
File Encoding         : 65001

Date: 2021-05-07 21:16:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for chatconfig
-- ----------------------------
DROP TABLE IF EXISTS `chatconfig`;
CREATE TABLE `chatconfig` (
  `id` varchar(11) NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `port` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `phone` varchar(11) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for delete_image
-- ----------------------------
DROP TABLE IF EXISTS `delete_image`;
CREATE TABLE `delete_image` (
  `image_id` int(11) NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`image_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_image` varchar(255) DEFAULT NULL,
  `goods_type` varchar(255) DEFAULT NULL,
  `goods_introduction` varchar(255) DEFAULT NULL,
  `goods_month_much` int(11) unsigned zerofill DEFAULT NULL,
  `goods_price` double(10,2) DEFAULT NULL,
  `store_name` varchar(255) DEFAULT NULL,
  `store_id` varchar(255) DEFAULT NULL,
  `time` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`goods_id`),
  KEY `goods_name` (`goods_name`(191))
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for goods_list
-- ----------------------------
DROP TABLE IF EXISTS `goods_list`;
CREATE TABLE `goods_list` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_price` double(10,2) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `list_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `list_id` (`list_id`),
  KEY `goods_id` (`goods_id`),
  KEY `store_id` (`store_id`),
  CONSTRAINT `goods_list_ibfk_1` FOREIGN KEY (`list_id`) REFERENCES `submit_goodslist` (`submit_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `goods_list_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `goods_list_ibfk_3` FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for location
-- ----------------------------
DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `username` varchar(100) NOT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `state` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for merchants
-- ----------------------------
DROP TABLE IF EXISTS `merchants`;
CREATE TABLE `merchants` (
  `phonenum` varchar(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `store_name` varchar(255) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `store_introduce` varchar(255) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`phonenum`),
  KEY `name` (`name`(191),`store_name`(191),`store_introduce`(191),`time`),
  KEY `store_name` (`store_name`(191)),
  KEY `store_id` (`store_id`),
  CONSTRAINT `merchants_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for phone_message
-- ----------------------------
DROP TABLE IF EXISTS `phone_message`;
CREATE TABLE `phone_message` (
  `phonenum` varchar(100) NOT NULL,
  `model` varchar(255) DEFAULT NULL,
  `sdk` varchar(255) DEFAULT NULL,
  `release` varchar(255) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`phonenum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for stores
-- ----------------------------
DROP TABLE IF EXISTS `stores`;
CREATE TABLE `stores` (
  `store_id` int(11) NOT NULL AUTO_INCREMENT,
  `store_name` varchar(255) DEFAULT NULL,
  `store_image` varchar(255) DEFAULT NULL,
  `boss_name` varchar(255) DEFAULT NULL,
  `start_time` varchar(100) DEFAULT NULL,
  `store_introduce` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`store_id`),
  KEY `store_name` (`store_name`(191)) USING BTREE,
  CONSTRAINT `stores_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `merchants` (`store_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for submit_goodslist
-- ----------------------------
DROP TABLE IF EXISTS `submit_goodslist`;
CREATE TABLE `submit_goodslist` (
  `submit_id` varchar(50) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `count_price` double(20,2) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`submit_id`),
  KEY `phone` (`phone`),
  CONSTRAINT `submit_goodslist_ibfk_1` FOREIGN KEY (`phone`) REFERENCES `customer` (`phone`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(100) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

```
### 日志的[logback-spring.xml文件](https://www.cnblogs.com/sxdcgaq8080/p/7885340.html)(日志配置)
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
