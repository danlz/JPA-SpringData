package pl.danlz.trainings.jpaspringdata.repository;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.danlz.trainings.jpaspringdata.entities.DiagnosticObject;
import pl.danlz.trainings.jpaspringdata.entities.DiagnosticObjectId;
import pl.danlz.trainings.jpaspringdata.entities.PropertyType;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * This test uses an embedded in memory H2 database.
 * <p>
 * Spring Boot will create a new database instance for this test.
 * The database schema will be created according to mappings defined in JPA annotations - see the {@code spring.jpa.hibernate.ddl-auto} property.
 * <b>This may not match the schema you have in the real database!</b>
 * </p>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class DiagnosticObjectRepositoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(DiagnosticObjectRepositoryTest.class);

    @Autowired
    private DiagnosticObjectRepository diagnosticObjectRepository;

    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    @Autowired
    private EntityManager entityManager;


    @Test
    @Sql("diagnosticObjects.sql")
    public void findByTechnicalNameContaining() {
        LOG.info("Entering findByTechnicalNameContaining()");

        List<DiagnosticObject> diagObjects = diagnosticObjectRepository.findByTechnicalNameContaining("Speed");

        Assert.assertThat(diagObjects, Matchers.contains(
                Matchers.allOf(
                        Matchers.hasProperty("technicalName", Matchers.equalTo("Speed-MV")),
                        Matchers.hasProperty("id", Matchers.hasProperty("id", Matchers.equalTo(3))),
                        Matchers.hasProperty("id", Matchers.hasProperty("branch", Matchers.equalTo(1))),
                        Matchers.hasProperty("id", Matchers.hasProperty("version", Matchers.equalTo(1)))
                )
        ));

        LOG.info("Leaving findByTechnicalNameContaining()");
    }

    @Test
    @Sql("propertyTypes.sql")
    public void saveNewDiagnosticObjectWithNewPropertyValue() {
        LOG.info("Entering saveNewDiagnosticObjectWithNewPropertyValue()");

        PropertyType propertyType = propertyTypeRepository.findById("trouble.code").get();

        DiagnosticObjectId newId = new DiagnosticObjectId(100, 1, 1);
        DiagnosticObject newDiagnosticObject = new DiagnosticObject(newId);
        newDiagnosticObject.setTechnicalName("TurboBoostOutOfRange-TC");
        newDiagnosticObject.setStatus(DiagnosticObject.Status.NEW);
        newDiagnosticObject.addPropertyValue(propertyType, "P1106");

        diagnosticObjectRepository.save(newDiagnosticObject);

        /*
         * Without flush() no inserts are executed in the database.
         */
        entityManager.flush();
        /*
         * To force a select query for the {@code diagnosticObjectRepository.findById()} call,
         * you can clear the EntityManager's cache.
         */
        //entityManager.clear();

        DiagnosticObject diagObject = diagnosticObjectRepository.findById(newId).get();

        Assert.assertEquals("TurboBoostOutOfRange-TC", diagObject.getTechnicalName());
        Assert.assertEquals(DiagnosticObject.Status.NEW, diagObject.getStatus());
        Assert.assertThat(diagObject.getPropertyValues(), Matchers.contains(
                Matchers.allOf(
                        Matchers.hasProperty("value", Matchers.equalTo("P1106")),
                        Matchers.hasProperty("propertyType", Matchers.sameInstance(propertyType))
                )
        ));

        LOG.info("Leaving saveNewDiagnosticObjectWithNewPropertyValue()");
    }

}
