package fi.netum.csc.service.dto.aoe;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Paging {

    @JsonProperty("from")
    private int from;

    @JsonProperty("size")
    private int size;

    @JsonProperty("sort")
    private String sort;

    public Paging() {}

    public Paging(int from, int size, String sort) {
        this.from = from;
        this.size = size;
        this.sort = sort;
    }

    @JsonProperty("from")
    public void setFrom(int from) {
        this.from = from;
    }

    @JsonProperty("size")
    public void setSize(int size) { this.size = size; }

    @JsonProperty("sort")
    public void setSort(String sort) { this.sort = sort; }

    @JsonProperty("from")
    public int getFrom() {
        return this.from;
    }

    @JsonProperty("size")
    public int getSize() { return this.size; }

    @JsonProperty("sort")
    public String getSort() { return this.sort; }
}
