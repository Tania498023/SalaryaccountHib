package Persistence;

import SoftwareDevelopDomain.Helpers;
import SoftwareDevelopDomain.Person.User;
import SoftwareDevelopDomain.Person.UserRole;
import SoftwareDevelopDomain.TimeRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.management.StringValueExp;
import java.io.*;
import java.awt.*;
import java.lang.annotation.Documented;
import java.util.*;
import java.util.List;

import static SoftwareDevelopDomain.Person.UserRole.MANAGER;
import static SoftwareDevelopDomain.Person.UserRole.valueOf;


public class FileRepository {
    /// Записываем коллекцию в файл user.csv
    public void fillFileUser(ArrayList<User> users, boolean userNeedWrite) throws IOException {
        String userPath = ".\\Data\\User.csv"; //".\\Data\\User.csv";
        File file = new File(userPath);


        if (!isFileExists(file))
            file.createNewFile();

        long size = file.length();

        if (!userNeedWrite && size > 0) //TODO true для рабочего(не фейкового файла)
            return;

        FileWriter writer;
        try {
            // File wr = new File("User.csv");
            writer = new FileWriter(file);
            for (Object user : users)//перебираем коллекцию и выбираем из нее элементы
            {
                User usr = (User) user;
                String userStr = usr.getName() + "," + usr.getUserRole() + System.lineSeparator();//создаем строку с разделительными символами и переносом строки

                writer.write(userStr);//записываем указанную строку
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean isFileExists(File file) {
        return file.exists() && !file.isDirectory();
    }

    /// Записываем коллекцию TimeRecord в файл согласно роли
    public void fillFileGeneric(List timeRecords, int roles, boolean genericneedwrite) throws IOException {
        String newPath = ConvertRoleToPath(roles);
        File file = new File(newPath);

        if (!isFileExists(file))
            file.createNewFile();

        long size = file.length();


        if (!genericneedwrite && size > 0)// TODO
            return;
        FileWriter writer;
        try {
            File wr = new File("User.csv");
            writer = new FileWriter(wr);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (var userRole : timeRecords)//перебираем коллекцию и выбираем из нее элементы
        {
            var usrRol = (TimeRecord) userRole;
            //создаем строку с разделительными символами и переносом строки
            String genericStr = usrRol.getDate() + "," + usrRol.getName() + "," + usrRol.getHours() + "," + usrRol.getMessage() + System.lineSeparator();

            writer.write(genericStr);//записываем указанную строку
        }
    }

    /// Конвертируем тип int(роль) в string(путь к файлу)
    private static String ConvertRoleToPath(int roles) {
        String newPath = "";

        if (roles == UserRole.MANAGER.ordinal()) {
            newPath = ".\\Data\\Manager.csv";
        }
        if (roles == UserRole.EMPLOYEE.ordinal()) {
            newPath = ".\\Data\\Employee.csv";
        } else if (roles == UserRole.FREELANCER.ordinal()) {
            newPath = ".\\Data\\Frilanser.csv";
        }

        return newPath;
    }

    // Считывает все строки файла User.csv и закрывает файл
    public List<User> readFileUser() throws IOException {
        //создаем экземпляр/объект User для использования по умолчанию, чтобы была возможность зайти в приложение даже, если файл пустой
        //добавляем этот объект в коллекцию tmpList
        var defaultUser = new User("defaultUser", MANAGER);
        var tmpList = new ArrayList<User>();
        tmpList.add(defaultUser);

        String userPath = ".\\Data\\User.csv";
        File file = new File(userPath);

        if (!isFileExists(file))
            return tmpList;

        List<User> users = new ArrayList<User>();
        FileReader fr;
        BufferedReader reader= null;

        try {
              fr = new FileReader(file);
              reader = new BufferedReader(fr);

            var str = reader.readLine();
            User user = null;

            UserRole strokaEnum;

            while (str != null) {

                try {
                str = reader.readLine();
              if(str == null)
                  break;

                var plitedStroka = str.split(",");



                    strokaEnum = UserRole.valueOf(plitedStroka[1]);
                    user = new User(plitedStroka[0], strokaEnum);//создали  объект
                } catch (Exception e) {
                    System.out.println("Не соответствует формат введенной строки!");
                }

                if (user != null) {
                    users.add(user);// добавили объект в коллекцию
                }

            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }finally {
            reader.close();
        }


        if (users.size() == 0) {
            return tmpList;
        }
        return users;
    }

    // Считывает все строки файла согласно роли
    public List<TimeRecord> readFileGeneric(int roles) {
        String newPath = ConvertRoleToPath(roles);
        File file = new File(newPath);

        if (!isFileExists(file))
            return null;
        List<TimeRecord> generic = new ArrayList<TimeRecord>();

        try {

            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            var str = reader.readLine();

            while (str != null) {

                str = reader.readLine();
                var plitedStroka = str.split(",");

                var user = new TimeRecord(plitedStroka);//создали  объект

                generic.add(user);// добавили объект в коллекцию
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return generic;
    }

    // Получаем имя пользователя и возвращаем коллекцию User

    public User userGet(String name) throws IOException {
        for (var record : readFileUser()) {
            if (record.getName() == name)
                return record;
        }
        return null;

    }
    // Получаем отчет по сотрудникам с учетом роли

    public List<TimeRecord> reportGet(UserRole userRole, LocalDateTime startdate, LocalDateTime enddate) {
        var records = readFileGeneric(userRole.ordinal());

        if (startdate == null) {
            startdate = LocalDateTime.now().minusYears(100);
        }

        if (enddate == null) {
            enddate = LocalDateTime.now();
        }


        List<TimeRecord> filteredRecord = new ArrayList<TimeRecord>();

        for (var item : records) {

            if (Helpers.getMillisecFromDate(item.getDate()) >= Helpers.getMillisecFromDate(startdate)
                    && Helpers.getMillisecFromDate(item.getDate()) <= Helpers.getMillisecFromDate(enddate)) {
                filteredRecord.add(item);
            }

        }
        return filteredRecord;
    }

    // Получаем отчет по конкретному сотруднику
    public List<TimeRecord> reportGetByUser(String userName, UserRole userRole, LocalDateTime from, LocalDateTime to) {
        List<TimeRecord> filteredRep = new ArrayList<TimeRecord>();
        var rep = reportGet(userRole, from, to);

        for (var item : rep) {

            if (item.getName() == userName) {
                filteredRep.add(item);
            }
        }
        return filteredRep;
    }
}












