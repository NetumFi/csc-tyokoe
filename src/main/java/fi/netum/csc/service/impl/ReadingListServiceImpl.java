package fi.netum.csc.service.impl;

import fi.netum.csc.domain.ReadingList;
import fi.netum.csc.domain.User;
import fi.netum.csc.repository.ReadingListRepository;
import fi.netum.csc.service.ReadingListService;
import fi.netum.csc.service.dto.ReadingListDTO;
import fi.netum.csc.service.mapper.ReadingListMapper;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReadingList}.
 */
@Service
@Transactional
public class ReadingListServiceImpl implements ReadingListService {

    private final Logger log = LoggerFactory.getLogger(ReadingListServiceImpl.class);

    private final ReadingListRepository readingListRepository;

    private final ReadingListMapper readingListMapper;

    public ReadingListServiceImpl(ReadingListRepository readingListRepository, ReadingListMapper readingListMapper) {
        this.readingListRepository = readingListRepository;
        this.readingListMapper = readingListMapper;
    }

    @Override
    public ReadingListDTO save(ReadingListDTO readingListDTO) {
        log.debug("Request to save ReadingList : {}", readingListDTO);
        ReadingList readingList = readingListMapper.toEntity(readingListDTO);
        readingList = readingListRepository.save(readingList);
        return readingListMapper.toDto(readingList);
    }

    @Override
    public ReadingListDTO update(ReadingListDTO readingListDTO) {
        log.debug("Request to save ReadingList : {}", readingListDTO);
        ReadingList readingList = readingListMapper.toEntity(readingListDTO);
        readingList = readingListRepository.save(readingList);
        return readingListMapper.toDto(readingList);
    }

    @Override
    public Optional<ReadingListDTO> partialUpdate(ReadingListDTO readingListDTO) {
        log.debug("Request to partially update ReadingList : {}", readingListDTO);

        return readingListRepository
            .findById(readingListDTO.getId())
            .map(existingReadingList -> {
                readingListMapper.partialUpdate(existingReadingList, readingListDTO);

                return existingReadingList;
            })
            .map(readingListRepository::save)
            .map(readingListMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReadingListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReadingLists");
        return readingListRepository.findAll(pageable).map(readingListMapper::toDto);
    }

    public Page<ReadingListDTO> findAllWithEagerRelationships(Pageable pageable) {
        return readingListRepository.findAllWithEagerRelationships(pageable).map(readingListMapper::toDto);
    }

    @Override
    public Page<ReadingListDTO> findAllByUserWithEagerRelationships(User user, Pageable pageable) {
        return readingListRepository.findAllByUserWithEagerRelationships(user, pageable).map(readingListMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReadingListDTO> findOne(Long id) {
        log.debug("Request to get ReadingList : {}", id);
        return readingListRepository.findOneWithEagerRelationships(id).map(readingListMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReadingList : {}", id);
        readingListRepository.deleteById(id);
    }

    public Page<ReadingListDTO> findAllByUser(User user, Pageable pageable) {
        return readingListRepository.findAllByUser(user, pageable).map(readingListMapper::toDto);
    }
}
