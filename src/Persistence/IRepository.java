package Persistence;

import SoftwareDevelopDomain.Person.User;
import SoftwareDevelopDomain.Person.UserRole;
import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IRepository {
    ArrayList<User> Users();
    boolean userCreate(UserRole userRole, String name);
    User userGet(String name);

    void timeRecordAdd(UserRole userRole, TimeRecord timeRecord);
    ArrayList<TimeRecord> reportGet (UserRole userRole, LocalDateTime from, LocalDateTime to);
    ArrayList<TimeRecord> reportGetByUser(String userName, UserRole userRole, LocalDateTime from, LocalDateTime to);

    ArrayList<TimeRecord> employees();
    ArrayList<TimeRecord> manager();
    ArrayList<TimeRecord> freelancer();
}
