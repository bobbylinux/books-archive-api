package online.bobbylinux.bobbybook.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import online.bobbylinux.bobbybook.dto.AuthorResponse;
import online.bobbylinux.bobbybook.services.AuthorService;

@RestController()
@RequestMapping("/authors")
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
	public ResponseEntity<AuthorResponse> createAuthor(@RequestParam String first_name, @RequestParam String last_name) throws NotFoundException {
		AuthorResponse author = authorService.createAuthor(first_name, last_name);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
	}
	
	
}
