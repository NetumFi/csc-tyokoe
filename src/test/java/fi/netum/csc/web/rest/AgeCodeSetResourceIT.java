package fi.netum.csc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fi.netum.csc.IntegrationTest;
import fi.netum.csc.domain.AgeCodeSet;
import fi.netum.csc.repository.AgeCodeSetRepository;
import fi.netum.csc.service.dto.AgeCodeSetDTO;
import fi.netum.csc.service.mapper.AgeCodeSetMapper;
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
 * Integration tests for the {@link AgeCodeSetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgeCodeSetResourceIT {

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

    private static final String ENTITY_API_URL = "/api/age-code-sets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AgeCodeSetRepository ageCodeSetRepository;

    @Autowired
    private AgeCodeSetMapper ageCodeSetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgeCodeSetMockMvc;

    private AgeCodeSet ageCodeSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgeCodeSet createEntity(EntityManager em) {
        AgeCodeSet ageCodeSet = new AgeCodeSet()
            .code(DEFAULT_CODE)
            .codeId(DEFAULT_CODE_ID)
            .labelEn(DEFAULT_LABEL_EN)
            .labelFi(DEFAULT_LABEL_FI)
            .labelSv(DEFAULT_LABEL_SV);
        return ageCodeSet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgeCodeSet createUpdatedEntity(EntityManager em) {
        AgeCodeSet ageCodeSet = new AgeCodeSet()
            .code(UPDATED_CODE)
            .codeId(UPDATED_CODE_ID)
            .labelEn(UPDATED_LABEL_EN)
            .labelFi(UPDATED_LABEL_FI)
            .labelSv(UPDATED_LABEL_SV);
        return ageCodeSet;
    }

    @BeforeEach
    public void initTest() {
        ageCodeSet = createEntity(em);
    }

    @Test
    @Transactional
    void createAgeCodeSet() throws Exception {
        int databaseSizeBeforeCreate = ageCodeSetRepository.findAll().size();
        // Create the AgeCodeSet
        AgeCodeSetDTO ageCodeSetDTO = ageCodeSetMapper.toDto(ageCodeSet);
        restAgeCodeSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ageCodeSetDTO)))
            .andExpect(status().isCreated());

        // Validate the AgeCodeSet in the database
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeCreate + 1);
        AgeCodeSet testAgeCodeSet = ageCodeSetList.get(ageCodeSetList.size() - 1);
        assertThat(testAgeCodeSet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAgeCodeSet.getCodeId()).isEqualTo(DEFAULT_CODE_ID);
        assertThat(testAgeCodeSet.getLabelEn()).isEqualTo(DEFAULT_LABEL_EN);
        assertThat(testAgeCodeSet.getLabelFi()).isEqualTo(DEFAULT_LABEL_FI);
        assertThat(testAgeCodeSet.getLabelSv()).isEqualTo(DEFAULT_LABEL_SV);
    }

    @Test
    @Transactional
    void createAgeCodeSetWithExistingId() throws Exception {
        // Create the AgeCodeSet with an existing ID
        ageCodeSet.setId(1L);
        AgeCodeSetDTO ageCodeSetDTO = ageCodeSetMapper.toDto(ageCodeSet);

        int databaseSizeBeforeCreate = ageCodeSetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgeCodeSetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ageCodeSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgeCodeSet in the database
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgeCodeSets() throws Exception {
        // Initialize the database
        ageCodeSetRepository.saveAndFlush(ageCodeSet);

        // Get all the ageCodeSetList
        restAgeCodeSetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ageCodeSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].codeId").value(hasItem(DEFAULT_CODE_ID)))
            .andExpect(jsonPath("$.[*].labelEn").value(hasItem(DEFAULT_LABEL_EN)))
            .andExpect(jsonPath("$.[*].labelFi").value(hasItem(DEFAULT_LABEL_FI)))
            .andExpect(jsonPath("$.[*].labelSv").value(hasItem(DEFAULT_LABEL_SV)));
    }

    @Test
    @Transactional
    void getAgeCodeSet() throws Exception {
        // Initialize the database
        ageCodeSetRepository.saveAndFlush(ageCodeSet);

        // Get the ageCodeSet
        restAgeCodeSetMockMvc
            .perform(get(ENTITY_API_URL_ID, ageCodeSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ageCodeSet.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.codeId").value(DEFAULT_CODE_ID))
            .andExpect(jsonPath("$.labelEn").value(DEFAULT_LABEL_EN))
            .andExpect(jsonPath("$.labelFi").value(DEFAULT_LABEL_FI))
            .andExpect(jsonPath("$.labelSv").value(DEFAULT_LABEL_SV));
    }

    @Test
    @Transactional
    void getNonExistingAgeCodeSet() throws Exception {
        // Get the ageCodeSet
        restAgeCodeSetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAgeCodeSet() throws Exception {
        // Initialize the database
        ageCodeSetRepository.saveAndFlush(ageCodeSet);

        int databaseSizeBeforeUpdate = ageCodeSetRepository.findAll().size();

        // Update the ageCodeSet
        AgeCodeSet updatedAgeCodeSet = ageCodeSetRepository.findById(ageCodeSet.getId()).get();
        // Disconnect from session so that the updates on updatedAgeCodeSet are not directly saved in db
        em.detach(updatedAgeCodeSet);
        updatedAgeCodeSet
            .code(UPDATED_CODE)
            .codeId(UPDATED_CODE_ID)
            .labelEn(UPDATED_LABEL_EN)
            .labelFi(UPDATED_LABEL_FI)
            .labelSv(UPDATED_LABEL_SV);
        AgeCodeSetDTO ageCodeSetDTO = ageCodeSetMapper.toDto(updatedAgeCodeSet);

        restAgeCodeSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ageCodeSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageCodeSetDTO))
            )
            .andExpect(status().isOk());

        // Validate the AgeCodeSet in the database
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeUpdate);
        AgeCodeSet testAgeCodeSet = ageCodeSetList.get(ageCodeSetList.size() - 1);
        assertThat(testAgeCodeSet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAgeCodeSet.getCodeId()).isEqualTo(UPDATED_CODE_ID);
        assertThat(testAgeCodeSet.getLabelEn()).isEqualTo(UPDATED_LABEL_EN);
        assertThat(testAgeCodeSet.getLabelFi()).isEqualTo(UPDATED_LABEL_FI);
        assertThat(testAgeCodeSet.getLabelSv()).isEqualTo(UPDATED_LABEL_SV);
    }

    @Test
    @Transactional
    void putNonExistingAgeCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = ageCodeSetRepository.findAll().size();
        ageCodeSet.setId(count.incrementAndGet());

        // Create the AgeCodeSet
        AgeCodeSetDTO ageCodeSetDTO = ageCodeSetMapper.toDto(ageCodeSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgeCodeSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ageCodeSetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageCodeSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgeCodeSet in the database
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgeCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = ageCodeSetRepository.findAll().size();
        ageCodeSet.setId(count.incrementAndGet());

        // Create the AgeCodeSet
        AgeCodeSetDTO ageCodeSetDTO = ageCodeSetMapper.toDto(ageCodeSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeCodeSetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageCodeSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgeCodeSet in the database
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgeCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = ageCodeSetRepository.findAll().size();
        ageCodeSet.setId(count.incrementAndGet());

        // Create the AgeCodeSet
        AgeCodeSetDTO ageCodeSetDTO = ageCodeSetMapper.toDto(ageCodeSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeCodeSetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ageCodeSetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgeCodeSet in the database
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgeCodeSetWithPatch() throws Exception {
        // Initialize the database
        ageCodeSetRepository.saveAndFlush(ageCodeSet);

        int databaseSizeBeforeUpdate = ageCodeSetRepository.findAll().size();

        // Update the ageCodeSet using partial update
        AgeCodeSet partialUpdatedAgeCodeSet = new AgeCodeSet();
        partialUpdatedAgeCodeSet.setId(ageCodeSet.getId());

        partialUpdatedAgeCodeSet.code(UPDATED_CODE).labelEn(UPDATED_LABEL_EN).labelSv(UPDATED_LABEL_SV);

        restAgeCodeSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgeCodeSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgeCodeSet))
            )
            .andExpect(status().isOk());

        // Validate the AgeCodeSet in the database
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeUpdate);
        AgeCodeSet testAgeCodeSet = ageCodeSetList.get(ageCodeSetList.size() - 1);
        assertThat(testAgeCodeSet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAgeCodeSet.getCodeId()).isEqualTo(DEFAULT_CODE_ID);
        assertThat(testAgeCodeSet.getLabelEn()).isEqualTo(UPDATED_LABEL_EN);
        assertThat(testAgeCodeSet.getLabelFi()).isEqualTo(DEFAULT_LABEL_FI);
        assertThat(testAgeCodeSet.getLabelSv()).isEqualTo(UPDATED_LABEL_SV);
    }

    @Test
    @Transactional
    void fullUpdateAgeCodeSetWithPatch() throws Exception {
        // Initialize the database
        ageCodeSetRepository.saveAndFlush(ageCodeSet);

        int databaseSizeBeforeUpdate = ageCodeSetRepository.findAll().size();

        // Update the ageCodeSet using partial update
        AgeCodeSet partialUpdatedAgeCodeSet = new AgeCodeSet();
        partialUpdatedAgeCodeSet.setId(ageCodeSet.getId());

        partialUpdatedAgeCodeSet
            .code(UPDATED_CODE)
            .codeId(UPDATED_CODE_ID)
            .labelEn(UPDATED_LABEL_EN)
            .labelFi(UPDATED_LABEL_FI)
            .labelSv(UPDATED_LABEL_SV);

        restAgeCodeSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgeCodeSet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgeCodeSet))
            )
            .andExpect(status().isOk());

        // Validate the AgeCodeSet in the database
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeUpdate);
        AgeCodeSet testAgeCodeSet = ageCodeSetList.get(ageCodeSetList.size() - 1);
        assertThat(testAgeCodeSet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAgeCodeSet.getCodeId()).isEqualTo(UPDATED_CODE_ID);
        assertThat(testAgeCodeSet.getLabelEn()).isEqualTo(UPDATED_LABEL_EN);
        assertThat(testAgeCodeSet.getLabelFi()).isEqualTo(UPDATED_LABEL_FI);
        assertThat(testAgeCodeSet.getLabelSv()).isEqualTo(UPDATED_LABEL_SV);
    }

    @Test
    @Transactional
    void patchNonExistingAgeCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = ageCodeSetRepository.findAll().size();
        ageCodeSet.setId(count.incrementAndGet());

        // Create the AgeCodeSet
        AgeCodeSetDTO ageCodeSetDTO = ageCodeSetMapper.toDto(ageCodeSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgeCodeSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ageCodeSetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ageCodeSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgeCodeSet in the database
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgeCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = ageCodeSetRepository.findAll().size();
        ageCodeSet.setId(count.incrementAndGet());

        // Create the AgeCodeSet
        AgeCodeSetDTO ageCodeSetDTO = ageCodeSetMapper.toDto(ageCodeSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeCodeSetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ageCodeSetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgeCodeSet in the database
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgeCodeSet() throws Exception {
        int databaseSizeBeforeUpdate = ageCodeSetRepository.findAll().size();
        ageCodeSet.setId(count.incrementAndGet());

        // Create the AgeCodeSet
        AgeCodeSetDTO ageCodeSetDTO = ageCodeSetMapper.toDto(ageCodeSet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeCodeSetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ageCodeSetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgeCodeSet in the database
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgeCodeSet() throws Exception {
        // Initialize the database
        ageCodeSetRepository.saveAndFlush(ageCodeSet);

        int databaseSizeBeforeDelete = ageCodeSetRepository.findAll().size();

        // Delete the ageCodeSet
        restAgeCodeSetMockMvc
            .perform(delete(ENTITY_API_URL_ID, ageCodeSet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgeCodeSet> ageCodeSetList = ageCodeSetRepository.findAll();
        assertThat(ageCodeSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
