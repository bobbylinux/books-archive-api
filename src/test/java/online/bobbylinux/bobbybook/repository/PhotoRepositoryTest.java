package online.bobbylinux.bobbybook.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import jakarta.transaction.Transactional;
import online.bobbylinux.bobbybook.entities.Photo;
import online.bobbylinux.bobbybook.enums.ContentType;

@DataJpaTest
@TestPropertySource(locations = "file:.env")
public class PhotoRepositoryTest {

    @Autowired
    private PhotoRepository photoRepository;

    @Test
    @Transactional
    void testGetPhoto() {
        Photo photo01 = new Photo("/test/photo01/", "photo-cover-01.png", ContentType.IMAGE_PNG);
        Photo photo02 = new Photo("/test/photo02/", "photo-cover-02.jpg", ContentType.IMAGE_JPEG);

        List<Photo> photos = photoRepository.saveAll(List.of(photo01, photo02));

        Optional<Photo> result = photoRepository.findById(photos.get(0).getId());
        assertEquals("/test/photo01/", result.get().getPath());
        assertEquals("photo-cover-01.png", result.get().getFileName());
        assertEquals(ContentType.IMAGE_PNG, result.get().getContentType());
    }
}
