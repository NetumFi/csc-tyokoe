package fi.netum.csc.service.impl;

import fi.netum.csc.domain.Age;
import fi.netum.csc.repository.AgeRepository;
import fi.netum.csc.service.AgeService;
import fi.netum.csc.service.dto.AgeDTO;
import fi.netum.csc.service.mapper.AgeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Age}.
 */
@Service
@Transactional
public class AgeServiceImpl implements AgeService {

    private final Logger log = LoggerFactory.getLogger(AgeServiceImpl.class);

    private final AgeRepository ageRepository;

    private final AgeMapper ageMapper;

    public AgeServiceImpl(AgeRepository ageRepository, AgeMapper ageMapper) {
        this.ageRepository = ageRepository;
        this.ageMapper = ageMapper;
    }

    @Override
    public AgeDTO save(AgeDTO ageDTO) {
        log.debug("Request to save Age : {}", ageDTO);
        Age age = ageMapper.toEntity(ageDTO);
        age = ageRepository.save(age);
        return ageMapper.toDto(age);
    }

    @Override
    public AgeDTO update(AgeDTO ageDTO) {
        log.debug("Request to save Age : {}", ageDTO);
        Age age = ageMapper.toEntity(ageDTO);
        age = ageRepository.save(age);
        return ageMapper.toDto(age);
    }

    @Override
    public Optional<AgeDTO> partialUpdate(AgeDTO ageDTO) {
        log.debug("Request to partially update Age : {}", ageDTO);

        return ageRepository
            .findById(ageDTO.getId())
            .map(existingAge -> {
                ageMapper.partialUpdate(existingAge, ageDTO);

                return existingAge;
            })
            .map(ageRepository::save)
            .map(ageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ages");
        return ageRepository.findAll(pageable).map(ageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgeDTO> findOne(Long id) {
        log.debug("Request to get Age : {}", id);
        return ageRepository.findById(id).map(ageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Age : {}", id);
        ageRepository.deleteById(id);
    }
}
