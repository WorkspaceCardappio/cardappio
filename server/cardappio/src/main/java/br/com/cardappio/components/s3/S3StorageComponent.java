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

import java.io.IOException;
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

    @Value("${AWS_BUCKET}")
    private String bucketName;

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

    public String getKey(MultipartFile file) {

        return LocalDate.now() + " - " + file.getOriginalFilename();
    }

    private void deleteMatchingObject(String oldFileName, S3Client client) {

        if (objectExists(oldFileName, client)) {

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(oldFileName)
                    .build();

            client.deleteObject(deleteRequest);
        }
    }

    private boolean objectExists(String oldFileName, S3Client client) {

        try {

            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
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
                .bucket(bucketName)
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
                .anyMatch(bucketName::equals);

        if (bucketAlreadyExists) {

            return;
        }

        createBucket(client);
    }

    private void createBucket(S3Client client) {

        client.createBucket(request -> request.bucket(bucketName));
    }


    public AwsBasicCredentials getCredentials() {

        return AwsBasicCredentials.create(this.accessKey, this.secretKey);
    }
}
