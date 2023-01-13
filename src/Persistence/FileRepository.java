package Persistence;

import SoftwareDevelopDomain.Helpers;
import SoftwareDevelopDomain.Person.User;
import SoftwareDevelopDomain.Person.UserRole;
import SoftwareDevelopDomain.TimeRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.time.LocalDate;
import java.io.*;
import java.util.*;

import static SoftwareDevelopDomain.Person.UserRole.MANAGER;



public class FileRepository {

    public static void fillXmlUser(ArrayList<User> users, boolean userNeedWrite) throws ParserConfigurationException, IOException, SAXException {
        String userPath = ".\\Data2\\Users.xml";
        File file = new File(userPath);
        Document document = null;
        if (!isFileExists(file)) {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse("Users.xml");

           }
        long size = file.length();

        if (!userNeedWrite && size > 0) //TODO true для рабочего(не фейкового файла)
            return;
        try {


            for (Object persons : users)//перебираем коллекцию и выбираем из нее элементы
            {
                User usr = (User) persons;

                Node root = document.getDocumentElement();
                Element user = document.createElement("user");
                Element name = document.createElement("name");
                // Устанавливаем значение текста внутри тега
                name.setTextContent(usr.getName());
                Element userRole = document.createElement("userrole");
                userRole.setTextContent(usr.getUserRole().toString());

                // Добавляем внутренние элементы книги в элемент <Book>
                user.appendChild(name);
                user.appendChild(userRole);

                // Добавляем книгу в корневой элемент
                root.appendChild(user);

                // Записываем XML в файл
                writeDocument(document);
            }

        } catch (Exception e) {

        }


    }
    private static void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream("Users.xml");
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }

    /// Записываем коллекцию в файл user.csv
@Deprecated
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

            writer = new FileWriter(file,true);//запись с добавлением строки в конец существующего текста
            for (Object user : users)//перебираем коллекцию и выбираем из нее элементы
            {
                User usr = (User) user;
                String userStr = usr.getName() + "," + usr.getUserRole() + System.lineSeparator();//создаем строку с разделительными символами и переносом строки

                writer.append(userStr);//записываем указанную строку
            }

        } catch (IOException e) {

        }
        finally {
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

    public static ArrayList<User> readXmlUser() throws ParserConfigurationException, IOException, SAXException {
        var defaultUser = new User("defaultUser", MANAGER);
        var tmpList = new ArrayList<User>();
        tmpList.add(defaultUser);

    String filepath = ".\\Data2\\Users.xml";
    File xmlFile = new File(filepath);
    if (!isFileExists(xmlFile))
        return tmpList;

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();

    Document doc = db.parse(xmlFile);
    Element root = doc.getDocumentElement();
    NodeList nl = root.getChildNodes();


        List<User> users = new ArrayList<User>();
            User user = null;
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                if ((node.getNodeType() == Node.ELEMENT_NODE)) {//проверяем является ли Элемент Нод узлу Элемент
                    Element element = (Element) node;//если нода- это элемент, то приводим ее к типу Элемент
                    try {

                        user = new User(element.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue(), UserRole.valueOf(element.getElementsByTagName("userrole").item(0).getChildNodes().item(0).getNodeValue()));//создали  объект

                    } catch (Exception e) {
                        System.out.println("Не соответствует формат введенной строки!");
                    }

                    if (user != null) {
                        users.add(user);// добавили объект в коллекцию
                    }
                }
            }
            if (users.size() == 0) {
                return tmpList;
            }

            return (ArrayList<User>) users;

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

    public User userGet(String name) throws IOException
    {
        for (var record : readFileUser())
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












