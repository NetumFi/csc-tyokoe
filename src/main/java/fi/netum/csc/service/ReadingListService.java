package fi.netum.csc.service;

import fi.netum.csc.domain.User;
import fi.netum.csc.service.dto.ReadingListDTO;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fi.netum.csc.domain.ReadingList}.
 */
public interface ReadingListService {
    /**
     * Save a readingList.
     *
     * @param readingListDTO the entity to save.
     * @return the persisted entity.
     */
    ReadingListDTO save(ReadingListDTO readingListDTO);

    /**
     * Updates a readingList.
     *
     * @param readingListDTO the entity to update.
     * @return the persisted entity.
     */
    ReadingListDTO update(ReadingListDTO readingListDTO);

    /**
     * Partially updates a readingList.
     *
     * @param readingListDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReadingListDTO> partialUpdate(ReadingListDTO readingListDTO);

    /**
     * Get all the readingLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReadingListDTO> findAll(Pageable pageable);

    /**
     * Get all the readingLists with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReadingListDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" readingList.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReadingListDTO> findOne(Long id);

    /**
     * Delete the "id" readingList.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<ReadingListDTO> findAllByUser(User user, Pageable pageable);
}
