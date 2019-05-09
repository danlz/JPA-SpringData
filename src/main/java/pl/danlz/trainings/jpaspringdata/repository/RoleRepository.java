package pl.danlz.trainings.jpaspringdata.repository;

import org.springframework.data.repository.CrudRepository;
import pl.danlz.trainings.jpaspringdata.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
