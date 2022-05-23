package fi.netum.csc.service.impl;

import fi.netum.csc.domain.SearchSetting;
import fi.netum.csc.domain.User;
import fi.netum.csc.repository.SearchSettingRepository;
import fi.netum.csc.service.SearchSettingService;
import fi.netum.csc.service.dto.SearchSettingDTO;
import fi.netum.csc.service.mapper.SearchSettingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SearchSetting}.
 */
@Service
@Transactional
public class SearchSettingServiceImpl implements SearchSettingService {

    private final Logger log = LoggerFactory.getLogger(SearchSettingServiceImpl.class);

    private final SearchSettingRepository searchSettingRepository;

    private final SearchSettingMapper searchSettingMapper;

    public SearchSettingServiceImpl(SearchSettingRepository searchSettingRepository, SearchSettingMapper searchSettingMapper) {
        this.searchSettingRepository = searchSettingRepository;
        this.searchSettingMapper = searchSettingMapper;
    }

    @Override
    public SearchSettingDTO save(SearchSettingDTO searchSettingDTO) {
        log.debug("Request to save SearchSetting : {}", searchSettingDTO);
        SearchSetting searchSetting = searchSettingMapper.toEntity(searchSettingDTO);
        searchSetting = searchSettingRepository.save(searchSetting);
        return searchSettingMapper.toDto(searchSetting);
    }

    @Override
    public SearchSettingDTO update(SearchSettingDTO searchSettingDTO) {
        log.debug("Request to save SearchSetting : {}", searchSettingDTO);
        SearchSetting searchSetting = searchSettingMapper.toEntity(searchSettingDTO);
        searchSetting = searchSettingRepository.save(searchSetting);
        return searchSettingMapper.toDto(searchSetting);
    }

    @Override
    public Optional<SearchSettingDTO> partialUpdate(SearchSettingDTO searchSettingDTO) {
        log.debug("Request to partially update SearchSetting : {}", searchSettingDTO);

        return searchSettingRepository
            .findById(searchSettingDTO.getId())
            .map(existingSearchSetting -> {
                searchSettingMapper.partialUpdate(existingSearchSetting, searchSettingDTO);

                return existingSearchSetting;
            })
            .map(searchSettingRepository::save)
            .map(searchSettingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SearchSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SearchSettings");
        return searchSettingRepository.findAll(pageable).map(searchSettingMapper::toDto);
    }

    public Page<SearchSettingDTO> findAllWithEagerRelationships(Pageable pageable) {
        return searchSettingRepository.findAllWithEagerRelationships(pageable).map(searchSettingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SearchSettingDTO> findOne(Long id) {
        log.debug("Request to get SearchSetting : {}", id);
        return searchSettingRepository.findOneWithEagerRelationships(id).map(searchSettingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SearchSetting : {}", id);
        searchSettingRepository.deleteById(id);
    }

    @Override
    public Optional<SearchSettingDTO> findByUser(User user) {
        Optional<SearchSetting> userSettingPage = searchSettingRepository.findOneByUser(user);
        return userSettingPage.map(searchSettingMapper::toDto);
    }

    @Override
    public Page<SearchSettingDTO> findAllByUserWithEagerRelationships(User user, Pageable pageable) {
        return searchSettingRepository.findAllByUserWithEagerRelationships(user, pageable).map(searchSettingMapper::toDto);
    }

    @Override
    public Page<SearchSettingDTO> findAllByUser(User user, Pageable pageable) {
        return searchSettingRepository.findAllByUser(user, pageable).map(searchSettingMapper::toDto);
    }
}
