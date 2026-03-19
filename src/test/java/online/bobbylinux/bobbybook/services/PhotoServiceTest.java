package online.bobbylinux.bobbybook.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import online.bobbylinux.bobbybook.dto.PhotoResponse;
import online.bobbylinux.bobbybook.entities.Photo;
import online.bobbylinux.bobbybook.enums.ContentType;
import online.bobbylinux.bobbybook.repository.PhotoRepository;

@ExtendWith(MockitoExtension.class)
public class PhotoServiceTest {
    @Mock
    private StorageServiceImpl storageService;

    @Mock
    private PhotoRepository photoRepository;

    @InjectMocks
    private PhotoServiceImpl photoService;

    @Test
    void testCreatePhoto() throws IOException, NotFoundException {
        String path = "https://s3.bobbylinux.online/books-archive/photos/uuid_foto.jpg";
        String fileName = "foto.jpg";
        MultipartFile file = mock(MultipartFile.class);
        when(file.getContentType()).thenReturn("image/jpeg");
        when(storageService.createFile(fileName, file)).thenReturn(path);

        Photo savedPhoto = new Photo(path, fileName, ContentType.IMAGE_JPEG);
        savedPhoto.setId(1L);
        when(photoRepository.save(any(Photo.class))).thenAnswer(invocation -> savedPhoto);

        PhotoResponse response = photoService.createPhoto(fileName, file);

        assertEquals(response.path(), path);
        assertEquals(response.fileName(), fileName);
        verify(storageService, times(1)).createFile(fileName, file);
        verify(photoRepository, times(1)).save(any(Photo.class));
    }
}
