package fi.netum.csc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fi.netum.csc.IntegrationTest;
import fi.netum.csc.domain.SearchHistory;
import fi.netum.csc.repository.SearchHistoryRepository;
import fi.netum.csc.service.SearchHistoryService;
import fi.netum.csc.service.dto.SearchHistoryDTO;
import fi.netum.csc.service.mapper.SearchHistoryMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SearchHistoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SearchHistoryResourceIT {

    private static final String DEFAULT_SEARCH_TERM = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_TERM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/search-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Mock
    private SearchHistoryRepository searchHistoryRepositoryMock;

    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    @Mock
    private SearchHistoryService searchHistoryServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSearchHistoryMockMvc;

    private SearchHistory searchHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SearchHistory createEntity(EntityManager em) {
        SearchHistory searchHistory = new SearchHistory().searchTerm(DEFAULT_SEARCH_TERM);
        return searchHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SearchHistory createUpdatedEntity(EntityManager em) {
        SearchHistory searchHistory = new SearchHistory().searchTerm(UPDATED_SEARCH_TERM);
        return searchHistory;
    }

    @BeforeEach
    public void initTest() {
        searchHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createSearchHistory() throws Exception {
        int databaseSizeBeforeCreate = searchHistoryRepository.findAll().size();
        // Create the SearchHistory
        SearchHistoryDTO searchHistoryDTO = searchHistoryMapper.toDto(searchHistory);
        restSearchHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(searchHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SearchHistory in the database
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        SearchHistory testSearchHistory = searchHistoryList.get(searchHistoryList.size() - 1);
        assertThat(testSearchHistory.getSearchTerm()).isEqualTo(DEFAULT_SEARCH_TERM);
    }

    @Test
    @Transactional
    void createSearchHistoryWithExistingId() throws Exception {
        // Create the SearchHistory with an existing ID
        searchHistory.setId(1L);
        SearchHistoryDTO searchHistoryDTO = searchHistoryMapper.toDto(searchHistory);

        int databaseSizeBeforeCreate = searchHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSearchHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(searchHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SearchHistory in the database
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSearchHistories() throws Exception {
        // Initialize the database
        searchHistoryRepository.saveAndFlush(searchHistory);

        // Get all the searchHistoryList
        restSearchHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(searchHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].searchTerm").value(hasItem(DEFAULT_SEARCH_TERM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSearchHistoriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(searchHistoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSearchHistoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(searchHistoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSearchHistoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(searchHistoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSearchHistoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(searchHistoryRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSearchHistory() throws Exception {
        // Initialize the database
        searchHistoryRepository.saveAndFlush(searchHistory);

        // Get the searchHistory
        restSearchHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, searchHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(searchHistory.getId().intValue()))
            .andExpect(jsonPath("$.searchTerm").value(DEFAULT_SEARCH_TERM));
    }

    @Test
    @Transactional
    void getNonExistingSearchHistory() throws Exception {
        // Get the searchHistory
        restSearchHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSearchHistory() throws Exception {
        // Initialize the database
        searchHistoryRepository.saveAndFlush(searchHistory);

        int databaseSizeBeforeUpdate = searchHistoryRepository.findAll().size();

        // Update the searchHistory
        SearchHistory updatedSearchHistory = searchHistoryRepository.findById(searchHistory.getId()).get();
        // Disconnect from session so that the updates on updatedSearchHistory are not directly saved in db
        em.detach(updatedSearchHistory);
        updatedSearchHistory.searchTerm(UPDATED_SEARCH_TERM);
        SearchHistoryDTO searchHistoryDTO = searchHistoryMapper.toDto(updatedSearchHistory);

        restSearchHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, searchHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(searchHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the SearchHistory in the database
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeUpdate);
        SearchHistory testSearchHistory = searchHistoryList.get(searchHistoryList.size() - 1);
        assertThat(testSearchHistory.getSearchTerm()).isEqualTo(UPDATED_SEARCH_TERM);
    }

    @Test
    @Transactional
    void putNonExistingSearchHistory() throws Exception {
        int databaseSizeBeforeUpdate = searchHistoryRepository.findAll().size();
        searchHistory.setId(count.incrementAndGet());

        // Create the SearchHistory
        SearchHistoryDTO searchHistoryDTO = searchHistoryMapper.toDto(searchHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSearchHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, searchHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(searchHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SearchHistory in the database
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSearchHistory() throws Exception {
        int databaseSizeBeforeUpdate = searchHistoryRepository.findAll().size();
        searchHistory.setId(count.incrementAndGet());

        // Create the SearchHistory
        SearchHistoryDTO searchHistoryDTO = searchHistoryMapper.toDto(searchHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSearchHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(searchHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SearchHistory in the database
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSearchHistory() throws Exception {
        int databaseSizeBeforeUpdate = searchHistoryRepository.findAll().size();
        searchHistory.setId(count.incrementAndGet());

        // Create the SearchHistory
        SearchHistoryDTO searchHistoryDTO = searchHistoryMapper.toDto(searchHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSearchHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(searchHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SearchHistory in the database
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSearchHistoryWithPatch() throws Exception {
        // Initialize the database
        searchHistoryRepository.saveAndFlush(searchHistory);

        int databaseSizeBeforeUpdate = searchHistoryRepository.findAll().size();

        // Update the searchHistory using partial update
        SearchHistory partialUpdatedSearchHistory = new SearchHistory();
        partialUpdatedSearchHistory.setId(searchHistory.getId());

        restSearchHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSearchHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSearchHistory))
            )
            .andExpect(status().isOk());

        // Validate the SearchHistory in the database
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeUpdate);
        SearchHistory testSearchHistory = searchHistoryList.get(searchHistoryList.size() - 1);
        assertThat(testSearchHistory.getSearchTerm()).isEqualTo(DEFAULT_SEARCH_TERM);
    }

    @Test
    @Transactional
    void fullUpdateSearchHistoryWithPatch() throws Exception {
        // Initialize the database
        searchHistoryRepository.saveAndFlush(searchHistory);

        int databaseSizeBeforeUpdate = searchHistoryRepository.findAll().size();

        // Update the searchHistory using partial update
        SearchHistory partialUpdatedSearchHistory = new SearchHistory();
        partialUpdatedSearchHistory.setId(searchHistory.getId());

        partialUpdatedSearchHistory.searchTerm(UPDATED_SEARCH_TERM);

        restSearchHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSearchHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSearchHistory))
            )
            .andExpect(status().isOk());

        // Validate the SearchHistory in the database
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeUpdate);
        SearchHistory testSearchHistory = searchHistoryList.get(searchHistoryList.size() - 1);
        assertThat(testSearchHistory.getSearchTerm()).isEqualTo(UPDATED_SEARCH_TERM);
    }

    @Test
    @Transactional
    void patchNonExistingSearchHistory() throws Exception {
        int databaseSizeBeforeUpdate = searchHistoryRepository.findAll().size();
        searchHistory.setId(count.incrementAndGet());

        // Create the SearchHistory
        SearchHistoryDTO searchHistoryDTO = searchHistoryMapper.toDto(searchHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSearchHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, searchHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(searchHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SearchHistory in the database
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSearchHistory() throws Exception {
        int databaseSizeBeforeUpdate = searchHistoryRepository.findAll().size();
        searchHistory.setId(count.incrementAndGet());

        // Create the SearchHistory
        SearchHistoryDTO searchHistoryDTO = searchHistoryMapper.toDto(searchHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSearchHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(searchHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SearchHistory in the database
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSearchHistory() throws Exception {
        int databaseSizeBeforeUpdate = searchHistoryRepository.findAll().size();
        searchHistory.setId(count.incrementAndGet());

        // Create the SearchHistory
        SearchHistoryDTO searchHistoryDTO = searchHistoryMapper.toDto(searchHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSearchHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(searchHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SearchHistory in the database
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSearchHistory() throws Exception {
        // Initialize the database
        searchHistoryRepository.saveAndFlush(searchHistory);

        int databaseSizeBeforeDelete = searchHistoryRepository.findAll().size();

        // Delete the searchHistory
        restSearchHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, searchHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
        assertThat(searchHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
