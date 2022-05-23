package fi.netum.csc.service.mapper;

import fi.netum.csc.domain.Note;
import fi.netum.csc.domain.User;
import fi.netum.csc.service.dto.NoteDTO;
import fi.netum.csc.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Note} and its DTO {@link NoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    NoteDTO toDto(Note s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
