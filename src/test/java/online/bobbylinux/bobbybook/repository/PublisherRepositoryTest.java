package online.bobbylinux.bobbybook.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import jakarta.transaction.Transactional;
import online.bobbylinux.bobbybook.entities.Author;
import online.bobbylinux.bobbybook.entities.Publisher;

@DataJpaTest
public class PublisherRepositoryTest {
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Test
    @Transactional
	void testGetPublisher() {
		Publisher publisher01 = new Publisher("Neri Pozza");
		Publisher publisher02 = new Publisher("Einaudi");
        
        List<Publisher> publishers = publisherRepository.saveAll(List.of(publisher01, publisher02));
        
        Optional<Publisher> result = publisherRepository.findById(publishers.get(0).getId());
        assertEquals("Neri Pozza", result.get().getName());
	}


    @Test
    @Transactional
	void testGetAllPublishers() {
		Publisher publisher01 = new Publisher("Neri Pozza");
        Publisher publisher02 = new Publisher("Mondadori");
        
        publisherRepository.saveAll(List.of(publisher01, publisher02));
        
        List<Publisher> result = publisherRepository.getAllPublishers();
        assertEquals(2, result.size());
        assertEquals("Mondadori", result.get(0).getName());
        assertEquals("Neri Pozza", result.get(1).getName());
	}

    @Test
    @Transactional
	void testSearchByName() {
		Publisher publisher01 = new Publisher("Mondadori");
		Publisher publisher02 = new Publisher("Einaudi");
        
        publisherRepository.saveAll(List.of(publisher01, publisher02));
        
        List<Publisher> result = publisherRepository.searchPublisher("inaudi");
        assertEquals(1, result.size());
        assertEquals("Einaudi", result.get(0).getName());
	}

    @Test
    @Transactional
	void testCreatePublisher() {
		Publisher publisher = new Publisher("Mondadori");
        Publisher savedPublisher = publisherRepository.save(publisher);
        
        assertNotNull(savedPublisher.getId());
        assertEquals("Mondadori", savedPublisher.getName());
	}

    @Test
    @Transactional
	void testDeletePublisher() {
		Publisher publisher = new Publisher("Einaudi");
        Publisher savedPublisher= publisherRepository.save(publisher);
        Long id = savedPublisher.getId();
        
        assertTrue(publisherRepository.existsById(id));
        publisherRepository.deleteById(id);
        assertFalse(publisherRepository.existsById(id));
	}
	
	@Test
    @Transactional
	void testUpdatePublisher() {
		Publisher publisher = new Publisher("Einaud");
        Long id = publisherRepository.save(publisher).getId();
        Optional<Publisher> result = publisherRepository.findById(id);

        if (!result.isEmpty()) {
            Publisher savedPublisher = result.get();
            savedPublisher.setName("Einaudi");
            Publisher updatedPublisher = publisherRepository.save(savedPublisher);
            assertEquals(savedPublisher, updatedPublisher);
        }
	}
}
