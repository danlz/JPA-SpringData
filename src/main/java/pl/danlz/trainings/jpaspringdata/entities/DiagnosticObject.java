package pl.danlz.trainings.jpaspringdata.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "diag_object")
public class DiagnosticObject {

    public enum Status {
        NEW,
        IN_PROGRESS,
        VALIDATED,
        ACCEPTED
    }

    /**
     * This entity has an id, which consists of multiple attributes.
     * The property names from {@code DiagnosticObjectId} class do not match the column names in the {@code diag_object} table,
     * so we have to override the attribute definitions with the {@code @AttributeOverride} annotations.
     * This id has to be manually set.
     */
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "object_id", nullable = false)),
            @AttributeOverride(name = "branch", column = @Column(name = "object_branch", nullable = false)),
            @AttributeOverride(name = "version", column = @Column(name = "object_version", nullable = false)),
    })
    private DiagnosticObjectId id;

    private String technicalName;

    private String description;

    /**
     * Store the enum constants as strings in the DB.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "object_status", nullable = false)
    private Status status;

    /**
     * The {@code java.util.Date} can store date and time,
     * but the SQL types can store only date or only time or both.
     * In this case we have only date in the DB.
     */
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date created;

    /**
     * This property is not mapped to the DB.
     */
    @Transient
    private int randomNumber = new Random().nextInt(1000);

    /**
     * We cascade the merge operation on {@code DiagnosticObject} to its {@code PropertyValue}s,
     * ie. if {@code DiagnosticObject} is updated, its {@code PropertyValue}s also are updated.
     * We also remove the orphaned {@code PropertyValue}s,
     * ie. if you remove a {@code PropertyValue} from the {@code propertValues} collection,
     * the row corresponding to the given {@code PropertyValue} object will be removed from database.
     * You have to cascade the persist operation for {@code orphanRemoval} to work,
     * because of this bug: <a href=https://hibernate.atlassian.net/browse/HHH-9571>HHH-9571</a>
     */
    @OneToMany(mappedBy = "diagnosticObject", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PropertyValue> propertyValues = new ArrayList<>();


    /**
     * Default constructor is needed by Hibernate.
     */
    private DiagnosticObject() {
    }

    public DiagnosticObject(DiagnosticObjectId id) {
        this.id = id;
        this.created = new Date();
    }

    public DiagnosticObjectId getId() {
        return id;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public List<PropertyValue> getPropertyValues() {
        return Collections.unmodifiableList(propertyValues);
    }

    public void addPropertyValue(PropertyType type, String value) {
        PropertyValue valueObject = new PropertyValue(this, type);
        valueObject.setValue(value);
        propertyValues.add(valueObject);
    }

    public void removePropertyValue(PropertyValue value) {
        propertyValues.remove(value);
    }


    @Override
    public String toString() {
        return "DiagnosticObject{" +
                "id=" + id +
                ", technicalName='" + technicalName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", created=" + created +
                ", randomNumber=" + randomNumber +
                '}';
    }
}
