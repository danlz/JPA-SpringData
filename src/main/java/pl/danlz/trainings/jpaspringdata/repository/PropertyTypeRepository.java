package pl.danlz.trainings.jpaspringdata.repository;

import org.springframework.data.repository.CrudRepository;
import pl.danlz.trainings.jpaspringdata.entities.PropertyType;
import pl.danlz.trainings.jpaspringdata.entities.PropertyTypeId;

public interface PropertyTypeRepository extends CrudRepository<PropertyType, PropertyTypeId> {
}
