package pl.danlz.trainings.jpaspringdata.repository;

import org.springframework.data.repository.CrudRepository;
import pl.danlz.trainings.jpaspringdata.entities.DiagnosticObject;
import pl.danlz.trainings.jpaspringdata.entities.DiagnosticObjectId;

public interface DiagnosticObjectRepository extends CrudRepository<DiagnosticObject, DiagnosticObjectId> {
}
