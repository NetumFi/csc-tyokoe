package fi.netum.csc.web.rest;

import fi.netum.csc.IntegrationTest;
import fi.netum.csc.domain.User;
import fi.netum.csc.service.AoeService;
import fi.netum.csc.service.SearchSettingService;
import fi.netum.csc.service.UserResultKeywordService;
import fi.netum.csc.service.UserService;
import fi.netum.csc.service.dto.aoe.AoeSearchParameters;
import fi.netum.csc.service.dto.aoe.Filter;
import fi.netum.csc.service.dto.aoe.Paging;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private final UserService userService = mock(UserService.class);

    private final SearchSettingService searchSettingService = mock(SearchSettingService.class);

    @Autowired
    private UserResultKeywordService userResultKeywordService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        SearchResource searchResource = new SearchResource(aoeService, userService, userResultKeywordService, searchSettingService);
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
     * Test getMetadatas
     */
    @Test
    void testGetMetadatas() throws Exception {
        restMockMvc.perform(get("/api/search/get-metadatas/{ids}", "100", "200")).andExpect(status().isOk());
    }

    /**
     * Test doSearch
     */
    @Test
    void testDoSearch() throws Exception {

        when(userService.getUserWithAuthorities()).thenReturn(Optional.empty());

        List<Filter> filters = List.of(new Filter("educationalLevels", List.of("e5a48ada-3de0-4246-9b8f-32d4ff68e22f")));
        String keywords = "sana1, sana2, sana3";
        Paging paging = new Paging(0, 3, "uusin");

        AoeSearchParameters aoeSearchParameters = new AoeSearchParameters(filters, keywords, paging);
        restMockMvc.perform(post("/api/search/do-search").contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aoeSearchParameters))).andExpect(status().isOk());
    }

    /**
     * Test getMetadata
     */
    @Test
    void testGetRecommendations() throws Exception {
        var optionalUser = Optional.of(new User());
        optionalUser.get().setLogin("akseli");
        when(userService.getUserWithAuthorities()).thenReturn(Optional.empty());
        when(searchSettingService.findAllByUser(any(), any())).thenReturn(null);
        restMockMvc.perform(get("/api/search/get-recommendations/")).andExpect(status().isOk());
    }

}
