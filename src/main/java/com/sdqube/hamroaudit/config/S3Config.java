package com.sdqube.hamroaudit.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 8/16/20 12:46 AM
 */
@Configuration
public class S3Config {
    @Value("${hamroaudit.aws.access_key_id}")
    private String awsId;

    @Value("${hamroaudit.aws.secret_access_key}")
    private String awsKey;

    @Value("${hamroaudit.s3.region}")
    private String region;

    @Bean
    public AmazonS3 s3client() {
        System.out.println("awsId = " + awsId);
        System.out.println("awsKey = " + awsKey);
        System.out.println("region = " + region);
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        System.out.println("s3Client.getS3AccountOwner() = " + s3Client.getS3AccountOwner());
        return s3Client;
    }
}