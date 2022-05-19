package fi.netum.csc.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A ReadingList.
 */
@Entity
@Table(name = "reading_list")
public class ReadingList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "material_id")
    private String materialId;

    @Column(name = "created")
    private Instant created;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReadingList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialId() {
        return this.materialId;
    }

    public ReadingList materialId(String materialId) {
        this.setMaterialId(materialId);
        return this;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public Instant getCreated() {
        return this.created;
    }

    public ReadingList created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReadingList user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReadingList)) {
            return false;
        }
        return id != null && id.equals(((ReadingList) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReadingList{" +
            "id=" + getId() +
            ", materialId='" + getMaterialId() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
