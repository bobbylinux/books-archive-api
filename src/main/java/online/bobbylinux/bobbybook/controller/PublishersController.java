package online.bobbylinux.bobbybook.controller;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import online.bobbylinux.bobbybook.dto.PublisherRequest;
import online.bobbylinux.bobbybook.dto.PublisherResponse;
import online.bobbylinux.bobbybook.services.PublisherService;

@RestController()
@RequestMapping("${api.prefix}/publishers")
public class PublishersController {

    private final PublisherService publisherService;

    public PublishersController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponse> getPublisher(@PathVariable Long id) throws NotFoundException {
        PublisherResponse publisher = publisherService.getPublisher(id);
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<PublisherResponse> createPublisher(@Valid @RequestBody PublisherRequest publisherRequest)
            throws NotFoundException {
        PublisherResponse publisher = publisherService.createPublisher(publisherRequest.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(publisher);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PublisherResponse> updatePublisher(@Valid @PathVariable Long id,
            @RequestBody PublisherRequest publisherRequest)
            throws NotFoundException {
        PublisherResponse publisher = publisherService.updatePublisher(id, publisherRequest.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(publisher);
    }

    @GetMapping
    public ResponseEntity<List<PublisherResponse>> getPublishers(@RequestParam(required = false) String search_string) {
        List<PublisherResponse> publishers;
        if (search_string != null) {
            publishers = publisherService.searchPublisher(search_string);
            return ResponseEntity.ok(publishers);
        }
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@Valid @PathVariable Long id)
            throws NotFoundException {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
