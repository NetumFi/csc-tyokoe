package fi.netum.csc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SearchHistoryMapperTest {

    private SearchHistoryMapper searchHistoryMapper;

    @BeforeEach
    public void setUp() {
        searchHistoryMapper = new SearchHistoryMapperImpl();
    }
}
