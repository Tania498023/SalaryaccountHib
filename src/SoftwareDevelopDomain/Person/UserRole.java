package SoftwareDevelopDomain.Person;

public enum UserRole {
    MANAGER("Manager"),
    EMPLOYEE("Employee"),
    FREELANCER("Freelancer");
    private String value;

    private UserRole(String value) {
        this.value = value;


    }

}