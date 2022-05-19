
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
    "authorname",
    "organization",
    "educationalmaterialid",
    "organizationkey"
})
@Generated("jsonschema2pojo")
public class Author {

    @JsonProperty("id")
    private String id;
    @JsonProperty("authorname")
    private String authorname;
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("educationalmaterialid")
    private String educationalmaterialid;
    @JsonProperty("organizationkey")
    private String organizationkey;
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

    @JsonProperty("authorname")
    public String getAuthorname() {
        return authorname;
    }

    @JsonProperty("authorname")
    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    @JsonProperty("organization")
    public String getOrganization() {
        return organization;
    }

    @JsonProperty("organization")
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @JsonProperty("educationalmaterialid")
    public String getEducationalmaterialid() {
        return educationalmaterialid;
    }

    @JsonProperty("educationalmaterialid")
    public void setEducationalmaterialid(String educationalmaterialid) {
        this.educationalmaterialid = educationalmaterialid;
    }

    @JsonProperty("organizationkey")
    public String getOrganizationkey() {
        return organizationkey;
    }

    @JsonProperty("organizationkey")
    public void setOrganizationkey(String organizationkey) {
        this.organizationkey = organizationkey;
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
