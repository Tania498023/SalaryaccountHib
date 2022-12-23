package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;

public class Manager extends Staff {
    private double monthBonuses = 20000;

    public static int stavka = 200000;

    public double getMonthBonuses() {
        return monthBonuses;
    }

    public void setMonthBonuses(double monthBonuses) {
        this.monthBonuses = monthBonuses;
    }



    public Manager(double monthSalary, User user, List<TimeRecord> timeRecord, LocalDateTime startDate, LocalDateTime endDate, double bonus, double monthBonus) {
        super(monthSalary, user, timeRecord, startDate, endDate, bonus);
        this.monthBonuses = monthBonus;
    }
}
