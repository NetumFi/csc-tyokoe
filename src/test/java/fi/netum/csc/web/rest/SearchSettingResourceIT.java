package fi.netum.csc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fi.netum.csc.IntegrationTest;
import fi.netum.csc.domain.SearchSetting;
import fi.netum.csc.repository.SearchSettingRepository;
import fi.netum.csc.service.SearchSettingService;
import fi.netum.csc.service.dto.SearchSettingDTO;
import fi.netum.csc.service.mapper.SearchSettingMapper;
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
 * Integration tests for the {@link SearchSettingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SearchSettingResourceIT {

    private static final String DEFAULT_SEARCH_TERM = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_TERM = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_AGE = "AAAAAAAAAA";
    private static final String UPDATED_AGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/search-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SearchSettingRepository searchSettingRepository;

    @Mock
    private SearchSettingRepository searchSettingRepositoryMock;

    @Autowired
    private SearchSettingMapper searchSettingMapper;

    @Mock
    private SearchSettingService searchSettingServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSearchSettingMockMvc;

    private SearchSetting searchSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SearchSetting createEntity(EntityManager em) {
        SearchSetting searchSetting = new SearchSetting().searchTerm(DEFAULT_SEARCH_TERM).role(DEFAULT_ROLE).age(DEFAULT_AGE);
        return searchSetting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SearchSetting createUpdatedEntity(EntityManager em) {
        SearchSetting searchSetting = new SearchSetting().searchTerm(UPDATED_SEARCH_TERM).role(UPDATED_ROLE).age(UPDATED_AGE);
        return searchSetting;
    }

    @BeforeEach
    public void initTest() {
        searchSetting = createEntity(em);
    }

    @Test
    @Transactional
    void createSearchSetting() throws Exception {
        int databaseSizeBeforeCreate = searchSettingRepository.findAll().size();
        // Create the SearchSetting
        SearchSettingDTO searchSettingDTO = searchSettingMapper.toDto(searchSetting);
        restSearchSettingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(searchSettingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SearchSetting in the database
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeCreate + 1);
        SearchSetting testSearchSetting = searchSettingList.get(searchSettingList.size() - 1);
        assertThat(testSearchSetting.getSearchTerm()).isEqualTo(DEFAULT_SEARCH_TERM);
        assertThat(testSearchSetting.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testSearchSetting.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    @Transactional
    void createSearchSettingWithExistingId() throws Exception {
        // Create the SearchSetting with an existing ID
        searchSetting.setId(1L);
        SearchSettingDTO searchSettingDTO = searchSettingMapper.toDto(searchSetting);

        int databaseSizeBeforeCreate = searchSettingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSearchSettingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(searchSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SearchSetting in the database
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSearchSettings() throws Exception {
        // Initialize the database
        searchSettingRepository.saveAndFlush(searchSetting);

        // Get all the searchSettingList
        restSearchSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(searchSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].searchTerm").value(hasItem(DEFAULT_SEARCH_TERM)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSearchSettingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(searchSettingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSearchSettingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(searchSettingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSearchSettingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(searchSettingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSearchSettingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(searchSettingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSearchSetting() throws Exception {
        // Initialize the database
        searchSettingRepository.saveAndFlush(searchSetting);

        // Get the searchSetting
        restSearchSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, searchSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(searchSetting.getId().intValue()))
            .andExpect(jsonPath("$.searchTerm").value(DEFAULT_SEARCH_TERM))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE));
    }

    @Test
    @Transactional
    void getNonExistingSearchSetting() throws Exception {
        // Get the searchSetting
        restSearchSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSearchSetting() throws Exception {
        // Initialize the database
        searchSettingRepository.saveAndFlush(searchSetting);

        int databaseSizeBeforeUpdate = searchSettingRepository.findAll().size();

        // Update the searchSetting
        SearchSetting updatedSearchSetting = searchSettingRepository.findById(searchSetting.getId()).get();
        // Disconnect from session so that the updates on updatedSearchSetting are not directly saved in db
        em.detach(updatedSearchSetting);
        updatedSearchSetting.searchTerm(UPDATED_SEARCH_TERM).role(UPDATED_ROLE).age(UPDATED_AGE);
        SearchSettingDTO searchSettingDTO = searchSettingMapper.toDto(updatedSearchSetting);

        restSearchSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, searchSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(searchSettingDTO))
            )
            .andExpect(status().isOk());

        // Validate the SearchSetting in the database
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeUpdate);
        SearchSetting testSearchSetting = searchSettingList.get(searchSettingList.size() - 1);
        assertThat(testSearchSetting.getSearchTerm()).isEqualTo(UPDATED_SEARCH_TERM);
        assertThat(testSearchSetting.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testSearchSetting.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    void putNonExistingSearchSetting() throws Exception {
        int databaseSizeBeforeUpdate = searchSettingRepository.findAll().size();
        searchSetting.setId(count.incrementAndGet());

        // Create the SearchSetting
        SearchSettingDTO searchSettingDTO = searchSettingMapper.toDto(searchSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSearchSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, searchSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(searchSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SearchSetting in the database
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSearchSetting() throws Exception {
        int databaseSizeBeforeUpdate = searchSettingRepository.findAll().size();
        searchSetting.setId(count.incrementAndGet());

        // Create the SearchSetting
        SearchSettingDTO searchSettingDTO = searchSettingMapper.toDto(searchSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSearchSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(searchSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SearchSetting in the database
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSearchSetting() throws Exception {
        int databaseSizeBeforeUpdate = searchSettingRepository.findAll().size();
        searchSetting.setId(count.incrementAndGet());

        // Create the SearchSetting
        SearchSettingDTO searchSettingDTO = searchSettingMapper.toDto(searchSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSearchSettingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(searchSettingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SearchSetting in the database
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSearchSettingWithPatch() throws Exception {
        // Initialize the database
        searchSettingRepository.saveAndFlush(searchSetting);

        int databaseSizeBeforeUpdate = searchSettingRepository.findAll().size();

        // Update the searchSetting using partial update
        SearchSetting partialUpdatedSearchSetting = new SearchSetting();
        partialUpdatedSearchSetting.setId(searchSetting.getId());

        restSearchSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSearchSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSearchSetting))
            )
            .andExpect(status().isOk());

        // Validate the SearchSetting in the database
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeUpdate);
        SearchSetting testSearchSetting = searchSettingList.get(searchSettingList.size() - 1);
        assertThat(testSearchSetting.getSearchTerm()).isEqualTo(DEFAULT_SEARCH_TERM);
        assertThat(testSearchSetting.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testSearchSetting.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    @Transactional
    void fullUpdateSearchSettingWithPatch() throws Exception {
        // Initialize the database
        searchSettingRepository.saveAndFlush(searchSetting);

        int databaseSizeBeforeUpdate = searchSettingRepository.findAll().size();

        // Update the searchSetting using partial update
        SearchSetting partialUpdatedSearchSetting = new SearchSetting();
        partialUpdatedSearchSetting.setId(searchSetting.getId());

        partialUpdatedSearchSetting.searchTerm(UPDATED_SEARCH_TERM).role(UPDATED_ROLE).age(UPDATED_AGE);

        restSearchSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSearchSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSearchSetting))
            )
            .andExpect(status().isOk());

        // Validate the SearchSetting in the database
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeUpdate);
        SearchSetting testSearchSetting = searchSettingList.get(searchSettingList.size() - 1);
        assertThat(testSearchSetting.getSearchTerm()).isEqualTo(UPDATED_SEARCH_TERM);
        assertThat(testSearchSetting.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testSearchSetting.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    void patchNonExistingSearchSetting() throws Exception {
        int databaseSizeBeforeUpdate = searchSettingRepository.findAll().size();
        searchSetting.setId(count.incrementAndGet());

        // Create the SearchSetting
        SearchSettingDTO searchSettingDTO = searchSettingMapper.toDto(searchSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSearchSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, searchSettingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(searchSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SearchSetting in the database
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSearchSetting() throws Exception {
        int databaseSizeBeforeUpdate = searchSettingRepository.findAll().size();
        searchSetting.setId(count.incrementAndGet());

        // Create the SearchSetting
        SearchSettingDTO searchSettingDTO = searchSettingMapper.toDto(searchSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSearchSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(searchSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SearchSetting in the database
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSearchSetting() throws Exception {
        int databaseSizeBeforeUpdate = searchSettingRepository.findAll().size();
        searchSetting.setId(count.incrementAndGet());

        // Create the SearchSetting
        SearchSettingDTO searchSettingDTO = searchSettingMapper.toDto(searchSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSearchSettingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(searchSettingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SearchSetting in the database
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSearchSetting() throws Exception {
        // Initialize the database
        searchSettingRepository.saveAndFlush(searchSetting);

        int databaseSizeBeforeDelete = searchSettingRepository.findAll().size();

        // Delete the searchSetting
        restSearchSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, searchSetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SearchSetting> searchSettingList = searchSettingRepository.findAll();
        assertThat(searchSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
