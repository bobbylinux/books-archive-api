package online.bobbylinux.bobbybook.services;

import java.io.IOException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.S3Exception;

public interface StorageService {
    public String createFile(String fileName, MultipartFile file)
            throws S3Exception, AwsServiceException, SdkClientException, IOException;

    public void deleteFile(String path) throws NotFoundException;
}
