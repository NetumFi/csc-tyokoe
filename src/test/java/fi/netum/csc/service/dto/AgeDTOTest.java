package fi.netum.csc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgeDTO.class);
        AgeDTO ageDTO1 = new AgeDTO();
        ageDTO1.setId(1L);
        AgeDTO ageDTO2 = new AgeDTO();
        assertThat(ageDTO1).isNotEqualTo(ageDTO2);
        ageDTO2.setId(ageDTO1.getId());
        assertThat(ageDTO1).isEqualTo(ageDTO2);
        ageDTO2.setId(2L);
        assertThat(ageDTO1).isNotEqualTo(ageDTO2);
        ageDTO1.setId(null);
        assertThat(ageDTO1).isNotEqualTo(ageDTO2);
    }
}
