package fi.netum.csc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A EducationLevelCodeSet.
 */
@Entity
@Table(name = "education_level_code_set")
public class EducationLevelCodeSet extends AbstractCodeSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "educationLevelCodeSet")
    @JsonIgnoreProperties(value = { "user", "educationLevelCodeSet", "ageCodeSet" }, allowSetters = true)
    private Set<SearchSetting> searchSettings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public EducationLevelCodeSet id(Long id) {
        this.setId(id);
        return this;
    }

    public EducationLevelCodeSet code(String code) {
        this.setCode(code);
        return this;
    }

    public EducationLevelCodeSet codeId(String codeId) {
        this.setCodeId(codeId);
        return this;
    }

    public EducationLevelCodeSet labelEn(String labelEn) {
        this.setLabelEn(labelEn);
        return this;
    }

    public EducationLevelCodeSet labelFi(String labelFi) {
        this.setLabelFi(labelFi);
        return this;
    }

    public EducationLevelCodeSet labelSv(String labelSv) {
        this.setLabelSv(labelSv);
        return this;
    }

    public Set<SearchSetting> getSearchSettings() {
        return this.searchSettings;
    }

    public void setSearchSettings(Set<SearchSetting> searchSettings) {
        if (this.searchSettings != null) {
            this.searchSettings.forEach(i -> i.setEducationLevelCodeSet(null));
        }
        if (searchSettings != null) {
            searchSettings.forEach(i -> i.setEducationLevelCodeSet(this));
        }
        this.searchSettings = searchSettings;
    }

    public EducationLevelCodeSet searchSettings(Set<SearchSetting> searchSettings) {
        this.setSearchSettings(searchSettings);
        return this;
    }

    public EducationLevelCodeSet addSearchSetting(SearchSetting searchSetting) {
        this.searchSettings.add(searchSetting);
        searchSetting.setEducationLevelCodeSet(this);
        return this;
    }

    public EducationLevelCodeSet removeSearchSetting(SearchSetting searchSetting) {
        this.searchSettings.remove(searchSetting);
        searchSetting.setEducationLevelCodeSet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationLevelCodeSet{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", codeId='" + getCodeId() + "'" +
            ", labelEn='" + getLabelEn() + "'" +
            ", labelFi='" + getLabelFi() + "'" +
            ", labelSv='" + getLabelSv() + "'" +
            "}";
    }
}
