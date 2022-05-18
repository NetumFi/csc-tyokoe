package fi.netum.csc.domain;

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

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "role")
    private String role;

    @Column(name = "age")
    private String age;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

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

    public String getEducationLevel() {
        return this.educationLevel;
    }

    public SearchSetting educationLevel(String educationLevel) {
        this.setEducationLevel(educationLevel);
        return this;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
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
            ", educationLevel='" + getEducationLevel() + "'" +
            ", role='" + getRole() + "'" +
            ", age='" + getAge() + "'" +
            "}";
    }
}
