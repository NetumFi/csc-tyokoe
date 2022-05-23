
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
    "id",
    "value",
    "educationalmaterialid",
    "educationalusekey"
})
@Generated("jsonschema2pojo")
public class EducationalUse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private String value;
    @JsonProperty("educationalmaterialid")
    private String educationalmaterialid;
    @JsonProperty("educationalusekey")
    private String educationalusekey;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("educationalmaterialid")
    public String getEducationalmaterialid() {
        return educationalmaterialid;
    }

    @JsonProperty("educationalmaterialid")
    public void setEducationalmaterialid(String educationalmaterialid) {
        this.educationalmaterialid = educationalmaterialid;
    }

    @JsonProperty("educationalusekey")
    public String getEducationalusekey() {
        return educationalusekey;
    }

    @JsonProperty("educationalusekey")
    public void setEducationalusekey(String educationalusekey) {
        this.educationalusekey = educationalusekey;
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
