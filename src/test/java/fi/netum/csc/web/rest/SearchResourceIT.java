package fi.netum.csc.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fi.netum.csc.IntegrationTest;
import fi.netum.csc.service.AoeService;
import fi.netum.csc.service.dto.aoe.AoeSearchParameters;
import fi.netum.csc.service.dto.aoe.Filter;
import fi.netum.csc.service.dto.aoe.Paging;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

/**
 * Test class for the SearchResource REST controller.
 *
 * @see SearchResource
 */
@IntegrationTest
class SearchResourceIT {

    private MockMvc restMockMvc;

    @Autowired
    private AoeService aoeService;

    @Mock
    private AoeService aoeServiceMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        SearchResource searchResource = new SearchResource(aoeService);
        restMockMvc = MockMvcBuilders.standaloneSetup(searchResource).build();
    }

    /**
     * Test getMetadata
     */
    @Test
    void testGetMetadata() throws Exception {
        restMockMvc.perform(get("/api/search/get-metadata/{id}", "100")).andExpect(status().isOk());
    }

    /**
     * Test doSearch
     */
    @Test
    void testDoSearch() throws Exception {

        List<Filter> filters = List.of(new Filter("educationalLevels", List.of("e5a48ada-3de0-4246-9b8f-32d4ff68e22f")));
        String keywords = "sana1, sana2, sana3";
        Paging paging = new Paging(0, 3, "uusin");

        AoeSearchParameters aoeSearchParameters = new AoeSearchParameters(filters, keywords, paging);
        restMockMvc.perform(post("/api/search/do-search").contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aoeSearchParameters))).andExpect(status().isOk());
    }
}
