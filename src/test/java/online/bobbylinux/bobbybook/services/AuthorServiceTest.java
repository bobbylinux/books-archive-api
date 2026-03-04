package online.bobbylinux.bobbybook.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import online.bobbylinux.bobbybook.entities.Author;
import online.bobbylinux.bobbybook.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
	@Mock
    private AuthorRepository authorRepository;
	
	@InjectMocks
    private AuthorServiceImpl authorService;
	
	@Test
    void testCreateAuthor() {
        Author author = new Author("Stephen", "King");
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author result = authorService.createAuthor("Stephen", "King");

        assertNotNull(result);
        verify(authorRepository, times(1)).save(author);
    }
}
