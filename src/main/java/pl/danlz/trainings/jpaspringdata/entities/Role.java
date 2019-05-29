package pl.danlz.trainings.jpaspringdata.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    /**
     * The {@code mappedBy} attribute tells, what gets inserted into the join table.
     * Only one end of many to many association may control it.
     *
     * This property is package protected, so that the {@code User} class can access it.
     */
    @ManyToMany(mappedBy = "roles")
    List<User> users = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public void addUser(User user) {
        user.addRole(this);
    }

    public void removeUser(User user) {
        user.removeRole(this);
    }


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
