package fi.netum.csc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fi.netum.csc.IntegrationTest;
import fi.netum.csc.domain.EducationLevelCodeSet;
import fi.netum.csc.repository.EducationLevelCodeSetRepository;
import fi.netum.csc.service.dto.EducationLevelCodeSetDTO;
import fi.netum.csc.service.mapper.EducationLevelCodeSetMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EducationLevelCodeSetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EducationLevelCodeSetResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL_EN = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_EN = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL_FI = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_FI = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL_SV = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_SV = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/education-level-code-sets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EducationLevelCodeSetRepository educationLevelCodeSetRepository;

    @Autowired
    private EducationLevelCodeSetMapper educationLevelCodeSetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEducationLevelCodeSetMockMvc;

    private EducationLevelCodeSet educationLevelCodeSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducationLevelCodeSet createEntity(EntityManager em) {
        EducationLevelCodeSet educationLevelCodeSet = new EducationLevelCodeSet()
            .code(DEFAULT_CODE)
            .codeId(DEFAULT_CODE_ID)
            .labelEn(DEFAULT_LABEL_EN)
            .labelFi(DEFAULT_LABEL_FI)
            .labelSv(DEFAULT_LABEL_SV);
        return educationLevelCodeSet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducationLevelCodeSet createUpdatedEntity(EntityManager em) {
        EducationLevelCodeSet educationLevelCodeSet = new EducationLevelCodeSet()
            .code(UPDATED_CODE)
            .codeId(UPDATED_CODE_ID)
            .labelEn(UPDATED_LABEL_EN)
            .labelFi(UPDATED_LABEL_FI)
            .labelSv(UPDATED_LABEL_SV);
        return educationLevelCodeSet;
    }

    @BeforeEach
    public void initTest() {
        educationLevelCodeSet = createEntity(em);
    }

    @Test
    @Transactional
    void createEducationLevelCodeSet() throws Exception {
        int databaseSizeBeforeCreate = educationLevelCodeSetRepository.findAll().size();
        // Create the EducationLevelCodeSet
        EducationLevelCodeSetDTO educationLevelCodeSetDTO = educationLevelCodeSetMapper.toDto(educationLevelCodeSet);
        restEducationLevelCodeSetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationLevelCodeSetDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EducationLevelCodeSet in the database
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeCreate + 1);
        EducationLevelCodeSet testEducationLevelCodeSet = educationLevelCodeSetList.get(educationLevelCodeSetList.size() - 1);
        assertThat(testEducationLevelCodeSet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEducationLevelCodeSet.getCodeId()).isEqualTo(DEFAULT_CODE_ID);
        assertThat(testEducationLevelCodeSet.getLabelEn()).isEqualTo(DEFAULT_LABEL_EN);
        assertThat(testEducationLevelCodeSet.getLabelFi()).isEqualTo(DEFAULT_LABEL_FI);
        assertThat(testEducationLevelCodeSet.getLabelSv()).isEqualTo(DEFAULT_LABEL_SV);
    }

    @Test
    @Transactional
    void createEducationLevelCodeSetWithExistingId() throws Exception {
        // Create the EducationLevelCodeSet with an existing ID
        educationLevelCodeSet.setId(1L);
        EducationLevelCodeSetDTO educationLevelCodeSetDTO = educationLevelCodeSetMapper.toDto(educationLevelCodeSet);

        int databaseSizeBeforeCreate = educationLevelCodeSetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationLevelCodeSetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationLevelCodeSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducationLevelCodeSet in the database
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEducationLevelCodeSets() throws Exception {
        // Initialize the database
        educationLevelCodeSetRepository.saveAndFlush(educationLevelCodeSet);

        // Get all the educationLevelCodeSetList
        restEducationLevelCodeSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationLevelCodeSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].codeId").value(hasItem(DEFAULT_CODE_ID)))
            .andExpect(jsonPath("$.[*].labelEn").value(hasItem(DEFAULT_LABEL_EN)))
            .andExpect(jsonPath("$.[*].labelFi").value(hasItem(DEFAULT_LABEL_FI)))
            .andExpect(jsonPath("$.[*].labelSv").value(hasItem(DEFAULT_LABEL_SV)));
    }

    @Test
    @Transactional
    void getEducationLevelCodeSet() throws Exception {
        // Initialize the database
        educationLevelCodeSetRepository.saveAndFlush(educationLevelCodeSet);

        // Get the educationLevelCodeSet
        restEducationLevelCodeSetMockMvc
            .perform(get(ENTITY_API_URL_ID, educationLevelCodeSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(educationLevelCodeSet.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.codeId").value(DEFAULT_CODE_ID))
            .andExpect(jsonPath("$.labelEn").value(DEFAULT_LABEL_EN))
            .andExpect(jsonPath("$.labelFi").value(DEFAULT_LABEL_FI))
            .andExpect(jsonPath("$.labelSv").value(DEFAULT_LABEL_SV));
    }

    @Test
    @Transactional
    void getNonExistingEducationLevelCodeSet() throws Exception {
        // Get the educationLevelCodeSet
        restEducationLevelCodeSetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEducationLevelCodeSet() throws Exception {
        // Initialize the database
        educationLevelCodeSetRepository.saveAndFlush(educationLevelCodeSet);

        int databaseSizeBeforeUpdate = educationLevelCodeSetRepository.findAll().size();

        // Update the educationLevelCodeSet
        EducationLevelCodeSet updatedEducationLevelCodeSet = educationLevelCodeSetRepository.findById(educationLevelCodeSet.getId()).get();
        // Disconnect from session so that the updates on updatedEducationLevelCodeSet are not directly saved in db
        em.detach(updatedEducationLevelCodeSet);
        updatedEducationLevelCodeSet
            .code(UPDATED_CODE)
            .codeId(UPDATED_CODE_ID)
            .labelEn(UPDATED_LABEL_EN)
            .labelFi(UPDATED_LABEL_FI)
            .labelSv(UPDATED_LABEL_SV);
        EducationLevelCodeSetDTO educationLevelCodeSetDTO = educationLevelCodeSetMapper.toDto(updatedEducationLevelCodeSet);

        restEducationLevelCodeSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationLevelCodeSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationLevelCodeSetDTO))
            )
            .andExpect(status().isOk());

        // Validate the EducationLevelCodeSet in the database
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeUpdate);
        EducationLevelCodeSet testEducationLevelCodeSet = educationLevelCodeSetList.get(educationLevelCodeSetList.size() - 1);
        assertThat(testEducationLevelCodeSet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEducationLevelCodeSet.getCodeId()).isEqualTo(UPDATED_CODE_ID);
        assertThat(testEducationLevelCodeSet.getLabelEn()).isEqualTo(UPDATED_LABEL_EN);
        assertThat(testEducationLevelCodeSet.getLabelFi()).isEqualTo(UPDATED_LABEL_FI);
        assertThat(testEducationLevelCodeSet.getLabelSv()).isEqualTo(UPDATED_LABEL_SV);
    }

    @Test
    @Transactional
    void putNonExistingEducationLevelCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = educationLevelCodeSetRepository.findAll().size();
        educationLevelCodeSet.setId(count.incrementAndGet());

        // Create the EducationLevelCodeSet
        EducationLevelCodeSetDTO educationLevelCodeSetDTO = educationLevelCodeSetMapper.toDto(educationLevelCodeSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationLevelCodeSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationLevelCodeSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationLevelCodeSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducationLevelCodeSet in the database
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEducationLevelCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = educationLevelCodeSetRepository.findAll().size();
        educationLevelCodeSet.setId(count.incrementAndGet());

        // Create the EducationLevelCodeSet
        EducationLevelCodeSetDTO educationLevelCodeSetDTO = educationLevelCodeSetMapper.toDto(educationLevelCodeSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationLevelCodeSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationLevelCodeSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducationLevelCodeSet in the database
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEducationLevelCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = educationLevelCodeSetRepository.findAll().size();
        educationLevelCodeSet.setId(count.incrementAndGet());

        // Create the EducationLevelCodeSet
        EducationLevelCodeSetDTO educationLevelCodeSetDTO = educationLevelCodeSetMapper.toDto(educationLevelCodeSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationLevelCodeSetMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationLevelCodeSetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EducationLevelCodeSet in the database
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEducationLevelCodeSetWithPatch() throws Exception {
        // Initialize the database
        educationLevelCodeSetRepository.saveAndFlush(educationLevelCodeSet);

        int databaseSizeBeforeUpdate = educationLevelCodeSetRepository.findAll().size();

        // Update the educationLevelCodeSet using partial update
        EducationLevelCodeSet partialUpdatedEducationLevelCodeSet = new EducationLevelCodeSet();
        partialUpdatedEducationLevelCodeSet.setId(educationLevelCodeSet.getId());

        partialUpdatedEducationLevelCodeSet.labelSv(UPDATED_LABEL_SV);

        restEducationLevelCodeSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducationLevelCodeSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducationLevelCodeSet))
            )
            .andExpect(status().isOk());

        // Validate the EducationLevelCodeSet in the database
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeUpdate);
        EducationLevelCodeSet testEducationLevelCodeSet = educationLevelCodeSetList.get(educationLevelCodeSetList.size() - 1);
        assertThat(testEducationLevelCodeSet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEducationLevelCodeSet.getCodeId()).isEqualTo(DEFAULT_CODE_ID);
        assertThat(testEducationLevelCodeSet.getLabelEn()).isEqualTo(DEFAULT_LABEL_EN);
        assertThat(testEducationLevelCodeSet.getLabelFi()).isEqualTo(DEFAULT_LABEL_FI);
        assertThat(testEducationLevelCodeSet.getLabelSv()).isEqualTo(UPDATED_LABEL_SV);
    }

    @Test
    @Transactional
    void fullUpdateEducationLevelCodeSetWithPatch() throws Exception {
        // Initialize the database
        educationLevelCodeSetRepository.saveAndFlush(educationLevelCodeSet);

        int databaseSizeBeforeUpdate = educationLevelCodeSetRepository.findAll().size();

        // Update the educationLevelCodeSet using partial update
        EducationLevelCodeSet partialUpdatedEducationLevelCodeSet = new EducationLevelCodeSet();
        partialUpdatedEducationLevelCodeSet.setId(educationLevelCodeSet.getId());

        partialUpdatedEducationLevelCodeSet
            .code(UPDATED_CODE)
            .codeId(UPDATED_CODE_ID)
            .labelEn(UPDATED_LABEL_EN)
            .labelFi(UPDATED_LABEL_FI)
            .labelSv(UPDATED_LABEL_SV);

        restEducationLevelCodeSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducationLevelCodeSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducationLevelCodeSet))
            )
            .andExpect(status().isOk());

        // Validate the EducationLevelCodeSet in the database
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeUpdate);
        EducationLevelCodeSet testEducationLevelCodeSet = educationLevelCodeSetList.get(educationLevelCodeSetList.size() - 1);
        assertThat(testEducationLevelCodeSet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEducationLevelCodeSet.getCodeId()).isEqualTo(UPDATED_CODE_ID);
        assertThat(testEducationLevelCodeSet.getLabelEn()).isEqualTo(UPDATED_LABEL_EN);
        assertThat(testEducationLevelCodeSet.getLabelFi()).isEqualTo(UPDATED_LABEL_FI);
        assertThat(testEducationLevelCodeSet.getLabelSv()).isEqualTo(UPDATED_LABEL_SV);
    }

    @Test
    @Transactional
    void patchNonExistingEducationLevelCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = educationLevelCodeSetRepository.findAll().size();
        educationLevelCodeSet.setId(count.incrementAndGet());

        // Create the EducationLevelCodeSet
        EducationLevelCodeSetDTO educationLevelCodeSetDTO = educationLevelCodeSetMapper.toDto(educationLevelCodeSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationLevelCodeSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, educationLevelCodeSetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationLevelCodeSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducationLevelCodeSet in the database
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEducationLevelCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = educationLevelCodeSetRepository.findAll().size();
        educationLevelCodeSet.setId(count.incrementAndGet());

        // Create the EducationLevelCodeSet
        EducationLevelCodeSetDTO educationLevelCodeSetDTO = educationLevelCodeSetMapper.toDto(educationLevelCodeSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationLevelCodeSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationLevelCodeSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducationLevelCodeSet in the database
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEducationLevelCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = educationLevelCodeSetRepository.findAll().size();
        educationLevelCodeSet.setId(count.incrementAndGet());

        // Create the EducationLevelCodeSet
        EducationLevelCodeSetDTO educationLevelCodeSetDTO = educationLevelCodeSetMapper.toDto(educationLevelCodeSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationLevelCodeSetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationLevelCodeSetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EducationLevelCodeSet in the database
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEducationLevelCodeSet() throws Exception {
        // Initialize the database
        educationLevelCodeSetRepository.saveAndFlush(educationLevelCodeSet);

        int databaseSizeBeforeDelete = educationLevelCodeSetRepository.findAll().size();

        // Delete the educationLevelCodeSet
        restEducationLevelCodeSetMockMvc
            .perform(delete(ENTITY_API_URL_ID, educationLevelCodeSet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EducationLevelCodeSet> educationLevelCodeSetList = educationLevelCodeSetRepository.findAll();
        assertThat(educationLevelCodeSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
