package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;

public class Manager extends Staff {
    private double monthBonuses = 20000;

    public static int stavka = 200000;


    public Manager(double monthSalar, User user, List<TimeRecord> timeRecord, LocalDateTime startDate, LocalDateTime endDate, double bonus, double monthBonuses) {
        super(monthSalar, user, timeRecord, startDate, endDate, bonus);
        this.monthBonuses = monthBonuses;
    }
}
