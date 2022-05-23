package fi.netum.csc.service.impl;

import fi.netum.csc.domain.AgeCodeSet;
import fi.netum.csc.repository.AgeCodeSetRepository;
import fi.netum.csc.service.AgeCodeSetService;
import fi.netum.csc.service.dto.AgeCodeSetDTO;
import fi.netum.csc.service.mapper.AgeCodeSetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AgeCodeSet}.
 */
@Service
@Transactional
public class AgeCodeSetServiceImpl implements AgeCodeSetService {

    private final Logger log = LoggerFactory.getLogger(AgeCodeSetServiceImpl.class);

    private final AgeCodeSetRepository ageCodeSetRepository;

    private final AgeCodeSetMapper ageCodeSetMapper;

    public AgeCodeSetServiceImpl(AgeCodeSetRepository ageCodeSetRepository, AgeCodeSetMapper ageCodeSetMapper) {
        this.ageCodeSetRepository = ageCodeSetRepository;
        this.ageCodeSetMapper = ageCodeSetMapper;
    }

    @Override
    public AgeCodeSetDTO save(AgeCodeSetDTO ageCodeSetDTO) {
        log.debug("Request to save AgeCodeSet : {}", ageCodeSetDTO);
        AgeCodeSet ageCodeSet = ageCodeSetMapper.toEntity(ageCodeSetDTO);
        ageCodeSet = ageCodeSetRepository.save(ageCodeSet);
        return ageCodeSetMapper.toDto(ageCodeSet);
    }

    @Override
    public AgeCodeSetDTO update(AgeCodeSetDTO ageCodeSetDTO) {
        log.debug("Request to save AgeCodeSet : {}", ageCodeSetDTO);
        AgeCodeSet ageCodeSet = ageCodeSetMapper.toEntity(ageCodeSetDTO);
        ageCodeSet = ageCodeSetRepository.save(ageCodeSet);
        return ageCodeSetMapper.toDto(ageCodeSet);
    }

    @Override
    public Optional<AgeCodeSetDTO> partialUpdate(AgeCodeSetDTO ageCodeSetDTO) {
        log.debug("Request to partially update AgeCodeSet : {}", ageCodeSetDTO);

        return ageCodeSetRepository
            .findById(ageCodeSetDTO.getId())
            .map(existingAgeCodeSet -> {
                ageCodeSetMapper.partialUpdate(existingAgeCodeSet, ageCodeSetDTO);

                return existingAgeCodeSet;
            })
            .map(ageCodeSetRepository::save)
            .map(ageCodeSetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgeCodeSetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AgeCodeSets");
        return ageCodeSetRepository.findAll(pageable).map(ageCodeSetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgeCodeSetDTO> findOne(Long id) {
        log.debug("Request to get AgeCodeSet : {}", id);
        return ageCodeSetRepository.findById(id).map(ageCodeSetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AgeCodeSet : {}", id);
        ageCodeSetRepository.deleteById(id);
    }
}
