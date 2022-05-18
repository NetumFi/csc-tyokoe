package fi.netum.csc.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fi.netum.csc.domain.AgeCodeSet} entity.
 */
public class AgeCodeSetDTO implements Serializable {

    private Long id;

    private String code;

    private String codeId;

    private String labelEn;

    private String labelFi;

    private String labelSv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getLabelEn() {
        return labelEn;
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn;
    }

    public String getLabelFi() {
        return labelFi;
    }

    public void setLabelFi(String labelFi) {
        this.labelFi = labelFi;
    }

    public String getLabelSv() {
        return labelSv;
    }

    public void setLabelSv(String labelSv) {
        this.labelSv = labelSv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgeCodeSetDTO)) {
            return false;
        }

        AgeCodeSetDTO ageCodeSetDTO = (AgeCodeSetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ageCodeSetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgeCodeSetDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", codeId='" + getCodeId() + "'" +
            ", labelEn='" + getLabelEn() + "'" +
            ", labelFi='" + getLabelFi() + "'" +
            ", labelSv='" + getLabelSv() + "'" +
            "}";
    }
}
