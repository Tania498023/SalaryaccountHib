package Persistence;

import Persistence.UsersClassXml.UserXML;
import SoftwareDevelopDomain.Helpers;
import SoftwareDevelopDomain.Person.User;
import SoftwareDevelopDomain.Person.UserRole;
import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDate;
import java.util.*;

import static SoftwareDevelopDomain.Person.UserRole.*;

public class MemoryRepository  implements IRepository {

    private ArrayList<TimeRecord> employees = new ArrayList<TimeRecord>();

    public MemoryRepository() {
        addFakeDataUser();
        addFakeDataEmployee();
        addFakeDataFreelancer();
        addFakeDataManager();
       //fillGeneric();
    }
    /*
    private void fillGeneric()
    {
        for (var user:employees)
        {
           sortGenerics(EMPLOYEE, user);
        }
        for (var user : freelancer)
        {
            sortGenerics(FREELANCER, user);
        }
        for (var user : manager)
        {
            sortGenerics(MANAGER, user);
        }
    }
*/
    public void addFakeDataEmployee()
    {
        employees.add(new TimeRecord(LocalDate.now().minusDays(3),"Иванов",8,"test message 1"));
        employees.add(new TimeRecord(LocalDate.now().minusDays(3),"Васильев",8,"test message 2"));
        employees.add(new TimeRecord(LocalDate.now().minusDays(2),"Иванов",10,"test message 3"));
        employees.add(new TimeRecord(LocalDate.now().minusDays(2),"Васильев",8,"test message 4"));

    };
    private ArrayList<TimeRecord> freelancer = new ArrayList<TimeRecord>();
    public void addFakeDataFreelancer()
    {
        freelancer.add(new TimeRecord(LocalDate.now().minusDays(3),"Смит",8,"test message 1"));
        freelancer.add(new TimeRecord(LocalDate.now().minusDays(3),"Бонд",8,"test message 2"));
        freelancer.add(new TimeRecord(LocalDate.now().minusDays(2),"Смит",10,"test message 3"));
        freelancer.add(new TimeRecord(LocalDate.now().minusDays(2),"Бонд",8,"test message 4"));

    };

    private ArrayList<TimeRecord> manager = new ArrayList<TimeRecord>();
    public void addFakeDataManager(){
        manager.add(new TimeRecord(LocalDate.now().minusDays(3),"Береговой",8,"test message 1"));
        manager.add(new TimeRecord(LocalDate.now().minusDays(2),"Береговой",10,"test message 2"));
    }

    private ArrayList<UserXML> users = new ArrayList<UserXML>();
public void addFakeDataUser(){

       users.add(new UserXML("ЯЯ", MANAGER));
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

    public ArrayList<TimeRecord> reportGet(UserRole userRole, LocalDate from, LocalDate to)
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
            from = LocalDate.now().minusYears(100);
        }
        if(to == null)
        {
            to = LocalDate.now();
        }
ArrayList<TimeRecord> getRep = new ArrayList<TimeRecord>();
        for (var item : records)
        {
            if((Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(to)
                    && Helpers.getMilliSecFromDate(item.getDate()) <= Helpers.getMilliSecFromDate(from))){
                getRep.add(item);
            }

        }
        return getRep;
    }
    public ArrayList<TimeRecord> reportGetByUser(String userName, UserRole userRole, LocalDate from , LocalDate to)
    {
        var fakeRep = reportGet(userRole, from, to);
        ArrayList<TimeRecord> fakeRepGet = new ArrayList<TimeRecord>();
        for (var item: fakeRep)
        {
        if(item.getName().equals(userName))
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
        var newUser = new UserXML(name, userRole);
        UserXML existedUser = userGet(name);
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
    public UserXML userGet(String name)
    {
        for(var record:Users())
        {
            if (record.getName().equals(name))
                return record;
        }
        return null;
    }
    public ArrayList<UserXML> Users()//возвращаем  List<User>
    {
        return users;
    }
    /*
    public void sortGenerics(UserRole userRole, TimeRecord timeRecord)
    {
        switch (userRole)
        {
            case MANAGER:
                if (!generic.containsKey(MANAGER))
                {
                    var itemsList = new ArrayList<TimeRecord>();
                    itemsList.add(timeRecord);
                    generic.put(MANAGER, itemsList);

                }
                else {
                    var it  = generic.get(userRole);
                    if(!it.contains(userRole))
                     it.add(timeRecord);
                }

                break;
            case EMPLOYEE:
                if (!generic.containsKey(EMPLOYEE))
                {
                    var itemsList = new ArrayList<TimeRecord>();
                    itemsList.add(timeRecord);
                    generic.put(EMPLOYEE, itemsList);

                }
                else {
                    var it  = generic.get(userRole);
                    if(!it.contains(userRole))
                        it.add(timeRecord);
                }
                break;
            case FREELANCER:
                if (!generic.containsKey(FREELANCER))
                {
                    var itemsList = new ArrayList<TimeRecord>();
                    itemsList.add(timeRecord);
                    generic.put(FREELANCER, itemsList);

                }
                else {
                    var it  = generic.get(userRole);
                    if(!it.contains(userRole))
                        it.add(timeRecord);
                }
                break;

        }
    }
*/
    public ArrayList<TimeRecord> generic = new ArrayList<TimeRecord>();

    public ArrayList<TimeRecord> Generic()
    {
        generic.add(new TimeRecord(LocalDate.now().minusDays(3),"Перехвал",8,"test message 1"));
        return generic;

    }
}
