package fi.netum.csc.service.dto.aoe;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Filter {

    @JsonProperty("filter")
    private String filter;

    @JsonProperty("values")
    private List<String> values;

    public Filter() {};

    public Filter(String filter, List<String> values)
    {
        this.filter = filter;
        this.values = values;
    }

    public String getFilter() {
        return filter;
    }
    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<String> getValues() {
        return values;
    }
    public void setValues(List<String> values) { this.values = values; }
}
