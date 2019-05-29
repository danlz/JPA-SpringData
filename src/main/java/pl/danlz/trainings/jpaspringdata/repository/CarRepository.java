package pl.danlz.trainings.jpaspringdata.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.danlz.trainings.jpaspringdata.dto.CarSearchResultDTO;
import pl.danlz.trainings.jpaspringdata.dto.SimpleCarDTO;
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

    /**
     * Finds simple cars with model code containing specified string.
     * <b>The parameter names in the {@link SimpleCarDTO#SimpleCarDTO(Integer, String)} constructor must match the field names in the {@link Car} entity!</b>
     * The SQL query contains only the columns needed to fill the {@link SimpleCarDTO}.
     * You cannot use {@code @Query} annotation in this case, because Spring Data builds the query.
     *
     * @param modelCodePart partial string
     * @return simple car DTO
     */
    List<SimpleCarDTO> findSimpleCarsByModelCodeContaining(String modelCodePart);

    /**
     * Finds cars with model code containing specified string.
     * The result objects contain the following data:
     * <ul>
     * <li>id of a car</li>
     * <li>VIN of a car</li>
     * <li>number of control units associated with a car</li>
     * </ul>
     * <b>You have to call the constructor in JPQL query!</b>
     *
     * @param modelCodePart partial string
     * @return search result DTO
     */
    @Query(value = "select new pl.danlz.trainings.jpaspringdata.dto.CarSearchResultDTO(c.id, c.vin, count(cu)) " +
                   "from Car c join c.controlUnits cu " +
                   "where c.modelCode like %:modelCodePart% " +
                   "group by c.id")
    List<CarSearchResultDTO> searchCars(String modelCodePart);

    /**
     * Finds cars, which have control units with serial number starting with specified prefix.
     * The result is sorted by VIN in descending order.
     *
     * @param serialNumberPrefix serial number prefix
     * @return cars
     */
    List<Car> findDistinctByControlUnitsSerialNumberStartingWithOrderByVinDesc(String serialNumberPrefix);

    /**
     * Example of a JPQL query, which gets translated into invalid SQL query.
     */
    @Query(value = "select new pl.danlz.trainings.jpaspringdata.dto.CarSearchResultDTO(c.id, c.vin, count(c.controlUnits)) " +
            "from Car c " +
            "where c.modelCode like %:modelCodePart% " +
            "group by c.id")
    List<CarSearchResultDTO> invalidQuery(String modelCodePart);
}
