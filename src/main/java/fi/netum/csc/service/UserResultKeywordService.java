package fi.netum.csc.service;

import fi.netum.csc.service.dto.UserResultKeywordDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fi.netum.csc.domain.UserResultKeyword}.
 */
public interface UserResultKeywordService {
    /**
     * Save a userResultKeyword.
     *
     * @param userResultKeywordDTO the entity to save.
     * @return the persisted entity.
     */
    UserResultKeywordDTO save(UserResultKeywordDTO userResultKeywordDTO);

    /**
     * Updates a userResultKeyword.
     *
     * @param userResultKeywordDTO the entity to update.
     * @return the persisted entity.
     */
    UserResultKeywordDTO update(UserResultKeywordDTO userResultKeywordDTO);

    /**
     * Partially updates a userResultKeyword.
     *
     * @param userResultKeywordDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserResultKeywordDTO> partialUpdate(UserResultKeywordDTO userResultKeywordDTO);

    /**
     * Get all the userResultKeywords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserResultKeywordDTO> findAll(Pageable pageable);

    /**
     * Get all the userResultKeywords with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserResultKeywordDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" userResultKeyword.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserResultKeywordDTO> findOne(Long id);

    /**
     * Delete the "id" userResultKeyword.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
