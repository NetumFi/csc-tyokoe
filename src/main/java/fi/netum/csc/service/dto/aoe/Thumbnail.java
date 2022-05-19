
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
    "filepath",
    "mimetype",
    "educationalmaterialid",
    "filename",
    "obsoleted",
    "filekey",
    "filebucket"
})
@Generated("jsonschema2pojo")
public class Thumbnail {

    @JsonProperty("id")
    private String id;
    @JsonProperty("filepath")
    private String filepath;
    @JsonProperty("mimetype")
    private String mimetype;
    @JsonProperty("educationalmaterialid")
    private String educationalmaterialid;
    @JsonProperty("filename")
    private String filename;
    @JsonProperty("obsoleted")
    private Integer obsoleted;
    @JsonProperty("filekey")
    private String filekey;
    @JsonProperty("filebucket")
    private String filebucket;
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

    @JsonProperty("filepath")
    public String getFilepath() {
        return filepath;
    }

    @JsonProperty("filepath")
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @JsonProperty("mimetype")
    public String getMimetype() {
        return mimetype;
    }

    @JsonProperty("mimetype")
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    @JsonProperty("educationalmaterialid")
    public String getEducationalmaterialid() {
        return educationalmaterialid;
    }

    @JsonProperty("educationalmaterialid")
    public void setEducationalmaterialid(String educationalmaterialid) {
        this.educationalmaterialid = educationalmaterialid;
    }

    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    @JsonProperty("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @JsonProperty("obsoleted")
    public Integer getObsoleted() {
        return obsoleted;
    }

    @JsonProperty("obsoleted")
    public void setObsoleted(Integer obsoleted) {
        this.obsoleted = obsoleted;
    }

    @JsonProperty("filekey")
    public String getFilekey() {
        return filekey;
    }

    @JsonProperty("filekey")
    public void setFilekey(String filekey) {
        this.filekey = filekey;
    }

    @JsonProperty("filebucket")
    public String getFilebucket() {
        return filebucket;
    }

    @JsonProperty("filebucket")
    public void setFilebucket(String filebucket) {
        this.filebucket = filebucket;
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
