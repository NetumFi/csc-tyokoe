package fi.netum.csc.service.mapper;

import fi.netum.csc.domain.Profile;
import fi.netum.csc.domain.User;
import fi.netum.csc.service.dto.ProfileDTO;
import fi.netum.csc.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    ProfileDTO toDto(Profile s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
