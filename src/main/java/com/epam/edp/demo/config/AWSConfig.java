package com.epam.edp.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWSConfig {
    @Value("${aws.region}")
    private String region;

    @Bean
    S3Client s3Client() {

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .build();
    }
}
