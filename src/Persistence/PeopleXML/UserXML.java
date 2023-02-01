package Persistence.PeopleXML;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class UserXML {
    private String name;
    private String userRole;

    public UserXML() {
    }

    public UserXML(String name, String userRole) {
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

    public String getUserRole() {
        return userRole;
    }

    @XmlElement
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}