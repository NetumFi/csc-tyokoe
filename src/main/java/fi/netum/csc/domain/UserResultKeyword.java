package fi.netum.csc.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A UserResultKeyword.
 */
@Entity
@Table(name = "user_result_keyword")
public class UserResultKeyword implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "result_keyword")
    private String resultKeyword;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserResultKeyword id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResultKeyword() {
        return this.resultKeyword;
    }

    public UserResultKeyword resultKeyword(String resultKeyword) {
        this.setResultKeyword(resultKeyword);
        return this;
    }

    public void setResultKeyword(String resultKeyword) {
        this.resultKeyword = resultKeyword;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserResultKeyword user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserResultKeyword)) {
            return false;
        }
        return id != null && id.equals(((UserResultKeyword) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserResultKeyword{" +
            "id=" + getId() +
            ", resultKeyword='" + getResultKeyword() + "'" +
            "}";
    }
}
