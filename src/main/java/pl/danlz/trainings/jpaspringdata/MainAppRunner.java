package pl.danlz.trainings.jpaspringdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.danlz.trainings.jpaspringdata.entities.*;
import pl.danlz.trainings.jpaspringdata.repository.CarRepository;
import pl.danlz.trainings.jpaspringdata.repository.ControlUnitRepository;
import pl.danlz.trainings.jpaspringdata.repository.DiagnosticObjectRepository;
import pl.danlz.trainings.jpaspringdata.repository.PropertyTypeRepository;

import java.util.Optional;

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

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    @Override
    public void run(ApplicationArguments args) {
        printObjects();

        createDiagnosticObject();

        handlePropertyTypes();

        handlePropertyValues();
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

    private void handlePropertyTypes() {
        PropertyType propertyType = propertyTypeRepository.findById("memory.selection").get();

        LOG.info("PROPERTY TYPE: {}", propertyType);

        PropertyType newPropertyType = new PropertyType("some.type.name");

        propertyTypeRepository.save(newPropertyType);

        LOG.info("NEW PROPERTY TYPE: {}", newPropertyType);
    }


    private void handlePropertyValues() {
        DiagnosticObject diagnosticObject = diagnosticObjectRepository.findById(new DiagnosticObjectId(1, 1, 1)).get();
        PropertyType propertyType = propertyTypeRepository.findById("memory.selection").get();

        diagnosticObject.addPropertyValue(propertyType, "0x1A00");

        Optional<PropertyValue> propertyValueToBeRemoved = diagnosticObject.getPropertyValues().stream().filter(pv -> "coefficient.a0".equals(pv.getPropertyType().getName())).findFirst();
        propertyValueToBeRemoved.ifPresent(pv -> diagnosticObject.removePropertyValue(pv));

        /*
         * Note that we are saving only the diagnostic object - property values are saved through cascade.
         */
        diagnosticObjectRepository.save(diagnosticObject);

        LOG.info("PROPERTY VALUES: {}", diagnosticObject.getPropertyValues());
    }

}
