package online.bobbylinux.bobbybook.services;

import java.io.IOException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import online.bobbylinux.bobbybook.dto.PhotoResponse;

public interface PhotoService {

    public PhotoResponse getPhoto(Long id) throws NotFoundException;

    public PhotoResponse createPhoto(String fileName, MultipartFile file) throws IOException, NotFoundException;

    public PhotoResponse updatePhoto(Long id, String fileName, MultipartFile file) throws NotFoundException;

    public void deletePhoto(Long id) throws NotFoundException;
}
