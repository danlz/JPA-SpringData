package pl.danlz.trainings.jpaspringdata.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "find_by_modelCode_containing",
                /**
                 * You cannot use the % sign directly in a named query, that't why we use the JPQL CONCAT function.
                */
                query = "select c from Car c where c.modelCode like CONCAT('%', :modelCodePart, '%')"
        )
})
@NamedNativeQuery(name = "find_by_modelCode_containing_native",
        /**
         * You cannot use the % sign directly in a named query, that't why we use the SQL CONCAT function.
        */
        query = "SELECT * FROM car where model_code like CONCAT('%', :modelCodePart, '%')",
        /**
         * You have to specify to which class map the result.
        */
        resultClass = Car.class
)
@Entity
@Table(name = "car")
public class Car {

    /**
     * Id is generated in the DB. That's why there is no setter in the class.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * {@code vin} is mapped implicitly.
     */
    private String vin;

    /**
     * Explicit mapping for {@code modelCode}, because property name does not match the column name in the DB.
     */
    @Column(name = "model_code")
    private String modelCode;

    /**
     * Annotations can also be placed on getters.
     */
    @Column(name = "production_date")
    private LocalDateTime productionDate;

    /**
     * The {@code mappedBy} property tells, which property of the {@code ControlUnit} entity owns the relation.
     * This means: which {@code ControlUnit}s belong to this {@code Car}.
     */
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<ControlUnit> controlUnits = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }

    public List<ControlUnit> getControlUnits() {
        return Collections.unmodifiableList(controlUnits);
    }

    public void addControlUnit(ControlUnit controlUnit) {
        controlUnits.add(controlUnit);
        controlUnit.setCar(this);
    }

    public void removeControlUnit(ControlUnit controlUnit) {
        controlUnits.remove(controlUnit);
        controlUnit.setCar(null);
    }


    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                ", modelCode='" + modelCode + '\'' +
                ", productionDate=" + productionDate +
                '}';
    }
}
