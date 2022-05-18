package fi.netum.csc.service.mapper;

import fi.netum.csc.domain.AgeCodeSet;
import fi.netum.csc.service.dto.AgeCodeSetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AgeCodeSet} and its DTO {@link AgeCodeSetDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgeCodeSetMapper extends EntityMapper<AgeCodeSetDTO, AgeCodeSet> {}
