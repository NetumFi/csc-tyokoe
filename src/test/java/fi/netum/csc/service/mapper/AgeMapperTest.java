package fi.netum.csc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgeMapperTest {

    private AgeMapper ageMapper;

    @BeforeEach
    public void setUp() {
        ageMapper = new AgeMapperImpl();
    }
}
