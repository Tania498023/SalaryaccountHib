package Persistence.RecordsClassXml;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "userRecord")
public class UserRecord {
    private String name;
    private String date;
    private String hour;
    private String message;

    public UserRecord() {
    }

    public UserRecord(String name, String date, String hour, String message) {

        this.name = name;
        this.date = date;
        this.hour = hour;
        this.message = message;
    }

    @Override
    public String toString() {
        return "RecUser{" +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", message='" + message + '\'' +
                '}';
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
