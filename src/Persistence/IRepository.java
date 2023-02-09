package Persistence;

import Persistence.UsersClassXml.UserXML;
import SoftwareDevelopDomain.Person.User;
import SoftwareDevelopDomain.Person.UserRole;
import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IRepository {
    ArrayList<UserXML> Users();
    boolean userCreate(UserRole userRole, String name);
    UserXML userGet(String name);

    void timeRecordAdd(UserRole userRole, TimeRecord timeRecord);
    ArrayList<TimeRecord> reportGet (UserRole userRole, LocalDate from, LocalDate to);
    ArrayList<TimeRecord> reportGetByUser(String userName, UserRole userRole, LocalDate from, LocalDate to);
    ArrayList<TimeRecord> employees();
    ArrayList<TimeRecord> manager();
    ArrayList<TimeRecord> freelancer();
}
