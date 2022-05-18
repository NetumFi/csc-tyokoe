package fi.netum.csc.service.mapper;

import fi.netum.csc.domain.Age;
import fi.netum.csc.service.dto.AgeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Age} and its DTO {@link AgeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgeMapper extends EntityMapper<AgeDTO, Age> {}
