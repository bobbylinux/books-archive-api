package online.bobbylinux.bobbybook.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import online.bobbylinux.bobbybook.dto.PublisherResponse;
import online.bobbylinux.bobbybook.entities.Publisher;
import online.bobbylinux.bobbybook.repository.PublisherRepository;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {
@Mock
	private PublisherRepository publisherRepository;

	@InjectMocks
	private PublisherServiceImpl publisherService;

	@Test
	void testGetPublisher() throws NotFoundException {
	    Long id = 1L;
        Publisher publisher = new Publisher("Mondadori");
        publisher.setId(id);

        when(publisherRepository.findById(id)).thenReturn(Optional.of(publisher));
        
        PublisherResponse result = publisherService.getPublisher(id);
        
        assertNotNull(result);
        assertEquals("Mondadori", result.name());

        verify(publisherRepository, times(1)).findById(id);
	}

    @Test
	void testSearchPublisher() {
		String searchString = "einaud";
        Publisher publisher01 = new Publisher("Einaudi");
        publisher01.setId(1L);
        Publisher publisher02 = new Publisher("Mondadori");
        publisher02.setId(2L);
        Publisher publisher03 = new Publisher("Neri Pozza");
        publisher02.setId(3L);
        Publisher publisher04 = new Publisher("Nerini");
        publisher02.setId(4L);
        
        List<Publisher> authors = new ArrayList<Publisher>();
        authors.add(publisher03);
        authors.add(publisher04);
        
        when(publisherRepository.searchPublisher(searchString)).thenReturn(authors);

        List<Publisher> result = publisherRepository.searchPublisher(searchString);
        
        assertNotNull(result);
        
        assertEquals(2, result.size());
        
        assertTrue(result.contains(publisher03));
        assertTrue(result.contains(publisher04));
        
        verify(publisherRepository, times(1)).searchPublisher(searchString);
	}
	
    @Test
	void testGetAllPublishers() {
        Publisher publisher01 = new Publisher("Einaudi");
        publisher01.setId(1L);
        PublisherResponse publisherResponse01 = new PublisherResponse(publisher01.getId(), publisher01.getName());
        Publisher publisher02 = new Publisher("Mondadori");
        publisher02.setId(2L);
        PublisherResponse publisherResponse02 = new PublisherResponse(publisher02.getId(), publisher02.getName());
        Publisher publisher03 = new Publisher("Neri Pozza");
        publisher03.setId(3L);
        PublisherResponse publisherResponse03 = new PublisherResponse(publisher03.getId(), publisher03.getName());
        Publisher publisher04 = new Publisher("Sellerio");
        publisher04.setId(4L);
        PublisherResponse publisherResponse04 = new PublisherResponse(publisher04.getId(), publisher04.getName());
        Publisher publisher05 = new Publisher("Bompiani");
        publisher05.setId(5L);
        PublisherResponse publisherResponse05 = new PublisherResponse(publisher05.getId(), publisher05.getName());

        List<Publisher> publishers = new ArrayList<Publisher>();
        publishers.add(publisher01);
        publishers.add(publisher02);
        publishers.add(publisher03);
        publishers.add(publisher04);
        publishers.add(publisher05);

        when(publisherRepository.getAllPublishers()).thenReturn(publishers);

        List<PublisherResponse> result = publisherService.getAllPublishers();
        
        assertNotNull(result);
        
        assertEquals(5, result.size());
        
        assertTrue(result.contains(publisherResponse01));
        assertTrue(result.contains(publisherResponse02));
        assertTrue(result.contains(publisherResponse03));
        assertTrue(result.contains(publisherResponse04));
        assertTrue(result.contains(publisherResponse05));
        
        verify(publisherRepository, times(1)).getAllPublishers();
	}

	@Test
	void testCreatePublisher() {
		Publisher publisher = new Publisher("Einaudi");
		when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

		PublisherResponse result = publisherService.createPublisher("Einaudi");

		assertNotNull(result);
		assertEquals(result.name(), "Einaudi");

		verify(publisherRepository, times(1)).save(any(Publisher.class));
	}
	
	@Test
	void testUpdatePublisher() throws NotFoundException {
	    Long id = 1L;
        Publisher existingPublisher = new Publisher("Sellerioo");
        existingPublisher.setId(id);
        
        when(publisherRepository.findById(id)).thenReturn(Optional.of(existingPublisher));
        when(publisherRepository.save(any(Publisher.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PublisherResponse updatedPublisher = publisherService.updatePublisher(id, "Sellerio");

        assertNotNull(updatedPublisher);
        assertEquals("Sellerio", updatedPublisher.name());

        verify(publisherRepository, times(1)).findById(id);
        verify(publisherRepository, times(1)).save(existingPublisher);		
	}

	@Test
	void testDeleteAuthor() throws NotFoundException {
		Long id = 1L;
		
		publisherService.deletePublisher(id);
		
		verify(publisherRepository).deleteById(id);
	    verifyNoMoreInteractions(publisherRepository);
	}
	
}
