package SoftwareDevelopDomain;

import java.time.LocalDateTime;

public class TimeRecord {
    private LocalDateTime dates;
    private String names;
    private int hours;
    private String messages;
    public TimeRecord(LocalDateTime date, String name, int hour, String message) {
        this.dates = date;
        this.names = name;
        this.hours = hour;
        this.messages = message;
    }
    public TimeRecord(String[] stroka) {
        dates = LocalDateTime.parse(stroka[0]);
        names = stroka[1];
        hours = Integer.parseInt(stroka[2]);
        messages = stroka[3];
    }

    public LocalDateTime getDate() {
        return dates;
    }
    public long getMillisecs() {
        return Helpers.getMillisecFromDate(dates);
    }
    public void setDate(LocalDateTime date) {
        this.dates = date;
    }
    public String getName() {
        return names;
    }

    public int getHours() {
        return hours;
    }

    public String getMessage() {
        return messages;
    }




}
