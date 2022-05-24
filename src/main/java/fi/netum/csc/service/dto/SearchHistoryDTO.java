package fi.netum.csc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fi.netum.csc.domain.SearchHistory} entity.
 */
public class SearchHistoryDTO implements Serializable, UserRelatable {

    private Long id;

    private String searchTerm;

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
        if (!(o instanceof SearchHistoryDTO)) {
            return false;
        }

        SearchHistoryDTO searchHistoryDTO = (SearchHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, searchHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SearchHistoryDTO{" +
            "id=" + getId() +
            ", searchTerm='" + getSearchTerm() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
