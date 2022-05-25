package fi.netum.csc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.netum.csc.domain.User;
import fi.netum.csc.service.dto.UserResultKeywordDTO;
import fi.netum.csc.service.dto.aoe.AoeSearchParameters;
import fi.netum.csc.service.dto.aoe.ItemMetadata;
import fi.netum.csc.service.dto.aoe.Keyword;
import fi.netum.csc.service.dto.aoe.SearchResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.config.JHipsterProperties;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Service
@Transactional
public class AoeService {

    // TODO: JhipsterPropertiesin kautta
    private final String aoeSearchUrl = "https://aoe.fi/api/v2/search";
    private final String aoeMetadataUrl = "https://aoe.fi/api/v2/metadata/";

    private final Logger log = LoggerFactory.getLogger(AoeService.class);
    private final Logger apiLogger = LoggerFactory.getLogger("AOEAPI");

    private final UserResultKeywordService userResultKeywordService;

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).build();
    private final JHipsterProperties jHipsterProperties;

    public AoeService(
        UserResultKeywordService userResultKeywordService, JHipsterProperties jHipsterProperties) {
        this.userResultKeywordService = userResultKeywordService;
        this.jHipsterProperties = jHipsterProperties;
    }

    private void updateResultKeywords(SearchResults result, User user)
    {
        if (user != null && result.getResults().stream().findFirst().isPresent())
        {
            Page<UserResultKeywordDTO> allByUser = userResultKeywordService.findAllByUser(user, PageRequest.of(0, 1));
            UserResultKeywordDTO userResultKeywordDTO = allByUser.get().findFirst().get();

            List<Keyword> keywords = result.getResults().stream().findFirst().get().getKeywords();

            StringBuilder newKeywordToUserResult = new StringBuilder();

            for (Keyword keyword : keywords) {
                newKeywordToUserResult.append(keyword.getValue());
                newKeywordToUserResult.append(" ");
            }
            log.debug("Talletetaan käyttäjälle result keywordiksi: " + newKeywordToUserResult.toString());

            userResultKeywordDTO.setResultKeyword(newKeywordToUserResult.toString().trim());
            userResultKeywordService.save(userResultKeywordDTO);
        }
    }

    public SearchResults doSearch(AoeSearchParameters aoeSearchParameters, User user)
        throws IOException, InterruptedException {

        String requestBody = aoeSearchParameters.toJson();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(aoeSearchUrl))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        log.info("Kutsutaan hakua urlille: " + aoeSearchUrl + ", kutsutta käytetty data: " + requestBody);
        apiLogger.info(aoeSearchParameters.toJson());

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        var result = objectMapper.readValue(response.body(), SearchResults.class);
        updateResultKeywords(result, user);
        return result;
    }

    public ItemMetadata getMetadata(String id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(aoeMetadataUrl + id))
            .GET()
            .build();

        log.info("Kutsutaan metadatahakua urlille: " + aoeMetadataUrl
            + ", kutsutta käytetty id: " + id);

        HttpResponse<String> response = httpClient.send(request,
            HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), ItemMetadata.class);
    }
}
