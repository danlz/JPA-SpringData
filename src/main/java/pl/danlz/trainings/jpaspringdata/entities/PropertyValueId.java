package pl.danlz.trainings.jpaspringdata.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PropertyValueId implements Serializable {

    private DiagnosticObjectId diagnosticObjectId;
    private String propertyTypeId;


    PropertyValueId() {
    }


    public DiagnosticObjectId getDiagnosticObjectId() {
        return diagnosticObjectId;
    }

    public String getPropertyTypeId() {
        return propertyTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyValueId that = (PropertyValueId) o;
        return diagnosticObjectId.equals(that.diagnosticObjectId) &&
                propertyTypeId.equals(that.propertyTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diagnosticObjectId, propertyTypeId);
    }


    /**
     * {@code toString()} is a must in an id class!
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "PropertyValueId{" +
                "diagnosticObjectId=" + diagnosticObjectId +
                ", propertyTypeId=" + propertyTypeId +
                '}';
    }
}
