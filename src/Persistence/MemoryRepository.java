package Persistence;

import SoftwareDevelopDomain.Helpers;
import SoftwareDevelopDomain.Person.User;
import SoftwareDevelopDomain.Person.UserRole;
import SoftwareDevelopDomain.TimeRecord;
import jdk.jshell.spi.ExecutionControl;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static SoftwareDevelopDomain.Person.UserRole.*;

public class MemoryRepository  implements IRepository {

    private ArrayList<TimeRecord> employees = new ArrayList<TimeRecord>();
    public void addFakeDataEmployee()
    {
        employees.add(new TimeRecord(LocalDateTime.now().minusDays(3),"Иванов",8,"test message 1"));
        employees.add(new TimeRecord(LocalDateTime.now().minusDays(3),"Васильев",8,"test message 2"));
        employees.add(new TimeRecord(LocalDateTime.now().minusDays(2),"Иванов",10,"test message 3"));
        employees.add(new TimeRecord(LocalDateTime.now().minusDays(2),"Васильев",8,"test message 4"));

    };
    private ArrayList<TimeRecord> freelancer = new ArrayList<TimeRecord>();
    public void addFakeDataFreelancer()
    {
        freelancer.add(new TimeRecord(LocalDateTime.now().minusDays(3),"Смит",8,"test message 1"));
        freelancer.add(new TimeRecord(LocalDateTime.now().minusDays(3),"Бонд",8,"test message 2"));
        freelancer.add(new TimeRecord(LocalDateTime.now().minusDays(2),"Смит",10,"test message 3"));
        freelancer.add(new TimeRecord(LocalDateTime.now().minusDays(2),"Бонд",8,"test message 4"));

    };

    private ArrayList<TimeRecord> manager = new ArrayList<TimeRecord>();
    public void addFakeDataManager(){
        manager.add(new TimeRecord(LocalDateTime.now().minusDays(3),"Береговой",8,"test message 1"));
        manager.add(new TimeRecord(LocalDateTime.now().minusDays(2),"Береговой",10,"test message 2"));
    }

    private ArrayList<User> users = new ArrayList<User>();
public void addFakeDataUser(){
    users.add(new User("Иванов", UserRole.valueOf("Employee")));
    users.add(new User("Васильев", UserRole.valueOf("Employee")));
    users.add(new User("Смит", UserRole.valueOf("Freelancer")));
    users.add(new User("Бонд", UserRole.valueOf("Freelancer")));
    users.add(new User("Береговой", UserRole.valueOf("Manager")));
}
    public ArrayList<TimeRecord> employees()
    {
        return employees;
    }
    public ArrayList<TimeRecord> freelancer()
    {
        return freelancer;
    }
    public ArrayList<TimeRecord> manager()
    {
        return manager;
    }

    public ArrayList<TimeRecord> reportGet(UserRole userRole, LocalDateTime from, LocalDateTime to)
    {
        var records = new ArrayList<TimeRecord>();
        switch (userRole)
        {
            case MANAGER:
                records = manager();
                break;
            case EMPLOYEE:
                records = employees();
                break;
            case FREELANCER:
                records = freelancer();
                break;
            default:
              System.out.println("Добавлена новая роль");
        }
        if(from == null)
        {
            from = LocalDateTime.now().minusYears(100);
        }
        if(to == null)
        {
            to = LocalDateTime.now();
        }
ArrayList<TimeRecord> getRep = new ArrayList<TimeRecord>();
        for (var item : records)
        {
            if((Helpers.getMillisecFromDate(item.getDate()) >= Helpers.getMillisecFromDate(to)
                    && Helpers.getMillisecFromDate(item.getDate()) <= Helpers.getMillisecFromDate(from))){
                getRep.add(item);
            }

        }
        return getRep;
    }
    public ArrayList<TimeRecord> reportGetByUser(String userName, UserRole userRole, LocalDateTime from , LocalDateTime to)
    {
        var fakeRep = reportGet(userRole, from, to);
        ArrayList<TimeRecord> fakeRepGet = new ArrayList<TimeRecord>();
        for (var item: fakeRep)
        {
        if(item.getName() == userName)
        {
            fakeRepGet.add(item);
        }
        }
        return fakeRepGet;
    }
    public void timeRecordAdd(UserRole userRole, TimeRecord timeRecord) {
        switch (userRole)
        {
            case MANAGER:
               manager.add(timeRecord);
                break;
            case EMPLOYEE:
                employees.add(timeRecord);
                break;
            case FREELANCER:
                freelancer.add(timeRecord);
                break;
            default:
             System.out.println("Добавлена новая роль");
        }
    }
    public boolean userCreate(UserRole userRole, String name)
    {
        var newUser = new User(name, userRole);
        User existedUser = userGet(name);
        if (existedUser == null)
        {
            users.add(newUser);
            return true;
        }
        else
        {
            return false;
        }
    }
    public User userGet(String name)
    {
        for(var record:Users())
        {
            if (record.getName() == name)
                return record;
        }
        return null;
    }
    public ArrayList<User> Users()//возвращаем  List<User>
    {

        return users;

    }


    public ArrayList<TimeRecord> generic = new ArrayList<TimeRecord>();
    public ArrayList<TimeRecord> Generic()
    {
        return generic;

    }
}
