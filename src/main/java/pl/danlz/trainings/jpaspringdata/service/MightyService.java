package pl.danlz.trainings.jpaspringdata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.danlz.trainings.jpaspringdata.entities.*;
import pl.danlz.trainings.jpaspringdata.repository.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MightyService {

    private static final Logger LOG = LoggerFactory.getLogger(MightyService.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ControlUnitRepository controlUnitRepository;

    @Autowired
    private DiagnosticObjectRepository diagnosticObjectRepository;

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ControlUnitService controlUnitService;

    public void doStuff() {
        LOG.info("Entering the 'doStuff()' method");

        //printObjects();

        //createDiagnosticObject();

        //handlePropertyTypes();

        //handlePropertyValues();

        //handleUsersAndRoles();

        LOG.info("Leaving the 'doStuff()' method");
    }

    /**
     * The {@code @Transactional} annotation tells that the method will be executed in a transaction.
     * The transaction starts before the method is executed and ends after the method returns.
     */
    @Transactional
    public Car getCar(Integer id) {
        LOG.info("Entering the 'getCar()' method");

        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("car not found - id=" + id));

        LOG.info("Leaving the 'getCar()' method");

        return car;
    }

    /**
     * Entity manager tracks if entities have been changed and updates the database automatically at the end of transaction.
     *
     * @param carId car id
     * @return updated car
     */
    @Transactional
    public Car endProduction(Integer carId) {
        LOG.info("Entering the 'endProduction()' method");

        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("car not found - id=" + carId));
        car.setProductionDate(LocalDateTime.now());

        LOG.info("Leaving the 'endProduction()' method");

        return car;
    }

    /**
     * In general the state of entities is synchronized with the database at the end of transaction,
     * even if the {@code save()} method of a repository is called multiple times.
     *
     * @param carId car id
     * @return updated car
     */
    @Transactional
    public Car updateMultipleAttributes(Integer carId) {
        LOG.info("Entering the 'updateAllAttributes()' method");

        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("car not found - id=" + carId));

        car.setProductionDate(LocalDateTime.now());
        carRepository.save(car);
        LOG.info("New production date saved");

        car.setModelCode(car.getModelCode() + "*");
        carRepository.save(car);
        LOG.info("New model code saved");

        LOG.info("Leaving the 'updateAllAttributes()' method");

        return car;
    }

    /**
     * Transaction assures that two operations are executed as whole or not at all.
     *
     * @return new car with a new control unit
     */
    @Transactional
    public Car createCarWithControlUnit() {
        LOG.info("Entering the 'createCarWithControlUnit()' method");

        Car newCar = new Car();
        newCar.setVin("WAUZZZ3Z00018888");
        newCar.setModelCode("A1");

        /*
         * You have to save the car before saving the control unit,
         * because the 'control_unit' table has a foreign to the 'car' table.
         */
        carRepository.save(newCar);

        controlUnitService.createControlUnit("ECU", "1S3.255.271.K", newCar);
        /*
         * Uncomment this line to see rollback behaviour.
         */
        //controlUnitService.createControlUnit("ECU", "to_short", newCar);

        LOG.info("Leaving the 'createCarWithControlUnit()' method");

        return newCar;
    }

    @Transactional
    public DiagnosticObject createDiagnosticObjectWithProperty() {
        PropertyType newPropertyType = new PropertyType("new.property.type");

        propertyTypeRepository.save(newPropertyType);

        DiagnosticObject newDiagnosticObject = new DiagnosticObject(new DiagnosticObjectId(100, 1,1));
        newDiagnosticObject.setTechnicalName("NEW_Battery-Voltage-MV");
        newDiagnosticObject.setStatus(DiagnosticObject.Status.NEW);
        newDiagnosticObject.addPropertyValue(newPropertyType, "12.5");

        diagnosticObjectRepository.save(newDiagnosticObject);

        return newDiagnosticObject;
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

    private void handleUsersAndRoles() {
        Role newRole = new Role();
        newRole.setName("new_role");

        roleRepository.save(newRole);

        User adminUser = userRepository.findById(1).get();
        adminUser.addRole(newRole);

        /*
         * Save the user, because it controls the relation.
         */
        userRepository.save(adminUser);

        LOG.info("ADMIN USER: {}", adminUser);
        LOG.info("ADMIN USER ROLES: {}", adminUser.getRoles());

        Role standardRole = roleRepository.findById(3).get();

        LOG.info("STANDARD ROLE: {}", standardRole);
        LOG.info("USERS WITH STANDARD ROLE: {}", standardRole.getUsers());
    }

}
