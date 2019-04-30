package pl.danlz.trainings.jpaspringdata.repository;

import org.springframework.data.repository.CrudRepository;
import pl.danlz.trainings.jpaspringdata.entities.Car;

public interface CarRepository extends CrudRepository<Car, Integer> {
}
