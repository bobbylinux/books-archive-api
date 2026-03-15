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
        List<Publisher> publishers = publisherRepository.getAllPublishers();
        return publishers.stream().map(a -> new PublisherResponse(a.getId(), a.getName())).toList();
    }

    @Override
    public PublisherResponse getPublisher(Long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        return new PublisherResponse(publisher.get().getId(), publisher.get().getName());
    }

    @Override
    public List<PublisherResponse> searchPublisher(String searchString) {
        List<Publisher> publishers = publisherRepository.searchPublisher(searchString);
        return publishers.stream().map(a -> new PublisherResponse(a.getId(), a.getName())).toList();
    }

    @Override
    public PublisherResponse createPublisher(String name) {
        Publisher publisher = new Publisher(name);
        Publisher savedPublisher = this.publisherRepository.save(publisher);
        return new PublisherResponse(savedPublisher.getId(), savedPublisher.getName());
    }

    @Override
    public PublisherResponse updatePublisher(Long id, String name) throws NotFoundException {
        if (id == null) {
			throw new IllegalArgumentException("Publisher Id to modify can't be null");
		}
		Optional<Publisher> publisher = publisherRepository.findById(id);
		if (publisher.isEmpty()) {
			throw new NotFoundException();
		}
		Publisher updatedPublisher = publisher.get();
		updatedPublisher.setName(name);
		Publisher savedPublisher = publisherRepository.save(updatedPublisher);
		return new PublisherResponse(savedPublisher.getId(), savedPublisher.getName());
    }

   
   @Override
    public void deletePublisher(Long id) throws NotFoundException {
        if (id == null) {
			throw new IllegalArgumentException("Publisher Id to delete can't be null");
		}
		publisherRepository.deleteById(id);
    }

}
