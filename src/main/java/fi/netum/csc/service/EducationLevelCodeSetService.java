package fi.netum.csc.service;

import fi.netum.csc.service.dto.EducationLevelCodeSetDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fi.netum.csc.domain.EducationLevelCodeSet}.
 */
public interface EducationLevelCodeSetService {
    /**
     * Save a educationLevelCodeSet.
     *
     * @param educationLevelCodeSetDTO the entity to save.
     * @return the persisted entity.
     */
    EducationLevelCodeSetDTO save(EducationLevelCodeSetDTO educationLevelCodeSetDTO);

    /**
     * Updates a educationLevelCodeSet.
     *
     * @param educationLevelCodeSetDTO the entity to update.
     * @return the persisted entity.
     */
    EducationLevelCodeSetDTO update(EducationLevelCodeSetDTO educationLevelCodeSetDTO);

    /**
     * Partially updates a educationLevelCodeSet.
     *
     * @param educationLevelCodeSetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EducationLevelCodeSetDTO> partialUpdate(EducationLevelCodeSetDTO educationLevelCodeSetDTO);

    /**
     * Get all the educationLevelCodeSets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EducationLevelCodeSetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" educationLevelCodeSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EducationLevelCodeSetDTO> findOne(Long id);

    /**
     * Delete the "id" educationLevelCodeSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
