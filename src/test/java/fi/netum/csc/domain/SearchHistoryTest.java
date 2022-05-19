package fi.netum.csc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SearchHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SearchHistory.class);
        SearchHistory searchHistory1 = new SearchHistory();
        searchHistory1.setId(1L);
        SearchHistory searchHistory2 = new SearchHistory();
        searchHistory2.setId(searchHistory1.getId());
        assertThat(searchHistory1).isEqualTo(searchHistory2);
        searchHistory2.setId(2L);
        assertThat(searchHistory1).isNotEqualTo(searchHistory2);
        searchHistory1.setId(null);
        assertThat(searchHistory1).isNotEqualTo(searchHistory2);
    }
}
