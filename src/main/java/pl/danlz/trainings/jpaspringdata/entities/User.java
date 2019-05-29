package pl.danlz.trainings.jpaspringdata.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String firstName;

    private String lastName;

    /**
     * Many to many association is represented in the database with a join table - {@code user_role} in this case.
     * The join table contains foreign keys to both ends of the association.
     */
    @ManyToMany
    @JoinTable(name = "user_role",
            /**
             * The {@code joinColumns} property tells how to join this entity's table with the join table.
             * In this case we join the {@code id} column of the {@code user} table with the {@code user_id} column of the {@code user_role} table.
            */
            joinColumns = @JoinColumn(name = "user_id"),
            /**
             * The {@code inverseJoinColumns} property tells how to join the join table with the table representing other end of the association.
             * In this case we join the {@code role_id} column of the {@code user_role} table with the {@code id} column of the {@code role} table.
            */
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Role> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public void addRole(Role role) {
        roles.add(role);
        role.users.add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.users.remove(this);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
