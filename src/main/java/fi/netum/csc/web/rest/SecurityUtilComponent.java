package fi.netum.csc.web.rest;

import fi.netum.csc.domain.User;
import fi.netum.csc.service.UserService;
import fi.netum.csc.service.dto.ReadingListDTO;
import fi.netum.csc.service.dto.UserDTO;
import fi.netum.csc.service.dto.UserRelatable;
import fi.netum.csc.service.mapper.UserMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static fi.netum.csc.security.SecurityUtils.hasCurrentUserAnyOfAuthorities;

@Component
public class SecurityUtilComponent {

    private UserService userService;
    private UserMapper userMapper;

    public SecurityUtilComponent(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public User getLoggedUser() {
        Optional<User> optionalUser = userService.getUserWithAuthorities();

        if( optionalUser.isEmpty() ) {
            throw new UsernameNotFoundException("User is not logged");
        }

        return optionalUser.get();
    }

    public UserDTO getUserDTO() {
        User user = getLoggedUser();

        return userMapper.userToUserDTO(user);

    }


    public UserRelatable verifyOwner(UserRelatable userRelatable) {
        if( !hasCurrentUserAnyOfAuthorities("ROLE_ADMIN") ) {
            UserDTO userDto = getUserDTO();
            userRelatable.setUser(userDto);
        }
        return userRelatable;
    }
}
