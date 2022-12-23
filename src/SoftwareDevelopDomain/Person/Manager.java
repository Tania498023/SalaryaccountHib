package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;

public class Manager extends Staff {
    public Manager(User user, List<TimeRecord> timeRecords, LocalDateTime startdate, LocalDateTime enddate)  {

    }

    public double getMonthBonus() {
        return monthBonus;
    }

    private double monthBonus = 20000;

    public static int stavka = 200000;


}
