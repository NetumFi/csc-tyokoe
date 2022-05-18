package fi.netum.csc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Age.class);
        Age age1 = new Age();
        age1.setId(1L);
        Age age2 = new Age();
        age2.setId(age1.getId());
        assertThat(age1).isEqualTo(age2);
        age2.setId(2L);
        assertThat(age1).isNotEqualTo(age2);
        age1.setId(null);
        assertThat(age1).isNotEqualTo(age2);
    }
}
