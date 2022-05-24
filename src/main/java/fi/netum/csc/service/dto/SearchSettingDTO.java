package fi.netum.csc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fi.netum.csc.domain.SearchSetting} entity.
 */
public class SearchSettingDTO implements Serializable, UserRelatable {

    private Long id;

    private String searchTerm;

    private String role;

    private String age;

    private UserDTO user;

    private EducationLevelCodeSetDTO educationLevelCodeSet;

    private AgeCodeSetDTO ageCodeSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public EducationLevelCodeSetDTO getEducationLevelCodeSet() {
        return educationLevelCodeSet;
    }

    public void setEducationLevelCodeSet(EducationLevelCodeSetDTO educationLevelCodeSet) {
        this.educationLevelCodeSet = educationLevelCodeSet;
    }

    public AgeCodeSetDTO getAgeCodeSet() {
        return ageCodeSet;
    }

    public void setAgeCodeSet(AgeCodeSetDTO ageCodeSet) {
        this.ageCodeSet = ageCodeSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SearchSettingDTO)) {
            return false;
        }

        SearchSettingDTO searchSettingDTO = (SearchSettingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, searchSettingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SearchSettingDTO{" +
            "id=" + getId() +
            ", searchTerm='" + getSearchTerm() + "'" +
            ", role='" + getRole() + "'" +
            ", age='" + getAge() + "'" +
            ", user=" + getUser() +
            ", educationLevelCodeSet=" + getEducationLevelCodeSet() +
            ", ageCodeSet=" + getAgeCodeSet() +
            "}";
    }
}
