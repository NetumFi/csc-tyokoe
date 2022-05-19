package fi.netum.csc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EducationLevelCodeSetMapperTest {

    private EducationLevelCodeSetMapper educationLevelCodeSetMapper;

    @BeforeEach
    public void setUp() {
        educationLevelCodeSetMapper = new EducationLevelCodeSetMapperImpl();
    }
}
