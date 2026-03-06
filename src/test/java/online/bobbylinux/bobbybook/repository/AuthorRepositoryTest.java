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

import online.bobbylinux.bobbybook.entities.Author;

@DataJpaTest
public class AuthorRepositoryTest {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Test
	void testGetAuthor() {
		Author author01 = new Author("Stephen", "King");
        Author author02 = new Author("George", "Martin");
        
        authorRepository.saveAll(List.of(author01, author02));
        
        Optional<Author> result = authorRepository.findById(1L);
        assertEquals("Stephen", result.get().getFirstName());
        assertEquals("King", result.get().getLastName());
	}
	
	@Test
	void testSearchByName() {
		Author author01 = new Author("Stephen", "King");
        Author author02 = new Author("George", "Martin");
        
        authorRepository.saveAll(List.of(author01, author02));
        
        List<Author> result = authorRepository.searchAuthors("king");
        assertEquals(1, result.size());
        assertEquals("Stephen", result.get(0).getFirstName());
        assertEquals("King", result.get(0).getLastName());
	}
	
	@Test
	void testCreateAuthor() {
		Author author = new Author("Stephen", "King");
        Author savedAuthor= authorRepository.save(author);
        
        assertNotNull(savedAuthor.getId());
        assertEquals("Stephen", savedAuthor.getFirstName());
        assertEquals("King", savedAuthor.getLastName());
	}
	
	@Test
	void testDeleteAuthor() {
		Author author = new Author("Stephen", "King");
        Author savedAuthor= authorRepository.save(author);
        Long id = savedAuthor.getId();
        
        assertTrue(authorRepository.existsById(id));
        authorRepository.deleteById(id);
        assertFalse(authorRepository.existsById(id));
	}
	
	@Test
	void testUpdateAuthor() {
		Author author = new Author("Stefan", "King");
        Author savedAuthor= authorRepository.save(author);
        Long id = savedAuthor.getId();
                
        assertTrue(authorRepository.existsById(id));
        authorRepository.deleteById(id);
        assertFalse(authorRepository.existsById(id));
	}
}
