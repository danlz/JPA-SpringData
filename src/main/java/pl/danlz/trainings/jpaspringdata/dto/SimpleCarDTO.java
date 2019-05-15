package pl.danlz.trainings.jpaspringdata.dto;

public class SimpleCarDTO {

    private Integer id;

    private String vin;

    public SimpleCarDTO(Integer id, String vin) {
        this.id = id;
        this.vin = vin;
    }


    public Integer getId() {
        return id;
    }

    public String getVin() {
        return vin;
    }


    @Override
    public String toString() {
        return "SimpleCarDTO{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                '}';
    }
}
