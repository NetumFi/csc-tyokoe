package fi.netum.csc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadingListMapperTest {

    private ReadingListMapper readingListMapper;

    @BeforeEach
    public void setUp() {
        readingListMapper = new ReadingListMapperImpl();
    }
}
