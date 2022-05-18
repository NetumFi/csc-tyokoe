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
public class AgeCodeSet implements Serializable {

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

    @OneToMany(mappedBy = "ageCodeSet")
    @JsonIgnoreProperties(value = { "user", "educationLevelCodeSet", "ageCodeSet" }, allowSetters = true)
    private Set<SearchSetting> searchSettings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AgeCodeSet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public AgeCodeSet code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeId() {
        return this.codeId;
    }

    public AgeCodeSet codeId(String codeId) {
        this.setCodeId(codeId);
        return this;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getLabelEn() {
        return this.labelEn;
    }

    public AgeCodeSet labelEn(String labelEn) {
        this.setLabelEn(labelEn);
        return this;
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn;
    }

    public String getLabelFi() {
        return this.labelFi;
    }

    public AgeCodeSet labelFi(String labelFi) {
        this.setLabelFi(labelFi);
        return this;
    }

    public void setLabelFi(String labelFi) {
        this.labelFi = labelFi;
    }

    public String getLabelSv() {
        return this.labelSv;
    }

    public AgeCodeSet labelSv(String labelSv) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgeCodeSet)) {
            return false;
        }
        return id != null && id.equals(((AgeCodeSet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

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
