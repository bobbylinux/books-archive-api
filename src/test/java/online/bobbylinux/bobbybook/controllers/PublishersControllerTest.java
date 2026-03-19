package online.bobbylinux.bobbybook.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import online.bobbylinux.bobbybook.dto.PublisherResponse;
import online.bobbylinux.bobbybook.services.PublisherService;

@WebMvcTest(PublishersControllerTest.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PublishersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PublisherService publisherService;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Test
    @WithMockUser(username = "test", roles = { "USER" })
    void testGetPublisher() throws Exception {
        PublisherResponse publisher = new PublisherResponse(1L, "Mondadori");

        when(publisherService.getPublisher(1L)).thenReturn(publisher);

        mockMvc.perform(get(apiPrefix + "/publishers/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Mondadori"));

        verify(publisherService, times(1)).getPublisher(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testCreatePublisher() throws Exception {
        PublisherResponse publisher = new PublisherResponse(1L, "Einaudi");

        when(publisherService.createPublisher("Einaudi")).thenReturn(publisher);

        mockMvc.perform(post(apiPrefix + "/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "name": "Einaudi"
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Einaudi"));

        verify(publisherService, times(1)).createPublisher("Einaudi");
    }

    @Test
    @WithMockUser(username = "test", roles = { "USER" })
    void testSearchPublisher() throws Exception {

        List<PublisherResponse> publishers = new ArrayList<PublisherResponse>();
        publishers.add(new PublisherResponse(203L, "Mondadorina"));
        publishers.add(new PublisherResponse(101L, "Alfonso Mondani"));
        publishers.add(new PublisherResponse(501L, "Mondo Libri"));
        publishers.add(new PublisherResponse(1L, "Mondadori"));

        when(publisherService.searchPublisher("monda")).thenReturn(publishers);

        mockMvc.perform(get(apiPrefix + "/publishers").param("search_string", "monda")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(203))
                .andExpect(jsonPath("$[0].name").value("Mondadorina"))
                .andExpect(jsonPath("$[1].id").value(101))
                .andExpect(jsonPath("$[1].first_name").value("Alfonso Mondani"))
                .andExpect(jsonPath("$[2].id").value(501))
                .andExpect(jsonPath("$[2].first_name").value("Mondo Libri"))
                .andExpect(jsonPath("$[3].id").value(1))
                .andExpect(jsonPath("$[3].first_name").value("Mondadori"));

        verify(publisherService, times(1)).searchPublisher("monda");
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testUpdatePublisher() throws Exception {
        PublisherResponse publisher = new PublisherResponse(1L, "Mondadori");
        Long id = 1L;
        when(publisherService.updatePublisher(id, "Mondadori")).thenReturn(publisher);

        mockMvc.perform(patch(apiPrefix + "/publishers/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "name": "Mondadori"
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.first_name").value("Stephen"))
                .andExpect(jsonPath("$.last_name").value("King"));

        verify(publisherService, times(1)).updatePublisher(id, "Mondadori");
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testDeletePublisher() throws Exception {
        Long id = 1L;

        doNothing().when(publisherService).deletePublisher(id);

        mockMvc.perform(delete(apiPrefix + "/publishers/" + id))
                .andExpect(status().isNoContent());

        verify(publisherService, times(1)).deletePublisher(id);
    }

    @Test
    @WithMockUser(username = "test", roles = { "USER" })
    void testGetAllPublishers() throws Exception {

        List<PublisherResponse> publishers = new ArrayList<PublisherResponse>();
        publishers.add(new PublisherResponse(203L, "Mondadori"));
        publishers.add(new PublisherResponse(101L, "Einaudi"));
        publishers.add(new PublisherResponse(501L, "Sellerio"));
        publishers.add(new PublisherResponse(1L, "Bur"));
        publishers.add(new PublisherResponse(102L, "Bompiani"));

        when(publisherService.getAllPublishers()).thenReturn(publishers);

        mockMvc.perform(get(apiPrefix + "/publishers")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(203))
                .andExpect(jsonPath("$[0].name").value("Mondadori"))
                .andExpect(jsonPath("$[1].id").value(101))
                .andExpect(jsonPath("$[1].name").value("Einaudi"))
                .andExpect(jsonPath("$[2].id").value(501))
                .andExpect(jsonPath("$[2].name").value("Sellerio"))
                .andExpect(jsonPath("$[3].id").value(1))
                .andExpect(jsonPath("$[3].name").value("Bur"))
                .andExpect(jsonPath("$[4].id").value(102))
                .andExpect(jsonPath("$[4].name").value("Bompiani"));

        verify(publisherService, times(1)).getAllPublishers();
    }
}
