package pl.danlz.trainings.jpaspringdata.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PropertyTypeId implements Serializable {

    private String name;
    private Integer version;


    private PropertyTypeId() {
    }

    public PropertyTypeId(String name, Integer version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public Integer getVersion() {
        return version;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyTypeId that = (PropertyTypeId) o;
        return name.equals(that.name) &&
                version.equals(that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }


    @Override
    public String toString() {
        return "PropertyTypeId{" +
                "name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
