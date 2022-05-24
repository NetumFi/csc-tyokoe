package fi.netum.csc.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link fi.netum.csc.domain.ReadingList} entity.
 */
public class ReadingListDTO implements Serializable, UserRelatable {

    private Long id;

    private String materialId;

    private Instant created;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
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
        if (!(o instanceof ReadingListDTO)) {
            return false;
        }

        ReadingListDTO readingListDTO = (ReadingListDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, readingListDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReadingListDTO{" +
            "id=" + getId() +
            ", materialId='" + getMaterialId() + "'" +
            ", created='" + getCreated() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
