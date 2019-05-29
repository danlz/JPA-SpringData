package pl.danlz.trainings.jpaspringdata.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.danlz.trainings.jpaspringdata.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsernameContaining(String username);

    /**
     * Example of a bulk update operation. The query updates multiple rows in the database.
     * <b>Unfortunately the objects in the {@code EntityManager}'s cache are not updated.</b>
     *      *
     * @param usernamePart part of username
     * @param suffix suffix to add
     * @return number of updated rows
     */
    @Modifying(flushAutomatically = false, clearAutomatically = false)
    @Query("update User u set u.username=CONCAT(u.username, :suffix) where u.username like %:usernamePart%")
    int appendSuffixToUsername(String usernamePart, String suffix);

    /**
     * This is not a bulk operation. There are multiple SELECT and DELETE statements executed.
     * <b>In this case the {@code EntityManager}'s cache is updated.</b>
     *
     * @param usernamePart part of username
     */
    void deleteByUsernameContaining(String usernamePart);
}
