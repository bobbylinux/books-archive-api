package online.bobbylinux.bobbybook.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import online.bobbylinux.bobbybook.controller.AuthorsController;
import online.bobbylinux.bobbybook.dto.AuthorResponse;
import online.bobbylinux.bobbybook.services.AuthorService;

@WebMvcTest(AuthorsController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AuthorService authorService;

	@Value("${api.prefix}")
	private String apiPrefix;

	@Test
	@WithMockUser(username = "test", roles = { "USER" })
	void testGetAuthor() throws Exception {
		AuthorResponse author = new AuthorResponse(1L, "Stephen", "King");

		when(authorService.getAuthor(1L)).thenReturn(author);

		mockMvc.perform(get(apiPrefix + "/authors/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.first_name").value("Stephen")).andExpect(jsonPath("$.last_name").value("King"));

		verify(authorService, times(1)).getAuthor(1L);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testCreateAuthors() throws Exception {
		AuthorResponse author = new AuthorResponse(1L, "Stephen", "King");

		when(authorService.createAuthor("Stephen", "King")).thenReturn(author);

		mockMvc.perform(post(apiPrefix + "/authors")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						    {
						        "first_name": "Stephen",
						        "last_name": "King"
						    }
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.first_name").value("Stephen"))
				.andExpect(jsonPath("$.last_name").value("King"));

		verify(authorService, times(1)).createAuthor("Stephen", "King");
	}

	@Test
	@WithMockUser(username = "test", roles = { "USER" })
	void testSearchAuthors() throws Exception {

		List<AuthorResponse> authors = new ArrayList<AuthorResponse>();
		authors.add(new AuthorResponse(203L, "Alfred", "Alkinga"));
		authors.add(new AuthorResponse(101L, "King", "John"));
		authors.add(new AuthorResponse(501L, "Robert", "King"));
		authors.add(new AuthorResponse(1L, "Stephen", "King"));

		when(authorService.searchAuthor("king")).thenReturn(authors);

		mockMvc.perform(get(apiPrefix + "/authors").param("search_string", "king")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(203))
				.andExpect(jsonPath("$[0].first_name").value("Alfred"))
				.andExpect(jsonPath("$[0].last_name").value("Alkinga"))
				.andExpect(jsonPath("$[1].id").value(101))
				.andExpect(jsonPath("$[1].first_name").value("King"))
				.andExpect(jsonPath("$[1].last_name").value("John"))
				.andExpect(jsonPath("$[2].id").value(501))
				.andExpect(jsonPath("$[2].first_name").value("Robert"))
				.andExpect(jsonPath("$[2].last_name").value("King"))
				.andExpect(jsonPath("$[3].id").value(1))
				.andExpect(jsonPath("$[3].first_name").value("Stephen"))
				.andExpect(jsonPath("$[3].last_name").value("King"));

		verify(authorService, times(1)).searchAuthor("king");
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testUpdateAuthor() throws Exception {
		AuthorResponse author = new AuthorResponse(1L, "Stephen", "King");
		Long id = 1L;
		when(authorService.updateAuthor(id, "Stephen", "King")).thenReturn(author);

		mockMvc.perform(patch(apiPrefix + "/authors/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						    {
						        "first_name": "Stephen",
						        "last_name": "King"
						    }
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.first_name").value("Stephen"))
				.andExpect(jsonPath("$.last_name").value("King"));

		verify(authorService, times(1)).updateAuthor(id, "Stephen", "King");
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void testDeleteAuthor() throws Exception {
		Long id = 1L;

		doNothing().when(authorService).deleteAuthor(id);

		mockMvc.perform(delete(apiPrefix + "/authors/" + id))
				.andExpect(status().isNoContent());

		verify(authorService, times(1)).deleteAuthor(id);
	}

	@Test
	@WithMockUser(username = "test", roles = { "USER" })
	void testGetAllAuthors() throws Exception {

		List<AuthorResponse> authors = new ArrayList<AuthorResponse>();
		authors.add(new AuthorResponse(203L, "George", "Martin"));
		authors.add(new AuthorResponse(101L, "Ken", "Follett"));
		authors.add(new AuthorResponse(501L, "Stephen", "King"));
		authors.add(new AuthorResponse(1L, "Isabel", "Allende"));
		authors.add(new AuthorResponse(102L, "Agata", "Christie"));

		when(authorService.getAllAuthors()).thenReturn(authors);

		mockMvc.perform(get(apiPrefix + "/authors")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(203))
				.andExpect(jsonPath("$[0].first_name").value("George"))
				.andExpect(jsonPath("$[0].last_name").value("Martin"))
				.andExpect(jsonPath("$[1].id").value(101))
				.andExpect(jsonPath("$[1].first_name").value("Ken"))
				.andExpect(jsonPath("$[1].last_name").value("Follett"))
				.andExpect(jsonPath("$[2].id").value(501))
				.andExpect(jsonPath("$[2].first_name").value("Stephen"))
				.andExpect(jsonPath("$[2].last_name").value("King"))
				.andExpect(jsonPath("$[3].id").value(1))
				.andExpect(jsonPath("$[3].first_name").value("Isabel"))
				.andExpect(jsonPath("$[3].last_name").value("Allende"))
				.andExpect(jsonPath("$[4].id").value(102))
				.andExpect(jsonPath("$[4].first_name").value("Agata"))
				.andExpect(jsonPath("$[4].last_name").value("Christie"));

		verify(authorService, times(1)).getAllAuthors();
	}
}
