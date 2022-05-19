package fi.netum.csc.service.dto.aoe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AoeSearchParameters {

    private final List<Filter> filters;
    private final String keywords;
    private final Pageable pageable;

    public AoeSearchParameters (List<Filter> filters, String keywords, Pageable pageable) {
        this.filters = filters;
        this.keywords = keywords;
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
