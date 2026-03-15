package online.bobbylinux.bobbybook.controller;

import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import online.bobbylinux.bobbybook.dto.AuthorRequest;
import online.bobbylinux.bobbybook.dto.AuthorResponse;
import online.bobbylinux.bobbybook.services.AuthorService;

@RestController()
@RequestMapping("${api.prefix}/authors")
public class AuthorsController {

	private final AuthorService authorService;

	public AuthorsController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthorResponse> getAuthor(@PathVariable Long id) throws NotFoundException {
		AuthorResponse author = authorService.getAuthor(id);
		return ResponseEntity.ok(author);
	}

	@PostMapping
	public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorRequest authorRequest)
			throws NotFoundException {
		AuthorResponse author = authorService.createAuthor(authorRequest.firstName(), authorRequest.last_name());
		return ResponseEntity.status(HttpStatus.CREATED).body(author);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<AuthorResponse> updateAuthor(@Valid @PathVariable Long id,
			@RequestBody AuthorRequest authorRequest)
			throws NotFoundException {
		AuthorResponse author = authorService.updateAuthor(id, authorRequest.firstName(), authorRequest.last_name());
		return ResponseEntity.status(HttpStatus.CREATED).body(author);
	}

	@GetMapping
	public ResponseEntity<List<AuthorResponse>> getAuthors(@RequestParam(required = false) String search_string) {
		List<AuthorResponse> authors;
		if (search_string != null) {
			authors = authorService.searchAuthor(search_string);
			return ResponseEntity.ok(authors);
		}
		return ResponseEntity.ok(authorService.getAllAuthors());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAuthor(@Valid @PathVariable Long id)
			throws NotFoundException {
		authorService.deleteAuthor(id);
		return ResponseEntity.noContent().build();
	}
}
