package fi.netum.csc.service.dto.aoe;

import java.util.List;

public class Filter {

    private final String filter;
    private final List<String> values;

    public Filter (String filter, List<String> values)
    {
        this.filter = filter;
        this.values = values;
    }

    public String getFilter() {
        return filter;
    }

    public List<String> getValues() {
        return values;
    }
}
