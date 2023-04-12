package myApp.App;


import myApp.Persistence.Repository;
import myApp.models.RecordHib;
import myApp.models.UserHib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import java.util.*;
import java.io.IOException;

public class Program {

    public static Repository mem;

    public static void main(String[] args) throws IOException, SAXException  {
        int userRole = 0;

        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://ubmicro.lan:5432/testdb");
        configuration.setProperty("hibernate.connection.username", "tanya");
        configuration.setProperty("hibernate.connection.password", "Q123456");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");

        configuration.addAnnotatedClass(UserHib.class);
        configuration.addAnnotatedClass(RecordHib.class);
        configuration.setProperty("hibernate.show_sql", "true");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        mem = new Repository();
        session.save((mem.addFakeDataUser()));
        UserHib userHib = session.createQuery("from UserHib user where user.id = 1", UserHib.class).getSingleResult();
        if(userHib==null) {
            session.save(mem.addFakeDataRecord(userHib));
            session.getTransaction().commit();
            System.out.println(userHib);
            List<RecordHib> records = session.createQuery("from RecordHib rec", RecordHib.class).getResultList();
            System.out.println(records.get(0));
        }


//        controlRole(fill);
//    }

//    public static void controlRole(FileRepository userReturn) throws IOException, SAXException, JAXBException//контроль вводимой роли при входе в программу
//    {
//        Scanner inpt;
//     do {
//            try {
//                System.out.println("Введите ваше имя");
//                inpt = new Scanner(System.in);
//                String name = inpt.nextLine();
//
//               polzovatel = userReturn.userGet(name);
//                if (polzovatel == null)
//              System.out.println("Пользователь с таким именем не существует");
//            } catch (Exception e) {
//
//            }
//    }
//       while (polzovatel == null);
//        displayMenu(polzovatel.getUserRole());
//    }

//    private static UserRole inputRole() {
//        UserRole enterUser = UserRole.DEFAULT;
//        Scanner inp;
//        do {
//            System.out.println("\n Введите 0, если менеджер \n Введите 1, если сотрудник \n Введите 2, если фрилансер");
//            inp = new Scanner(System.in);
//            String inputRole = inp.nextLine();
//            {
//
//                if (inputRole.equals("0")) {
//                    enterUser = UserRole.MANAGER;
//                    break;
//                } else if (inputRole.equals("1")) {
//                    enterUser = UserRole.EMPLOYEE;
//                    break;
//                } else if (inputRole.equals("2")) {
//                    enterUser = UserRole.FREELANCER;
//                    break;
//                } else
//                    System.out.println("Вы ввели несуществующую роль");
//            }
//        } while (true);
//
//        return enterUser;
//    }

//    private static void displayMenu(UserRole userRole) throws IOException, SAXException, JAXBException {
//        do {
//            if (userRole == UserRole.MANAGER) {
//                System.out.println("Меню Руководитель");
//                showManagerMenu();
//                break;
//            }
//            if (userRole == UserRole.EMPLOYEE) {
//                System.out.println("Меню Сотрудник");
//                showEmployeeMenu();
//                break;
//            }
//            if (userRole == UserRole.FREELANCER) {
//                System.out.println("Меню Фрилансер");
//                showFreelancerMenu();
//                break;
//            }
//        }
//        while (true);
//    }

//    private static void showManagerMenu() throws IOException, SAXException, JAXBException {
//        int actionManager = 0;
//        Scanner inp;
//        do {
//            System.out.println("Выберите действие  \n " +
//                    "Введите 1, если вы хотите добавить сотрудника \n " +
//                    "Введите 2, если вы хотите добавить время сотруднику \n " +
//                    "Введите 3, если вы хотите посмотреть отчет по всем сотрудникам (возможность выбрать период) \n " +
//                    "Введите 4, если вы хотите посмотреть часы работы сотрудника \n " +
//                    "Введите 5, если вы хотите выйти из программы");
//            try {
//
//                inp = new Scanner(System.in);
//                String enterManager = inp.nextLine();
//                actionManager = Integer.parseInt(enterManager);
//            } catch (Exception e) {
//                System.out.println("Неверный формат данных");
//
//            }
//            if (actionManager == 1) {
//                addWorker();
//                break;
//            } else if (actionManager == 2) {
//                addWorkerHour();
//                break;
//            } else if (actionManager == 3) {
//                watchWorkerReport();
//                break;
//            } else if (actionManager == 4) {
//                watchWorkerHour();
//                break;
//            } else if (actionManager == 5) {
//                System.out.println("Вы вышли из приложения!");
//                System.exit(0);
//
//                break;
//            }
//        }
//
//        while (true);
//    }

//    private static void showEmployeeMenu() throws IOException, SAXException, JAXBException {
//        int actionEmployee = 0;
//        Scanner inp;
//        do {
//            System.out.println("Выберите действие  \n " +
//                    "Введите 1, если вы хотите ввести часы \n " +
//                    "Введите 2, если вы хотите просмотреть часы");
//            try {
//
//                inp = new Scanner(System.in);
//                String enterEmployee = inp.nextLine();
//                actionEmployee = Integer.parseInt(enterEmployee);
//            } catch (Exception e) {
//                System.out.println("Вы ввели неверный формат!");
//            }
//            if (actionEmployee == 1) {
//                addStaffHour();
//                break;
//            } else if (actionEmployee == 2) {
//                watchStaffHour();
//                break;
//            }
//        }
//        while (true);
//    }

//    private static void showFreelancerMenu() throws IOException, SAXException, JAXBException {
//        int actionFreelancer = 0;
//        Scanner inp;
//        do {
//            System.out.println("Выберите действие  \n " +
//                    "Введите 1, если вы хотите ввести часы \n " +
//                    "Введите 2, если вы хотите просмотреть часы");
//            try {
//
//                inp = new Scanner(System.in);
//                String enterFreelancer = inp.nextLine();
//                actionFreelancer = Integer.parseInt(enterFreelancer);
//            } catch (Exception e) {
//                System.out.println("Вы ввели неверный формат!");
//            }
//
//            if (actionFreelancer == 1) {
//                addStaffHour();
//                break;
//            } else if (actionFreelancer == 2) {
//                watchStaffHour();
//                break;
//            }
//
//        }
//        while (true);
//    }

//    private static void menuUp() throws IOException, SAXException, JAXBException {
//        int choice;
//
//
//        Scanner inp;
//        System.out.println("Выберите действие  \n " +
//                "Введите 1, если вы хотите продолжить \n " +
//                "Введите любое значение, если вы хотите выйти из меню");
//        do {
//            try {
//                inp = new Scanner(System.in);
//                String enterChoice = inp.nextLine();
//                choice = Integer.parseInt(enterChoice);
//            } catch (Exception e) {
//                System.out.println("Неверный формат данных");
//                System.out.println("Попробуйте ввести другое значение!");
//                //  System.exit(0);
//                continue;
//
//            }
//            if (choice == 1) {
//                if (polzovatel.getUserRole() == UserRole.MANAGER) {
//                    showManagerMenu();
//                }
//                if (polzovatel.getUserRole() == UserRole.FREELANCER) {
//                    showFreelancerMenu();
//                }
//                if (polzovatel.getUserRole() == UserRole.EMPLOYEE) {
//                    showEmployeeMenu();
//                }
//
//            } else
//                System.out.println("Работа завершена");
//
//            System.exit(0);
//        }
//        while (true);
//    }
//
//    private static void watchStaffHour() throws IOException, SAXException, JAXBException {
//        watchHour();
//        menuUp();
//    }
//
//    private static void watchHour() throws IOException, JAXBException {
//        List<TimeRecord> HH = fill.readXmlRecord(polzovatel.getUserRole().ordinal());//!!!метод вернул коллекцию, сохранили в переменную
//
//        LocalDate startDate;
//        LocalDate endDate;
//        Scanner inp;
//
//        do {
//            try {
//                System.out.println("Введите дату начала отчета");
//
//                inp = new Scanner(System.in);
//                String inpStartDate = inp.nextLine();
//
//                if (inpStartDate == null && inpStartDate.isEmpty()) {
//                    System.out.println("Дата должна быть введена!");
//                }
//                if (!(inpStartDate == null && inpStartDate.isEmpty())) {
//                    startDate = LocalDate.parse(inpStartDate);
//                } else {
//                    System.out.println("Введенная дата неверная!");
//                    continue;//пропускаем все условия ниже и переходим в конец цикла
//                }
//                System.out.println("Введите дату окончания отчета");
//                inp = new Scanner(System.in);
//                String inpEndDate = inp.nextLine();
//
//                if (inpEndDate == null && inpEndDate.isEmpty()) {
//                    System.out.println("Дата должна быть введена!");
//                }
//                if (!(inpEndDate == null && inpEndDate.isEmpty())) {
//                    endDate = LocalDate.parse(inpEndDate);
//
//                } else {
//                    System.out.println("Введенная дата неверная!");
//                    continue;
//                }
//                if (Helpers.getMilliSecFromDate(endDate) < Helpers.getMilliSecFromDate(startDate)) {
//                    System.out.println("Вы  вводите некорректную дату");
//                } else
//                    break;
//
//            } catch (Exception e) {
//                System.out.println("Введен неверный формат!");
//            }
//        }
//        while (true);
//
//        for (var item : HH) {
//          if (Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(startDate) && Helpers.getMilliSecFromDate(item.getDate()) <= Helpers.getMilliSecFromDate(endDate)) {
//                if (item.getName().equals(polzovatel.getName())) {
//                    System.out.println(item.getDate().toString() + "\t" + item.getName() + "\t" + item.getHours() + "\t" + item.getMessage());
//                }
//            }
//        }
//    }

//    private static void addStaffHour() throws IOException, SAXException, JAXBException {
//        addHour();
//        menuUp();
//    }

//    private static void addHour() throws IOException {
//        int hour;
//        LocalDate date;
//        Scanner inp;
//        do {
//            try {
//
//                System.out.println("Введите отработанное время");
//                inp = new Scanner(System.in);
//                String enterH = inp.nextLine();
//                hour = Integer.parseInt(enterH);
//
//                if (hour <= 0 || hour >= 24) {
//                    System.out.println("Вы вводите некорректные данные");
//                    continue;
//                }
//                System.out.println("Введите дату");
//                inp = new Scanner(System.in);
//                String enterDate = inp.nextLine();
//                date = LocalDate.parse(enterDate);
//
//                if (date != LocalDate.MIN && Helpers.getMilliSecFromDate(date) <= Helpers.getMilliSecFromDate(LocalDate.now()) && polzovatel.getUserRole() == UserRole.EMPLOYEE) {
//                    addHourWithControlDate(polzovatel, hour, date);
//                    break;
//                } else if (date != LocalDate.MAX && Helpers.getMilliSecFromDate(date) <= Helpers.getMilliSecFromDate(LocalDate.now()) && Helpers.getMilliSecFromDate(date) >= Helpers.getMilliSecFromDate(LocalDate.now().minusDays(2)) && polzovatel.getUserRole() == UserRole.FREELANCER) {
//                    addHourWithControlDate(polzovatel, hour, date);
//                    break;
//                } else {
//                    System.out.println("Дата введена некорректно!");
//
//                }
//            } catch (Exception e) {
//                System.out.println("Введен неверный формат!");
//                break;
//            }
//        }
//        while (true);
//    }

//    private static void addWorkerHour() throws IOException, JAXBException {
//        UserXML worker;
//        LocalDate date;
//        int hour;
//        Scanner inn;
//        do {
//            System.out.println("*************************************************");
//            System.out.println("Введите пользователя");
//            inn = new Scanner(System.in);
//            String name = inn.nextLine();
//            worker = fill.userGet(name);
//
//            if (worker == null) {
//                System.out.println("Пользователь не существует");
//                return;
//            }
//            try {
//
//                System.out.println("Введите дату");
//                inn = new Scanner(System.in);
//                String inputDateString = inn.nextLine();
//                date = LocalDate.parse(inputDateString);
//
//                if (inputDateString == null && inputDateString.isEmpty()) {
//
//                    System.out.println("Дата должна быть введена");
//                    continue;
//                } else if (Helpers.getMilliSecFromDate(date) > Helpers.getMilliSecFromDate(LocalDate.now())) {
//                    System.out.println("Введенная дата неверная!");
//                    continue;
//                }
//                System.out.println("Введите отработанное время");
//                inn = new Scanner(System.in);
//                String enterHour = inn.nextLine();
//                hour = Integer.parseInt(enterHour);
//
//                if (hour == 0 || hour >= 24) {
//                    System.out.println("Введено неверное количество часов!");
//                    continue;
//                }
//                addHourWithControlDate(worker, hour, date);
//                menuUp();
//            } catch (Exception e) {
//                System.out.println("Введен неверный формат!");
//            }
//        }
//        while (true);
//    }

//    private static void addHourWithControlDate(UserXML Us, int H, LocalDate date) throws IOException {
//        Scanner inn;
//        do {
//            System.out.println("Введите сообщение");
//            inn = new Scanner(System.in);
//            String mas = inn.nextLine();
//
//            if (mas == null || mas.isEmpty()) {
//                System.out.println("Поле должно быть заполнено!");
//
//            } else {
//                var time = new TimeRecord(date, Us.getName(), H, mas);
//                List<TimeRecord> times = new ArrayList<>();
//                times.add(time);
//                fill.fillRecordHib((ArrayList<TimeRecord>) times, Us.getUserRole().ordinal(), true);
//                break;
//            }
//        }
//        while (true);
//    }

//    private static void addWorker() throws IOException, SAXException, JAXBException {
//        Scanner inn;
//        String enterName;
//        do {
//            System.out.println("Введите имя пользователя");
//            try {
//                inn = new Scanner(System.in);
//                enterName = inn.nextLine();
//                if (enterName.length() == 0) {
//                    System.out.println("Поле не может быть пустым");
//                } else {
//                    break;
//                }
//            } catch (Exception e) {
//
//            }
//        }
//        while (true);
//
//        var currentUser = fill.userGet(enterName);
//
//        if (currentUser == null) {
//
//        } else {
//            System.out.println("Такой пользователь существует!");
//            menuUp();
//        }
//        System.out.println("Введите роль пользователя");
//        var IR = inputRole();
//        var user = new UserXML(enterName, IR);
//        ArrayList<UserXML> users = new ArrayList<>();
//        users.add(user);
//        fill.fillXmlUser(users, true);//режим true- введенный пользователь добавляется в файл
//
//        Map<UserRole, ArrayList<String>> groupWorkRep = new HashMap<>();
//
//        var groupUser = fill.readXmlUser();
//        for (var groupItem : groupUser) {
//            //проверяем наличие Ключа, если его нет, добавляем и ключ, и значение
//            if (!groupWorkRep.containsKey(groupItem.getUserRole())) {
//                var itemsList = new ArrayList<String>();
//                itemsList.add(groupItem.getName());
//                groupWorkRep.put(groupItem.getUserRole(), itemsList);
//            } else//иначе ключ есть, просто обновляем значение
//            {
//                var itemsList = groupWorkRep.get(groupItem.getUserRole());
//                if (!itemsList.contains(groupItem.getName()))
//                    itemsList.add(groupItem.getName());
//            }
//        }
//        for (var groupWork : groupWorkRep.entrySet()) {
//            if (groupWork.getKey() == user.getUserRole()) {
//                System.out.println("-------------------------");
//                System.out.println(groupWork.getKey());
//                System.out.println("-------------------------");
//            }
//            if (groupWork.getKey() == user.getUserRole())
//                for (var item : groupWork.getValue()) {
//
//                    System.out.println(item);
//                }
//        }
//        System.out.println("-------------------------");
//    menuUp();
//    }

//    private static void watchWorkerReport() throws IOException, SAXException, JAXBException {
//        LocalDate startDate;
//        LocalDate endDate;
//        int itogHour = 0;
//        double itogTotalPay = 0;
//        Scanner inn;
//        do {
//            try {
//                System.out.println("Введите дату начала отчета");
//                inn = new Scanner(System.in);
//                String enterStartDate = inn.nextLine();
//
//                if (enterStartDate == null && enterStartDate.isEmpty()) {
//                    System.out.println("Дата должна быть введена!");
//                }
//                if (!(enterStartDate == null && enterStartDate.isEmpty())) {
//
//                    startDate = LocalDate.parse(enterStartDate);
//                } else {
//                    System.out.println("Вы вводите некорректные данные");
//                    continue;
//                }
//                System.out.println("Введите дату окончания отчета");
//                inn = new Scanner(System.in);
//                String enterEndtDate = inn.nextLine();
//
//                if (enterEndtDate == null && enterEndtDate.isEmpty()) {
//                    System.out.println("Дата должна быть введена!");
//                }
//                if (!(enterEndtDate == null && enterEndtDate.isEmpty())) {
//
//                    endDate = LocalDate.parse(enterEndtDate);
//                } else {
//                    System.out.println("Вы вводите некорректные данные");
//                    continue;
//                }
//                if (Helpers.getMilliSecFromDate(endDate) < Helpers.getMilliSecFromDate(startDate)) {
//                    System.out.println("Вы  вводите некорректную дату");
//
//                } else
//                    break;
//            } catch (Exception e) {
//
//            }
//        }
//        while (true);
//
//        ArrayList<TimeRecord> allWorkRep = new ArrayList<>();//создали новую общую коллекцию (пустая)
//        List<TimeRecord> allWork = fill.readXmlRecord(-1);//вычитываем все файлы в коллекцию allWork
//        allWorkRep.addAll(allWork);//добавляем группу элементов коллекции allWork в общую коллекцию allWorkRep
//
//        Map<String, List<TimeRecord>> workMap = new HashMap<>();//создаем новый словарь (пока пустой), в котором тип
//        //Ключа строка(фильтруем по имени, так как в нашем приложении оно уникально), тип Значения Список(List<>)
//
//        for (var workItem : allWorkRep)//перебираем общую коллекцию и каждый ее элемент кладем в переменную workItem
//        {
//            if (Helpers.getMilliSecFromDate(workItem.getDate()) >= Helpers.getMilliSecFromDate(startDate) && Helpers.getMilliSecFromDate(workItem.getDate()) <= Helpers.getMilliSecFromDate(endDate))//фильтруем дату отчета
//                if (!workMap.containsKey(workItem.getName()))//проверяем наличие Ключа, если его нет
//                {
//                    var itemList = new ArrayList<TimeRecord>();
//                    itemList.add((workItem));
//                    workMap.put(workItem.getName(), itemList);
//                } else//иначе ключ есть
//                {
//                    List<TimeRecord> items = workMap.get(workItem.getName());
//                    if (!items.contains(workItem.getName())) {
//                        items.add(workItem);
//                    }
//                }
//        }
//
//        for (var sortWork : workMap.entrySet()) {
//            var repHour = fill.userGet(sortWork.getKey());//получаем имя-ключ из словаря и значение кладем в переменную rephour
//            var HH = sortWork.getValue();//значение из словаря положили в переменную HH
//            double bonus = 0;
//
//            if (repHour.getUserRole() == UserRole.MANAGER)//проверяем роль через имя
//            {
//                var totp = new Manager(repHour, HH, startDate, endDate);//создаем новый экземпляр типа Manager
//                System.out.println("");
//                System.out.println("--------------------------------------");
//                System.out.println("Сотрудник \t" + sortWork.getKey());
//                totp.printRepPerson();
//
//                System.out.println("Всего отработано часов\t" + totp.sumHours);//итоговое время по конкретному сотруднику
//                System.out.println("Всего заработано рублей\t" + totp.getTotalPay());//итоговая зп по конкретному сотруднику
//                itogHour += totp.sumHours;//итоговое время по всем независимо от роли
//                itogTotalPay += totp.getTotalPay();//итоговая з/п по всем независимо от роли
//
//
//            } else if (repHour.getUserRole() == UserRole.EMPLOYEE) {
//                var totp = new Employee(repHour, HH, startDate, endDate, bonus);
//                System.out.println("");
//                System.out.println("--------------------------------------");
//                System.out.println("Сотрудник \t" + sortWork.getKey());
//                totp.printRepPerson();
//
//                System.out.println("Всего отработано часов\t" + totp.sumHours);
//                System.out.println("Всего заработано рублей\t" + totp.getTotalPay());
//                itogHour += totp.sumHours;
//                itogTotalPay += totp.getTotalPay();
//
//            } else if (repHour.getUserRole() == UserRole.FREELANCER) {
//                var totp = new Freelancer(repHour, HH, startDate, endDate);
//                System.out.println("");
//                System.out.println("--------------------------------------");
//                System.out.println("Сотрудник \t" + sortWork.getKey());
//                totp.printRepPerson();
//
//                System.out.println("Всего отработано часов\t" + totp.sumHours);
//                System.out.println("Всего заработано рублей\t" + totp.getTotalPay());
//                itogHour += totp.sumHours;
//                itogTotalPay += totp.getTotalPay();
//            }
//        }
//        System.out.println("");
//        System.out.println("--------------------------------------");
//        System.out.println("Всего отработано часов\t" + itogHour);
//        System.out.println("Всего заработано рублей\t" + itogTotalPay);
//
//        menuUp();
//    }

//    private static void watchWorkerHour() throws IOException, SAXException, JAXBException {
//        LocalDate startDate;
//        LocalDate endDate;
//        Scanner inn;
//        do {
//            try {
//                System.out.println("Введите дату начала отчета");
//                inn = new Scanner(System.in);
//                String enterStartDate = inn.nextLine();
//
//                if ((enterStartDate == null && enterStartDate.isEmpty())) {
//                    System.out.println("Дата должна быть введена!");
//                }
//                if (!(enterStartDate == null && enterStartDate.isEmpty())) {
//                    startDate = LocalDate.parse(enterStartDate);
//                } else {
//                    System.out.println("Вы вводите некорректные данные");
//                    continue;
//                }
//                System.out.println("Введите дату окончания отчета");
//                inn = new Scanner(System.in);
//                String enterEndDate = inn.nextLine();
//
//                if ((enterEndDate == null && enterEndDate.isEmpty())) {
//                    System.out.println("Дата должна быть введена!");
//                }
//                if (!(enterEndDate == null && enterEndDate.isEmpty())) {
//                    endDate = LocalDate.parse(enterEndDate);
//                } else {
//                    System.out.println("Вы вводите некорректные данные");
//                    continue;
//                }
//                if (Helpers.getMilliSecFromDate(endDate) < Helpers.getMilliSecFromDate(startDate)) {
//                    System.out.println("Вы  вводите некорректную дату");
//                } else
//                    break;
//            } catch (Exception e) {
//
//            }
//        }
//        while (true);
//
//        UserXML repHour;
//        double bonus = 0;
//        Scanner inp;
//        do {
//            try {
//                System.out.println("---------------------");
//                System.out.println("Введите пользователя");
//
//                inp = new Scanner(System.in);
//                String inputString = inp.nextLine();
//                if (inputString.isEmpty()) {
//                    System.out.println("Пользователь должен быть введен!");
//                    continue;
//                } else {
//                    System.out.println("---------------------");
//                    repHour = fill.userGet(inputString);
//                }
//                if (repHour == null) {
//                    System.out.println("Пользователь не существует");
//                } else {
//                    break;
//                }
//            } catch (Exception e) {
//
//            }
//        }
//        while (true);
//
//        var HH = fill.readXmlRecord(repHour.getUserRole().ordinal());
//        if (repHour.getUserRole() == UserRole.MANAGER) {
//
//            var totp = new Manager(repHour, HH, startDate, endDate);
//            totp.printRepPerson();
//            System.out.println("Всего отработано часов\t" + totp.sumHours);
//            System.out.println("Всего заработано рублей\t" + totp.getTotalPay());
//
//        } else if (repHour.getUserRole() == UserRole.EMPLOYEE) {
//            var totp = new Employee(repHour, HH, startDate, endDate, bonus);
//            totp.printRepPerson();
//            System.out.println("Всего отработано часов\t" + totp.sumHours);
//            System.out.println("Всего заработано рублей\t" + totp.getTotalPay());
//
//        } else if (repHour.getUserRole() == UserRole.FREELANCER) {
//            var totp = new Freelancer(repHour, HH, startDate, endDate);
//            totp.printRepPerson();
//            System.out.println("Всего отработано часов\t" + totp.sumHours);
//            System.out.println("Всего заработано рублей\t" + totp.getTotalPay());
//        }
//        menuUp();
    }
}