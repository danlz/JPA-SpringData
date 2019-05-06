package pl.danlz.trainings.jpaspringdata.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * An {@code @Embeddable} object can be nested within other entities.
 */
@Embeddable
public class DiagnosticObjectId implements Serializable {

    private Integer id;
    private Integer branch;
    private Integer version;


    /**
     * Default constructor is needed by Hibernate.
     */
    private DiagnosticObjectId() {
    }

    public DiagnosticObjectId(Integer id, Integer branch, Integer version) {
        this.id = id;
        this.branch = branch;
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBranch() {
        return branch;
    }

    public Integer getVersion() {
        return version;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiagnosticObjectId that = (DiagnosticObjectId) o;
        return id.equals(that.id) &&
                branch.equals(that.branch) &&
                version.equals(that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branch, version);
    }


    @Override
    public String toString() {
        return "DiagnosticObjectId{" +
                "id=" + id +
                ", branch=" + branch +
                ", version=" + version +
                '}';
    }
}
