
package fi.netum.csc.service.dto.aoe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import fi.netum.csc.service.dto.aoe.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "createdAt",
    "publishedAt",
    "updatedAt",
    "materialName",
    "description",
    "authors",
    "learningResourceTypes",
    "license",
    "educationalLevels",
    "educationalRoles",
    "keywords",
    "languages",
    "educationalSubjects",
    "teaches",
    "hasDownloadableFiles",
    "thumbnail",
    "popularity",
    "educationalUses",
    "accessibilityFeatures",
    "accessibilityHazards"
})
@Generated("jsonschema2pojo")
public class Result {

    @JsonProperty("id")
    private String id;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("publishedAt")
    private String publishedAt;
    @JsonProperty("updatedAt")
    private String updatedAt;
    @JsonProperty("materialName")
    private List<MaterialName> materialName = null;
    @JsonProperty("description")
    private List<Description> description = null;
    @JsonProperty("authors")
    private List<Author> authors = null;
    @JsonProperty("learningResourceTypes")
    private List<LearningResourceType> learningResourceTypes = null;
    @JsonProperty("license")
    private License license;
    @JsonProperty("educationalLevels")
    private List<EducationalLevel> educationalLevels = null;
    @JsonProperty("educationalRoles")
    private List<EducationalRole> educationalRoles = null;
    @JsonProperty("keywords")
    private List<Keyword> keywords = null;
    @JsonProperty("languages")
    private List<String> languages = null;
    @JsonProperty("educationalSubjects")
    private List<Object> educationalSubjects = null;
    @JsonProperty("teaches")
    private List<Object> teaches = null;
    @JsonProperty("hasDownloadableFiles")
    private Boolean hasDownloadableFiles;
    @JsonProperty("thumbnail")
    private Object thumbnail;
    @JsonProperty("popularity")
    private Double popularity;
    @JsonProperty("educationalUses")
    private List<EducationalUse> educationalUses = null;
    @JsonProperty("accessibilityFeatures")
    private List<Object> accessibilityFeatures = null;
    @JsonProperty("accessibilityHazards")
    private List<Object> accessibilityHazards = null;
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

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("publishedAt")
    public String getPublishedAt() {
        return publishedAt;
    }

    @JsonProperty("publishedAt")
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @JsonProperty("updatedAt")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updatedAt")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("materialName")
    public List<MaterialName> getMaterialName() {
        return materialName;
    }

    @JsonProperty("materialName")
    public void setMaterialName(List<MaterialName> materialName) {
        this.materialName = materialName;
    }

    @JsonProperty("description")
    public List<Description> getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(List<Description> description) {
        this.description = description;
    }

    @JsonProperty("authors")
    public List<Author> getAuthors() {
        return authors;
    }

    @JsonProperty("authors")
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @JsonProperty("learningResourceTypes")
    public List<LearningResourceType> getLearningResourceTypes() {
        return learningResourceTypes;
    }

    @JsonProperty("learningResourceTypes")
    public void setLearningResourceTypes(List<LearningResourceType> learningResourceTypes) {
        this.learningResourceTypes = learningResourceTypes;
    }

    @JsonProperty("license")
    public License getLicense() {
        return license;
    }

    @JsonProperty("license")
    public void setLicense(License license) {
        this.license = license;
    }

    @JsonProperty("educationalLevels")
    public List<EducationalLevel> getEducationalLevels() {
        return educationalLevels;
    }

    @JsonProperty("educationalLevels")
    public void setEducationalLevels(List<EducationalLevel> educationalLevels) {
        this.educationalLevels = educationalLevels;
    }

    @JsonProperty("educationalRoles")
    public List<EducationalRole> getEducationalRoles() {
        return educationalRoles;
    }

    @JsonProperty("educationalRoles")
    public void setEducationalRoles(List<EducationalRole> educationalRoles) {
        this.educationalRoles = educationalRoles;
    }

    @JsonProperty("keywords")
    public List<Keyword> getKeywords() {
        return keywords;
    }

    @JsonProperty("keywords")
    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    @JsonProperty("languages")
    public List<String> getLanguages() {
        return languages;
    }

    @JsonProperty("languages")
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    @JsonProperty("educationalSubjects")
    public List<Object> getEducationalSubjects() {
        return educationalSubjects;
    }

    @JsonProperty("educationalSubjects")
    public void setEducationalSubjects(List<Object> educationalSubjects) {
        this.educationalSubjects = educationalSubjects;
    }

    @JsonProperty("teaches")
    public List<Object> getTeaches() {
        return teaches;
    }

    @JsonProperty("teaches")
    public void setTeaches(List<Object> teaches) {
        this.teaches = teaches;
    }

    @JsonProperty("hasDownloadableFiles")
    public Boolean getHasDownloadableFiles() {
        return hasDownloadableFiles;
    }

    @JsonProperty("hasDownloadableFiles")
    public void setHasDownloadableFiles(Boolean hasDownloadableFiles) {
        this.hasDownloadableFiles = hasDownloadableFiles;
    }

    @JsonProperty("thumbnail")
    public Object getThumbnail() {
        return thumbnail;
    }

    @JsonProperty("thumbnail")
    public void setThumbnail(Object thumbnail) {
        this.thumbnail = thumbnail;
    }

    @JsonProperty("popularity")
    public Double getPopularity() {
        return popularity;
    }

    @JsonProperty("popularity")
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    @JsonProperty("educationalUses")
    public List<EducationalUse> getEducationalUses() {
        return educationalUses;
    }

    @JsonProperty("educationalUses")
    public void setEducationalUses(List<EducationalUse> educationalUses) {
        this.educationalUses = educationalUses;
    }

    @JsonProperty("accessibilityFeatures")
    public List<Object> getAccessibilityFeatures() {
        return accessibilityFeatures;
    }

    @JsonProperty("accessibilityFeatures")
    public void setAccessibilityFeatures(List<Object> accessibilityFeatures) {
        this.accessibilityFeatures = accessibilityFeatures;
    }

    @JsonProperty("accessibilityHazards")
    public List<Object> getAccessibilityHazards() {
        return accessibilityHazards;
    }

    @JsonProperty("accessibilityHazards")
    public void setAccessibilityHazards(List<Object> accessibilityHazards) {
        this.accessibilityHazards = accessibilityHazards;
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
