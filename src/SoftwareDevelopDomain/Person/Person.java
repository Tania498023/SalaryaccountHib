package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;

public class Person {
    public LocalDate startDates;
    public LocalDate endDates;
    public int sumHours;
    private static User users;
    protected static double totalPays;
    private List<TimeRecord> timeRecords;

    public Person(User user, List<TimeRecord> timeRecord, LocalDate startDate, LocalDate endDate) {
        this.users = user;
        this.timeRecords = timeRecord;
        this.startDates = startDate;
        this.endDates = endDate;
    }

    public User getUser() {
        return users;
    }

    public void setUser(User user) {
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
        System.out.println("Отчет по сотруднику " + users.getName() + users.getUserRole() + "за период с " + startDates.toString()
                + "по" + endDates.toString());
        for (TimeRecord item:timeRecords) {
          //  if((item.getDate() >= startDates) && (item.getDate() <= endDates)){
            //  TODO realise

           // }
    if(users.getName().equals(item.getName())){
    System.out.println(item.getDate().toString() + item.getHours() + item.getMessage());
    sumHours += item.getHours();
}
        }
    }
}
