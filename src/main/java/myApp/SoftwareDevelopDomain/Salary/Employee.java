package myApp.SoftwareDevelopDomain.Salary;



import myApp.models.RecordHib;
import myApp.models.UserHib;

import java.time.LocalDate;
import java.util.List;

public class Employee extends Staff {

    public  static int stavka = 120000;
    public  static double monthBonuses = 0;

    public Employee(UserHib user, List<RecordHib> timeRecord, LocalDate startDate, LocalDate endDate, double bonus) {
        super(stavka, user, timeRecord, startDate, endDate, bonus);
    }
}
