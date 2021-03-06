package com.zhangheng.file.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.zhangheng.file.entity.S3Config;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guanweiming
 */
@Configuration
public class AppConfig {

    @Autowired
    @Qualifier(value = "s3config")
    S3Config s3Config;

    private String url;
    private String access;
    private String secret;


    @Bean
    public MinioClient s3Client() throws InvalidPortException, InvalidEndpointException {
        url=s3Config.getS3Url();
        access=s3Config.getAccessKey();
        secret=s3Config.getSecretKey();
        return new MinioClient(url, access, secret);
    }

    @Bean
    public AmazonS3 s3() {
        url=s3Config.getS3Url();
        access=s3Config.getAccessKey();
        secret=s3Config.getSecretKey();
        AWSCredentials credentials = new BasicAWSCredentials(access, secret);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(url, Regions.US_EAST_1.name()))
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        return s3Client;
    }
}
