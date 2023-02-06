package Persistence.UsersClassXml;


import SoftwareDevelopDomain.Person.UserRole;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "user")
public class UserXML {
    private String name;
    private UserRole userRole;

    public UserXML() {
    }

    public UserXML(String name, UserRole userRole) {
        this.name = name;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "[" + name + ", " + userRole + "]";
    }

    public String getName() { return this.name; }
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    @XmlElement
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}