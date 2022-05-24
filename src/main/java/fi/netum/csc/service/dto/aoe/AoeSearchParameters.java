package fi.netum.csc.service.dto.aoe;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "filters",
    "keywords",
    "paging"
})

public class AoeSearchParameters implements Serializable {

    @JsonProperty("filters")
    private List<Filter> filters;

    @JsonProperty("keywords")
    private String keywords;

    @JsonProperty("paging")
    private Paging paging;

    public AoeSearchParameters() {};

    public AoeSearchParameters (List<Filter> filters, String keywords, Paging paging) {
        this.filters = filters;
        this.keywords = keywords;
        this.paging = paging;
    }

    @JsonProperty("filters")
    public List<Filter> getFilters() {
        return filters;
    }

    @JsonProperty("keywords")
    public String getKeywords() {
        return keywords;
    }

    @JsonProperty("paging")
    public Paging getPaging() {
        return paging;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public String toJson() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(Map.of("filters", filters.stream().collect(Collectors.toMap(Filter::getFilter, Filter::getValues)),
            "keywords", keywords,
            "from", paging.getFrom(),
            "size", paging.getSize(),
            "sort", paging.getSort()));
    }
}
