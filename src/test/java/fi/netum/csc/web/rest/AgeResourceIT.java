package fi.netum.csc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fi.netum.csc.IntegrationTest;
import fi.netum.csc.domain.Age;
import fi.netum.csc.repository.AgeRepository;
import fi.netum.csc.service.dto.AgeDTO;
import fi.netum.csc.service.mapper.AgeMapper;
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
 * Integration tests for the {@link AgeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgeResourceIT {

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

    private static final String ENTITY_API_URL = "/api/ages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AgeRepository ageRepository;

    @Autowired
    private AgeMapper ageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgeMockMvc;

    private Age age;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Age createEntity(EntityManager em) {
        Age age = new Age()
            .code(DEFAULT_CODE)
            .codeId(DEFAULT_CODE_ID)
            .labelEn(DEFAULT_LABEL_EN)
            .labelFi(DEFAULT_LABEL_FI)
            .labelSv(DEFAULT_LABEL_SV);
        return age;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Age createUpdatedEntity(EntityManager em) {
        Age age = new Age()
            .code(UPDATED_CODE)
            .codeId(UPDATED_CODE_ID)
            .labelEn(UPDATED_LABEL_EN)
            .labelFi(UPDATED_LABEL_FI)
            .labelSv(UPDATED_LABEL_SV);
        return age;
    }

    @BeforeEach
    public void initTest() {
        age = createEntity(em);
    }

    @Test
    @Transactional
    void createAge() throws Exception {
        int databaseSizeBeforeCreate = ageRepository.findAll().size();
        // Create the Age
        AgeDTO ageDTO = ageMapper.toDto(age);
        restAgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ageDTO)))
            .andExpect(status().isCreated());

        // Validate the Age in the database
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeCreate + 1);
        Age testAge = ageList.get(ageList.size() - 1);
        assertThat(testAge.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAge.getCodeId()).isEqualTo(DEFAULT_CODE_ID);
        assertThat(testAge.getLabelEn()).isEqualTo(DEFAULT_LABEL_EN);
        assertThat(testAge.getLabelFi()).isEqualTo(DEFAULT_LABEL_FI);
        assertThat(testAge.getLabelSv()).isEqualTo(DEFAULT_LABEL_SV);
    }

    @Test
    @Transactional
    void createAgeWithExistingId() throws Exception {
        // Create the Age with an existing ID
        age.setId(1L);
        AgeDTO ageDTO = ageMapper.toDto(age);

        int databaseSizeBeforeCreate = ageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Age in the database
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAges() throws Exception {
        // Initialize the database
        ageRepository.saveAndFlush(age);

        // Get all the ageList
        restAgeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(age.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].codeId").value(hasItem(DEFAULT_CODE_ID)))
            .andExpect(jsonPath("$.[*].labelEn").value(hasItem(DEFAULT_LABEL_EN)))
            .andExpect(jsonPath("$.[*].labelFi").value(hasItem(DEFAULT_LABEL_FI)))
            .andExpect(jsonPath("$.[*].labelSv").value(hasItem(DEFAULT_LABEL_SV)));
    }

    @Test
    @Transactional
    void getAge() throws Exception {
        // Initialize the database
        ageRepository.saveAndFlush(age);

        // Get the age
        restAgeMockMvc
            .perform(get(ENTITY_API_URL_ID, age.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(age.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.codeId").value(DEFAULT_CODE_ID))
            .andExpect(jsonPath("$.labelEn").value(DEFAULT_LABEL_EN))
            .andExpect(jsonPath("$.labelFi").value(DEFAULT_LABEL_FI))
            .andExpect(jsonPath("$.labelSv").value(DEFAULT_LABEL_SV));
    }

    @Test
    @Transactional
    void getNonExistingAge() throws Exception {
        // Get the age
        restAgeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAge() throws Exception {
        // Initialize the database
        ageRepository.saveAndFlush(age);

        int databaseSizeBeforeUpdate = ageRepository.findAll().size();

        // Update the age
        Age updatedAge = ageRepository.findById(age.getId()).get();
        // Disconnect from session so that the updates on updatedAge are not directly saved in db
        em.detach(updatedAge);
        updatedAge.code(UPDATED_CODE).codeId(UPDATED_CODE_ID).labelEn(UPDATED_LABEL_EN).labelFi(UPDATED_LABEL_FI).labelSv(UPDATED_LABEL_SV);
        AgeDTO ageDTO = ageMapper.toDto(updatedAge);

        restAgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Age in the database
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeUpdate);
        Age testAge = ageList.get(ageList.size() - 1);
        assertThat(testAge.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAge.getCodeId()).isEqualTo(UPDATED_CODE_ID);
        assertThat(testAge.getLabelEn()).isEqualTo(UPDATED_LABEL_EN);
        assertThat(testAge.getLabelFi()).isEqualTo(UPDATED_LABEL_FI);
        assertThat(testAge.getLabelSv()).isEqualTo(UPDATED_LABEL_SV);
    }

    @Test
    @Transactional
    void putNonExistingAge() throws Exception {
        int databaseSizeBeforeUpdate = ageRepository.findAll().size();
        age.setId(count.incrementAndGet());

        // Create the Age
        AgeDTO ageDTO = ageMapper.toDto(age);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Age in the database
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAge() throws Exception {
        int databaseSizeBeforeUpdate = ageRepository.findAll().size();
        age.setId(count.incrementAndGet());

        // Create the Age
        AgeDTO ageDTO = ageMapper.toDto(age);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Age in the database
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAge() throws Exception {
        int databaseSizeBeforeUpdate = ageRepository.findAll().size();
        age.setId(count.incrementAndGet());

        // Create the Age
        AgeDTO ageDTO = ageMapper.toDto(age);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Age in the database
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgeWithPatch() throws Exception {
        // Initialize the database
        ageRepository.saveAndFlush(age);

        int databaseSizeBeforeUpdate = ageRepository.findAll().size();

        // Update the age using partial update
        Age partialUpdatedAge = new Age();
        partialUpdatedAge.setId(age.getId());

        partialUpdatedAge.code(UPDATED_CODE).codeId(UPDATED_CODE_ID);

        restAgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAge))
            )
            .andExpect(status().isOk());

        // Validate the Age in the database
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeUpdate);
        Age testAge = ageList.get(ageList.size() - 1);
        assertThat(testAge.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAge.getCodeId()).isEqualTo(UPDATED_CODE_ID);
        assertThat(testAge.getLabelEn()).isEqualTo(DEFAULT_LABEL_EN);
        assertThat(testAge.getLabelFi()).isEqualTo(DEFAULT_LABEL_FI);
        assertThat(testAge.getLabelSv()).isEqualTo(DEFAULT_LABEL_SV);
    }

    @Test
    @Transactional
    void fullUpdateAgeWithPatch() throws Exception {
        // Initialize the database
        ageRepository.saveAndFlush(age);

        int databaseSizeBeforeUpdate = ageRepository.findAll().size();

        // Update the age using partial update
        Age partialUpdatedAge = new Age();
        partialUpdatedAge.setId(age.getId());

        partialUpdatedAge
            .code(UPDATED_CODE)
            .codeId(UPDATED_CODE_ID)
            .labelEn(UPDATED_LABEL_EN)
            .labelFi(UPDATED_LABEL_FI)
            .labelSv(UPDATED_LABEL_SV);

        restAgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAge))
            )
            .andExpect(status().isOk());

        // Validate the Age in the database
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeUpdate);
        Age testAge = ageList.get(ageList.size() - 1);
        assertThat(testAge.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAge.getCodeId()).isEqualTo(UPDATED_CODE_ID);
        assertThat(testAge.getLabelEn()).isEqualTo(UPDATED_LABEL_EN);
        assertThat(testAge.getLabelFi()).isEqualTo(UPDATED_LABEL_FI);
        assertThat(testAge.getLabelSv()).isEqualTo(UPDATED_LABEL_SV);
    }

    @Test
    @Transactional
    void patchNonExistingAge() throws Exception {
        int databaseSizeBeforeUpdate = ageRepository.findAll().size();
        age.setId(count.incrementAndGet());

        // Create the Age
        AgeDTO ageDTO = ageMapper.toDto(age);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Age in the database
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAge() throws Exception {
        int databaseSizeBeforeUpdate = ageRepository.findAll().size();
        age.setId(count.incrementAndGet());

        // Create the Age
        AgeDTO ageDTO = ageMapper.toDto(age);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Age in the database
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAge() throws Exception {
        int databaseSizeBeforeUpdate = ageRepository.findAll().size();
        age.setId(count.incrementAndGet());

        // Create the Age
        AgeDTO ageDTO = ageMapper.toDto(age);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Age in the database
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAge() throws Exception {
        // Initialize the database
        ageRepository.saveAndFlush(age);

        int databaseSizeBeforeDelete = ageRepository.findAll().size();

        // Delete the age
        restAgeMockMvc.perform(delete(ENTITY_API_URL_ID, age.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Age> ageList = ageRepository.findAll();
        assertThat(ageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
