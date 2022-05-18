package fi.netum.csc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fi.netum.csc.domain.SearchSetting} entity.
 */
public class SearchSettingDTO implements Serializable {

    private Long id;

    private String searchTerm;

    private String educationLevel;

    private String role;

    private String age;

    private UserDTO user;

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

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
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
            ", educationLevel='" + getEducationLevel() + "'" +
            ", role='" + getRole() + "'" +
            ", age='" + getAge() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
