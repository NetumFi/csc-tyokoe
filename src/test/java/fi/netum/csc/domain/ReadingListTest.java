package fi.netum.csc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReadingListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReadingList.class);
        ReadingList readingList1 = new ReadingList();
        readingList1.setId(1L);
        ReadingList readingList2 = new ReadingList();
        readingList2.setId(readingList1.getId());
        assertThat(readingList1).isEqualTo(readingList2);
        readingList2.setId(2L);
        assertThat(readingList1).isNotEqualTo(readingList2);
        readingList1.setId(null);
        assertThat(readingList1).isNotEqualTo(readingList2);
    }
}
