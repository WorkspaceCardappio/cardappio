package br.com.cardappio.components.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.model.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3StorageComponent {

    @Value("${AWS_BUCKET}")
    private String bucketName;

    private final S3Client client;
    private final software.amazon.awssdk.services.s3.presigner.S3Presigner s3Presigner;

    public void saveFile(MultipartFile file, String key, String oldFileName) {

        createDefaultBucket();

        if (Objects.nonNull(oldFileName)) {

            deleteMatchingObject(oldFileName);
        }

        PutObjectRequest request = buildRequest(file, key);

        try {
            client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            log.info("File uploaded successfully: {}", key);
        } catch (IOException e) {
            log.error("Failed to read file for upload: {}", key, e);
            throw new RuntimeException("Falha ao ler o arquivo para upload", e);
        } catch (S3Exception e) {
            log.error("Failed to upload to S3: bucket={}, key={}, error={}", bucketName, key, e.awsErrorDetails().errorMessage(), e);
            throw new RuntimeException("Falha ao enviar para S3: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    public String getKey(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        if (Objects.isNull(originalFilename) || originalFilename.isBlank()) {
            originalFilename = "file";
        }

        String sanitizedFilename = originalFilename
                .replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

        return LocalDate.now() + "/" + UUID.randomUUID() + "_" + sanitizedFilename;
    }

    public String generatePresignedUrl(String key) {

        if (Objects.isNull(key) || key.isBlank()) {
            return null;
        }

        try {
            log.debug("Generating presigned URL: bucket={}, key={}", bucketName, key);

            GetObjectPresignRequest presignRequest =
                    software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest.builder()
                            .signatureDuration(Duration.ofHours(1))
                            .getObjectRequest(req -> req.bucket(bucketName).key(key))
                            .build();

            PresignedGetObjectRequest presignedRequest =
                    s3Presigner.presignGetObject(presignRequest);

            String url = presignedRequest.url().toString();
            log.debug("Generated presigned URL: {}", url);

            return url;
        } catch (Exception e) {
            log.error("Failed to generate presigned URL: bucket={}, key={}", bucketName, key, e);
            throw new RuntimeException("Falha ao gerar URL pré-assinada: " + e.getMessage(), e);
        }
    }

    public void deleteMatchingObject(String oldFileName) {

        if (objectExists(oldFileName)) {

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(oldFileName)
                    .build();

            client.deleteObject(deleteRequest);
        }
    }

    private boolean objectExists(String oldFileName) {

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

    private PutObjectRequest buildRequest(MultipartFile file, String key) {

        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .metadata(Map.of("original-filename", file.getOriginalFilename()))
                .build();
    }

    private void createDefaultBucket() {

        boolean bucketAlreadyExists = client.listBuckets()
                .buckets()
                .stream()
                .map(Bucket::name)
                .anyMatch(bucketName::equals);

        if (bucketAlreadyExists) {

            return;
        }

        createBucket();
    }

    private void createBucket() {

        client.createBucket(request -> request.bucket(bucketName));
    }
}
