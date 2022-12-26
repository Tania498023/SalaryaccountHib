package SoftwareDevelopDomain.Person;

public class User {
    private String names;
    private UserRole userRoles;

    public User() {
    }
    public User(String name, UserRole userRole) {
        this.names = name;
        this.userRoles = userRole;
    }



    public String getName() {
        return names;
    }
    public void setName(String name) {
        this.names = name;
    }

    public UserRole getUserRole() {
        return userRoles;
    }
    public void setUserRole(UserRole userRole) {
        this.userRoles = userRole;
    }













}
