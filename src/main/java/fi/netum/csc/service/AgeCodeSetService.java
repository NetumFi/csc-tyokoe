package fi.netum.csc.service;

import fi.netum.csc.service.dto.AgeCodeSetDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fi.netum.csc.domain.AgeCodeSet}.
 */
public interface AgeCodeSetService {
    /**
     * Save a ageCodeSet.
     *
     * @param ageCodeSetDTO the entity to save.
     * @return the persisted entity.
     */
    AgeCodeSetDTO save(AgeCodeSetDTO ageCodeSetDTO);

    /**
     * Updates a ageCodeSet.
     *
     * @param ageCodeSetDTO the entity to update.
     * @return the persisted entity.
     */
    AgeCodeSetDTO update(AgeCodeSetDTO ageCodeSetDTO);

    /**
     * Partially updates a ageCodeSet.
     *
     * @param ageCodeSetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AgeCodeSetDTO> partialUpdate(AgeCodeSetDTO ageCodeSetDTO);

    /**
     * Get all the ageCodeSets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AgeCodeSetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ageCodeSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AgeCodeSetDTO> findOne(Long id);

    /**
     * Delete the "id" ageCodeSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
