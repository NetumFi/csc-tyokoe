package fi.netum.csc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReadingListDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReadingListDTO.class);
        ReadingListDTO readingListDTO1 = new ReadingListDTO();
        readingListDTO1.setId(1L);
        ReadingListDTO readingListDTO2 = new ReadingListDTO();
        assertThat(readingListDTO1).isNotEqualTo(readingListDTO2);
        readingListDTO2.setId(readingListDTO1.getId());
        assertThat(readingListDTO1).isEqualTo(readingListDTO2);
        readingListDTO2.setId(2L);
        assertThat(readingListDTO1).isNotEqualTo(readingListDTO2);
        readingListDTO1.setId(null);
        assertThat(readingListDTO1).isNotEqualTo(readingListDTO2);
    }
}
