# [原项目查考](https://github.com/guangee/demo_upload)

1. 原生上传
2. Minio上传
3. s3上传
## 修改新增功能
* 增加一个前端HTML页面
* 上传功能新增用户验证
* 上传结果返回形式分为HTML页面格式和JSON格式（可以用作Android应用的数据返回格式）

# 学习目标：文件上传下载

1. SpringBoot原生文件上传下载，图片在线浏览
2. 使用[Minio](https://docs.min.io/cn/)作为文件服务器
3. 学习使用S3的[Java SDK](https://docs.aws.amazon.com/zh_cn/AmazonS3/latest/dev/ObjectOperations.html)上传

```
//docker安装minio
docker pull minio/minio
// docker启动文件服务器
 docker run -d -p 9000:9000 -e MINIO_ACCESS_KEY=admin -e MINIO_SECRET_KEY=admin123 -v /mnt/data/oss:/data minio/minio server /data 
// 简易启动方式
docker run -p 9000:9000 minio/minio server /data 
```

> MinIO 是一个基于Apache License v2.0开源协议的对象存储服务。它兼容亚马逊S3云存储服务接口，非常适合于存储大容量非结构化的数据，例如图片、视频、日志文件、备份数据和容器/虚拟机镜像等，而一个对象文件可以是任意大小，从几kb到最大5T不等。
>
> MinIO是一个非常轻量的服务,可以很简单的和其他应用的结合，类似 NodeJS, Redis 或者 MySQL。
