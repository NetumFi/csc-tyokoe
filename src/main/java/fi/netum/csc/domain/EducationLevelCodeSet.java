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
public class EducationLevelCodeSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "code_id")
    private String codeId;

    @Column(name = "label_en")
    private String labelEn;

    @Column(name = "label_fi")
    private String labelFi;

    @Column(name = "label_sv")
    private String labelSv;

    @OneToMany(mappedBy = "educationLevelCodeSet")
    @JsonIgnoreProperties(value = { "user", "educationLevelCodeSet", "ageCodeSet" }, allowSetters = true)
    private Set<SearchSetting> searchSettings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EducationLevelCodeSet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public EducationLevelCodeSet code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeId() {
        return this.codeId;
    }

    public EducationLevelCodeSet codeId(String codeId) {
        this.setCodeId(codeId);
        return this;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getLabelEn() {
        return this.labelEn;
    }

    public EducationLevelCodeSet labelEn(String labelEn) {
        this.setLabelEn(labelEn);
        return this;
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn;
    }

    public String getLabelFi() {
        return this.labelFi;
    }

    public EducationLevelCodeSet labelFi(String labelFi) {
        this.setLabelFi(labelFi);
        return this;
    }

    public void setLabelFi(String labelFi) {
        this.labelFi = labelFi;
    }

    public String getLabelSv() {
        return this.labelSv;
    }

    public EducationLevelCodeSet labelSv(String labelSv) {
        this.setLabelSv(labelSv);
        return this;
    }

    public void setLabelSv(String labelSv) {
        this.labelSv = labelSv;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EducationLevelCodeSet)) {
            return false;
        }
        return id != null && id.equals(((EducationLevelCodeSet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

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
