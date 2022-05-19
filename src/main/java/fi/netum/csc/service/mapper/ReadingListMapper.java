package fi.netum.csc.service.mapper;

import fi.netum.csc.domain.ReadingList;
import fi.netum.csc.domain.User;
import fi.netum.csc.service.dto.ReadingListDTO;
import fi.netum.csc.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReadingList} and its DTO {@link ReadingListDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReadingListMapper extends EntityMapper<ReadingListDTO, ReadingList> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    ReadingListDTO toDto(ReadingList s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
