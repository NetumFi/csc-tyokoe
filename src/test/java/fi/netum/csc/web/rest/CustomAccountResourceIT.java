package fi.netum.csc.web.rest;

import fi.netum.csc.IntegrationTest;
import fi.netum.csc.domain.SearchHistory;
import fi.netum.csc.domain.User;
import fi.netum.csc.repository.ReadingListRepository;
import fi.netum.csc.repository.SearchHistoryRepository;
import fi.netum.csc.repository.UserRepository;
import fi.netum.csc.service.dto.ReadingListDTO;
import fi.netum.csc.service.dto.SearchSettingDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CustomAccountResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;
    private User savedUser;
    @Autowired
    private ReadingListRepository readingListRepository;



    @BeforeEach
    public void initTest() {
        User user = new User();
        user.setLogin("juuso");
        user.setEmail("junit-juuso@localhost");
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        savedUser = userRepository.saveAndFlush(user);

    }


    @Test
    @WithMockUser("juuso")
    @Transactional
    public void testGetCurrentUserSearchSettings() throws Exception {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setSearchTerm("junit-searchterm");
        searchHistory.setUser(savedUser);
        searchHistoryRepository.saveAndFlush(searchHistory);


        mockMvc.perform(get("/api/account/searchhistory").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].searchTerm").value(searchHistory.getSearchTerm()))
            .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    @WithMockUser("juuso")
    @Transactional
    public void testCreateReadingList() throws Exception {

        ReadingListDTO dto = new ReadingListDTO();
        dto.setMaterialId("12341");
        String dtoJson = convertObject2JsonString(dto);

        mockMvc.perform(post("/api/account/readinglist").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(dtoJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.materialId").value(dto.getMaterialId()))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.created").exists())
            .andExpect(jsonPath("$.user").exists())
            .andExpect(jsonPath("$.user.id").exists())
            .andExpect(jsonPath("$.user.id").value(savedUser.getId()));
    }


    @Test
    @WithMockUser("juuso")
    @Transactional
    public void testGetCurrentUserSearchHistory() throws Exception {
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.searchTerm("Junit-searchterm");
        searchHistory.setUser(savedUser);
        SearchHistory savedHistory = searchHistoryRepository.saveAndFlush(searchHistory);

        mockMvc.perform((get("/api/account/searchhistory").accept(MediaType.APPLICATION_JSON)))
            .andExpect(status().isOk())
            .andExpect((jsonPath("$[0].id")).value(savedHistory.getId()))
            .andExpect((jsonPath("$[0].searchTerm")).value(savedHistory.getSearchTerm()))
            .andExpect((jsonPath("$[0].user.id")).value(savedHistory.getUser().getId()));
    }

    @Test
    @WithMockUser("juuso")
    @Transactional
    public void testAddSearchHistory() throws Exception {


        SearchSettingDTO dto = new SearchSettingDTO();
        dto.setSearchTerm("junit-new-searchterm");
        String dtoJson = convertObject2JsonString(dto);
        mockMvc.perform(post("/api/account/searchhistory").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(dtoJson))
            .andExpect(status().isCreated())
            .andExpect((jsonPath("$.searchTerm").value(dto.getSearchTerm())))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.user").exists());
    }

    private String convertObject2JsonString(Object dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String dtoJson = mapper.writeValueAsString(dto);
        return dtoJson;
    }


}
