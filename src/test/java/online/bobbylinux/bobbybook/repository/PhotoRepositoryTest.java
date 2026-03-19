package online.bobbylinux.bobbybook.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        Photo photo = new Photo(
                "https://s3.bobbylinux.online/bobbybook-covers/photos/uuid_test.jpg",
                "test.jpg",
                ContentType.IMAGE_JPEG);
        Photo savedPhoto = photoRepository.save(photo);

        Optional<Photo> result = photoRepository.findById(savedPhoto.getId());

        assertFalse(result.isEmpty());
        assertEquals(result.get().getPath(), photo.getPath());
        assertEquals(result.get().getFileName(), "test.jpg");
        assertEquals(result.get().getContentType(), ContentType.IMAGE_JPEG);
    }
}
