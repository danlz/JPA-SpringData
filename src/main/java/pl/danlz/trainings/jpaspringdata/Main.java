package pl.danlz.trainings.jpaspringdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.danlz.trainings.jpaspringdata.entities.Car;
import pl.danlz.trainings.jpaspringdata.entities.ControlUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final String PERSISTENCE_UNIT_NAME = "jpa_training";

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = factory.createEntityManager();


        Car car = entityManager.find(Car.class, 1);

        LOG.info("CAR: {}", car);
        LOG.info("CAR --> CONTROL_UNITS: {}", car.getControlUnits());


        ControlUnit controlUnit = entityManager.find(ControlUnit.class, 11);

        LOG.info("CONTROL UNIT: {}", controlUnit);
        LOG.info("CONTROL UNIT --> CAR: {}", controlUnit.getCar());


        entityManager.close();
        factory.close();
    }
}
