package fi.netum.csc.service.impl;

import fi.netum.csc.domain.EducationLevelCodeSet;
import fi.netum.csc.repository.EducationLevelCodeSetRepository;
import fi.netum.csc.service.EducationLevelCodeSetService;
import fi.netum.csc.service.dto.EducationLevelCodeSetDTO;
import fi.netum.csc.service.mapper.EducationLevelCodeSetMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EducationLevelCodeSet}.
 */
@Service
@Transactional
public class EducationLevelCodeSetServiceImpl implements EducationLevelCodeSetService {

    private final Logger log = LoggerFactory.getLogger(EducationLevelCodeSetServiceImpl.class);

    private final EducationLevelCodeSetRepository educationLevelCodeSetRepository;

    private final EducationLevelCodeSetMapper educationLevelCodeSetMapper;

    public EducationLevelCodeSetServiceImpl(
        EducationLevelCodeSetRepository educationLevelCodeSetRepository,
        EducationLevelCodeSetMapper educationLevelCodeSetMapper
    ) {
        this.educationLevelCodeSetRepository = educationLevelCodeSetRepository;
        this.educationLevelCodeSetMapper = educationLevelCodeSetMapper;
    }

    @Override
    public EducationLevelCodeSetDTO save(EducationLevelCodeSetDTO educationLevelCodeSetDTO) {
        log.debug("Request to save EducationLevelCodeSet : {}", educationLevelCodeSetDTO);
        EducationLevelCodeSet educationLevelCodeSet = educationLevelCodeSetMapper.toEntity(educationLevelCodeSetDTO);
        educationLevelCodeSet = educationLevelCodeSetRepository.save(educationLevelCodeSet);
        return educationLevelCodeSetMapper.toDto(educationLevelCodeSet);
    }

    @Override
    public EducationLevelCodeSetDTO update(EducationLevelCodeSetDTO educationLevelCodeSetDTO) {
        log.debug("Request to save EducationLevelCodeSet : {}", educationLevelCodeSetDTO);
        EducationLevelCodeSet educationLevelCodeSet = educationLevelCodeSetMapper.toEntity(educationLevelCodeSetDTO);
        educationLevelCodeSet = educationLevelCodeSetRepository.save(educationLevelCodeSet);
        return educationLevelCodeSetMapper.toDto(educationLevelCodeSet);
    }

    @Override
    public Optional<EducationLevelCodeSetDTO> partialUpdate(EducationLevelCodeSetDTO educationLevelCodeSetDTO) {
        log.debug("Request to partially update EducationLevelCodeSet : {}", educationLevelCodeSetDTO);

        return educationLevelCodeSetRepository
            .findById(educationLevelCodeSetDTO.getId())
            .map(existingEducationLevelCodeSet -> {
                educationLevelCodeSetMapper.partialUpdate(existingEducationLevelCodeSet, educationLevelCodeSetDTO);

                return existingEducationLevelCodeSet;
            })
            .map(educationLevelCodeSetRepository::save)
            .map(educationLevelCodeSetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EducationLevelCodeSetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EducationLevelCodeSets");
        return educationLevelCodeSetRepository.findAll(pageable).map(educationLevelCodeSetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EducationLevelCodeSetDTO> findOne(Long id) {
        log.debug("Request to get EducationLevelCodeSet : {}", id);
        return educationLevelCodeSetRepository.findById(id).map(educationLevelCodeSetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EducationLevelCodeSet : {}", id);
        educationLevelCodeSetRepository.deleteById(id);
    }
}
