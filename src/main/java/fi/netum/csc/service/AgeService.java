package fi.netum.csc.service;

import fi.netum.csc.service.dto.AgeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fi.netum.csc.domain.Age}.
 */
public interface AgeService {
    /**
     * Save a age.
     *
     * @param ageDTO the entity to save.
     * @return the persisted entity.
     */
    AgeDTO save(AgeDTO ageDTO);

    /**
     * Updates a age.
     *
     * @param ageDTO the entity to update.
     * @return the persisted entity.
     */
    AgeDTO update(AgeDTO ageDTO);

    /**
     * Partially updates a age.
     *
     * @param ageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AgeDTO> partialUpdate(AgeDTO ageDTO);

    /**
     * Get all the ages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AgeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" age.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AgeDTO> findOne(Long id);

    /**
     * Delete the "id" age.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
