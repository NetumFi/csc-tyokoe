
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "materials",
    "owner",
    "name",
    "createdAt",
    "updatedAt",
    "publishedAt",
    "archivedAt",
    "suitsAllEarlyChildhoodSubjects",
    "suitsAllPrePrimarySubjects",
    "suitsAllBasicStudySubjects",
    "suitsAllUpperSecondarySubjects",
    "suitsAllVocationalDegrees",
    "suitsAllSelfMotivatedSubjects",
    "suitsAllBranches",
    "suitsAllUpperSecondarySubjectsNew",
    "ratingContentAverage",
    "ratingVisualAverage",
    "viewCounter",
    "downloadCounter",
    "author",
    "publisher",
    "description",
    "keywords",
    "learningResourceTypes",
    "timeRequired",
    "expires",
    "typicalAgeRange",
    "educationalAlignment",
    "educationalLevels",
    "educationalUses",
    "accessibilityFeatures",
    "accessibilityHazards",
    "license",
    "isBasedOn",
    "educationalRoles",
    "thumbnail",
    "attachments",
    "versions",
    "hasDownloadableFiles",
    "urn"
})
@Generated("jsonschema2pojo")
public class ItemMetadata {

    @JsonProperty("id")
    private String id;
    @JsonProperty("materials")
    private List<Material> materials = null;
    @JsonProperty("owner")
    private Boolean owner;
    @JsonProperty("name")
    private List<Name> name = null;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("updatedAt")
    private String updatedAt;
    @JsonProperty("publishedAt")
    private String publishedAt;
    @JsonProperty("archivedAt")
    private Object archivedAt;
    @JsonProperty("suitsAllEarlyChildhoodSubjects")
    private Boolean suitsAllEarlyChildhoodSubjects;
    @JsonProperty("suitsAllPrePrimarySubjects")
    private Boolean suitsAllPrePrimarySubjects;
    @JsonProperty("suitsAllBasicStudySubjects")
    private Boolean suitsAllBasicStudySubjects;
    @JsonProperty("suitsAllUpperSecondarySubjects")
    private Boolean suitsAllUpperSecondarySubjects;
    @JsonProperty("suitsAllVocationalDegrees")
    private Boolean suitsAllVocationalDegrees;
    @JsonProperty("suitsAllSelfMotivatedSubjects")
    private Boolean suitsAllSelfMotivatedSubjects;
    @JsonProperty("suitsAllBranches")
    private Boolean suitsAllBranches;
    @JsonProperty("suitsAllUpperSecondarySubjectsNew")
    private Boolean suitsAllUpperSecondarySubjectsNew;
    @JsonProperty("ratingContentAverage")
    private Object ratingContentAverage;
    @JsonProperty("ratingVisualAverage")
    private Object ratingVisualAverage;
    @JsonProperty("viewCounter")
    private String viewCounter;
    @JsonProperty("downloadCounter")
    private String downloadCounter;
    @JsonProperty("author")
    private List<Author> author = null;
    @JsonProperty("publisher")
    private List<Object> publisher = null;
    @JsonProperty("description")
    private List<Description> description = null;
    @JsonProperty("keywords")
    private List<Keyword> keywords = null;
    @JsonProperty("learningResourceTypes")
    private List<LearningResourceType> learningResourceTypes = null;
    @JsonProperty("timeRequired")
    private String timeRequired;
    @JsonProperty("expires")
    private Object expires;
    @JsonProperty("typicalAgeRange")
    private TypicalAgeRange typicalAgeRange;
    @JsonProperty("educationalAlignment")
    private List<Object> educationalAlignment = null;
    @JsonProperty("educationalLevels")
    private List<EducationalLevel> educationalLevels = null;
    @JsonProperty("educationalUses")
    private List<EducationalUse> educationalUses = null;
    @JsonProperty("accessibilityFeatures")
    private List<Object> accessibilityFeatures = null;
    @JsonProperty("accessibilityHazards")
    private List<Object> accessibilityHazards = null;
    @JsonProperty("license")
    private License license;
    @JsonProperty("isBasedOn")
    private List<Object> isBasedOn = null;
    @JsonProperty("educationalRoles")
    private List<EducationalRole> educationalRoles = null;
    @JsonProperty("thumbnail")
    private Thumbnail thumbnail;
    @JsonProperty("attachments")
    private List<Object> attachments = null;
    @JsonProperty("versions")
    private List<Version> versions = null;
    @JsonProperty("hasDownloadableFiles")
    private Boolean hasDownloadableFiles;
    @JsonProperty("urn")
    private Object urn;
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

    @JsonProperty("materials")
    public List<Material> getMaterials() {
        return materials;
    }

    @JsonProperty("materials")
    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    @JsonProperty("owner")
    public Boolean getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    @JsonProperty("name")
    public List<Name> getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(List<Name> name) {
        this.name = name;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updatedAt")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updatedAt")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("publishedAt")
    public String getPublishedAt() {
        return publishedAt;
    }

    @JsonProperty("publishedAt")
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @JsonProperty("archivedAt")
    public Object getArchivedAt() {
        return archivedAt;
    }

    @JsonProperty("archivedAt")
    public void setArchivedAt(Object archivedAt) {
        this.archivedAt = archivedAt;
    }

    @JsonProperty("suitsAllEarlyChildhoodSubjects")
    public Boolean getSuitsAllEarlyChildhoodSubjects() {
        return suitsAllEarlyChildhoodSubjects;
    }

    @JsonProperty("suitsAllEarlyChildhoodSubjects")
    public void setSuitsAllEarlyChildhoodSubjects(Boolean suitsAllEarlyChildhoodSubjects) {
        this.suitsAllEarlyChildhoodSubjects = suitsAllEarlyChildhoodSubjects;
    }

    @JsonProperty("suitsAllPrePrimarySubjects")
    public Boolean getSuitsAllPrePrimarySubjects() {
        return suitsAllPrePrimarySubjects;
    }

    @JsonProperty("suitsAllPrePrimarySubjects")
    public void setSuitsAllPrePrimarySubjects(Boolean suitsAllPrePrimarySubjects) {
        this.suitsAllPrePrimarySubjects = suitsAllPrePrimarySubjects;
    }

    @JsonProperty("suitsAllBasicStudySubjects")
    public Boolean getSuitsAllBasicStudySubjects() {
        return suitsAllBasicStudySubjects;
    }

    @JsonProperty("suitsAllBasicStudySubjects")
    public void setSuitsAllBasicStudySubjects(Boolean suitsAllBasicStudySubjects) {
        this.suitsAllBasicStudySubjects = suitsAllBasicStudySubjects;
    }

    @JsonProperty("suitsAllUpperSecondarySubjects")
    public Boolean getSuitsAllUpperSecondarySubjects() {
        return suitsAllUpperSecondarySubjects;
    }

    @JsonProperty("suitsAllUpperSecondarySubjects")
    public void setSuitsAllUpperSecondarySubjects(Boolean suitsAllUpperSecondarySubjects) {
        this.suitsAllUpperSecondarySubjects = suitsAllUpperSecondarySubjects;
    }

    @JsonProperty("suitsAllVocationalDegrees")
    public Boolean getSuitsAllVocationalDegrees() {
        return suitsAllVocationalDegrees;
    }

    @JsonProperty("suitsAllVocationalDegrees")
    public void setSuitsAllVocationalDegrees(Boolean suitsAllVocationalDegrees) {
        this.suitsAllVocationalDegrees = suitsAllVocationalDegrees;
    }

    @JsonProperty("suitsAllSelfMotivatedSubjects")
    public Boolean getSuitsAllSelfMotivatedSubjects() {
        return suitsAllSelfMotivatedSubjects;
    }

    @JsonProperty("suitsAllSelfMotivatedSubjects")
    public void setSuitsAllSelfMotivatedSubjects(Boolean suitsAllSelfMotivatedSubjects) {
        this.suitsAllSelfMotivatedSubjects = suitsAllSelfMotivatedSubjects;
    }

    @JsonProperty("suitsAllBranches")
    public Boolean getSuitsAllBranches() {
        return suitsAllBranches;
    }

    @JsonProperty("suitsAllBranches")
    public void setSuitsAllBranches(Boolean suitsAllBranches) {
        this.suitsAllBranches = suitsAllBranches;
    }

    @JsonProperty("suitsAllUpperSecondarySubjectsNew")
    public Boolean getSuitsAllUpperSecondarySubjectsNew() {
        return suitsAllUpperSecondarySubjectsNew;
    }

    @JsonProperty("suitsAllUpperSecondarySubjectsNew")
    public void setSuitsAllUpperSecondarySubjectsNew(Boolean suitsAllUpperSecondarySubjectsNew) {
        this.suitsAllUpperSecondarySubjectsNew = suitsAllUpperSecondarySubjectsNew;
    }

    @JsonProperty("ratingContentAverage")
    public Object getRatingContentAverage() {
        return ratingContentAverage;
    }

    @JsonProperty("ratingContentAverage")
    public void setRatingContentAverage(Object ratingContentAverage) {
        this.ratingContentAverage = ratingContentAverage;
    }

    @JsonProperty("ratingVisualAverage")
    public Object getRatingVisualAverage() {
        return ratingVisualAverage;
    }

    @JsonProperty("ratingVisualAverage")
    public void setRatingVisualAverage(Object ratingVisualAverage) {
        this.ratingVisualAverage = ratingVisualAverage;
    }

    @JsonProperty("viewCounter")
    public String getViewCounter() {
        return viewCounter;
    }

    @JsonProperty("viewCounter")
    public void setViewCounter(String viewCounter) {
        this.viewCounter = viewCounter;
    }

    @JsonProperty("downloadCounter")
    public String getDownloadCounter() {
        return downloadCounter;
    }

    @JsonProperty("downloadCounter")
    public void setDownloadCounter(String downloadCounter) {
        this.downloadCounter = downloadCounter;
    }

    @JsonProperty("author")
    public List<Author> getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    @JsonProperty("publisher")
    public List<Object> getPublisher() {
        return publisher;
    }

    @JsonProperty("publisher")
    public void setPublisher(List<Object> publisher) {
        this.publisher = publisher;
    }

    @JsonProperty("description")
    public List<Description> getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(List<Description> description) {
        this.description = description;
    }

    @JsonProperty("keywords")
    public List<Keyword> getKeywords() {
        return keywords;
    }

    @JsonProperty("keywords")
    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    @JsonProperty("learningResourceTypes")
    public List<LearningResourceType> getLearningResourceTypes() {
        return learningResourceTypes;
    }

    @JsonProperty("learningResourceTypes")
    public void setLearningResourceTypes(List<LearningResourceType> learningResourceTypes) {
        this.learningResourceTypes = learningResourceTypes;
    }

    @JsonProperty("timeRequired")
    public String getTimeRequired() {
        return timeRequired;
    }

    @JsonProperty("timeRequired")
    public void setTimeRequired(String timeRequired) {
        this.timeRequired = timeRequired;
    }

    @JsonProperty("expires")
    public Object getExpires() {
        return expires;
    }

    @JsonProperty("expires")
    public void setExpires(Object expires) {
        this.expires = expires;
    }

    @JsonProperty("typicalAgeRange")
    public TypicalAgeRange getTypicalAgeRange() {
        return typicalAgeRange;
    }

    @JsonProperty("typicalAgeRange")
    public void setTypicalAgeRange(TypicalAgeRange typicalAgeRange) {
        this.typicalAgeRange = typicalAgeRange;
    }

    @JsonProperty("educationalAlignment")
    public List<Object> getEducationalAlignment() {
        return educationalAlignment;
    }

    @JsonProperty("educationalAlignment")
    public void setEducationalAlignment(List<Object> educationalAlignment) {
        this.educationalAlignment = educationalAlignment;
    }

    @JsonProperty("educationalLevels")
    public List<EducationalLevel> getEducationalLevels() {
        return educationalLevels;
    }

    @JsonProperty("educationalLevels")
    public void setEducationalLevels(List<EducationalLevel> educationalLevels) {
        this.educationalLevels = educationalLevels;
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

    @JsonProperty("license")
    public License getLicense() {
        return license;
    }

    @JsonProperty("license")
    public void setLicense(License license) {
        this.license = license;
    }

    @JsonProperty("isBasedOn")
    public List<Object> getIsBasedOn() {
        return isBasedOn;
    }

    @JsonProperty("isBasedOn")
    public void setIsBasedOn(List<Object> isBasedOn) {
        this.isBasedOn = isBasedOn;
    }

    @JsonProperty("educationalRoles")
    public List<EducationalRole> getEducationalRoles() {
        return educationalRoles;
    }

    @JsonProperty("educationalRoles")
    public void setEducationalRoles(List<EducationalRole> educationalRoles) {
        this.educationalRoles = educationalRoles;
    }

    @JsonProperty("thumbnail")
    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    @JsonProperty("thumbnail")
    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    @JsonProperty("attachments")
    public List<Object> getAttachments() {
        return attachments;
    }

    @JsonProperty("attachments")
    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    @JsonProperty("versions")
    public List<Version> getVersions() {
        return versions;
    }

    @JsonProperty("versions")
    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    @JsonProperty("hasDownloadableFiles")
    public Boolean getHasDownloadableFiles() {
        return hasDownloadableFiles;
    }

    @JsonProperty("hasDownloadableFiles")
    public void setHasDownloadableFiles(Boolean hasDownloadableFiles) {
        this.hasDownloadableFiles = hasDownloadableFiles;
    }

    @JsonProperty("urn")
    public Object getUrn() {
        return urn;
    }

    @JsonProperty("urn")
    public void setUrn(Object urn) {
        this.urn = urn;
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
