package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.Settings;

import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDate;
import java.util.List;

public class Staff extends Person {
    public int totalHours;
    private double monthSalarys;
    double bonuses;

    public double getMonthSalary() {
        return monthSalarys;
    }


    public Staff(double monthSalary, User user, List<TimeRecord> timeRecord, LocalDate startDate, LocalDate endDate,double bonus) {
        super(user, timeRecord, startDate, endDate);
        bonuses = bonus;
        monthSalarys = monthSalary;
        raschetTotalPay(user.getName(),timeRecord, startDate,endDate);
    }
    private void raschetTotalPay(String name, List<TimeRecord> timeRecord,LocalDate startDate, LocalDate endDate)
    {

        double payPerHour = monthSalarys/Settings.WORKHOURSINMONTH;
        double totalPay = 0;
        double bonusPerDay = bonuses/Settings.WORKHOURSINMONTH * Settings.WORKHOURSINDAY;


        for (var timeRecordses: timeRecord)
        {
if(name.equals(timeRecordses.getName())) // перепроверить equals(==)!!!!!!!!!!!!!
   // if (timeRecord.getDate()>=starDate&&timeRecord.getDate()<=endDate) TODO !!!!!!!!!!!!!
    if(timeRecordses.getHours()<=Settings.WORKHOURSINDAY)
    {
        totalPay +=payPerHour*timeRecordses.getHours();
        totalHours += timeRecordses.getHours();
    }
else if (getUser().getUserRole()==UserRole.MANAGER){
        totalPay += payPerHour*Settings.WORKHOURSINDAY+bonusPerDay;
    }
else {
        totalPay += payPerHour*timeRecordses.getHours()+(timeRecordses.getHours()-Settings.WORKHOURSINDAY)*payPerHour;
    }
 double totalsPays = totalPay;
        }
    }
}
