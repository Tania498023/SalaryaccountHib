package SoftwareDevelopDomain;

import java.time.LocalDate;

public class TimeRecord {
    private LocalDate dates;
    private String names;
    private int hours;
    private String messages;
    public TimeRecord(LocalDate date, String name, int hour, String message) {
        this.dates = date;
        this.names = name;
        this.hours = hour;
        this.messages = message;
    }
    public TimeRecord(String[] stroka) {//хорошим тоном является проверка входных параметров и бросание Exception в вызывающий код(а не сокрытие ошибки)
        dates = LocalDate.parse(stroka[0]);
        names = stroka[1];
        hours = Integer.parseInt(stroka[2]);
        messages = stroka[3];
    }

    public LocalDate getDate() {
        return dates;
    }
    public long getMilliSecs() {
        return Helpers.getMilliSecFromDate(dates);
    }
    public void setDate(LocalDate date) {
        this.dates = date;
    }
    public String getName() {
        return names;
    }

    public Integer getHours() {
        return hours;
    }

    public String getMessage() {
        return messages;
    }




}
