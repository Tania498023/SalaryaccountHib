package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.Helpers;
import SoftwareDevelopDomain.Settings;
import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDate;
import java.util.List;

public class Freelancer extends Person {

    public Freelancer(User user, List<TimeRecord> timeRecord, LocalDate startDate, LocalDate endDate) {
        super(user, timeRecord, startDate, endDate);

        double totalPay = 0;
        for (var timeRecords : timeRecord) {
            if(user.getName().equals(timeRecords.getName()))
                if(Helpers.getMilliSecFromDate(timeRecords.getDate())>= Helpers.getMilliSecFromDate(startDate) && Helpers.getMilliSecFromDate(timeRecords.getDate())<= Helpers.getMilliSecFromDate(endDate))
                {
                totalPay += Settings.PAYPERHOUR * timeRecords.getHours();
                }
          double totalPays = totalPay;
        }
    }
}
