package SoftwareDevelopDomain.Person;

public enum UserRole {
    MANAGER("Manager"),
    EMPLOYEE("Employee"),
    FREELANCER("Freelancer"),

    DEFAULT("Default");



    private String value;

    private UserRole(String value) {
        this.value = value;


    }

}