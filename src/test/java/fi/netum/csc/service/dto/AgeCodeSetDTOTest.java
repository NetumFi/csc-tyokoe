package fi.netum.csc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgeCodeSetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgeCodeSetDTO.class);
        AgeCodeSetDTO ageCodeSetDTO1 = new AgeCodeSetDTO();
        ageCodeSetDTO1.setId(1L);
        AgeCodeSetDTO ageCodeSetDTO2 = new AgeCodeSetDTO();
        assertThat(ageCodeSetDTO1).isNotEqualTo(ageCodeSetDTO2);
        ageCodeSetDTO2.setId(ageCodeSetDTO1.getId());
        assertThat(ageCodeSetDTO1).isEqualTo(ageCodeSetDTO2);
        ageCodeSetDTO2.setId(2L);
        assertThat(ageCodeSetDTO1).isNotEqualTo(ageCodeSetDTO2);
        ageCodeSetDTO1.setId(null);
        assertThat(ageCodeSetDTO1).isNotEqualTo(ageCodeSetDTO2);
    }
}
