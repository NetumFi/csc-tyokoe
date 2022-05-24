package fi.netum.csc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserResultKeywordMapperTest {

    private UserResultKeywordMapper userResultKeywordMapper;

    @BeforeEach
    public void setUp() {
        userResultKeywordMapper = new UserResultKeywordMapperImpl();
    }
}
