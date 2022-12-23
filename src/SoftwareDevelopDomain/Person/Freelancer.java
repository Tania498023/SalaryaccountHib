package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.Settings;
import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;

public class Freelancer extends Person {

    public Freelancer(User user, List<TimeRecord> timeRecord, LocalDateTime startDate, LocalDateTime endDate) {
        super(user, timeRecord, startDate, endDate);

        double totalPay = 0;
        for (var timeRecords : timeRecord) {
            if(user.getName() == timeRecords.getName())
                //if(timeRecords.getDate()>= startDate && timeRecords.getDate()<= endDate) TODO!!!
                {
                totalPay += Settings.PAYPERHOUR * timeRecords.getHours();
                }
          double totalPays = totalPay;
        }
    }
}
