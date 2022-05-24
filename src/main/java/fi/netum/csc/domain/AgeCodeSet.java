package fi.netum.csc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AgeCodeSet.
 */
@Entity
@Table(name = "age_code_set")
public class AgeCodeSet extends AbstractCodeSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "ageCodeSet")
    @JsonIgnoreProperties(value = { "user", "educationLevelCodeSet", "ageCodeSet" }, allowSetters = true)
    private Set<SearchSetting> searchSettings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public AgeCodeSet code(String code) {
        this.setCode(code);
        return this;
    }

    public AgeCodeSet codeId(String codeId) {
        this.setCodeId(codeId);
        return this;
    }

    public AgeCodeSet labelEn(String labelEn) {
        this.setLabelEn(labelEn);
        return this;
    }

    public AgeCodeSet labelFi(String labelFi) {
        this.setLabelFi(labelFi);
        return this;
    }

    public AgeCodeSet labelSv(String labelSv) {
        this.setLabelSv(labelSv);
        return this;
    }

    public Set<SearchSetting> getSearchSettings() {
        return this.searchSettings;
    }

    public void setSearchSettings(Set<SearchSetting> searchSettings) {
        if (this.searchSettings != null) {
            this.searchSettings.forEach(i -> i.setAgeCodeSet(null));
        }
        if (searchSettings != null) {
            searchSettings.forEach(i -> i.setAgeCodeSet(this));
        }
        this.searchSettings = searchSettings;
    }

    public AgeCodeSet searchSettings(Set<SearchSetting> searchSettings) {
        this.setSearchSettings(searchSettings);
        return this;
    }

    public AgeCodeSet addSearchSetting(SearchSetting searchSetting) {
        this.searchSettings.add(searchSetting);
        searchSetting.setAgeCodeSet(this);
        return this;
    }

    public AgeCodeSet removeSearchSetting(SearchSetting searchSetting) {
        this.searchSettings.remove(searchSetting);
        searchSetting.setAgeCodeSet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    // prettier-ignore
    @Override
    public String toString() {
        return "AgeCodeSet{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", codeId='" + getCodeId() + "'" +
            ", labelEn='" + getLabelEn() + "'" +
            ", labelFi='" + getLabelFi() + "'" +
            ", labelSv='" + getLabelSv() + "'" +
            "}";
    }
}
