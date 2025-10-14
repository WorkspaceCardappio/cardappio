package br.com.cardappio.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;

@Component
public class S3StorageService {

    @Value("${AWS_ACCESS}")
    private String accessKey;

    @Value("${AWS_SECRET}")
    private String secretKey;

    private static final String BUCKET_NAME = "cardappio-bucket";

    public void saveFile(String file) {

        S3Client client = S3Client
                .builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(this::getCredentials)
                .build();

        createDefaultBucket(client);
    }

    private void createDefaultBucket(S3Client client) {

        boolean bucketAlreadyExists = client.listBuckets()
                .buckets()
                .stream()
                .map(Bucket::name)
                .anyMatch(BUCKET_NAME::equals);

        if (bucketAlreadyExists) {

            return;
        }

        createBucket(client);
    }

    private void createBucket(S3Client client) {

        client.createBucket(request -> request.bucket(BUCKET_NAME));
    }


    private AwsBasicCredentials getCredentials() {

        return AwsBasicCredentials.create(this.accessKey, this.secretKey);
    }
}
