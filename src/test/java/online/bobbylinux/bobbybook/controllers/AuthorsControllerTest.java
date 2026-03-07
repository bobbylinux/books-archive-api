package online.bobbylinux.bobbybook.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import online.bobbylinux.bobbybook.controller.AuthorsController;
import online.bobbylinux.bobbybook.dto.AuthorResponse;
import online.bobbylinux.bobbybook.services.AuthorService;

@WebMvcTest(AuthorsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthorsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AuthorService authorService;


	@Test
	@WithMockUser(username = "test", roles = { "USER" })
	void testGetAuthor() throws Exception {
		AuthorResponse author = new AuthorResponse(1L, "Stephen", "King");

		when(authorService.getAuthor(1L)).thenReturn(author);

		mockMvc.perform(get("/authors/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.first_name").value("Stephen")).andExpect(jsonPath("$.last_name").value("King"));

		verify(authorService, times(1)).getAuthor(1L);
	}
	
	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
    void testCreateAuthor() throws Exception {
        AuthorResponse author= new AuthorResponse(1L, "Stephen", "King");

        when(authorService.createAuthor("Stephen", "King")).thenReturn(author);

        mockMvc.perform(post("/authors")
                .param("first_name", "Stephen")
                .param("last_name", "King"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.first_name").value("Stephen"))
            .andExpect(jsonPath("$.last_name").value("King"));

        verify(authorService, times(1)).createAuthor("Stephen", "King");
    }

}
