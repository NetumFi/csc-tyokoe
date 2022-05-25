package fi.netum.csc.service;

import fi.netum.csc.IntegrationTest;
import fi.netum.csc.domain.User;
import fi.netum.csc.service.dto.aoe.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.config.JHipsterProperties;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * Integration tests for {@link AoeService}.
 */
@IntegrationTest
class AoeServiceIT {

    @Autowired
    private JHipsterProperties jHipsterProperties;

    private final HttpClient httpClient = mock(HttpClient.class);

    @Captor
    private ArgumentCaptor<HttpRequest> messageCaptorHttpRequest;

    @Captor
    private ArgumentCaptor<String> messageCaptorString;

    private AoeService aoeService;

    @Autowired
    private UserResultKeywordService userResultKeywordService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        aoeService = new AoeService(userResultKeywordService, jHipsterProperties);
        aoeService.setHttpClient(httpClient);
    }

    @Test
    void testDoSearch() throws Exception {
        HttpResponse httpResponse = mock(HttpResponse.class);
        when(httpClient.send(any(), any())).thenReturn(httpResponse);

        User user = null;

        InputStream is = Test.class.getResourceAsStream("/searchResultMetadata.json");
        String answer = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        when(httpResponse.body()).thenReturn(answer);
        when(httpResponse.statusCode()).thenReturn(200);

        List<Filter> filters = List.of(new Filter("educationalLevels", List.of("e5a48ada-3de0-4246-9b8f-32d4ff68e22f")),
            new Filter("learningResourceTypes", List.of("73bed523-aa9b-4463-8bed-3b31ce3a927a",
                "c1256389-a47d-4a44-beb2-bdbbc79abb28")));

        String keywords = "hakusana1, hakusana2, hakusana3";
        Paging paging = new Paging(0, 3, "uusin");

        AoeSearchParameters aoeSearchParameters = new AoeSearchParameters(filters, keywords, paging);

        SearchResults searchResults = aoeService.doSearch(aoeSearchParameters, user);
        assertThat(searchResults.getHits()).isEqualTo(284);
        assertThat(searchResults.getResults().stream().count()).isEqualTo(1);
        verify(httpClient).send(messageCaptorHttpRequest.capture(), any());
        HttpRequest httpRequest = messageCaptorHttpRequest.getValue();
        assertThat(httpRequest.uri().toString()).isEqualTo("https://aoe.fi/api/v2/search");
    }

    @Test
    void testDoGetMetadata() throws Exception {
        HttpResponse httpResponse = mock(HttpResponse.class);
        when(httpClient.send(any(), any())).thenReturn(httpResponse);

        InputStream is = Test.class.getResourceAsStream("/oneMetadata.json");
        String answer = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        when(httpResponse.body()).thenReturn(answer);
        when(httpResponse.statusCode()).thenReturn(200);
        ItemMetadata itemMetadata = aoeService.getMetadata("100");
        assertThat(itemMetadata.getDownloadCounter()).isEqualTo("333");
        assertThat(itemMetadata.getId()).isEqualTo("1901");

        verify(httpClient).send(messageCaptorHttpRequest.capture(), any());
        HttpRequest httpRequest = messageCaptorHttpRequest.getValue();
        assertThat(httpRequest.uri().toString()).isEqualTo("https://aoe.fi/api/v2/metadata/100");
    }
}
