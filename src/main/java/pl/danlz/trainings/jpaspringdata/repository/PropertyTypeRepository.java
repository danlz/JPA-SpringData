package pl.danlz.trainings.jpaspringdata.repository;

import org.springframework.data.repository.CrudRepository;
import pl.danlz.trainings.jpaspringdata.entities.PropertyType;

public interface PropertyTypeRepository extends CrudRepository<PropertyType, String> {
}
