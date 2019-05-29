package pl.danlz.trainings.jpaspringdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.danlz.trainings.jpaspringdata.entities.Car;
import pl.danlz.trainings.jpaspringdata.entities.DiagnosticObject;
import pl.danlz.trainings.jpaspringdata.entities.PropertyValue;
import pl.danlz.trainings.jpaspringdata.service.MightyService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 * Entry point of the application.
 */
@Component
public class MainAppRunner implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MainAppRunner.class);

    @Autowired
    private MightyService mightyService;


    @Override
    public void run(ApplicationArguments args) {
//        printQueryResult(
//                mightyService.findCars()
//        );

//        printQueryResult(mightyService.findSimpleCars("S"));
//        printQueryResult(mightyService.searchForCarsVersion1("S"));
//        printQueryResult(mightyService.searchForCarsVersion2("S"));


//        mightyService.appendSuffixToUsername("user", "***");
//
//        mightyService.deleteUsers("user");


//        mightyService.doStuff();

//        printCarData();

//        printCarAndControlUnitsData();

//        endCarProduction();

//        updateMultipleCarAttributes();

//        createCarWithControlUnit();

//        createDiagnosticObjectWithProperty();
    }

    private void printCarData() {
        Car car = mightyService.getCar(1);

        LOG.info("Printing car data...");
        LOG.info("CAR: {}", car);
    }

    private void printCarAndControlUnitsData() {
        Car car = mightyService.getCar(1);

        LOG.info("Printing car and control units data...");
        LOG.info("CAR: {}", car);
        LOG.info("CONTROL UNITS: {}", car.getControlUnits());
    }

    private void endCarProduction() {
        Car car = mightyService.endProduction(2);

        LOG.info("PRODUCED CAR: {}", car);
    }

    private void updateMultipleCarAttributes() {
        Car car = mightyService.updateMultipleAttributes(4);

        LOG.info("UPDATED CAR: {}", car);
    }

    private void createCarWithControlUnit() {
        Car car = mightyService.createCarWithControlUnit();

        LOG.info("CAR: {}", car);
        LOG.info("CONTROL UNITS: {}", car.getControlUnits());
    }

    private void createDiagnosticObjectWithProperty() {
        DiagnosticObject diagnosticObject = mightyService.createDiagnosticObjectWithProperty();

        LOG.info("DIAGNOSTIC OBJECT: {}", diagnosticObject);
        for (PropertyValue propertyValue : diagnosticObject.getPropertyValues()) {
            LOG.info("   PROPERTY VALUE: {}   PROPERTY TYPE: {}", propertyValue.getValue(), propertyValue.getPropertyType());
        }
    }

    private void printQueryResult(Object o) {
        LOG.info("QUERY RESULT:");
        if (o instanceof Iterable) {
            int count = 0;
            for (Object elem : (Iterable) o) {
                LOG.info("{}", elem);
                count++;
            }
            LOG.info("ITEMS FOUND: {}", count);
        } else {
            LOG.info("{}", o);
        }
    }


    @Autowired
    private EntityManagerFactory factory;

    /**
     * Creates and registers an {@link EntityManager} in a thread-bound variable.
     * This allows to lazily load associations after the transaction has been committed.
     */
    @PostConstruct
    private void registerEntityManager() {
        EntityManager em = factory.createEntityManager();
        EntityManagerHolder emHolder = new EntityManagerHolder(em);
        TransactionSynchronizationManager.bindResource(factory, emHolder);
    }

}
