package online.bobbylinux.bobbybook.services;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;

import online.bobbylinux.bobbybook.dto.AuthorResponse;

public interface AuthorService {
	
	public List<AuthorResponse> getAllAuthors();
	
	public Page<AuthorResponse> getAllAuthorsPaginated(int page, int size);

	public AuthorResponse getAuthor(Long id) throws NotFoundException;
	
	public List<AuthorResponse> searchAuthor(String searchString);
	
	public void createAuthor(String firstName, String lastName);
	
	public void updateAuthor(Long id, String firstname, String lastName) throws NotFoundException;
	
	public void deleteAuthor(Long id) throws NotFoundException;
	
}
