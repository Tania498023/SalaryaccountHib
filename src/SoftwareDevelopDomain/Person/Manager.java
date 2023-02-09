package SoftwareDevelopDomain.Person;

import Persistence.UsersClassXml.UserXML;
import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDate;
import java.util.List;

public class Manager extends Staff {
    public static  double monthBonuses = 20000;

    public static int stavka = 200000;



    public Manager(UserXML user, List<TimeRecord> timeRecord, LocalDate startDate, LocalDate endDate) {
        super(stavka, user, timeRecord, startDate, endDate,monthBonuses);

    }
}
