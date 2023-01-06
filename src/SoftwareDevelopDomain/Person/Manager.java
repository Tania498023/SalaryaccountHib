package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDate;
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



    public Manager( User user, List<TimeRecord> timeRecord, LocalDate startDate, LocalDate endDate, double bonus ) {
        super(stavka, user, timeRecord, startDate, endDate, bonus);

    }
}
