package pl.danlz.trainings.jpaspringdata.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.danlz.trainings.jpaspringdata.entities.Car;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Integer> {

    /**
     * Finds cars with exact model code.
     *
     * @param modelCode modelCode
     * @return cars
     */
    List<Car> findByModelCode(String modelCode);

    /**
     * Finds cars with model code containing specified string.
     *
     * @param modelCodePart partial string
     * @return cars
     */
    List<Car> findByModelCodeContaining(String modelCodePart);

    /**
     * Finds cars that have control units with specified type code.
     *
     * @param typeCode type code
     * @return cars
     */
    List<Car> findByControlUnitsTypeCode(String typeCode);

    /**
     * Finds cars that have control units with specified type code.
     *
     * @param typeCode type code
     * @param sort     sorting option
     * @return cars
     */
    List<Car> findByControlUnitsTypeCode(String typeCode, Sort sort);

    /**
     * Finds 3 cars with VIN containing specified string.
     * The result is sorted by model code in ascending order.
     *
     * @param vinPart partial VIN
     * @return cars
     */
    List<Car> findTop3ByVinContainingOrderByModelCodeAsc(String vinPart);

    /**
     * Finds cars with model code containing specified string.
     * The query is defined in JPQL.
     *
     * @param modelCodePart partial string
     * @return cars
     */
    @Query("select c from Car c where c.modelCode like %:modelCodePart%")
    //@Query(name = "find_by_modelCode_containing")
    List<Car> findByModelCodeContainingCustomQuery(@Param("modelCodePart") String modelCodePart);

    /**
     * Finds cars with model code containing specified string.
     * The query is defined in SQL.
     *
     * @param modelCodePart partial string
     * @return cars
     */
    @Query(value = "SELECT * FROM car where model_code like %:modelCodePart%", nativeQuery = true)
    //@Query(name = "find_by_modelCode_containing_native")
    List<Car> findByModelCodeContainingNativeQuery(@Param("modelCodePart") String modelCodePart);
}
