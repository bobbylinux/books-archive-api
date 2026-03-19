package online.bobbylinux.bobbybook.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import online.bobbylinux.bobbybook.dto.PhotoResponse;
import online.bobbylinux.bobbybook.entities.Photo;
import online.bobbylinux.bobbybook.enums.ContentType;
import online.bobbylinux.bobbybook.repository.PhotoRepository;

public class PhotoServiceImpl implements PhotoService {

    @Autowired
    StorageService storageService;

    @Autowired
    PhotoRepository photoRepository;

    @Override
    public PhotoResponse getPhoto(Long id) throws NotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPhoto'");
    }

    @Override
    @Transactional
    public PhotoResponse createPhoto(String fileName, MultipartFile file) throws IOException, NotFoundException {
        String path = null;

        try {

            path = storageService.createFile(fileName, file);

            Photo photo = new Photo(path, fileName, ContentType.fromMimeType(file.getContentType()));

            Photo savedPhoto = photoRepository.save(photo);

            return new PhotoResponse(savedPhoto.getId(), savedPhoto.getPath(), savedPhoto.getFileName(),
                    savedPhoto.getContentType());

        } catch (Exception exception) {
            if (path != null) {
                storageService.deleteFile(path);
            }
            throw exception;
        }
    }

    @Override
    public PhotoResponse updatePhoto(Long id, String fileName, MultipartFile file) throws NotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePhoto'");
    }

    @Override
    public void deletePhoto(Long id) throws NotFoundException {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        photoRepository.deleteById(id);
        storageService.deleteFile(photo.getPath());
    }

}
