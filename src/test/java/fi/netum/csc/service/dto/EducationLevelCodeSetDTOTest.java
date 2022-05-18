package fi.netum.csc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EducationLevelCodeSetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationLevelCodeSetDTO.class);
        EducationLevelCodeSetDTO educationLevelCodeSetDTO1 = new EducationLevelCodeSetDTO();
        educationLevelCodeSetDTO1.setId(1L);
        EducationLevelCodeSetDTO educationLevelCodeSetDTO2 = new EducationLevelCodeSetDTO();
        assertThat(educationLevelCodeSetDTO1).isNotEqualTo(educationLevelCodeSetDTO2);
        educationLevelCodeSetDTO2.setId(educationLevelCodeSetDTO1.getId());
        assertThat(educationLevelCodeSetDTO1).isEqualTo(educationLevelCodeSetDTO2);
        educationLevelCodeSetDTO2.setId(2L);
        assertThat(educationLevelCodeSetDTO1).isNotEqualTo(educationLevelCodeSetDTO2);
        educationLevelCodeSetDTO1.setId(null);
        assertThat(educationLevelCodeSetDTO1).isNotEqualTo(educationLevelCodeSetDTO2);
    }
}
