package fi.netum.csc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fi.netum.csc.domain.UserResultKeyword} entity.
 */
public class UserResultKeywordDTO implements Serializable {

    private Long id;

    private String resultKeyword;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResultKeyword() {
        return resultKeyword;
    }

    public void setResultKeyword(String resultKeyword) {
        this.resultKeyword = resultKeyword;
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
        if (!(o instanceof UserResultKeywordDTO)) {
            return false;
        }

        UserResultKeywordDTO userResultKeywordDTO = (UserResultKeywordDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userResultKeywordDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserResultKeywordDTO{" +
            "id=" + getId() +
            ", resultKeyword='" + getResultKeyword() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
