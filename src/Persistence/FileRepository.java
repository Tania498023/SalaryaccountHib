package Persistence;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import Persistence.PeopleXML.UserXML;
import Persistence.PeopleXML.UsersXML;
import Persistence.PersonsJava.Persons;
import Persistence.PersonsJava.Role;
import SoftwareDevelopDomain.Helpers;
import SoftwareDevelopDomain.Person.User;
import SoftwareDevelopDomain.Person.UserRole;
import SoftwareDevelopDomain.TimeRecord;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.xml.sax.SAXException;


import static SoftwareDevelopDomain.Person.UserRole.MANAGER;
import static SoftwareDevelopDomain.Person.UserRole.values;


public class FileRepository {

    public static void fillXmlUser(ArrayList<User> users, boolean userNeedWrite) throws IOException, SAXException {

        UsersXML pers = new UsersXML();

        try {
            // Создаем файл
            File file = new File(".\\src\\Users.xml"); //
            // Вызываем статический метод JAXBContext
            JAXBContext jaxbContext = JAXBContext.newInstance(UsersXML.class);
            // Возвращает объект класса Marshaller, для того чтобы трансформировать объект
            Marshaller marshaller = jaxbContext.createMarshaller();
            // Читабельное форматирование
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


            try {
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                pers = (UsersXML) unmarshaller.unmarshal(file);
                long size = file.length();
                if (!userNeedWrite && size > 0) //TODO true для рабочего(не фейкового файла)
                    return;

                for (var ps : users) {
                    var userxml = new UserXML(ps.getName(), ps.getUserRole().toString());

                    pers.add(userxml);

                }
            } catch (Exception e) {
            }


            // Записываем в файл, marshal(из памяти, в файл)
            marshaller.marshal(pers, file);
            //  marshaller.marshal(pers, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFileExists(File file) {
        return file.exists() && !file.isDirectory();
    }

    public void fillXmlRecord(ArrayList<TimeRecord> timeRecords, int roles, boolean genericNeedWrite) throws IOException {
        Persons rec = new Persons();

        try {
            // Создаем файл
            File file = new File(".\\src\\Persons.xml"); //
            // Вызываем статический метод JAXBContext
            JAXBContext jaxbContext = JAXBContext.newInstance(Persons.class);
            // Возвращает объект класса Marshaller, для того чтобы трансформировать объект
            Marshaller marshaller = jaxbContext.createMarshaller();
            // Читабельное форматирование
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            try {
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                rec = (Persons) unmarshaller.unmarshal(file);
                long size = file.length();
                if (!genericNeedWrite && size > 0) //TODO true для рабочего(не фейкового файла)
                    return;

                for (var ps : timeRecords) {
                    var recxml = new Role("", ps.getName(), ps.getDate().toString(), ps.getHours().toString(), ps.getMessage());//TODO

                    rec.add(recxml);
                }

            } catch (Exception e) {
                System.out.println(e);
            }

            // Записываем в файл, marshal(из памяти, в файл)
            marshaller.marshal(rec, file);
            //  marshaller.marshal(pers, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    /// Конвертируем тип int(роль) в string(путь к файлу)
    @Deprecated
    private static String ConvertRoleToPath(int roles) {
        String newPath = "";

        newPath = ".\\src\\Persons.xml";

        return newPath;
    }


    public static ArrayList<User> readXmlUser() throws JAXBException {
        var defaultUser = new User("Я", MANAGER);
        var tmpList = new ArrayList<User>();
        tmpList.add(defaultUser);

        UsersXML pers = new UsersXML();
        // Создаем файл
        File xmlFile = new File(".\\src\\Users.xml");
        // Вызываем статический метод JAXBContext
        JAXBContext jaxbContext = JAXBContext.newInstance(UsersXML.class);
        // Возвращает объект класса Marshaller, для того чтобы трансформировать объект
        Marshaller marshaller = jaxbContext.createMarshaller();
        // Читабельное форматирование
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //   try {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        pers = (UsersXML) unmarshaller.unmarshal(xmlFile);
        if (pers.getSize() == 0) {
            return tmpList;
        }
        ArrayList<User> xmlList = new ArrayList<>();

        for (var psu : pers.getUsr()) {
            var userxml = new User(psu.getName(), UserRole.valueOf(psu.getUserRole()));
            xmlList.add(userxml);
        }
        return xmlList;

    }


    // Считывает все строки файла согласно роли
    public List<TimeRecord> readXmlRecord(int roles) throws FileNotFoundException, JAXBException {
        Persons pers = new Persons();

        // Создаем файл
        File xmlFile = new File(".\\src\\Persons.xml");
        // Вызываем статический метод JAXBContext
        JAXBContext jaxbContext = JAXBContext.newInstance(Persons.class);
        // Возвращает объект класса Marshaller, для того чтобы трансформировать объект
        Marshaller marshaller = jaxbContext.createMarshaller();
        // Читабельное форматирование
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //   try {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        pers = (Persons) unmarshaller.unmarshal(xmlFile);

        ArrayList<TimeRecord> listRec = new ArrayList<>();

        for (var ps : pers.getUsrRec()) {
            var recxml = new TimeRecord(LocalDate.parse(ps.getDate()),ps.getName(), Integer.parseInt(ps.getHour()), ps.getMessage());//TODO

            listRec.add(recxml);
        }
        return listRec;

    }

    // Получаем имя пользователя и возвращаем коллекцию User

    public User userGet(String name) throws IOException, JAXBException {
        for (var record : readXmlUser()) {
            if (record.getName().equals(name))
                return record;
        }
        return null;

    }

    // Получаем отчет по сотрудникам с учетом роли

    public List<TimeRecord> reportGet(UserRole userRole, LocalDate startDate, LocalDate endDate) throws FileNotFoundException, JAXBException {
        var records = readXmlRecord(userRole.ordinal());

        if (startDate == null) {
            startDate = LocalDate.now().minusYears(100);
        }

        if (endDate == null) {
            endDate = LocalDate.now();
        }

        List<TimeRecord> filteredRecord = new ArrayList<TimeRecord>();

        for (var item : records) {

            if (Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(startDate)
                    && Helpers.getMilliSecFromDate(item.getDate()) <= Helpers.getMilliSecFromDate(endDate)) {
                filteredRecord.add(item);
            }

        }
        return filteredRecord;
    }

    // Получаем отчет по конкретному сотруднику
    public List<TimeRecord> reportGetByUser(String userName, UserRole userRole, LocalDate from, LocalDate to) throws FileNotFoundException, JAXBException {
        List<TimeRecord> filteredRep = new ArrayList<TimeRecord>();
        var rep = reportGet(userRole, from, to);

        for (var item : rep) {

            if (item.getName().equals(userName)) {
                filteredRep.add(item);
            }
        }
        return filteredRep;
    }
}












