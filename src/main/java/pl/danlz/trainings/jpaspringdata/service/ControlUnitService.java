package pl.danlz.trainings.jpaspringdata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.danlz.trainings.jpaspringdata.entities.Car;
import pl.danlz.trainings.jpaspringdata.entities.ControlUnit;
import pl.danlz.trainings.jpaspringdata.repository.ControlUnitRepository;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class ControlUnitService {

    private static final Logger LOG = LoggerFactory.getLogger(ControlUnitService.class);

    @Autowired
    private ControlUnitRepository controlUnitRepository;


    /**
     * It is possible to configure how the transactional behaviour propagates over consecutive method calls.
     * The exact behaviour depends on the underlying database capabilities, ie. nested transactions.
     *
     * @param typeCode type code
     * @param serialNumber serial number
     * @param car car to which this control unit belongs
     * @return new control unit
     */
    @Transactional
    //@Transactional(Transactional.TxType.NEVER)
    //@Transactional(Transactional.TxType.REQUIRES_NEW)
    public ControlUnit createControlUnit(String typeCode, String serialNumber, Car car) {
        LOG.info("Entering the 'createControlUnit()' method");

        Objects.requireNonNull(typeCode, "'typeCode' must not be null");
        Objects.requireNonNull(serialNumber, "'serialNumber' must not be null");
        if (serialNumber.length() != 13) {
            throw new IllegalArgumentException("'serialNumber' must be 13 characters long");
        }

        ControlUnit newControlUnit = new ControlUnit();
        newControlUnit.setTypeCode(typeCode);
        newControlUnit.setSerialNumber(serialNumber);
        car.addControlUnit(newControlUnit);

        controlUnitRepository.save(newControlUnit);

        LOG.info("Leaving the 'createControlUnit()' method");

        return newControlUnit;
    }
}
