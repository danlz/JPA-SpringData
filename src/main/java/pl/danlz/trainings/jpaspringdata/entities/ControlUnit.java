package pl.danlz.trainings.jpaspringdata.entities;

public class ControlUnit {

    /**
     * Id is generated in the DB. That's why there is no setter in the class.
     */
    private Integer id;

    private String typeCode;

    private String serialNumber;

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
