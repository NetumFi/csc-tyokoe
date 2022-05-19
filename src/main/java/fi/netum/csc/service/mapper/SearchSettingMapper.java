package fi.netum.csc.service.mapper;

import fi.netum.csc.domain.AgeCodeSet;
import fi.netum.csc.domain.EducationLevelCodeSet;
import fi.netum.csc.domain.SearchSetting;
import fi.netum.csc.domain.User;
import fi.netum.csc.service.dto.AgeCodeSetDTO;
import fi.netum.csc.service.dto.EducationLevelCodeSetDTO;
import fi.netum.csc.service.dto.SearchSettingDTO;
import fi.netum.csc.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SearchSetting} and its DTO {@link SearchSettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface SearchSettingMapper extends EntityMapper<SearchSettingDTO, SearchSetting> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "educationLevelCodeSet", source = "educationLevelCodeSet", qualifiedByName = "educationLevelCodeSetId")
    @Mapping(target = "ageCodeSet", source = "ageCodeSet", qualifiedByName = "ageCodeSetId")
    SearchSettingDTO toDto(SearchSetting s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("educationLevelCodeSetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EducationLevelCodeSetDTO toDtoEducationLevelCodeSetId(EducationLevelCodeSet educationLevelCodeSet);

    @Named("ageCodeSetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AgeCodeSetDTO toDtoAgeCodeSetId(AgeCodeSet ageCodeSet);
}
