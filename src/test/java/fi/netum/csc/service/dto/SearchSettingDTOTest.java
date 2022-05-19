package fi.netum.csc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SearchSettingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchSettingDTO.class);
        SearchSettingDTO searchSettingDTO1 = new SearchSettingDTO();
        searchSettingDTO1.setId(1L);
        SearchSettingDTO searchSettingDTO2 = new SearchSettingDTO();
        assertThat(searchSettingDTO1).isNotEqualTo(searchSettingDTO2);
        searchSettingDTO2.setId(searchSettingDTO1.getId());
        assertThat(searchSettingDTO1).isEqualTo(searchSettingDTO2);
        searchSettingDTO2.setId(2L);
        assertThat(searchSettingDTO1).isNotEqualTo(searchSettingDTO2);
        searchSettingDTO1.setId(null);
        assertThat(searchSettingDTO1).isNotEqualTo(searchSettingDTO2);
    }
}
