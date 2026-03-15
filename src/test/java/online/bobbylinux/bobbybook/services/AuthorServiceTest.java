package online.bobbylinux.bobbybook.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import online.bobbylinux.bobbybook.dto.AuthorResponse;
import online.bobbylinux.bobbybook.entities.Author;
import online.bobbylinux.bobbybook.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void testGetAuthor() throws NotFoundException {
        Long id = 1L;
        Author author = new Author("Stephen", "King");
        author.setId(id);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        AuthorResponse result = authorService.getAuthor(id);

        assertNotNull(result);
        assertEquals("Stephen", result.firstName());
        assertEquals("King", result.lastName());

        verify(authorRepository, times(1)).findById(id);
    }

    @Test
    void testSearchAuthor() {
        String searchString = "king";
        Author author01 = new Author("Stephen", "King");
        author01.setId(1L);
        Author author02 = new Author("George", "Martin");
        author02.setId(2L);
        Author author03 = new Author("Simmon", "Askinga");
        author03.setId(3L);
        Author author04 = new Author("Badkinga", "Aliussy");
        author04.setId(4L);

        List<Author> authors = new ArrayList<Author>();
        authors.add(author01);
        authors.add(author03);
        authors.add(author04);

        when(authorRepository.searchAuthors(searchString)).thenReturn(authors);

        List<Author> result = authorRepository.searchAuthors(searchString);

        assertNotNull(result);

        assertEquals(3, result.size());

        assertTrue(result.contains(author01));
        assertTrue(result.contains(author03));
        assertTrue(result.contains(author04));

        verify(authorRepository, times(1)).searchAuthors(searchString);
    }

    @Test
    void testGetAllAuthors() {
        Author author01 = new Author("Stephen", "King");
        author01.setId(1L);
        AuthorResponse authorResponse01 = new AuthorResponse(author01.getId(), author01.getFirstName(),
                author01.getLastName());
        Author author02 = new Author("George", "Martin");
        author02.setId(2L);
        AuthorResponse authorResponse02 = new AuthorResponse(author02.getId(), author02.getFirstName(),
                author02.getLastName());
        Author author03 = new Author("Simmon", "Askinga");
        author03.setId(3L);
        AuthorResponse authorResponse03 = new AuthorResponse(author03.getId(), author03.getFirstName(),
                author03.getLastName());
        Author author04 = new Author("Badkinga", "Aliussy");
        author04.setId(4L);
        AuthorResponse authorResponse04 = new AuthorResponse(author04.getId(), author04.getFirstName(),
                author04.getLastName());

        List<Author> authors = new ArrayList<Author>();
        authors.add(author01);
        authors.add(author02);
        authors.add(author03);
        authors.add(author04);

        when(authorRepository.getAllAuthors()).thenReturn(authors);

        List<AuthorResponse> result = authorService.getAllAuthors();

        assertNotNull(result);

        assertEquals(4, result.size());

        assertTrue(result.contains(authorResponse01));
        assertTrue(result.contains(authorResponse02));
        assertTrue(result.contains(authorResponse03));
        assertTrue(result.contains(authorResponse04));
        verify(authorRepository, times(1)).getAllAuthors();
    }

    @Test
    void testCreateAuthor() {
        Author author = new Author("Stephen", "King");
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorResponse result = authorService.createAuthor("Stephen", "King");

        assertNotNull(result);
        assertEquals(result.firstName(), "Stephen");
        assertEquals(result.lastName(), "King");

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void testUpdateAuthor() throws NotFoundException {
        Long id = 1L;
        Author existingAuthor = new Author("Stephen", "King");
        existingAuthor.setId(id);

        when(authorRepository.findById(id)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuthorResponse updatedAuthor = authorService.updateAuthor(id, "Stephanus", "Kingston");

        assertNotNull(updatedAuthor);
        assertEquals("Stephanus", updatedAuthor.firstName());
        assertEquals("Kingston", updatedAuthor.lastName());

        verify(authorRepository, times(1)).findById(id);
        verify(authorRepository, times(1)).save(existingAuthor);
    }

    @Test
    void testDeleteAuthor() {
        Long id = 1L;

        authorService.deleteAuthor(id);

        verify(authorRepository).deleteById(id);
        verifyNoMoreInteractions(authorRepository);
    }

}
