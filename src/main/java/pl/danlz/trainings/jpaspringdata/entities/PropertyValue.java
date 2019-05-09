package pl.danlz.trainings.jpaspringdata.entities;

import javax.persistence.*;

@Entity
public class PropertyValue {

    /**
     * In this case {@code @AttributeOverrides} are not needed, because the {@code MapsId} annotations tell how the id is built.
     */
    @EmbeddedId
//    @AttributeOverrides({
//            @AttributeOverride(name = "diagnosticObjectId.id", column = @Column(name = "object_id")),
//            @AttributeOverride(name = "diagnosticObjectId.branch", column = @Column(name = "object_branch")),
//            @AttributeOverride(name = "diagnosticObjectId.version", column = @Column(name = "object_version")),
//            @AttributeOverride(name = "propertyTypeId", column = @Column(name = "property_type_id")),
//    })
    private PropertyValueId id = new PropertyValueId();

    @Column(name = "property_value")
    private String value;

    /**
     * This is a bidirectional association. See {@code DiagnosticObject.propertyValues}.
     *
     * The {@code @MapsId} annotation tells, that the id of the {@code DiagnosticObject} entity is part of this entity id.
     * In this case it is the {@code PropertyValueId.diagnosticObjectId} property.
     */
    @ManyToOne
    @JoinColumns({
            /**
             * Always specify the {@code referencedColumnName},
             * so that Hibernate knows which column should be joined with which column.
             */
            @JoinColumn(name = "object_id", referencedColumnName = "object_id"),
            @JoinColumn(name = "object_branch", referencedColumnName = "object_branch"),
            @JoinColumn(name = "object_version", referencedColumnName = "object_version")
    })
    @MapsId("diagnosticObjectId")
    private DiagnosticObject diagnosticObject;

    /**
     * This is a unidirectional association. {@code PropertyType} does not know its values.
     *
     * The {@code @MapsId} annotation tells, that the id of the {@code PropertyType} entity is part of this entity id.
     * In this case it is the {@code PropertyValueId.propertyTypeId} property.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_type_id", referencedColumnName = "property_type_name")
    @MapsId("propertyTypeId")
    private PropertyType propertyType;


    private PropertyValue() {
    }

    public PropertyValue(DiagnosticObject diagnosticObject, PropertyType propertyType) {
        this.diagnosticObject = diagnosticObject;
        this.propertyType = propertyType;
    }


    public PropertyValueId getId() {
        return id;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "PropertyValue{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
