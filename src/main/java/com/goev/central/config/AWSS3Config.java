package com.goev.central.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.goev.central.constant.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@Slf4j
public class AWSS3Config {
    @Bean
    public AmazonS3 getAmazonS3Client(ApplicationConstants applicationConstants) {
        AWSCredentials credentials = new BasicAWSCredentials(
                ApplicationConstants.AWS_ACCESS_KEY_ID,
                ApplicationConstants.AWS_ACCESS_KEY_SECRET
        );
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(clientConfiguration)
                .withRegion(Regions.AP_SOUTH_1)
                .enablePathStyleAccess()
                .disableChunkedEncoding()
                .build();
    }
}
