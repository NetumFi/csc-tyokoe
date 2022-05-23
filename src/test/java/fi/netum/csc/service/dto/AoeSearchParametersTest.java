package fi.netum.csc.service.dto;

import fi.netum.csc.service.dto.aoe.AoeSearchParameters;
import fi.netum.csc.service.dto.aoe.Filter;
import fi.netum.csc.service.dto.aoe.Paging;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;

public class AoeSearchParametersTest {

    @Test
    void testAoeSearchParameters() throws Exception {
        List<Filter> filters = List.of(new Filter("educationalLevels", List.of("e5a48ada-3de0-4246-9b8f-32d4ff68e22f")),
            new Filter("learningResourceTypes", List.of("73bed523-aa9b-4463-8bed-3b31ce3a927a",
                "c1256389-a47d-4a44-beb2-bdbbc79abb28")));

        String keywords = "hakusana1, hakusana2, hakusana3";
        Paging paging = new Paging(0, 3, "uusin");

        AoeSearchParameters aoeSearchParameters = new AoeSearchParameters(filters, keywords, paging);

        String searchParametersExpected = "{\"size\":3,\"keywords\":\"hakusana1, hakusana2, hakusana3\",\"from\":0,\"sort\":" +
            "\"uusin\",\"filters\":{\"educationalLevels\":[\"e5a48ada-3de0-4246-9b8f-32d4ff68e22f\"],\"learningResourceTypes\""
            + ":[\"73bed523-aa9b-4463-8bed-3b31ce3a927a\",\"c1256389-a47d-4a44-beb2-bdbbc79abb28\"]}}";
        JSONAssert.assertEquals(searchParametersExpected, aoeSearchParameters.toJson(), true);
    }
}
