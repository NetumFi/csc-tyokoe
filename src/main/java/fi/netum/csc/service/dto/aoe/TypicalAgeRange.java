
package fi.netum.csc.service.dto.aoe;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "typicalAgeRangeMin",
    "typicalAgeRangeMax"
})
@Generated("jsonschema2pojo")
public class TypicalAgeRange {

    @JsonProperty("typicalAgeRangeMin")
    private Object typicalAgeRangeMin;
    @JsonProperty("typicalAgeRangeMax")
    private Object typicalAgeRangeMax;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("typicalAgeRangeMin")
    public Object getTypicalAgeRangeMin() {
        return typicalAgeRangeMin;
    }

    @JsonProperty("typicalAgeRangeMin")
    public void setTypicalAgeRangeMin(Object typicalAgeRangeMin) {
        this.typicalAgeRangeMin = typicalAgeRangeMin;
    }

    @JsonProperty("typicalAgeRangeMax")
    public Object getTypicalAgeRangeMax() {
        return typicalAgeRangeMax;
    }

    @JsonProperty("typicalAgeRangeMax")
    public void setTypicalAgeRangeMax(Object typicalAgeRangeMax) {
        this.typicalAgeRangeMax = typicalAgeRangeMax;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
