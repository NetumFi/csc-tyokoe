package fi.netum.csc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fi.netum.csc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Note.class);
        Note note1 = new Note();
        note1.setId(1L);
        Note note2 = new Note();
        note2.setId(note1.getId());
        assertThat(note1).isEqualTo(note2);
        note2.setId(2L);
        assertThat(note1).isNotEqualTo(note2);
        note1.setId(null);
        assertThat(note1).isNotEqualTo(note2);
    }
}
