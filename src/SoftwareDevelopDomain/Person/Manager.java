package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDate;
import java.util.List;

public class Manager extends Staff {
    public static  double monthBonuses = 20000;

    public static int stavka = 200000;



    public Manager( User user, List<TimeRecord> timeRecord, LocalDate startDate, LocalDate endDate) {
        super(stavka, user, timeRecord, startDate, endDate,monthBonuses);

    }
}
