package fi.netum.csc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserResultKeywordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserResultKeyword.class);
        UserResultKeyword userResultKeyword1 = new UserResultKeyword();
        userResultKeyword1.setId(1L);
        UserResultKeyword userResultKeyword2 = new UserResultKeyword();
        userResultKeyword2.setId(userResultKeyword1.getId());
        assertThat(userResultKeyword1).isEqualTo(userResultKeyword2);
        userResultKeyword2.setId(2L);
        assertThat(userResultKeyword1).isNotEqualTo(userResultKeyword2);
        userResultKeyword1.setId(null);
        assertThat(userResultKeyword1).isNotEqualTo(userResultKeyword2);
    }
}
