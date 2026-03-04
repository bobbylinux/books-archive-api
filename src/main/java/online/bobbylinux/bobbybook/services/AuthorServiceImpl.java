package online.bobbylinux.bobbybook.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import online.bobbylinux.bobbybook.dto.AuthorResponse;
import online.bobbylinux.bobbybook.entities.Author;
import online.bobbylinux.bobbybook.repository.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	private final AuthorRepository authorRepository;

	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public List<AuthorResponse> getAllAuthors() {
		List<Author> authors = authorRepository.findAll();
		return authors.stream()
				.map(a -> new AuthorResponse(a.getId(), a.getFirstName(), a.getLastName()))
				.toList();
	}

	@Override
	public List<AuthorResponse> searchAuthor(String searchString) {
		List<Author> authors = authorRepository.searchAuthors(searchString);
		return authors.stream()
				.map(a -> new AuthorResponse(a.getId(), a.getFirstName(), a.getLastName()))
				.toList();
	}
	
	@Override
	public AuthorResponse getAuthor(Long id) throws NotFoundException {
		Optional<Author> author = authorRepository.findById(id);
		return new AuthorResponse(author.get().getId(), author.get().getFirstName(), author.get().getLastName());
	}

	@Override
	public Author createAuthor(String firstName, String lastName) {
		Author author = new Author(firstName, lastName);
		return authorRepository.save(author);
	}

	@Override
	public void updateAuthor(Long id, String firstname, String lastName) throws NotFoundException {
		Optional<Author> author = authorRepository.findById(id);
		if (author.isEmpty()) {
			throw new NotFoundException();
		}
	
	}

	@Override
	public void deleteAuthor(Long id) throws NotFoundException {
		Optional<Author> author = authorRepository.findById(id);
		if (author.isEmpty()) {
			throw new NotFoundException();
		}

	}

}
