package fi.netum.csc.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Age.
 */
@Entity
@Table(name = "age")
public class Age implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "code_id")
    private String codeId;

    @Column(name = "label_en")
    private String labelEn;

    @Column(name = "label_fi")
    private String labelFi;

    @Column(name = "label_sv")
    private String labelSv;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Age id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Age code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeId() {
        return this.codeId;
    }

    public Age codeId(String codeId) {
        this.setCodeId(codeId);
        return this;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getLabelEn() {
        return this.labelEn;
    }

    public Age labelEn(String labelEn) {
        this.setLabelEn(labelEn);
        return this;
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn;
    }

    public String getLabelFi() {
        return this.labelFi;
    }

    public Age labelFi(String labelFi) {
        this.setLabelFi(labelFi);
        return this;
    }

    public void setLabelFi(String labelFi) {
        this.labelFi = labelFi;
    }

    public String getLabelSv() {
        return this.labelSv;
    }

    public Age labelSv(String labelSv) {
        this.setLabelSv(labelSv);
        return this;
    }

    public void setLabelSv(String labelSv) {
        this.labelSv = labelSv;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Age)) {
            return false;
        }
        return id != null && id.equals(((Age) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Age{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", codeId='" + getCodeId() + "'" +
            ", labelEn='" + getLabelEn() + "'" +
            ", labelFi='" + getLabelFi() + "'" +
            ", labelSv='" + getLabelSv() + "'" +
            "}";
    }
}
