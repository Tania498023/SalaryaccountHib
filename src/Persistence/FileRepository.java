package Persistence;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import Persistence.PeopleXML.UserXML;
import Persistence.PeopleXML.UsersXML;
import SoftwareDevelopDomain.Helpers;
import SoftwareDevelopDomain.Person.User;
import SoftwareDevelopDomain.Person.UserRole;
import SoftwareDevelopDomain.TimeRecord;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;

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

    @Deprecated /// Записываем коллекцию в файл user.csv
    public void fillFileUser(ArrayList<User> users, boolean userNeedWrite) throws IOException {
        String userPath = ".\\Data\\User.csv"; //".\\Data\\User.csv";
        File file = new File(userPath);

        if (!isFileExists(file))
            file.createNewFile();
        long size = file.length();
        if (!userNeedWrite && size > 0) //TODO true для рабочего(не фейкового файла)
            return;
        FileWriter writer = null;
        try {

            writer = new FileWriter(file, true);//запись с добавлением строки в конец существующего текста
            for (Object user : users)//перебираем коллекцию и выбираем из нее элементы
            {
                User usr = (User) user;
                String userStr = usr.getName() + "," + usr.getUserRole() + System.lineSeparator();//создаем строку с разделительными символами и переносом строки

                writer.append(userStr);//записываем указанную строку
            }

        } catch (IOException e) {

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static boolean isFileExists(File file) {
        return file.exists() && !file.isDirectory();
    }

    /// Записываем коллекцию TimeRecord в файл согласно роли
    public void fillFileGeneric(ArrayList<TimeRecord> timeRecords, int roles, boolean genericNeedWrite) throws IOException {
        String newPath = ConvertRoleToPath(roles);
        File file = new File(newPath);

        if (!isFileExists(file))
            file.createNewFile();
        long size = file.length();

        if (!genericNeedWrite && size > 0)// TODO
            return;
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            for (var userRole : timeRecords)//перебираем коллекцию и выбираем из нее элементы
            {
                var usrRol = (TimeRecord) userRole;
                //создаем строку с разделительными символами и переносом строки
                String genericStr = usrRol.getDate() + "," + usrRol.getName() + "," + usrRol.getHours() + "," + usrRol.getMessage() + System.lineSeparator();
                writer.append(genericStr);//записываем указанную строку
            }
        } catch (IOException e) {

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void fillFileGeneric(HashMap<UserRole, ArrayList<TimeRecord>> timeRecords, int roles, boolean genericNeedWrite) throws IOException {

        var keys = timeRecords.keySet();
        for (var curKey : keys) {
            String newPath = ConvertRoleToPath(curKey.ordinal());
            File file = new File(newPath);
            if (!isFileExists(file))
                file.createNewFile();
            long size = file.length();

            if (!genericNeedWrite && size > 0)// TODO
                return;
            FileWriter writer = null;
            try {
                writer = new FileWriter(file, true);
                for (var ur : timeRecords.get(curKey))//перебираем коллекцию и выбираем из нее элементы
                {
                    //создаем строку с разделительными символами и переносом строки
                    String genericStr = ur.getDate() + "," + ur.getName() + "," + ur.getHours() + "," + ur.getMessage() + System.lineSeparator();
                    writer.append(genericStr);//записываем указанную строку

                }
            } catch (IOException e) {

            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }

    }

    /// Конвертируем тип int(роль) в string(путь к файлу)
    @Deprecated
    private static String ConvertRoleToPath(int roles) {
        String newPath = "";

        newPath = ".\\Data2\\Persons.xml";

        return newPath;
    }


    public static ArrayList<User> readXmlUser() throws JAXBException {
        //создаем экземпляр/объект User для использования по умолчанию, чтобы была возможность зайти в приложение даже, если файл пустой
        //добавляем этот объект в коллекцию tmpList
        var defaultUser = new User("defaultUser", MANAGER);
        var tmpLists = new ArrayList<User>();
        tmpLists.add(defaultUser);

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

        ArrayList<User> xmlList = new ArrayList<>();

        for (var psu : pers.getUsr()) {
            var userxml = new User(psu.getName(),UserRole.valueOf(psu.getUserRole()));
            xmlList.add(userxml);
        }
        if (xmlList.size() == 0)
        {
            return tmpLists;
        }

            return xmlList ;


}

//Считывает все строки файла User.csv и закрывает файл
    @Deprecated
    public List<User> readFileUser() throws IOException {
        //создаем экземпляр/объект User для использования по умолчанию, чтобы была возможность зайти в приложение даже, если файл пустой
        //добавляем этот объект в коллекцию tmpList
        var defaultUser = new User("defaultUser", MANAGER);
        var tmpList = new ArrayList<User>();
        tmpList.add(defaultUser);

        String userPath = ".\\Data\\User.csv";

        if (!isFileExists(new File(userPath)))
            return tmpList;

        List<User> users = new ArrayList<User>();

        Scanner sc = new Scanner(new File(userPath));
        List<String> lines = new ArrayList<String>();
        while (sc.hasNextLine())
        {
            lines.add(sc.nextLine());
        }

        String[] str = lines.toArray(new String[0]);
        UserRole strokaEnum;
        for (var stroka : str)
        {
            if (str != null || str.length > 0)
            {
                var plitedStroka = stroka.split(",");
                User user = null;
                try
                {

                    strokaEnum = UserRole.valueOf(plitedStroka[1]);
                    user = new User(plitedStroka[0], strokaEnum);//создали  объект

                }
                catch (Exception e)
                {
                    System.out.println("Не соответствует формат введенной строки!");
                }

                if (user != null) {
                    users.add(user);// добавили объект в коллекцию
                }
            }
        }

        if (users.size() == 0)
        {
            return tmpList;
        }
        return users;
    }

    // Считывает все строки файла согласно роли
    public List<TimeRecord> readFileGeneric(int roles) throws FileNotFoundException {
        String newPath = ConvertRoleToPath(roles);
        File file = new File(newPath);

        if (!isFileExists(file))
            return null;
        List<TimeRecord> generic = new ArrayList<TimeRecord>();
        Scanner sc = new Scanner(new File(newPath));
        List<String> lines = new ArrayList<String>();
        while (sc.hasNextLine())
        {
        lines.add(sc.nextLine());
        }
        String[] str = lines.toArray(new String[0]);
        for (var stroka :str)
        {
            if (str!= null || str.length>0)
            {
                var plitedStroka = stroka.split(",");
                if (plitedStroka.length<4){System.out.println("Строка неверная "+ stroka);
                    break;
                }
                else if (plitedStroka.length==4) {
                    var user = new TimeRecord(plitedStroka);//создали  объект
                    generic.add(user);// добавили объект в коллекцию
                }

            }
        }

        return generic;
        }

    // Получаем имя пользователя и возвращаем коллекцию User

    public User userGet(String name) throws IOException, JAXBException {
        for (var record : readXmlUser())
        {
            if (record.getName().equals(name))
                return record;
        }
        return null;

    }
    // Получаем отчет по сотрудникам с учетом роли

    public List<TimeRecord> reportGet(UserRole userRole, LocalDate startDate, LocalDate endDate) throws FileNotFoundException {
        var records = readFileGeneric(userRole.ordinal());

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
    public List<TimeRecord> reportGetByUser(String userName, UserRole userRole, LocalDate from, LocalDate to) throws FileNotFoundException {
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












