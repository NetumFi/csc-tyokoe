package fi.netum.csc.service.dto;

import fi.netum.csc.service.dto.aoe.AoeSearchParameters;
import fi.netum.csc.service.dto.aoe.Filter;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public class AoeSearchParametersTest {

    @Test
    void testDoSearch() throws Exception {
        List<Filter> filters = List.of(new Filter("educationalLevels", List.of("e5a48ada-3de0-4246-9b8f-32d4ff68e22f")),
            new Filter("learningResourceTypes", List.of("73bed523-aa9b-4463-8bed-3b31ce3a927a",
                "c1256389-a47d-4a44-beb2-bdbbc79abb28")));

        String keywords = "hakusana1, hakusana2, hakusana3";
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by("uusin").descending());

        AoeSearchParameters aoeSearchParameters = new AoeSearchParameters(filters, keywords, pageRequest);

        String searchParametersExpected = "{\"size\":3,\"keywords\":\"hakusana1, hakusana2, hakusana3\",\"from\":0,\"sort\":" +
            "\"uusin\",\"filters\":{\"educationalLevels\":[\"e5a48ada-3de0-4246-9b8f-32d4ff68e22f\"],\"learningResourceTypes\""
            + ":[\"73bed523-aa9b-4463-8bed-3b31ce3a927a\",\"c1256389-a47d-4a44-beb2-bdbbc79abb28\"]}}";
        JSONAssert.assertEquals(searchParametersExpected, aoeSearchParameters.toJson(), true);
    }
}
