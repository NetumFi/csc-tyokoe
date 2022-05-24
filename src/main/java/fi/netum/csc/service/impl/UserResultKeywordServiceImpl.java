package fi.netum.csc.service.impl;

import fi.netum.csc.domain.UserResultKeyword;
import fi.netum.csc.repository.UserResultKeywordRepository;
import fi.netum.csc.service.UserResultKeywordService;
import fi.netum.csc.service.dto.UserResultKeywordDTO;
import fi.netum.csc.service.mapper.UserResultKeywordMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserResultKeyword}.
 */
@Service
@Transactional
public class UserResultKeywordServiceImpl implements UserResultKeywordService {

    private final Logger log = LoggerFactory.getLogger(UserResultKeywordServiceImpl.class);

    private final UserResultKeywordRepository userResultKeywordRepository;

    private final UserResultKeywordMapper userResultKeywordMapper;

    public UserResultKeywordServiceImpl(
        UserResultKeywordRepository userResultKeywordRepository,
        UserResultKeywordMapper userResultKeywordMapper
    ) {
        this.userResultKeywordRepository = userResultKeywordRepository;
        this.userResultKeywordMapper = userResultKeywordMapper;
    }

    @Override
    public UserResultKeywordDTO save(UserResultKeywordDTO userResultKeywordDTO) {
        log.debug("Request to save UserResultKeyword : {}", userResultKeywordDTO);
        UserResultKeyword userResultKeyword = userResultKeywordMapper.toEntity(userResultKeywordDTO);
        userResultKeyword = userResultKeywordRepository.save(userResultKeyword);
        return userResultKeywordMapper.toDto(userResultKeyword);
    }

    @Override
    public UserResultKeywordDTO update(UserResultKeywordDTO userResultKeywordDTO) {
        log.debug("Request to save UserResultKeyword : {}", userResultKeywordDTO);
        UserResultKeyword userResultKeyword = userResultKeywordMapper.toEntity(userResultKeywordDTO);
        userResultKeyword = userResultKeywordRepository.save(userResultKeyword);
        return userResultKeywordMapper.toDto(userResultKeyword);
    }

    @Override
    public Optional<UserResultKeywordDTO> partialUpdate(UserResultKeywordDTO userResultKeywordDTO) {
        log.debug("Request to partially update UserResultKeyword : {}", userResultKeywordDTO);

        return userResultKeywordRepository
            .findById(userResultKeywordDTO.getId())
            .map(existingUserResultKeyword -> {
                userResultKeywordMapper.partialUpdate(existingUserResultKeyword, userResultKeywordDTO);

                return existingUserResultKeyword;
            })
            .map(userResultKeywordRepository::save)
            .map(userResultKeywordMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResultKeywordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserResultKeywords");
        return userResultKeywordRepository.findAll(pageable).map(userResultKeywordMapper::toDto);
    }

    public Page<UserResultKeywordDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userResultKeywordRepository.findAllWithEagerRelationships(pageable).map(userResultKeywordMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResultKeywordDTO> findOne(Long id) {
        log.debug("Request to get UserResultKeyword : {}", id);
        return userResultKeywordRepository.findOneWithEagerRelationships(id).map(userResultKeywordMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserResultKeyword : {}", id);
        userResultKeywordRepository.deleteById(id);
    }
}
