package fi.netum.csc.service;

import fi.netum.csc.domain.User;
import fi.netum.csc.service.dto.SearchSettingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fi.netum.csc.domain.SearchSetting}.
 */
public interface SearchSettingService {
    /**
     * Save a searchSetting.
     *
     * @param searchSettingDTO the entity to save.
     * @return the persisted entity.
     */
    SearchSettingDTO save(SearchSettingDTO searchSettingDTO);

    /**
     * Updates a searchSetting.
     *
     * @param searchSettingDTO the entity to update.
     * @return the persisted entity.
     */
    SearchSettingDTO update(SearchSettingDTO searchSettingDTO);

    /**
     * Partially updates a searchSetting.
     *
     * @param searchSettingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SearchSettingDTO> partialUpdate(SearchSettingDTO searchSettingDTO);

    /**
     * Get all the searchSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SearchSettingDTO> findAll(Pageable pageable);

    /**
     * Get all the searchSettings with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SearchSettingDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" searchSetting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SearchSettingDTO> findOne(Long id);

    /**
     * Delete the "id" searchSetting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    Optional<SearchSettingDTO> findByUser(User user);

    Page<SearchSettingDTO> findAllByUserWithEagerRelationships(User user, Pageable pageable);

    Page<SearchSettingDTO> findAllByUser(User user, Pageable pageable);
}
