package SoftwareDevelopDomain.Person;

import Persistence.UsersClassXml.UserXML;
import SoftwareDevelopDomain.Helpers;
import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;

public class Person {
    public LocalDate startDates;
    public LocalDate endDates;
    public int sumHours;
    private static UserXML users;
    protected static double totalPays;
    private List<TimeRecord> timeRecords;

    public Person(UserXML user, List<TimeRecord> timeRecord, LocalDate startDate, LocalDate endDate) {
        this.users = user;
        this.timeRecords = timeRecord;
        this.startDates = startDate;
        this.endDates = endDate;
    }

    public UserXML getUser() {
        return users;
    }

    public void setUser(UserXML user) {
        this.users = user;
    }

    public double getTotalPay() {
        return totalPays;
    }

    public void setTotalPay(double totalPay) {
        this.totalPays = totalPays;
    }

    public List<TimeRecord> getTimeRecord() {
        return timeRecords;
    }

    public void setTimeRecord(List<TimeRecord> timeRecord) {
        this.timeRecords = timeRecord;
    }



    public void printRepPerson(){
        System.out.println("Отчет по сотруднику \t" + users.getName() + "\t" + users.getUserRole() + "\t за период с \t " + startDates.toString()
                + "\t по" +"\t" + endDates.toString());
        for (TimeRecord item:timeRecords) {
           if((Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(startDates)) && (Helpers.getMilliSecFromDate(item.getDate()) <= Helpers.getMilliSecFromDate(endDates))){


           }
    if(users.getName().equals(item.getName())){
    System.out.println(item.getDate().toString() + "\t" + item.getHours() + "\t" + item.getMessage());
    sumHours += item.getHours();
}
        }
    }
}
