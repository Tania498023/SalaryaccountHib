package myApp.App;


import myApp.Persistence.Repository;
import myApp.SoftwareDevelopDomain.Helpers;
import myApp.models.RecordHib;
import myApp.models.UserHib;
import myApp.models.UserRoleHib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xml.sax.SAXException;

import java.time.LocalDate;
import java.util.*;
import java.io.IOException;

public class Program {

    public static Repository mem;
    public static UserHib globalUserHib;

    public static void main(String[] args) throws IOException, SAXException {
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

        List<UserHib> userHibList = session.createQuery("from UserHib user ", UserHib.class).getResultList();//список объектов user из таблицы(БД)
        List<RecordHib> recHibList = session.createQuery("from RecordHib rec", RecordHib.class).getResultList();
        if (userHibList == null) {
            session.save((mem.addFakeDataUser()));
            globalUserHib = session.createQuery("from UserHib user ", UserHib.class).getSingleResult();
            session.save(mem.addFakeDataRecord(globalUserHib));
            session.getTransaction().commit();
            System.out.println(globalUserHib);
            List<RecordHib> records = session.createQuery("from RecordHib rec", RecordHib.class).getResultList();
            System.out.println(records.get(0));
        }


        controlRole(session, userHibList,recHibList);

    }

    public static void controlRole(Session session, List<UserHib> usHib,List<RecordHib> cRole) throws IOException {
        //вводим имя пользователя, по имени пользователя получили объект
        //по роли объекта через метод displayMenu входим в программу
//      контроль вводимой роли при входе в программу

        Scanner inpt;
        do {
            try {
                System.out.println("Введите ваше имя");
                inpt = new Scanner(System.in);
                String name = inpt.nextLine();

                for (UserHib item : usHib) {
                    if (item.getLastName().equals(name)) {
                        globalUserHib = item;
                        break;
                    }
                }
                if (globalUserHib == null) {
                    System.out.println("Пользователь с таким именем не существует");
                }
            } catch (Exception e) {

            }
        }
        while (globalUserHib == null);
        displayMenu(session,globalUserHib.getUserRoleHib(), usHib,cRole);

    }

    private static UserRoleHib inputRole() {
        UserRoleHib enterUser = UserRoleHib.DEFAULT;
        Scanner inp;
        do {
            System.out.println("\n Введите 0, если менеджер \n Введите 1, если сотрудник \n Введите 2, если фрилансер");
            inp = new Scanner(System.in);
            String inputRole = inp.nextLine();
            {

                if (inputRole.equals("0")) {
                    enterUser = UserRoleHib.MANAGER;
                    break;
                } else if (inputRole.equals("1")) {
                    enterUser = UserRoleHib.EMPLOYEE;
                    break;
                } else if (inputRole.equals("2")) {
                    enterUser = UserRoleHib.FREELANCER;
                    break;
                } else
                    System.out.println("Вы ввели несуществующую роль");
            }
        } while (true);

        return enterUser;
    }

    private static void displayMenu(Session session,UserRoleHib userRole,  List<UserHib> dispMenu,List<RecordHib> dM) throws IOException {
        do {

            if (userRole == UserRoleHib.MANAGER) {
                System.out.println("Меню Руководитель");
                showManagerMenu(session, dispMenu,dM);
                break;
            }
            if (userRole == UserRoleHib.EMPLOYEE) {
                System.out.println("Меню Сотрудник");
                showEmployeeMenu(session, dispMenu,dM);
                break;
            }
            if (userRole == UserRoleHib.FREELANCER) {
                System.out.println("Меню Фрилансер");
                showFreelancerMenu(session, dispMenu,dM);
                break;
            }
        }
        while (true);
    }

    private static void showManagerMenu(Session session, List<UserHib> managMenu,List<RecordHib> showManagerMenu) throws IOException {
        int actionManager = 0;
        Scanner inp;
        do {
            System.out.println("Выберите действие  \n " +
                    "Введите 1, если вы хотите добавить сотрудника \n " +
                    "Введите 2, если вы хотите добавить время сотруднику \n " +
                    "Введите 3, если вы хотите посмотреть отчет по всем сотрудникам (возможность выбрать период) \n " +
                    "Введите 4, если вы хотите посмотреть часы работы сотрудника \n " +
                    "Введите 5, если вы хотите выйти из программы");
            try {

                inp = new Scanner(System.in);
                String enterManager = inp.nextLine();
                actionManager = Integer.parseInt(enterManager);
            } catch (Exception e) {
                System.out.println("Неверный формат данных");

            }
            if (actionManager == 1) {
                addWorker(session, managMenu,showManagerMenu);
                break;
            } else if (actionManager == 2) {
                addWorkerHour(session, managMenu,showManagerMenu);
                break;
            } else if (actionManager == 3) {
                  watchWorkerReport(session,managMenu,showManagerMenu);
                break;
            } else if (actionManager == 4) {
                     watchWorkerHour(session,managMenu,showManagerMenu);
                break;
            } else if (actionManager == 5) {
                System.out.println("Вы вышли из приложения!");
                System.exit(0);

                break;
            }
        }

        while (true);
    }

    private static void showEmployeeMenu(Session session, List<UserHib> hEm,List<RecordHib> showEmployeeMenu ) throws IOException {
        int actionEmployee = 0;
        Scanner inp;
        do {
            System.out.println("Выберите действие  \n " +
                    "Введите 1, если вы хотите ввести часы \n " +
                    "Введите 2, если вы хотите просмотреть часы");
            try {

                inp = new Scanner(System.in);
                String enterEmployee = inp.nextLine();
                actionEmployee = Integer.parseInt(enterEmployee);
            } catch (Exception e) {
                System.out.println("Вы ввели неверный формат!");
            }
            if (actionEmployee == 1) {
                addStaffHour(session, hEm,showEmployeeMenu);
                break;
            } else if (actionEmployee == 2) {
                watchStaffHour(session, hEm,showEmployeeMenu);
                break;
            }
        }
        while (true);
    }

    private static void showFreelancerMenu(Session session, List<UserHib> sFm,List<RecordHib>showFreelancerMenu) throws IOException {
        int actionFreelancer = 0;
        Scanner inp;
        do {
            System.out.println("Выберите действие  \n " +
                    "Введите 1, если вы хотите ввести часы \n " +
                    "Введите 2, если вы хотите просмотреть часы");
            try {

                inp = new Scanner(System.in);
                String enterFreelancer = inp.nextLine();
                actionFreelancer = Integer.parseInt(enterFreelancer);
            } catch (Exception e) {
                System.out.println("Вы ввели неверный формат!");
            }

            if (actionFreelancer == 1) {
                addStaffHour(session, sFm,showFreelancerMenu);
                break;
            } else if (actionFreelancer == 2) {
                watchStaffHour(session, sFm,showFreelancerMenu);
                break;
            }

        }
        while (true);
    }

    private static void menuUp(Session session, List<UserHib> menuUp,List<RecordHib>mUp) throws IOException {
        int choice;


        Scanner inp;
        System.out.println("Выберите действие  \n " +
                "Введите 1, если вы хотите продолжить \n " +
                "Введите любое значение, если вы хотите выйти из меню");
        do {
            try {
                inp = new Scanner(System.in);
                String enterChoice = inp.nextLine();
                choice = Integer.parseInt(enterChoice);
            } catch (Exception e) {
                System.out.println("Неверный формат данных");
                System.out.println("Попробуйте ввести другое значение!");
                continue;

            }
            if (choice == 1) {
                if (globalUserHib.getUserRoleHib() == UserRoleHib.MANAGER) {
                    showManagerMenu(session, menuUp,mUp);
                }
                if (globalUserHib.getUserRoleHib() == UserRoleHib.FREELANCER) {
                    showFreelancerMenu(session, menuUp,mUp);
                }
                if (globalUserHib.getUserRoleHib() == UserRoleHib.EMPLOYEE) {
                    showEmployeeMenu(session, menuUp,mUp);
                }

            } else
                System.out.println("Работа завершена");

            System.exit(0);
        }
        while (true);
    }

    private static void watchStaffHour(Session session, List<UserHib> wSh,List<RecordHib> watchStaffHour) throws IOException {
        watchHour(session);
        menuUp(session, wSh,watchStaffHour);
    }

    private static void watchHour(Session session) throws IOException {
        List<RecordHib> recordHib_rec = session.createQuery("from RecordHib rec", RecordHib.class).getResultList();
        LocalDate startDate;
        LocalDate endDate;
        Scanner inp;

        do {
            try {
                System.out.println("Введите дату начала отчета");

                inp = new Scanner(System.in);
                String inpStartDate = inp.nextLine();

                if (inpStartDate == null && inpStartDate.isEmpty()) {
                    System.out.println("Дата должна быть введена!");
                }
                if (!(inpStartDate == null && inpStartDate.isEmpty())) {
                    startDate = LocalDate.parse(inpStartDate);
                } else {
                    System.out.println("Введенная дата неверная!");
                    continue;//пропускаем все условия ниже и переходим в конец цикла
                }
                System.out.println("Введите дату окончания отчета");
                inp = new Scanner(System.in);
                String inpEndDate = inp.nextLine();

                if (inpEndDate == null && inpEndDate.isEmpty()) {
                    System.out.println("Дата должна быть введена!");
                }
                if (!(inpEndDate == null && inpEndDate.isEmpty())) {
                    endDate = LocalDate.parse(inpEndDate);

                } else {
                    System.out.println("Введенная дата неверная!");
                    continue;
                }
                if (Helpers.getMilliSecFromDate(endDate) < Helpers.getMilliSecFromDate(startDate)) {
                    System.out.println("Вы  вводите некорректную дату");
                } else
                    break;

            } catch (Exception e) {
                System.out.println("Введен неверный формат!");
            }
        }
        while (true);

        for (RecordHib item : recordHib_rec) {
            if (Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(startDate) && Helpers.getMilliSecFromDate(item.getDate()) <= Helpers.getMilliSecFromDate(endDate)) {
                if (item.getLastName().equals(globalUserHib.getLastName())) {
                    System.out.println(item.getDate().toString() + "\t" + item.getLastName() + "\t" + item.getHour() + "\t" + item.getMessage());
                }
            }
        }
    }

    private static void addStaffHour(Session session, List<UserHib> aSh,List<RecordHib>addStaffHour) throws IOException {
        addHour(session);
        menuUp(session, aSh,addStaffHour);
    }

    private static void addHour(Session session) throws IOException {
        int hour;
        LocalDate date;
        String mas = "";
        Scanner inp;
        do {
            try {

                System.out.println("Введите отработанное время");
                inp = new Scanner(System.in);
                String enterH = inp.nextLine();
                hour = Integer.parseInt(enterH);

                if (hour <= 0 || hour >= 24) {
                    System.out.println("Вы вводите некорректные данные");
                    continue;
                }
                System.out.println("Введите дату");
                inp = new Scanner(System.in);
                String enterDate = inp.nextLine();
                date = LocalDate.parse(enterDate);

                if (date != LocalDate.MIN && Helpers.getMilliSecFromDate(date) <= Helpers.getMilliSecFromDate(LocalDate.now()) && globalUserHib.getUserRoleHib() == UserRoleHib.EMPLOYEE) {
                    addHourWithControlDate(session, date, hour, mas, globalUserHib);
                    break;
                } else if (date != LocalDate.MAX && Helpers.getMilliSecFromDate(date) <= Helpers.getMilliSecFromDate(LocalDate.now()) && Helpers.getMilliSecFromDate(date) >= Helpers.getMilliSecFromDate(LocalDate.now().minusDays(2)) && globalUserHib.getUserRoleHib() == UserRoleHib.FREELANCER) {
                    addHourWithControlDate(session, date, hour, mas, globalUserHib);
                    break;
                } else {
                    System.out.println("Дата введена некорректно!");

                }
            } catch (Exception e) {
                System.out.println("Введен неверный формат!");
                break;
            }
        }
        while (true);
    }

    private static void addWorkerHour(Session session, List<UserHib> workerHour,List<RecordHib>addWorkerHour) throws IOException {

        UserHib worker;
        LocalDate date;
        int hour;
        String mas = "";
        Scanner inn;
        do {
            System.out.println("*************************************************");
            System.out.println("Введите пользователя");
            inn = new Scanner(System.in);
            String _name = inn.nextLine();

            for (UserHib item : workerHour) {
                if (item.getLastName().equals(_name)) {
                    globalUserHib = item;
                    break;
                }
            }

            if (globalUserHib == null) {
                System.out.println("Пользователь не существует");
                return;
            }
            try {

                System.out.println("Введите дату");
                inn = new Scanner(System.in);
                String inputDateString = inn.nextLine();
                date = LocalDate.parse(inputDateString);

                if (inputDateString == null && inputDateString.isEmpty()) {

                    System.out.println("Дата должна быть введена");
                    continue;
                } else if (Helpers.getMilliSecFromDate(date) > Helpers.getMilliSecFromDate(LocalDate.now())) {
                    System.out.println("Введенная дата неверная!");
                    continue;
                }
                System.out.println("Введите отработанное время");
                inn = new Scanner(System.in);
                String enterHour = inn.nextLine();
                hour = Integer.parseInt(enterHour);

                if (hour == 0 || hour >= 24) {
                    System.out.println("Введено неверное количество часов!");
                    continue;
                }
                addHourWithControlDate(session, date, hour, mas, globalUserHib);
                menuUp(session, workerHour,addWorkerHour);
            } catch (Exception e) {
                System.out.println("Введен неверный формат!");
            }
        }
        while (true);
    }

    private static void addHourWithControlDate(Session session, LocalDate date, int H, String mas, UserHib Us) throws IOException {
        Scanner inn;
        do {
            System.out.println("Введите сообщение");
            inn = new Scanner(System.in);
            mas = inn.nextLine();

            if (mas == null || mas.isEmpty()) {

                System.out.println("Поле должно быть заполнено!");

            } else {
                RecordHib time = new RecordHib(date, H, mas,globalUserHib);
                List<RecordHib> times = new ArrayList<>();
                times.add(time);
                session.save(time);
                session.getTransaction().commit();

                break;
            }
        }
        while (true);
    }

    private static void addWorker(Session session, List<UserHib> aW,List<RecordHib>addWorker) throws IOException {
        Scanner inn;
        String enterName;
        String addW = "";
        do {
            System.out.println("Введите имя пользователя");
            try {
                inn = new Scanner(System.in);
                enterName = inn.nextLine();
                if (enterName.length() == 0) {
                    System.out.println("Поле не может быть пустым");
                } else {
                    break;
                }
            } catch (Exception e) {

            }
        }
        while (true);
        for (UserHib item : aW) {
            if (!enterName.equals(item.getLastName())) {
                addW = enterName;
            }
        }

        if (addW == null) {
            System.out.println("Такой пользователь существует!");
            menuUp(session, aW,addWorker);
        }

        System.out.println("Введите роль пользователя");
        UserRoleHib IR = inputRole();
        UserHib user = new UserHib(enterName, IR);
        aW.add(user);
        session.save(user);
        session.getTransaction().commit();

        System.out.println("-------------------------");
        System.out.println(user.getUserRoleHib());
        System.out.println("-------------------------");
        for (UserHib groupWork : aW) {
            if (groupWork.getUserRoleHib() == user.getUserRoleHib()) {
                System.out.println(groupWork.getLastName());

            }
        }
        System.out.println("-------------------------");
        menuUp(session, aW,addWorker);
    }


    private static void watchWorkerReport(Session session,List<UserHib> watchWorkerReport,List<RecordHib> rec) throws IOException {
        LocalDate startDate;
        LocalDate endDate;
        int sumHours=0;
        int itogHour = 0;
        double itogTotalPay = 0;
        Scanner inn;
        do {
            try {
                System.out.println("Введите дату начала отчета");
                inn = new Scanner(System.in);
                String enterStartDate = inn.nextLine();

                if (enterStartDate == null && enterStartDate.isEmpty()) {
                    System.out.println("Дата должна быть введена!");
                }
                if (!(enterStartDate == null && enterStartDate.isEmpty())) {

                    startDate = LocalDate.parse(enterStartDate);
                } else {
                    System.out.println("Вы вводите некорректные данные");
                    continue;
                }
                System.out.println("Введите дату окончания отчета");
                inn = new Scanner(System.in);
                String enterEndtDate = inn.nextLine();

                if (enterEndtDate == null && enterEndtDate.isEmpty()) {
                    System.out.println("Дата должна быть введена!");
                }
                if (!(enterEndtDate == null && enterEndtDate.isEmpty())) {

                    endDate = LocalDate.parse(enterEndtDate);
                } else {
                    System.out.println("Вы вводите некорректные данные");
                    continue;
                }
                if (Helpers.getMilliSecFromDate(endDate) < Helpers.getMilliSecFromDate(startDate)) {
                    System.out.println("Вы  вводите некорректную дату");

                } else
                    break;
            } catch (Exception e) {

            }
        }
        while (true);

        for (RecordHib workItem : rec)//перебираем общую коллекцию и каждый ее элемент кладем в переменную workItem
        {
            if (Helpers.getMilliSecFromDate(workItem.getDate()) >= Helpers.getMilliSecFromDate(startDate) && Helpers.getMilliSecFromDate(workItem.getDate()) <= Helpers.getMilliSecFromDate(endDate))//фильтруем дату отчета
            {

                System.out.println("");
                System.out.println("--------------------------------------");
                System.out.println("Сотрудник \t" + workItem.getLastName().getLastName());
                System.out.println("Отчет по сотруднику \t" + workItem.getLastName().getLastName() + "\t" + workItem.getLastName().getUserRoleHib() + "\t за период с \t " + startDate.toString()
                        + "\t по" +"\t" + endDate.toString());

                    System.out.println(workItem.getDate().toString() + "\t" + workItem.getHour() + "\t" + workItem.getMessage());
                    sumHours +=workItem.getHour();
                    System.out.println("Всего отработано часов\t" + sumHours);

            }
        }

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
//            }

        System.out.println("");
        System.out.println("--------------------------------------");
        System.out.println("Всего отработано часов\t" + itogHour);
        System.out.println("Всего заработано рублей\t" + itogTotalPay);

        menuUp(session,watchWorkerReport,rec);
    }


    private static void watchWorkerHour(Session session,List<UserHib>watchWorkerHour,List<RecordHib> workerHour ) throws IOException{
        LocalDate startDate;
        LocalDate endDate;
        Scanner inn;
        do {
            try {
                System.out.println("Введите дату начала отчета");
                inn = new Scanner(System.in);
                String enterStartDate = inn.nextLine();

                if ((enterStartDate == null && enterStartDate.isEmpty())) {
                    System.out.println("Дата должна быть введена!");
                }
                if (!(enterStartDate == null && enterStartDate.isEmpty())) {
                    startDate = LocalDate.parse(enterStartDate);
                } else {
                    System.out.println("Вы вводите некорректные данные");
                    continue;
                }
                System.out.println("Введите дату окончания отчета");
                inn = new Scanner(System.in);
                String enterEndDate = inn.nextLine();

                if ((enterEndDate == null && enterEndDate.isEmpty())) {
                    System.out.println("Дата должна быть введена!");
                }
                if (!(enterEndDate == null && enterEndDate.isEmpty())) {
                    endDate = LocalDate.parse(enterEndDate);
                } else {
                    System.out.println("Вы вводите некорректные данные");
                    continue;
                }
                if (Helpers.getMilliSecFromDate(endDate) < Helpers.getMilliSecFromDate(startDate)) {
                    System.out.println("Вы  вводите некорректную дату");
                } else
                    break;
            } catch (Exception e) {

            }
        }
        while (true);

        int sumHours=0;
        double bonus = 0;
        Scanner inp;
        UserHib enterLastName = null;
        do {
            try {
                System.out.println("---------------------");
                System.out.println("Введите пользователя");

                inp = new Scanner(System.in);
                String inputString = inp.nextLine();
                if (inputString.isEmpty()) {
                    System.out.println("Пользователь должен быть введен!");
                    continue;
                } else {
                    for (UserHib item : watchWorkerHour) {
                        if (item.getLastName().equals(inputString)) {
                            enterLastName = item;
                        }
                    }
                }
                    if (enterLastName == null) {
                        System.out.println("Пользователь не существует");
                    } else {
                        break;
                    }

                } catch(Exception e){

                }
            }

        while (true);

        System.out.println("Отчет по сотруднику \t" + enterLastName.getLastName() + "\t" + enterLastName.getUserRoleHib() + "\t за период с \t " + startDate.toString()
                        + "\t по" +"\t" + endDate.toString());
        for (RecordHib workItem : workerHour)//перебираем общую коллекцию и каждый ее элемент кладем в переменную workItem
        {
            if (Helpers.getMilliSecFromDate(workItem.getDate()) >= Helpers.getMilliSecFromDate(startDate) && Helpers.getMilliSecFromDate(workItem.getDate()) <= Helpers.getMilliSecFromDate(endDate))//фильтруем дату отчета
            {


                if (enterLastName.getLastName().equals(workItem.getLastName().getLastName())) {

                    System.out.println(workItem.getDate().toString() + "\t" + workItem.getHour() + "\t" + workItem.getMessage());
                    sumHours += workItem.getHour();
                }
            }
        }
        System.out.println("Всего отработано часов\t" + sumHours);
        System.out.println("Всего заработано рублей\t" );
        menuUp(session,watchWorkerHour,workerHour);


    }
}