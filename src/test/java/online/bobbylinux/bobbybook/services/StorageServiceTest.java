package online.bobbylinux.bobbybook.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@ExtendWith(MockitoExtension.class)
public class StorageServiceTest {
    @Mock
    private S3Client s3Client;

    @InjectMocks
    private StorageServiceImpl storageService;

    @BeforeEach
    void setUp() throws Exception {
        // inietta manualmente i @Value
        Field endpointField = StorageServiceImpl.class.getDeclaredField("endpoint");
        endpointField.setAccessible(true);
        endpointField.set(storageService, "https://s3.bobbylinux.online");

        Field bucketField = StorageServiceImpl.class.getDeclaredField("bucket");
        bucketField.setAccessible(true);
        bucketField.set(storageService, "books-archive");
    }

    @Test
    void testCreateFile() throws IOException {
        String fileName = "foto.jpg";

        MultipartFile file = mock(MultipartFile.class);
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getBytes()).thenReturn("contenuto".getBytes());
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                .thenReturn(PutObjectResponse.builder().build());

        String path = storageService.createFile(fileName, file);
        System.out.println(path);
        assertTrue(path.contains("s3.bobbylinux.online"));
        assertTrue(path.contains("books-archive/"));
        assertTrue(path.endsWith("_" + fileName));
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void testDeleteFile() throws NotFoundException {
        // arrange
        String path = "https://s3.bobbylinux.online/bobbybook-covers/photos/uuid_foto.jpg";
        when(s3Client.deleteObject(any(DeleteObjectRequest.class)))
                .thenReturn(DeleteObjectResponse.builder().build());

        storageService.deleteFile(path);

        verify(s3Client, times(1)).deleteObject(any(DeleteObjectRequest.class));
    }
}
