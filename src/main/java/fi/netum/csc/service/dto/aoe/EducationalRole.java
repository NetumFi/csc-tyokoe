
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
    "educationalrole",
    "educationalmaterialid",
    "educationalrolekey"
})
@Generated("jsonschema2pojo")
public class EducationalRole {

    @JsonProperty("id")
    private String id;
    @JsonProperty("educationalrole")
    private String educationalrole;
    @JsonProperty("educationalmaterialid")
    private String educationalmaterialid;
    @JsonProperty("educationalrolekey")
    private String educationalrolekey;
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

    @JsonProperty("educationalrole")
    public String getEducationalrole() {
        return educationalrole;
    }

    @JsonProperty("educationalrole")
    public void setEducationalrole(String educationalrole) {
        this.educationalrole = educationalrole;
    }

    @JsonProperty("educationalmaterialid")
    public String getEducationalmaterialid() {
        return educationalmaterialid;
    }

    @JsonProperty("educationalmaterialid")
    public void setEducationalmaterialid(String educationalmaterialid) {
        this.educationalmaterialid = educationalmaterialid;
    }

    @JsonProperty("educationalrolekey")
    public String getEducationalrolekey() {
        return educationalrolekey;
    }

    @JsonProperty("educationalrolekey")
    public void setEducationalrolekey(String educationalrolekey) {
        this.educationalrolekey = educationalrolekey;
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
