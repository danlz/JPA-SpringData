package pl.danlz.trainings.jpaspringdata.entities;

import javax.persistence.*;

@Entity
public class ControlUnit {

    /**
     * Id is generated in the DB. That's why there is no setter in the class.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_code")
    private String typeCode;

    @Column(name = "serial_number")
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "fk_car")
    private Car car;


    public Integer getId() {
        return id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Car getCar() {
        return car;
    }

    /**
     * Only the {@code Car} class should call this setter.
     *
     * @param car car
     */
    void setCar(Car car) {
        this.car = car;
    }


    @Override
    public String toString() {
        return "ControlUnit{" +
                "id=" + id +
                ", typeCode='" + typeCode + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
