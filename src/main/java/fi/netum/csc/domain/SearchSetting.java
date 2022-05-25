package fi.netum.csc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A SearchSetting.
 */
@Entity
@Table(name = "search_setting")
public class SearchSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "search_term")
    private String searchTerm;

    @Column(name = "role")
    private String role;

    @Column(name = "age")
    private String age;

    @Column(name = "field_of_study")
    private String fieldOfStudy;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "searchSettings" }, allowSetters = true)
    private EducationLevelCodeSet educationLevelCodeSet;

    @ManyToOne
    @JsonIgnoreProperties(value = { "searchSettings" }, allowSetters = true)
    private AgeCodeSet ageCodeSet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SearchSetting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchTerm() {
        return this.searchTerm;
    }

    public SearchSetting searchTerm(String searchTerm) {
        this.setSearchTerm(searchTerm);
        return this;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getRole() {
        return this.role;
    }

    public SearchSetting role(String role) {
        this.setRole(role);
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAge() {
        return this.age;
    }

    public SearchSetting age(String age) {
        this.setAge(age);
        return this;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFieldOfStudy() {
        return this.fieldOfStudy;
    }

    public SearchSetting fieldOfStudy(String fieldOfStudy) {
        this.setFieldOfStudy(fieldOfStudy);
        return this;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SearchSetting user(User user) {
        this.setUser(user);
        return this;
    }

    public EducationLevelCodeSet getEducationLevelCodeSet() {
        return this.educationLevelCodeSet;
    }

    public void setEducationLevelCodeSet(EducationLevelCodeSet educationLevelCodeSet) {
        this.educationLevelCodeSet = educationLevelCodeSet;
    }

    public SearchSetting educationLevelCodeSet(EducationLevelCodeSet educationLevelCodeSet) {
        this.setEducationLevelCodeSet(educationLevelCodeSet);
        return this;
    }

    public AgeCodeSet getAgeCodeSet() {
        return this.ageCodeSet;
    }

    public void setAgeCodeSet(AgeCodeSet ageCodeSet) {
        this.ageCodeSet = ageCodeSet;
    }

    public SearchSetting ageCodeSet(AgeCodeSet ageCodeSet) {
        this.setAgeCodeSet(ageCodeSet);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SearchSetting)) {
            return false;
        }
        return id != null && id.equals(((SearchSetting) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SearchSetting{" +
            "id=" + getId() +
            ", searchTerm='" + getSearchTerm() + "'" +
            ", role='" + getRole() + "'" +
            ", age='" + getAge() + "'" +
            ", fieldOfStudy='" + getFieldOfStudy() + "'" +
            "}";
    }
}
