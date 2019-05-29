package pl.danlz.trainings.jpaspringdata.repository;

import org.springframework.data.repository.CrudRepository;
import pl.danlz.trainings.jpaspringdata.entities.DiagnosticObject;
import pl.danlz.trainings.jpaspringdata.entities.DiagnosticObjectId;

import java.util.List;

public interface DiagnosticObjectRepository extends CrudRepository<DiagnosticObject, DiagnosticObjectId> {

    List<DiagnosticObject> findByTechnicalNameContaining(String technicalNamePart);
}
