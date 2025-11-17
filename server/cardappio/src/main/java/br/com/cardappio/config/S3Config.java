package br.com.cardappio.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@RequiredArgsConstructor
public class S3Config {

    @Value("${AWS_ACCESS}")
    private String accessKey;

    @Value("${AWS_SECRET}")
    private String secretKey;

    @Bean
    public S3Client s3Client() {

        return S3Client
                .builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(this::getCredentials)
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {

        return S3Presigner
                .builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(this.accessKey, this.secretKey)
                ))
                .build();
    }

    public AwsBasicCredentials getCredentials() {

        return AwsBasicCredentials.create(this.accessKey, this.secretKey);
    }

}
