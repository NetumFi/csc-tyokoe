
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
    "language",
    "link",
    "priority",
    "filepath",
    "originalfilename",
    "filesize",
    "mimetype",
    "format",
    "filekey",
    "filebucket",
    "publishedat",
    "pdfkey",
    "displayName"
})
@Generated("jsonschema2pojo")
public class Material {

    @JsonProperty("id")
    private String id;
    @JsonProperty("language")
    private String language;
    @JsonProperty("link")
    private String link;
    @JsonProperty("priority")
    private Integer priority;
    @JsonProperty("filepath")
    private String filepath;
    @JsonProperty("originalfilename")
    private String originalfilename;
    @JsonProperty("filesize")
    private Integer filesize;
    @JsonProperty("mimetype")
    private String mimetype;
    @JsonProperty("format")
    private String format;
    @JsonProperty("filekey")
    private String filekey;
    @JsonProperty("filebucket")
    private String filebucket;
    @JsonProperty("publishedat")
    private String publishedat;
    @JsonProperty("pdfkey")
    private Object pdfkey;
    @JsonProperty("displayName")
    private DisplayName displayName;
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

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("priority")
    public Integer getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @JsonProperty("filepath")
    public String getFilepath() {
        return filepath;
    }

    @JsonProperty("filepath")
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @JsonProperty("originalfilename")
    public String getOriginalfilename() {
        return originalfilename;
    }

    @JsonProperty("originalfilename")
    public void setOriginalfilename(String originalfilename) {
        this.originalfilename = originalfilename;
    }

    @JsonProperty("filesize")
    public Integer getFilesize() {
        return filesize;
    }

    @JsonProperty("filesize")
    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    @JsonProperty("mimetype")
    public String getMimetype() {
        return mimetype;
    }

    @JsonProperty("mimetype")
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
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

    @JsonProperty("publishedat")
    public String getPublishedat() {
        return publishedat;
    }

    @JsonProperty("publishedat")
    public void setPublishedat(String publishedat) {
        this.publishedat = publishedat;
    }

    @JsonProperty("pdfkey")
    public Object getPdfkey() {
        return pdfkey;
    }

    @JsonProperty("pdfkey")
    public void setPdfkey(Object pdfkey) {
        this.pdfkey = pdfkey;
    }

    @JsonProperty("displayName")
    public DisplayName getDisplayName() {
        return displayName;
    }

    @JsonProperty("displayName")
    public void setDisplayName(DisplayName displayName) {
        this.displayName = displayName;
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
