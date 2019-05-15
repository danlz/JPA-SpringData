package pl.danlz.trainings.jpaspringdata.dto;


public class CarSearchResultDTO {

    private Integer id;

    private String vin;

    private Long numberOfControlUnits;

    public CarSearchResultDTO(Integer id, String vin, Long numberOfControlUnits) {
        this.id = id;
        this.vin = vin;
        this.numberOfControlUnits = numberOfControlUnits;
    }


    public Integer getId() {
        return id;
    }

    public String getVin() {
        return vin;
    }

    public Long getNumberOfControlUnits() {
        return numberOfControlUnits;
    }


    @Override
    public String toString() {
        return "CarSearchResultDTO{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                ", numberOfControlUnits=" + numberOfControlUnits +
                '}';
    }
}
