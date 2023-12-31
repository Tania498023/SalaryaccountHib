package myApp.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "salary_user")
public class UserHib {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "userrole")
    private UserRoleHib userRole;
   @OneToMany
    private List<RecordHib> records;

    public UserHib() {
    }

    public UserHib(Integer id, String lastName, UserRoleHib userRole, List<RecordHib> records) {
        this.id = id;
        this.lastName = lastName;
        this.userRole = userRole;
        this.records = records;
    }

    public UserHib(Integer id, String lastName, UserRoleHib userRole) {
        this.id = id;
        this.lastName = lastName;
        this.userRole = userRole;
    }

    public UserHib(String lastName, UserRoleHib userRole) {
        this.lastName = lastName;
        this.userRole = userRole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRoleHib getUserRoleHib() {
        return userRole;
    }

    public void setUserRoleHib(UserRoleHib userRole) {
        this.userRole = userRole;
    }

    public List<RecordHib> getRecords() {
        return records;
    }

    public void setRecords(List<RecordHib> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return this.id + " " + this.lastName + " " + this.userRole+ " "+ this.records;
    }
}
