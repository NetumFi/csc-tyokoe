package fi.netum.csc.service.impl;

import fi.netum.csc.domain.SearchHistory;
import fi.netum.csc.domain.User;
import fi.netum.csc.repository.SearchHistoryRepository;
import fi.netum.csc.service.SearchHistoryService;
import fi.netum.csc.service.dto.SearchHistoryDTO;
import fi.netum.csc.service.mapper.SearchHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SearchHistory}.
 */
@Service
@Transactional
public class SearchHistoryServiceImpl implements SearchHistoryService {

    private final Logger log = LoggerFactory.getLogger(SearchHistoryServiceImpl.class);

    private final SearchHistoryRepository searchHistoryRepository;

    private final SearchHistoryMapper searchHistoryMapper;

    public SearchHistoryServiceImpl(SearchHistoryRepository searchHistoryRepository, SearchHistoryMapper searchHistoryMapper) {
        this.searchHistoryRepository = searchHistoryRepository;
        this.searchHistoryMapper = searchHistoryMapper;
    }

    @Override
    public SearchHistoryDTO save(SearchHistoryDTO searchHistoryDTO) {
        log.debug("Request to save SearchHistory : {}", searchHistoryDTO);
        SearchHistory searchHistory = searchHistoryMapper.toEntity(searchHistoryDTO);
        searchHistory = searchHistoryRepository.save(searchHistory);
        return searchHistoryMapper.toDto(searchHistory);
    }

    @Override
    public SearchHistoryDTO update(SearchHistoryDTO searchHistoryDTO) {
        log.debug("Request to save SearchHistory : {}", searchHistoryDTO);
        SearchHistory searchHistory = searchHistoryMapper.toEntity(searchHistoryDTO);
        searchHistory = searchHistoryRepository.save(searchHistory);
        return searchHistoryMapper.toDto(searchHistory);
    }

    @Override
    public Optional<SearchHistoryDTO> partialUpdate(SearchHistoryDTO searchHistoryDTO) {
        log.debug("Request to partially update SearchHistory : {}", searchHistoryDTO);

        return searchHistoryRepository
            .findById(searchHistoryDTO.getId())
            .map(existingSearchHistory -> {
                searchHistoryMapper.partialUpdate(existingSearchHistory, searchHistoryDTO);

                return existingSearchHistory;
            })
            .map(searchHistoryRepository::save)
            .map(searchHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SearchHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SearchHistories");
        return searchHistoryRepository.findAll(pageable).map(searchHistoryMapper::toDto);
    }

    public Page<SearchHistoryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return searchHistoryRepository.findAllWithEagerRelationships(pageable).map(searchHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SearchHistoryDTO> findOne(Long id) {
        log.debug("Request to get SearchHistory : {}", id);
        return searchHistoryRepository.findOneWithEagerRelationships(id).map(searchHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SearchHistory : {}", id);
        searchHistoryRepository.deleteById(id);
    }

    @Override
    public Page<SearchHistoryDTO> findAllByUser(User user, Pageable pageable) {
        Page<SearchHistory> searchHistoryPage = searchHistoryRepository.findAllByUser(user, pageable);

        return searchHistoryPage.map(searchHistoryMapper::toDto);
    }

    @Override
    public Page<SearchHistoryDTO> findAllByUserWithEagerRelationships(User user, Pageable pageable) {
        return searchHistoryRepository.findAllByUserWithEagerRelationships(user, pageable).map(searchHistoryMapper::toDto);
    }
}
