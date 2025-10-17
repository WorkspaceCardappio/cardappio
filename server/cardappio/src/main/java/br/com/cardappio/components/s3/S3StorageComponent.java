package br.com.cardappio.components.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class S3StorageComponent {

    @Value("${AWS_ACCESS}")
    private String accessKey;

    @Value("${AWS_SECRET}")
    private String secretKey;

    private final S3Presigner s3Presigner;

    private static final String BUCKET_NAME = "cardappio-bucket";

    public void saveFile(MultipartFile file, String oldFileName) {

        S3Client client = buildClient();

        createDefaultBucket(client);

        if (Objects.nonNull(oldFileName)) {

            deleteMatchingObject(oldFileName, client);
        }

        PutObjectRequest request = buildRequest(file);

        try {
            client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao ler o arquivo para upload", e);
        } catch (S3Exception e) {
            throw new RuntimeException("Falha ao enviar para S3: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    public String getPresignedUrl(String objectKey) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(objectKey)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }

    public String getKey(MultipartFile file) {

        return LocalDate.now() + " - " + file.getOriginalFilename();
    }

    private void deleteMatchingObject(String oldFileName, S3Client client) {

        if (objectExists(oldFileName, client)) {

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(oldFileName)
                    .build();

            client.deleteObject(deleteRequest);
        }
    }

    private boolean objectExists(String oldFileName, S3Client client) {

        try {

            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(oldFileName)
                    .build();

            client.headObject(headObjectRequest);

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    private S3Client buildClient() {
        return S3Client
                .builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(this::getCredentials)
                .build();
    }

    private PutObjectRequest buildRequest(MultipartFile file) {
        return PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(getKey(file))
                .contentType(file.getContentType())
                .metadata(Map.of("original-filename", file.getOriginalFilename()))
                .build();
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


    public AwsBasicCredentials getCredentials() {

        return AwsBasicCredentials.create(this.accessKey, this.secretKey);
    }
}
