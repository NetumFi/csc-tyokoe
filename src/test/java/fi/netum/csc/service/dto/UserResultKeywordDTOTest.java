package fi.netum.csc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserResultKeywordDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserResultKeywordDTO.class);
        UserResultKeywordDTO userResultKeywordDTO1 = new UserResultKeywordDTO();
        userResultKeywordDTO1.setId(1L);
        UserResultKeywordDTO userResultKeywordDTO2 = new UserResultKeywordDTO();
        assertThat(userResultKeywordDTO1).isNotEqualTo(userResultKeywordDTO2);
        userResultKeywordDTO2.setId(userResultKeywordDTO1.getId());
        assertThat(userResultKeywordDTO1).isEqualTo(userResultKeywordDTO2);
        userResultKeywordDTO2.setId(2L);
        assertThat(userResultKeywordDTO1).isNotEqualTo(userResultKeywordDTO2);
        userResultKeywordDTO1.setId(null);
        assertThat(userResultKeywordDTO1).isNotEqualTo(userResultKeywordDTO2);
    }
}
