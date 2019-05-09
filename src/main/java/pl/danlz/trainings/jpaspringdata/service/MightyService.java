package pl.danlz.trainings.jpaspringdata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.danlz.trainings.jpaspringdata.entities.*;
import pl.danlz.trainings.jpaspringdata.repository.*;

import javax.transaction.Transactional;
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

    public void doStuff() {
        LOG.info("Entering the 'doStuff()' method");

        //printObjects();

        //createDiagnosticObject();

        //handlePropertyTypes();

        //handlePropertyValues();

        handleUsersAndRoles();

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
