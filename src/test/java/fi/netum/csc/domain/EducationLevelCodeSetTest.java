package fi.netum.csc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EducationLevelCodeSetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationLevelCodeSet.class);
        EducationLevelCodeSet educationLevelCodeSet1 = new EducationLevelCodeSet();
        educationLevelCodeSet1.setId(1L);
        EducationLevelCodeSet educationLevelCodeSet2 = new EducationLevelCodeSet();
        educationLevelCodeSet2.setId(educationLevelCodeSet1.getId());
        assertThat(educationLevelCodeSet1).isEqualTo(educationLevelCodeSet2);
        educationLevelCodeSet2.setId(2L);
        assertThat(educationLevelCodeSet1).isNotEqualTo(educationLevelCodeSet2);
        educationLevelCodeSet1.setId(null);
        assertThat(educationLevelCodeSet1).isNotEqualTo(educationLevelCodeSet2);
    }
}
