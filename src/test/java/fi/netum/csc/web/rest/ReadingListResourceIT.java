package fi.netum.csc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fi.netum.csc.IntegrationTest;
import fi.netum.csc.domain.ReadingList;
import fi.netum.csc.repository.ReadingListRepository;
import fi.netum.csc.service.ReadingListService;
import fi.netum.csc.service.dto.ReadingListDTO;
import fi.netum.csc.service.mapper.ReadingListMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ReadingListResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ReadingListResourceIT {

    private static final String DEFAULT_MATERIAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/reading-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReadingListRepository readingListRepository;

    @Mock
    private ReadingListRepository readingListRepositoryMock;

    @Autowired
    private ReadingListMapper readingListMapper;

    @Mock
    private ReadingListService readingListServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReadingListMockMvc;

    private ReadingList readingList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReadingList createEntity(EntityManager em) {
        ReadingList readingList = new ReadingList().materialId(DEFAULT_MATERIAL_ID).created(DEFAULT_CREATED);
        return readingList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReadingList createUpdatedEntity(EntityManager em) {
        ReadingList readingList = new ReadingList().materialId(UPDATED_MATERIAL_ID).created(UPDATED_CREATED);
        return readingList;
    }

    @BeforeEach
    public void initTest() {
        readingList = createEntity(em);
    }

    @Test
    @Transactional
    void createReadingList() throws Exception {
        int databaseSizeBeforeCreate = readingListRepository.findAll().size();
        // Create the ReadingList
        ReadingListDTO readingListDTO = readingListMapper.toDto(readingList);
        restReadingListMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(readingListDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReadingList in the database
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeCreate + 1);
        ReadingList testReadingList = readingListList.get(readingListList.size() - 1);
        assertThat(testReadingList.getMaterialId()).isEqualTo(DEFAULT_MATERIAL_ID);
        assertThat(testReadingList.getCreated()).isEqualTo(DEFAULT_CREATED);
    }

    @Test
    @Transactional
    void createReadingListWithExistingId() throws Exception {
        // Create the ReadingList with an existing ID
        readingList.setId(1L);
        ReadingListDTO readingListDTO = readingListMapper.toDto(readingList);

        int databaseSizeBeforeCreate = readingListRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReadingListMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(readingListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReadingList in the database
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReadingLists() throws Exception {
        // Initialize the database
        readingListRepository.saveAndFlush(readingList);

        // Get all the readingListList
        restReadingListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(readingList.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReadingListsWithEagerRelationshipsIsEnabled() throws Exception {
        when(readingListServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReadingListMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(readingListServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReadingListsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(readingListServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReadingListMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(readingListRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getReadingList() throws Exception {
        // Initialize the database
        readingListRepository.saveAndFlush(readingList);

        // Get the readingList
        restReadingListMockMvc
            .perform(get(ENTITY_API_URL_ID, readingList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(readingList.getId().intValue()))
            .andExpect(jsonPath("$.materialId").value(DEFAULT_MATERIAL_ID))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReadingList() throws Exception {
        // Get the readingList
        restReadingListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReadingList() throws Exception {
        // Initialize the database
        readingListRepository.saveAndFlush(readingList);

        int databaseSizeBeforeUpdate = readingListRepository.findAll().size();

        // Update the readingList
        ReadingList updatedReadingList = readingListRepository.findById(readingList.getId()).get();
        // Disconnect from session so that the updates on updatedReadingList are not directly saved in db
        em.detach(updatedReadingList);
        updatedReadingList.materialId(UPDATED_MATERIAL_ID).created(UPDATED_CREATED);
        ReadingListDTO readingListDTO = readingListMapper.toDto(updatedReadingList);

        restReadingListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, readingListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(readingListDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReadingList in the database
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeUpdate);
        ReadingList testReadingList = readingListList.get(readingListList.size() - 1);
        assertThat(testReadingList.getMaterialId()).isEqualTo(UPDATED_MATERIAL_ID);
        assertThat(testReadingList.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    void putNonExistingReadingList() throws Exception {
        int databaseSizeBeforeUpdate = readingListRepository.findAll().size();
        readingList.setId(count.incrementAndGet());

        // Create the ReadingList
        ReadingListDTO readingListDTO = readingListMapper.toDto(readingList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReadingListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, readingListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(readingListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReadingList in the database
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReadingList() throws Exception {
        int databaseSizeBeforeUpdate = readingListRepository.findAll().size();
        readingList.setId(count.incrementAndGet());

        // Create the ReadingList
        ReadingListDTO readingListDTO = readingListMapper.toDto(readingList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReadingListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(readingListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReadingList in the database
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReadingList() throws Exception {
        int databaseSizeBeforeUpdate = readingListRepository.findAll().size();
        readingList.setId(count.incrementAndGet());

        // Create the ReadingList
        ReadingListDTO readingListDTO = readingListMapper.toDto(readingList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReadingListMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(readingListDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReadingList in the database
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReadingListWithPatch() throws Exception {
        // Initialize the database
        readingListRepository.saveAndFlush(readingList);

        int databaseSizeBeforeUpdate = readingListRepository.findAll().size();

        // Update the readingList using partial update
        ReadingList partialUpdatedReadingList = new ReadingList();
        partialUpdatedReadingList.setId(readingList.getId());

        partialUpdatedReadingList.created(UPDATED_CREATED);

        restReadingListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReadingList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReadingList))
            )
            .andExpect(status().isOk());

        // Validate the ReadingList in the database
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeUpdate);
        ReadingList testReadingList = readingListList.get(readingListList.size() - 1);
        assertThat(testReadingList.getMaterialId()).isEqualTo(DEFAULT_MATERIAL_ID);
        assertThat(testReadingList.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    void fullUpdateReadingListWithPatch() throws Exception {
        // Initialize the database
        readingListRepository.saveAndFlush(readingList);

        int databaseSizeBeforeUpdate = readingListRepository.findAll().size();

        // Update the readingList using partial update
        ReadingList partialUpdatedReadingList = new ReadingList();
        partialUpdatedReadingList.setId(readingList.getId());

        partialUpdatedReadingList.materialId(UPDATED_MATERIAL_ID).created(UPDATED_CREATED);

        restReadingListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReadingList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReadingList))
            )
            .andExpect(status().isOk());

        // Validate the ReadingList in the database
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeUpdate);
        ReadingList testReadingList = readingListList.get(readingListList.size() - 1);
        assertThat(testReadingList.getMaterialId()).isEqualTo(UPDATED_MATERIAL_ID);
        assertThat(testReadingList.getCreated()).isEqualTo(UPDATED_CREATED);
    }

    @Test
    @Transactional
    void patchNonExistingReadingList() throws Exception {
        int databaseSizeBeforeUpdate = readingListRepository.findAll().size();
        readingList.setId(count.incrementAndGet());

        // Create the ReadingList
        ReadingListDTO readingListDTO = readingListMapper.toDto(readingList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReadingListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, readingListDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(readingListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReadingList in the database
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReadingList() throws Exception {
        int databaseSizeBeforeUpdate = readingListRepository.findAll().size();
        readingList.setId(count.incrementAndGet());

        // Create the ReadingList
        ReadingListDTO readingListDTO = readingListMapper.toDto(readingList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReadingListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(readingListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReadingList in the database
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReadingList() throws Exception {
        int databaseSizeBeforeUpdate = readingListRepository.findAll().size();
        readingList.setId(count.incrementAndGet());

        // Create the ReadingList
        ReadingListDTO readingListDTO = readingListMapper.toDto(readingList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReadingListMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(readingListDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReadingList in the database
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReadingList() throws Exception {
        // Initialize the database
        readingListRepository.saveAndFlush(readingList);

        int databaseSizeBeforeDelete = readingListRepository.findAll().size();

        // Delete the readingList
        restReadingListMockMvc
            .perform(delete(ENTITY_API_URL_ID, readingList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReadingList> readingListList = readingListRepository.findAll();
        assertThat(readingListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
