package pl.danlz.trainings.jpaspringdata.repository;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.danlz.trainings.jpaspringdata.dto.CarSearchResultDTO;
import pl.danlz.trainings.jpaspringdata.entities.Car;

import java.util.List;

/**
 * This test uses the database configured in the application - see the {@code @AutoConfigureTestDatabase} annotation.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;


    @Test
    public void findDistinctByControlUnitsSerialNumberStartingWithOrderByVinDesc() {
        List<Car> cars = carRepository.findDistinctByControlUnitsSerialNumberStartingWithOrderByVinDesc("8P0");

        /*
         * The 'contains' matcher checks if the collections has exactly the specified items.
         */
        Assert.assertThat(cars, Matchers.contains(
                Matchers.hasProperty("vin", Matchers.equalTo("WAUZZZ8P12345999")),
                Matchers.hasProperty("vin", Matchers.equalTo("WAUZZZ8P12345678"))
        ));
    }

    @Test
    public void searchCars() {
        List<CarSearchResultDTO> cars = carRepository.searchCars("S");

        /*
         * The 'hasItems' matcher checks if the specified items are in the collection,
         * but the collection may have more items.
         */
        Assert.assertThat(cars, Matchers.hasItems(
                Matchers.allOf(
                        Matchers.hasProperty("vin", Matchers.equalTo("WAUZZZ8P12345678")),
                        Matchers.hasProperty("numberOfControlUnits", Matchers.equalTo(2L))
                ),
                Matchers.allOf(
                        Matchers.hasProperty("vin", Matchers.equalTo("WAUZZZ9X55345678")),
                        Matchers.hasProperty("numberOfControlUnits", Matchers.equalTo(3L))
                ),
                Matchers.allOf(
                        Matchers.hasProperty("vin", Matchers.equalTo("WAUZZZ8P12345999")),
                        Matchers.hasProperty("numberOfControlUnits", Matchers.equalTo(2L))
                )
        ));
        Assert.assertEquals(3, cars.size());
    }

    /**
     * This test fails.
     */
    @Test
    public void invalidQuery() {
        carRepository.invalidQuery("S");
    }
}
