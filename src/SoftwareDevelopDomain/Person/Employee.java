package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;

public class Employee extends Staff {

    public  static int stavka = 120000;
    public  static double monthBonuses = 0;

    public Employee(double monthSalary, User user, List<TimeRecord> timeRecord, LocalDateTime startDate, LocalDateTime endDate, double bonus) {
        super(monthSalary, user, timeRecord, startDate, endDate, bonus);
    }
}
