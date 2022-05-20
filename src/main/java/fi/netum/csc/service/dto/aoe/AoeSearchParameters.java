package fi.netum.csc.service.dto.aoe;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "filters",
    "keywords",
    "pageable"
})

public class AoeSearchParameters implements Serializable {

    @JsonProperty("filters")
    private List<Filter> filters;

    @JsonProperty("keywords")
    private String keywords;

    @JsonProperty("pageable")
    private MyPageable pageable;

    public AoeSearchParameters() {};

    public AoeSearchParameters (List<Filter> filters, String keywords, MyPageable pageable) {
        this.filters = filters;
        this.keywords = keywords;
        this.pageable = pageable;
    }

    @JsonProperty("filters")
    public List<Filter> getFilters() {
        return filters;
    }

    @JsonProperty("keywords")
    public String getKeywords() {
        return keywords;
    }

    @JsonProperty("pageable")
    public Pageable getPageable() {
        return pageable;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public void setPageable(MyPageable pageable) {
        this.pageable = pageable;
    }

    public String toJson() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(Map.of("filters", filters.stream().collect(Collectors.toMap(Filter::getFilter, Filter::getValues)),
            "keywords", keywords,
            "from", pageable.getOffset(),
            "size", pageable.getPageSize(),
            "sort", pageable.getSort().iterator().next().getProperty()));
    }
}
