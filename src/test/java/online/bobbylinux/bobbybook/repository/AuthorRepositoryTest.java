package online.bobbylinux.bobbybook.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import online.bobbylinux.bobbybook.entities.Author;

@DataJpaTest
public class AuthorRepositoryTest {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Test
	void testSearchByName() {
		//setup dati 
		Author author01 = new Author("Stephen", "King");
        Author author02 = new Author("George", "Martin");
        
        authorRepository.saveAll(List.of(author01, author02));
        
        List<Author> result = authorRepository.searchAuthors("king");
        assertThat(result).hasSize(1);
        assertEquals("Stephen", result.get(0).getFirstName());
        assertEquals("King", result.get(0).getLastName());
	}
	
	@Test
	void testCreateAuthor() {
		//setup dati        
		Author author = new Author("Stephen", "King");
        Author savedAuthor= authorRepository.save(author);
        
        assertNotNull(savedAuthor.getId());
        assertEquals("Stephen", savedAuthor.getFirstName());
        assertEquals("King", savedAuthor.getLastName());
	}
	
	
}
