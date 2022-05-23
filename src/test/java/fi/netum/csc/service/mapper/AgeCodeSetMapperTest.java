package fi.netum.csc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgeCodeSetMapperTest {

    private AgeCodeSetMapper ageCodeSetMapper;

    @BeforeEach
    public void setUp() {
        ageCodeSetMapper = new AgeCodeSetMapperImpl();
    }
}
