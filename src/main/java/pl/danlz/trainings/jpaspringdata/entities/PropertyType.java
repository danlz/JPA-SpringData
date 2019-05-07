package pl.danlz.trainings.jpaspringdata.entities;

import javax.persistence.*;

@Entity
public class PropertyType {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "property_type_name", nullable = false)),
            @AttributeOverride(name = "version", column = @Column(name = "property_type_version", nullable = false))
    })
    private PropertyTypeId id;

    private boolean calculated;

    private String example;


    private PropertyType() {
    }

    public PropertyType(PropertyTypeId id) {
        this.id = id;
    }


    public PropertyTypeId getId() {
        return id;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public void setCalculated(boolean calculated) {
        this.calculated = calculated;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }


    @Override
    public String toString() {
        return "PropertyType{" +
                "id=" + id +
                ", calculated=" + calculated +
                ", example='" + example + '\'' +
                '}';
    }
}
