package SoftwareDevelopDomain.Person;

import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;

public class Person {
    public LocalDateTime startDates;
    public LocalDateTime endDates;
    public int sumHour;
    private int sumhour;

    public Person(User user, List<TimeRecord> timeRecords, LocalDateTime startDate, LocalDateTime endDate) {
        this.user = user;
        this.timerecords = timeRecords;
        this.startDates = startDate;
        this.endDates = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private static User user;

    public double getTotalpay() {
        return totalpay;
    }

    public void setTotalpay(double totalpay) {
        this.totalpay = totalpay;
    }

    private static double totalpay;

    public List<TimeRecord> getTimerecords() {
        return timerecords;
    }

    public void setTimerecords(List<TimeRecord> timerecords) {
        this.timerecords = timerecords;
    }

    private List<TimeRecord> timerecords;

    public void printRepPerson(){
        System.out.println("Отчет по сотруднику [" + user.getName() + user.getUserRole() + "за период с " + startDates.toString()
                + "по" + endDates.toString());
        for (TimeRecord item:timerecords) {
          //  if((item.getDate() >= startdates) && (item.getDate() <= enddates)){
            //  TODO realise

           // }
if(user.getName()== item.getName()){
    System.out.println(item.getDate().toString() + item.getHours() + item.getMessage());
    sumhour+=item.getHours();
}
        }
    }
}
