package fi.netum.csc.web.rest;

import fi.netum.csc.domain.User;
import org.apache.commons.lang3.RandomStringUtils;

public class UserTestUtil {

    public static User createUser() {
        User user = new User();
        user.setLogin("juuso");
        user.setEmail("junit-juuso@localhost");
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);

        return user;
    }
}
