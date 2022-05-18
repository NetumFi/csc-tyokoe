package fi.netum.csc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SearchSettingMapperTest {

    private SearchSettingMapper searchSettingMapper;

    @BeforeEach
    public void setUp() {
        searchSettingMapper = new SearchSettingMapperImpl();
    }
}
