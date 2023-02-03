package Persistence.PersonsJava;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
public class Role {
    private String role;
    private String name;
    private String date;
    private String hour;
    private String message;

    public Role() {
    }

    public Role(String role, String name, String date, String hour, String message) {
        this.role = role;
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.message = message;
    }

    @Override
    public String toString() {
        return "RecUser{" +
                "role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getMessage() {
        return message;
    }
@XmlElement
    public void setRole(String role) {
        this.role = role;
    }
@XmlElement
    public void setName(String name) {
        this.name = name;
    }
@XmlElement
    public void setDate(String date) {
        this.date = date;
    }
@XmlElement
    public void setHour(String hour) {
        this.hour = hour;
    }
@XmlElement
    public void setMessage(String message) {
        this.message = message;
    }
}
