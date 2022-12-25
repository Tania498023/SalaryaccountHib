package Persistence;

import SoftwareDevelopDomain.Person.User;
import SoftwareDevelopDomain.Person.UserRole;
import SoftwareDevelopDomain.TimeRecord;
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
    public void fillFileUser(List users, boolean userNeedWrite ) throws IOException {
        String userPath = "\\Data\\User.csv";
        File file = new File(userPath);

        if (!isFileExists(file))
            return;

        long size = file.length();

        if (!userNeedWrite && size > 0) //TODO true для рабочего(не фейкового файла)
            return;

        FileWriter writer;
        try {
            File wr = new File("User.csv");
            writer = new FileWriter(wr);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (var user : users)//перебираем коллекцию и выбираем из нее элементы
        {
            var usr = (User)user;
            String userStr = usr.getName() + "," + usr.getUserRole() + System.lineSeparator();//создаем строку с разделительными символами и переносом строки

            writer.write(userStr);//записываем указанную строку
        }
    }
    public static boolean isFileExists(File file) {
        return file.exists() && !file.isDirectory();
    }

    /// Записываем коллекцию TimeRecord в файл согласно роли
    public void fillFileGeneric(List timeRecords, int roles, boolean genericneedwrite) throws IOException
    {
        String newpath = ConvertRoleToPath(roles);
        File file = new File(newpath);

        if (!isFileExists(file))
            return;

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
        for ( var userRole : timeRecords)//перебираем коллекцию и выбираем из нее элементы
        {
            var usrRol = (TimeRecord)userRole;
            //создаем строку с разделительными символами и переносом строки
            String genericstr = usrRol.getDate()+ "," + usrRol.getName() + "," + usrRol.getHours() + "," + usrRol.getMessage() + System.lineSeparator();

            writer.write(genericstr);//записываем указанную строку
        }
    }
    /// Конвертируем тип int(роль) в string(путь к файлу)
    private static String ConvertRoleToPath(int roles)
    {
        String newpath = "";

        if (roles == UserRole.MANAGER.ordinal())
        {
            newpath =".\\Data\\Manager.csv";
        }
        if (roles == UserRole.EMPLOYEE.ordinal())
        {
            newpath = ".\\Data\\Employee.csv";
        }
        else if (roles == UserRole.FREELANCER.ordinal())
        {
            newpath = ".\\Data\\Frilanser.csv";
        }

        return newpath;
    }
// Считывает все строки файла User.csv и закрывает файл
public List<User> readFileUser()  {
    //создаем экземпляр/объект User для использования по умолчанию, чтобы была возможность зайти в приложение даже, если файл пустой
    //добавляем этот объект в коллекцию tmplist
    var defaultuser = new User("defaultuser", MANAGER);
    var tmplist = new ArrayList<User>();
    tmplist.add(defaultuser);

    String userpath = ".\\Data\\User.csv";
    File file = new File(userpath);

    if (!isFileExists(file))
        return tmplist;

    List<User> users = new ArrayList<User>();

    try {
    FileReader fr = new FileReader(file);
    BufferedReader reader = new BufferedReader(fr);

        var str = reader.readLine();

        while (str != null) {


            str = reader.readLine();
            var plitedstroka = str.split(",");
            User user = null;

            UserRole strokainenum;


        try
        {
            strokainenum = UserRole.valueOf(plitedstroka[1]);
            user = new User(plitedstroka[0], strokainenum);//создали  объект
        }

        catch(Exception e)
        {
            System.out.println("Не соответствует формат введенной строки!");
        }

            if (user != null) {
                users.add(user);// добавили объект в коллекцию
            }

        }
    }catch (FileNotFoundException e) {

    }
    catch (IOException e){

    }
    if(users.size() == 0)
    {
        return tmplist;
    }
    return users;
}

}
