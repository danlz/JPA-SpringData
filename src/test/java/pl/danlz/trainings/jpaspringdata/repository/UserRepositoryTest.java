package pl.danlz.trainings.jpaspringdata.repository;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.danlz.trainings.jpaspringdata.entities.Role;
import pl.danlz.trainings.jpaspringdata.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * This test uses the database configured in the application - see the {@code @AutoConfigureTestDatabase} annotation.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EntityManager entityManager;


    @Test
    public void loadUserRoles() {
        User user = userRepository.findById(1).get();
        List<Role> roles = user.getRoles();

        Assert.assertThat(roles, Matchers.hasItems(
                Matchers.hasProperty("name", Matchers.equalTo("verifier_role")),
                Matchers.hasProperty("name", Matchers.equalTo("admin_role")),
                Matchers.hasProperty("name", Matchers.equalTo("standard_role"))
        ));
        Assert.assertEquals(3, roles.size());
    }

    /**
     * Each method in the test class is executed in a transaction,
     * which is rolled back at the end of the method.
     * The consequence of this is, that not all cached queries are sent to the database.
     */
    @Test
    //@Commit
    public void createUserAndRole() {
        Role newRole = new Role();
        newRole.setName("new_role");

        roleRepository.save(newRole);

        User newUser = new User();
        newUser.setUsername("new_username");
        newUser.addRole(newRole);

        userRepository.save(newUser);

        /*
         * You have to flush the EntityManager, so that the data is inserted into the user_role table.
         */
        entityManager.flush();

        User user = userRepository.findByUsername("new_username");

        Assert.assertEquals("new_username", user.getUsername());
        Assert.assertThat(user.getRoles(), Matchers.contains(
                Matchers.hasProperty("name", Matchers.equalTo("new_role"))
        ));
    }
}
