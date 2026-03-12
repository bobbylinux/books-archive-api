package online.bobbylinux.bobbybook.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import online.bobbylinux.bobbybook.dto.PublisherResponse;
import online.bobbylinux.bobbybook.entities.Publisher;
import online.bobbylinux.bobbybook.repository.PublisherRepository;

@Service
public class PublisherServiceImpl implements PublisherService{

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public List<PublisherResponse> getAllPublishers() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllPublishers'");
    }

    @Override
    public PublisherResponse getPublisher(Long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        return new PublisherResponse(publisher.get().getId(), publisher.get().getName());
    }

    @Override
    public List<PublisherResponse> searchPublisher(String searchString) {
        throw new UnsupportedOperationException("Unimplemented method 'searchPublisher'");
    }

    @Override
    public PublisherResponse createPublisher(String name) {
        throw new UnsupportedOperationException("Unimplemented method 'createPublisher'");
    }

    @Override
    public PublisherResponse updatePublisher(Long id, String name) throws NotFoundException {
        throw new UnsupportedOperationException("Unimplemented method 'updatePublisher'");
    }

    @Override
    public void deletePublisher(Long id) throws NotFoundException {
        throw new UnsupportedOperationException("Unimplemented method 'deletePublisher'");
    }

}
