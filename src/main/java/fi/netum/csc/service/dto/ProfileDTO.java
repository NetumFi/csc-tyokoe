package fi.netum.csc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fi.netum.csc.domain.Profile} entity.
 */
public class ProfileDTO implements Serializable {

    private Long id;

    private String keywords;

    private String education;

    private String language;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
        if (!(o instanceof ProfileDTO)) {
            return false;
        }

        ProfileDTO profileDTO = (ProfileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, profileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfileDTO{" +
            "id=" + getId() +
            ", keywords='" + getKeywords() + "'" +
            ", education='" + getEducation() + "'" +
            ", language='" + getLanguage() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
