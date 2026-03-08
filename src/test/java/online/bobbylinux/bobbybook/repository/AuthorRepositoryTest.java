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

@DataJpaTest
public class AuthorRepositoryTest {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Test
    @Transactional
	void testGetAuthor() {
		Author author01 = new Author("Stephen", "King");
        Author author02 = new Author("George", "Martin");
        
        List<Author> authors = authorRepository.saveAll(List.of(author01, author02));
        
        Optional<Author> result = authorRepository.findById(authors.get(0).getId());
        assertEquals("Stephen", result.get().getFirstName());
        assertEquals("King", result.get().getLastName());
	}
	
    @Test
    @Transactional
	void testGetAllAuthors() {
		Author author01 = new Author("Stephen", "King");
        Author author02 = new Author("George", "Martin");
        
        authorRepository.saveAll(List.of(author01, author02));
        
        List<Author> result = authorRepository.getAllAuthors();
        assertEquals(2, result.size());
        assertEquals("Stephen", result.get(0).getFirstName());
        assertEquals("King", result.get(0).getLastName());
        assertEquals("George", result.get(1).getFirstName());
        assertEquals("Martin", result.get(1).getLastName());
	}

	@Test
    @Transactional
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
    @Transactional
	void testCreateAuthor() {
		Author author = new Author("Stephen", "King");
        Author savedAuthor= authorRepository.save(author);
        
        assertNotNull(savedAuthor.getId());
        assertEquals("Stephen", savedAuthor.getFirstName());
        assertEquals("King", savedAuthor.getLastName());
	}
	
	@Test
    @Transactional
	void testDeleteAuthor() {
		Author author = new Author("Stephen", "King");
        Author savedAuthor= authorRepository.save(author);
        Long id = savedAuthor.getId();
        
        assertTrue(authorRepository.existsById(id));
        authorRepository.deleteById(id);
        assertFalse(authorRepository.existsById(id));
	}
	
	@Test
    @Transactional
	void testUpdateAuthor() {
		Author author = new Author("Stefan", "King");
        Author savedAuthor= authorRepository.save(author);
        Long id = savedAuthor.getId();
                
        assertTrue(authorRepository.existsById(id));
        authorRepository.deleteById(id);
        assertFalse(authorRepository.existsById(id));
	}
}
