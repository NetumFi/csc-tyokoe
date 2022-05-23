package fi.netum.csc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.netum.csc.service.dto.aoe.AoeSearchParameters;
import fi.netum.csc.service.dto.aoe.ItemMetadata;
import fi.netum.csc.service.dto.aoe.SearchResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.config.JHipsterProperties;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Transactional
public class AoeService {

    // TODO: JhipsterPropertiesin kautta
    private final String aoeSearchUrl = "https://aoe.fi/api/v2/search";
    private final String aoeMetadataUrl = "https://aoe.fi/api/v2/metadata/";

    private final Logger log = LoggerFactory.getLogger(AoeService.class);

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private HttpClient httpClient = HttpClient.newBuilder().build();
    private final JHipsterProperties jHipsterProperties;

    public AoeService(
        JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    public SearchResults doSearch(AoeSearchParameters aoeSearchParameters)
        throws IOException, InterruptedException {

        String requestBody = aoeSearchParameters.toJson();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(aoeSearchUrl))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        log.debug("Kutsutaan hakua urlille: " + aoeSearchUrl + ", kutsutta käytetty data: " + requestBody);

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), SearchResults.class);

    }

    public ItemMetadata getMetadata(int id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(aoeMetadataUrl + id))
            .GET()
            .build();

        log.debug("Kutsutaan metadatahakua urlille: " + aoeMetadataUrl
            + ", kutsutta käytetty id: " + id);

        HttpResponse<String> response = httpClient.send(request,
            HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), ItemMetadata.class);
    }
}
