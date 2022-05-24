package fi.netum.csc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fi.netum.csc.IntegrationTest;
import fi.netum.csc.domain.UserResultKeyword;
import fi.netum.csc.repository.UserResultKeywordRepository;
import fi.netum.csc.service.UserResultKeywordService;
import fi.netum.csc.service.dto.UserResultKeywordDTO;
import fi.netum.csc.service.mapper.UserResultKeywordMapper;
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
 * Integration tests for the {@link UserResultKeywordResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserResultKeywordResourceIT {

    private static final String DEFAULT_RESULT_KEYWORD = "AAAAAAAAAA";
    private static final String UPDATED_RESULT_KEYWORD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-result-keywords";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserResultKeywordRepository userResultKeywordRepository;

    @Mock
    private UserResultKeywordRepository userResultKeywordRepositoryMock;

    @Autowired
    private UserResultKeywordMapper userResultKeywordMapper;

    @Mock
    private UserResultKeywordService userResultKeywordServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserResultKeywordMockMvc;

    private UserResultKeyword userResultKeyword;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserResultKeyword createEntity(EntityManager em) {
        UserResultKeyword userResultKeyword = new UserResultKeyword().resultKeyword(DEFAULT_RESULT_KEYWORD);
        return userResultKeyword;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserResultKeyword createUpdatedEntity(EntityManager em) {
        UserResultKeyword userResultKeyword = new UserResultKeyword().resultKeyword(UPDATED_RESULT_KEYWORD);
        return userResultKeyword;
    }

    @BeforeEach
    public void initTest() {
        userResultKeyword = createEntity(em);
    }

    @Test
    @Transactional
    void createUserResultKeyword() throws Exception {
        int databaseSizeBeforeCreate = userResultKeywordRepository.findAll().size();
        // Create the UserResultKeyword
        UserResultKeywordDTO userResultKeywordDTO = userResultKeywordMapper.toDto(userResultKeyword);
        restUserResultKeywordMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userResultKeywordDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserResultKeyword in the database
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeCreate + 1);
        UserResultKeyword testUserResultKeyword = userResultKeywordList.get(userResultKeywordList.size() - 1);
        assertThat(testUserResultKeyword.getResultKeyword()).isEqualTo(DEFAULT_RESULT_KEYWORD);
    }

    @Test
    @Transactional
    void createUserResultKeywordWithExistingId() throws Exception {
        // Create the UserResultKeyword with an existing ID
        userResultKeyword.setId(1L);
        UserResultKeywordDTO userResultKeywordDTO = userResultKeywordMapper.toDto(userResultKeyword);

        int databaseSizeBeforeCreate = userResultKeywordRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserResultKeywordMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userResultKeywordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserResultKeyword in the database
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserResultKeywords() throws Exception {
        // Initialize the database
        userResultKeywordRepository.saveAndFlush(userResultKeyword);

        // Get all the userResultKeywordList
        restUserResultKeywordMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userResultKeyword.getId().intValue())))
            .andExpect(jsonPath("$.[*].resultKeyword").value(hasItem(DEFAULT_RESULT_KEYWORD)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserResultKeywordsWithEagerRelationshipsIsEnabled() throws Exception {
        when(userResultKeywordServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserResultKeywordMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userResultKeywordServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserResultKeywordsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userResultKeywordServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserResultKeywordMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(userResultKeywordRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUserResultKeyword() throws Exception {
        // Initialize the database
        userResultKeywordRepository.saveAndFlush(userResultKeyword);

        // Get the userResultKeyword
        restUserResultKeywordMockMvc
            .perform(get(ENTITY_API_URL_ID, userResultKeyword.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userResultKeyword.getId().intValue()))
            .andExpect(jsonPath("$.resultKeyword").value(DEFAULT_RESULT_KEYWORD));
    }

    @Test
    @Transactional
    void getNonExistingUserResultKeyword() throws Exception {
        // Get the userResultKeyword
        restUserResultKeywordMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserResultKeyword() throws Exception {
        // Initialize the database
        userResultKeywordRepository.saveAndFlush(userResultKeyword);

        int databaseSizeBeforeUpdate = userResultKeywordRepository.findAll().size();

        // Update the userResultKeyword
        UserResultKeyword updatedUserResultKeyword = userResultKeywordRepository.findById(userResultKeyword.getId()).get();
        // Disconnect from session so that the updates on updatedUserResultKeyword are not directly saved in db
        em.detach(updatedUserResultKeyword);
        updatedUserResultKeyword.resultKeyword(UPDATED_RESULT_KEYWORD);
        UserResultKeywordDTO userResultKeywordDTO = userResultKeywordMapper.toDto(updatedUserResultKeyword);

        restUserResultKeywordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userResultKeywordDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userResultKeywordDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserResultKeyword in the database
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeUpdate);
        UserResultKeyword testUserResultKeyword = userResultKeywordList.get(userResultKeywordList.size() - 1);
        assertThat(testUserResultKeyword.getResultKeyword()).isEqualTo(UPDATED_RESULT_KEYWORD);
    }

    @Test
    @Transactional
    void putNonExistingUserResultKeyword() throws Exception {
        int databaseSizeBeforeUpdate = userResultKeywordRepository.findAll().size();
        userResultKeyword.setId(count.incrementAndGet());

        // Create the UserResultKeyword
        UserResultKeywordDTO userResultKeywordDTO = userResultKeywordMapper.toDto(userResultKeyword);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserResultKeywordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userResultKeywordDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userResultKeywordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserResultKeyword in the database
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserResultKeyword() throws Exception {
        int databaseSizeBeforeUpdate = userResultKeywordRepository.findAll().size();
        userResultKeyword.setId(count.incrementAndGet());

        // Create the UserResultKeyword
        UserResultKeywordDTO userResultKeywordDTO = userResultKeywordMapper.toDto(userResultKeyword);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserResultKeywordMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userResultKeywordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserResultKeyword in the database
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserResultKeyword() throws Exception {
        int databaseSizeBeforeUpdate = userResultKeywordRepository.findAll().size();
        userResultKeyword.setId(count.incrementAndGet());

        // Create the UserResultKeyword
        UserResultKeywordDTO userResultKeywordDTO = userResultKeywordMapper.toDto(userResultKeyword);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserResultKeywordMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userResultKeywordDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserResultKeyword in the database
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserResultKeywordWithPatch() throws Exception {
        // Initialize the database
        userResultKeywordRepository.saveAndFlush(userResultKeyword);

        int databaseSizeBeforeUpdate = userResultKeywordRepository.findAll().size();

        // Update the userResultKeyword using partial update
        UserResultKeyword partialUpdatedUserResultKeyword = new UserResultKeyword();
        partialUpdatedUserResultKeyword.setId(userResultKeyword.getId());

        restUserResultKeywordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserResultKeyword.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserResultKeyword))
            )
            .andExpect(status().isOk());

        // Validate the UserResultKeyword in the database
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeUpdate);
        UserResultKeyword testUserResultKeyword = userResultKeywordList.get(userResultKeywordList.size() - 1);
        assertThat(testUserResultKeyword.getResultKeyword()).isEqualTo(DEFAULT_RESULT_KEYWORD);
    }

    @Test
    @Transactional
    void fullUpdateUserResultKeywordWithPatch() throws Exception {
        // Initialize the database
        userResultKeywordRepository.saveAndFlush(userResultKeyword);

        int databaseSizeBeforeUpdate = userResultKeywordRepository.findAll().size();

        // Update the userResultKeyword using partial update
        UserResultKeyword partialUpdatedUserResultKeyword = new UserResultKeyword();
        partialUpdatedUserResultKeyword.setId(userResultKeyword.getId());

        partialUpdatedUserResultKeyword.resultKeyword(UPDATED_RESULT_KEYWORD);

        restUserResultKeywordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserResultKeyword.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserResultKeyword))
            )
            .andExpect(status().isOk());

        // Validate the UserResultKeyword in the database
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeUpdate);
        UserResultKeyword testUserResultKeyword = userResultKeywordList.get(userResultKeywordList.size() - 1);
        assertThat(testUserResultKeyword.getResultKeyword()).isEqualTo(UPDATED_RESULT_KEYWORD);
    }

    @Test
    @Transactional
    void patchNonExistingUserResultKeyword() throws Exception {
        int databaseSizeBeforeUpdate = userResultKeywordRepository.findAll().size();
        userResultKeyword.setId(count.incrementAndGet());

        // Create the UserResultKeyword
        UserResultKeywordDTO userResultKeywordDTO = userResultKeywordMapper.toDto(userResultKeyword);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserResultKeywordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userResultKeywordDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userResultKeywordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserResultKeyword in the database
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserResultKeyword() throws Exception {
        int databaseSizeBeforeUpdate = userResultKeywordRepository.findAll().size();
        userResultKeyword.setId(count.incrementAndGet());

        // Create the UserResultKeyword
        UserResultKeywordDTO userResultKeywordDTO = userResultKeywordMapper.toDto(userResultKeyword);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserResultKeywordMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userResultKeywordDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserResultKeyword in the database
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserResultKeyword() throws Exception {
        int databaseSizeBeforeUpdate = userResultKeywordRepository.findAll().size();
        userResultKeyword.setId(count.incrementAndGet());

        // Create the UserResultKeyword
        UserResultKeywordDTO userResultKeywordDTO = userResultKeywordMapper.toDto(userResultKeyword);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserResultKeywordMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userResultKeywordDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserResultKeyword in the database
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserResultKeyword() throws Exception {
        // Initialize the database
        userResultKeywordRepository.saveAndFlush(userResultKeyword);

        int databaseSizeBeforeDelete = userResultKeywordRepository.findAll().size();

        // Delete the userResultKeyword
        restUserResultKeywordMockMvc
            .perform(delete(ENTITY_API_URL_ID, userResultKeyword.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserResultKeyword> userResultKeywordList = userResultKeywordRepository.findAll();
        assertThat(userResultKeywordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
