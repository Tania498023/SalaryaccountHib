package Persistence.RecordsClassXml;
import Persistence.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;

@XmlRootElement(name = "userRecord")
public class UserRecord {
    private LocalDate date;
    private String name;
    private Integer hour;
    private String message;

    public UserRecord() {
    }

    public UserRecord(LocalDate date,String name, Integer hour, String message) {
        this.date = date;
        this.name = name;
        this.hour = hour;
        this.message = message;
    }
    @Override
    public String toString() {
        return "RecUser{" +
                ", name='" + date + '\'' +
                ", date='" + name + '\'' +
                ", hour='" + hour + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
    public LocalDate getDate() {
        return date;
    }
    public String getName() {
        return name;
    }

    public Integer getHour() {
        return hour;
    }

    public String getMessage() {
        return message;
    }
    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setDate(LocalDate date) {
        this.date = date;
    }
@XmlElement
    public void setName(String name) {
        this.name = name;
    }

@XmlElement
    public void setHour(Integer hour) {
        this.hour = hour;
    }
@XmlElement
    public void setMessage(String message) {
        this.message = message;
    }
}
