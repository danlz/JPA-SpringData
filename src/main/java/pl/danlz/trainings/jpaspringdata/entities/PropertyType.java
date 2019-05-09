package pl.danlz.trainings.jpaspringdata.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PropertyType {

    @Id
    @Column(name = "property_type_name", updatable = false)
    private String name;

    private boolean calculated;

    private String example;


    /**
     * This constructor has to be protected, because of Hibernate bug?
     */
    protected PropertyType() {
    }

    public PropertyType(String name) {
        this.name = name;
    }

    public String getId() {
        return name;
    }

    public String getName() {
        return name;
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
                "name='" + name + '\'' +
                ", calculated=" + calculated +
                ", example='" + example + '\'' +
                '}';
    }
}
