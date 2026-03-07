package online.bobbylinux.bobbybook.services;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import online.bobbylinux.bobbybook.dto.AuthorResponse;

public interface AuthorService {
	
	public List<AuthorResponse> getAllAuthors();
	
	public AuthorResponse getAuthor(Long id) throws NotFoundException;
	
	public List<AuthorResponse> searchAuthor(String searchString);
	
	public AuthorResponse createAuthor(String firstName, String lastName);
	
	public AuthorResponse updateAuthor(Long id, String firstName, String lastName) throws NotFoundException;
	
	public void deleteAuthor(Long id) throws NotFoundException;
	
}
