package fi.netum.csc.service.mapper;

import fi.netum.csc.domain.User;
import fi.netum.csc.domain.UserResultKeyword;
import fi.netum.csc.service.dto.UserDTO;
import fi.netum.csc.service.dto.UserResultKeywordDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserResultKeyword} and its DTO {@link UserResultKeywordDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserResultKeywordMapper extends EntityMapper<UserResultKeywordDTO, UserResultKeyword> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    UserResultKeywordDTO toDto(UserResultKeyword s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
