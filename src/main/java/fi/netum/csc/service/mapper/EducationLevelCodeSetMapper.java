package fi.netum.csc.service.mapper;

import fi.netum.csc.domain.EducationLevelCodeSet;
import fi.netum.csc.service.dto.EducationLevelCodeSetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EducationLevelCodeSet} and its DTO {@link EducationLevelCodeSetDTO}.
 */
@Mapper(componentModel = "spring")
public interface EducationLevelCodeSetMapper extends EntityMapper<EducationLevelCodeSetDTO, EducationLevelCodeSet> {}
