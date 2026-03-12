package online.bobbylinux.bobbybook.services;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import online.bobbylinux.bobbybook.dto.PublisherResponse;

public interface PublisherService {

    public List<PublisherResponse> getAllPublishers();

    public PublisherResponse getPublisher(Long id);

    public List<PublisherResponse> searchPublisher(String searchString);

    public PublisherResponse createPublisher(String name);
	
	public PublisherResponse updatePublisher(Long id, String name) throws NotFoundException;
	
	public void deletePublisher(Long id) throws NotFoundException;
    
}
