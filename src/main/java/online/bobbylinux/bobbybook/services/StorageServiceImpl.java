package online.bobbylinux.bobbybook.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class StorageServiceImpl implements StorageService {
    @Autowired
    private S3Client s3Client;

    @Value("${s3.bucket}")
    private String bucket;

    @Value("${s3.endpoint}")
    private String endpoint;

    @Override
    public String createFile(String fileName, MultipartFile file)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        String key = UUID.randomUUID() + "_" + fileName;
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes()));

        return endpoint + "/" + bucket + "/" + key;
    }

    @Override
    public void deleteFile(String path) throws NotFoundException {
        String key = path.replace(endpoint + "/" + bucket + "/", "");

        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build());
    }

}
