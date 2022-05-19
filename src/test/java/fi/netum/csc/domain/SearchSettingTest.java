package fi.netum.csc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SearchSettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchSetting.class);
        SearchSetting searchSetting1 = new SearchSetting();
        searchSetting1.setId(1L);
        SearchSetting searchSetting2 = new SearchSetting();
        searchSetting2.setId(searchSetting1.getId());
        assertThat(searchSetting1).isEqualTo(searchSetting2);
        searchSetting2.setId(2L);
        assertThat(searchSetting1).isNotEqualTo(searchSetting2);
        searchSetting1.setId(null);
        assertThat(searchSetting1).isNotEqualTo(searchSetting2);
    }
}
