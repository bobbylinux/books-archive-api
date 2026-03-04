package online.bobbylinux.bobbybook.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import online.bobbylinux.bobbybook.entities.Author;
import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(result.get(0).getFirstName()).isEqualTo("Stephen");
        assertThat(result.get(0).getLastName()).isEqualTo("King");
	}
}
