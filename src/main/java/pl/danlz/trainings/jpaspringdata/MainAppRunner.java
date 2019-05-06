package pl.danlz.trainings.jpaspringdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.danlz.trainings.jpaspringdata.entities.Car;
import pl.danlz.trainings.jpaspringdata.entities.ControlUnit;
import pl.danlz.trainings.jpaspringdata.entities.DiagnosticObject;
import pl.danlz.trainings.jpaspringdata.entities.DiagnosticObjectId;
import pl.danlz.trainings.jpaspringdata.repository.CarRepository;
import pl.danlz.trainings.jpaspringdata.repository.ControlUnitRepository;
import pl.danlz.trainings.jpaspringdata.repository.DiagnosticObjectRepository;

import javax.transaction.Transactional;

/**
 * Entry point of the application.
 */
@Component
public class MainAppRunner implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MainAppRunner.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ControlUnitRepository controlUnitRepository;

    @Autowired
    private DiagnosticObjectRepository diagnosticObjectRepository;

    @Override
    public void run(ApplicationArguments args) {
        printObjects();

        createDiagnosticObject();
    }

    private void printObjects() {
        Car car = carRepository.findById(1).get();

        LOG.info("CAR: {}", car);
        LOG.info("CAR --> CONTROL_UNITS: {}", car.getControlUnits());


        ControlUnit controlUnit = controlUnitRepository.findById(11).get();

        LOG.info("CONTROL UNIT: {}", controlUnit);
        LOG.info("CONTROL UNIT --> CAR: {}", controlUnit.getCar());
    }

    private void createDiagnosticObject() {
        DiagnosticObject diagnosticObject = new DiagnosticObject(new DiagnosticObjectId(50, 1,1));
        diagnosticObject.setTechnicalName("Coolant-Temp-MV");
        diagnosticObject.setStatus(DiagnosticObject.Status.NEW);

        diagnosticObjectRepository.save(diagnosticObject);

        Iterable<DiagnosticObject> diagnosticObjects = diagnosticObjectRepository.findAll();

        LOG.info("DIAGNOSTIC OBJECTS: {}", diagnosticObjects);
    }

}
