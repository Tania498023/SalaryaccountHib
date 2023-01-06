package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDate;
import java.util.List;

public class Employee extends Staff {

    public  static int stavka = 120000;
    public  static double monthBonuses = 0;

    public Employee(  User user, List<TimeRecord> timeRecord, LocalDate startDate, LocalDate endDate, double bonus) {
        super(stavka, user, timeRecord, startDate, endDate, bonus);
    }
}
