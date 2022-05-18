package fi.netum.csc.service.mapper;

import fi.netum.csc.domain.SearchSetting;
import fi.netum.csc.domain.User;
import fi.netum.csc.service.dto.SearchSettingDTO;
import fi.netum.csc.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SearchSetting} and its DTO {@link SearchSettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface SearchSettingMapper extends EntityMapper<SearchSettingDTO, SearchSetting> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    SearchSettingDTO toDto(SearchSetting s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
