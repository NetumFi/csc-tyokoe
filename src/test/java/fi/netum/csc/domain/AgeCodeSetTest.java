package fi.netum.csc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgeCodeSetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgeCodeSet.class);
        AgeCodeSet ageCodeSet1 = new AgeCodeSet();
        ageCodeSet1.setId(1L);
        AgeCodeSet ageCodeSet2 = new AgeCodeSet();
        ageCodeSet2.setId(ageCodeSet1.getId());
        assertThat(ageCodeSet1).isEqualTo(ageCodeSet2);
        ageCodeSet2.setId(2L);
        assertThat(ageCodeSet1).isNotEqualTo(ageCodeSet2);
        ageCodeSet1.setId(null);
        assertThat(ageCodeSet1).isNotEqualTo(ageCodeSet2);
    }
}
