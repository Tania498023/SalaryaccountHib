package myApp.App;


import myApp.SoftwareDevelopDomain.Helpers;
import myApp.SoftwareDevelopDomain.Salary.Employee;
import myApp.SoftwareDevelopDomain.Salary.Freelancer;
import myApp.SoftwareDevelopDomain.Salary.Manager;
import myApp.SoftwareDevelopDomain.Salary.Person;
import myApp.models.RecordHib;
import myApp.models.UserHib;

import myApp.models.UserRoleHib;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.xml.sax.SAXException;

import java.time.LocalDate;
import java.util.*;
import java.io.IOException;

public class Program {

    public static MyDbWork myDbWork;
    public static UserHib globalUserHib;

    public static void main(String[] args) throws IOException, SAXException {
      myDbWork = new MyDbWork();


        controlRole();
    }

    public static void controlRole() throws IOException {
        //вводим имя пользователя, по имени пользователя получили объект
        //по роли объекта через метод displayMenu входим в программу
//      контроль вводимой роли при входе в программу

        Scanner inpt;
        do {
            try {
                System.out.println("Введите ваше имя");
                inpt = new Scanner(System.in);
                String name = inpt.nextLine();

                for (UserHib item : myDbWork.GetUserHibList()) {
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
        displayMenu(globalUserHib.getUserRoleHib(), myDbWork.GetUserHibList(), myDbWork.GetRecHibList());

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

    private static void displayMenu(UserRoleHib userRole, List<UserHib> dispMenu, List<RecordHib> dM) throws IOException {
        do {

            if (userRole == UserRoleHib.MANAGER) {
                System.out.println("Меню Руководитель");
                showManagerMenu(dispMenu, dM);
                break;
            }
            if (userRole == UserRoleHib.EMPLOYEE) {
                System.out.println("Меню Сотрудник");
                showEmployeeMenu(dispMenu, dM);
                break;
            }
            if (userRole == UserRoleHib.FREELANCER) {
                System.out.println("Меню Фрилансер");
                showFreelancerMenu(dispMenu, dM);
                break;
            }
        }
        while (true);
    }

    private static void showManagerMenu(List<UserHib> managMenu, List<RecordHib> showManagerMenu) throws IOException {
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
                addWorker(managMenu, showManagerMenu);
                break;
            } else if (actionManager == 2) {
                addWorkerHour(managMenu, showManagerMenu);
                break;
            } else if (actionManager == 3) {
                watchWorkerReport(managMenu, showManagerMenu);
                break;
            } else if (actionManager == 4) {
                watchWorkerHour(managMenu, showManagerMenu);
                break;
            } else if (actionManager == 5) {
                System.out.println("Вы вышли из приложения!");
                System.exit(0);

                break;
            }
        }

        while (true);
    }

    private static void showEmployeeMenu(List<UserHib> hEm, List<RecordHib> showEmployeeMenu) throws IOException {
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
                addStaffHour(hEm, showEmployeeMenu);
                break;
            } else if (actionEmployee == 2) {
                watchStaffHour(hEm, showEmployeeMenu);
                break;
            }
        }
        while (true);
    }

    private static void showFreelancerMenu(List<UserHib> sFm, List<RecordHib> showFreelancerMenu) throws IOException {
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
                addStaffHour(sFm, showFreelancerMenu);
                break;
            } else if (actionFreelancer == 2) {
                watchStaffHour(sFm, showFreelancerMenu);
                break;
            }

        }
        while (true);
    }

    private static void menuUp(List<UserHib> menuUp, List<RecordHib> mUp) throws IOException {
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
                    showManagerMenu(menuUp, mUp);
                }
                if (globalUserHib.getUserRoleHib() == UserRoleHib.FREELANCER) {
                    showFreelancerMenu(menuUp, mUp);
                }
                if (globalUserHib.getUserRoleHib() == UserRoleHib.EMPLOYEE) {
                    showEmployeeMenu(menuUp, mUp);
                }

            } else
                System.out.println("Работа завершена");

            System.exit(0);
        }
        while (true);
    }

    private static void watchStaffHour(List<UserHib> wSh, List<RecordHib> watchStaffHour) throws IOException {
        watchHour( wSh, watchStaffHour);
        menuUp(wSh, watchStaffHour);
    }

    private static void watchHour(List<UserHib> wHus, List<RecordHib> wHrec) throws IOException {

        LocalDate startDate = null;
        LocalDate endDate = null;
        Scanner inp;
UserHib work=null;
        do {
            try {
                System.out.println("Введите дату начала отчета");

                inp = new Scanner(System.in);
                String inpStartDate = inp.nextLine();

                if (inpStartDate == null || inpStartDate.isEmpty()) {
                    System.out.println("Дата должна быть введена!");
                    continue;
                }
                if (!(inpStartDate == null && inpStartDate.isEmpty())) {
                    startDate = LocalDate.parse(inpStartDate);
                }
                if (Helpers.getMilliSecFromDate(startDate) > Helpers.getMilliSecFromDate(LocalDate.now())) {
                    System.out.println("Введенная дата неверная!");
                    continue;//пропускаем все условия ниже и переходим в конец цикла
                }
                System.out.println("Введите дату окончания отчета");
                inp = new Scanner(System.in);
                String inpEndDate = inp.nextLine();

                if (inpEndDate == null || inpEndDate.isEmpty()) {
                    System.out.println("Дата должна быть введена!");
                    continue;
                }
                if (!(inpEndDate == null && inpEndDate.isEmpty())) {
                    endDate = LocalDate.parse(inpEndDate);

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

            if (wHrec.stream().anyMatch(x -> x.getLastName().getLastName() == globalUserHib.getLastName())) {//если в списке рекордов есть введенное имя globalUserHib, то печатаем шапку к отчету
                System.out.println("");
                System.out.println("--------------------------------------");
                System.out.println("Отчет \t за период с \t " + startDate.toString() + "\t по" + "\t" + endDate.toString());
                System.out.println("--------------------------------------");
            }

            Person person = new Person(globalUserHib,wHrec,startDate,endDate);
            person.printHourPerson();
            System.out.println("Всего отработано часов\t" + person.sumHours);
    }

    private static void addStaffHour(List<UserHib> aSh, List<RecordHib> addStaffHour) throws IOException {
        addHour();
        menuUp( aSh, addStaffHour);
    }

    private static void addHour() throws IOException {
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
                    addHourWithControlDate(date, hour, mas, globalUserHib);
                    break;
                } else if (date != LocalDate.MAX && Helpers.getMilliSecFromDate(date) <= Helpers.getMilliSecFromDate(LocalDate.now()) && Helpers.getMilliSecFromDate(date) >= Helpers.getMilliSecFromDate(LocalDate.now().minusDays(2)) && globalUserHib.getUserRoleHib() == UserRoleHib.FREELANCER) {
                    addHourWithControlDate(date, hour, mas, globalUserHib);
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

    private static void addWorkerHour(List<UserHib> workerHour, List<RecordHib> addWorkerHour) throws IOException {

        UserHib worker = null;
        LocalDate date = null;
        int hour;
        String mas = "";
        Scanner inn;
        do {
            System.out.println("*************************************************");
            System.out.println("Введите пользователя");
            inn = new Scanner(System.in);
            String _name = inn.nextLine();

            if (_name.length() == 0) {
                System.out.println("Пользователь должен быть заполнен!");
                continue;
            }
            for (UserHib item : workerHour) {
                if (item.getLastName().equals(_name)) {
                    worker = item;
                }
            }
            if (worker == null) {
                System.out.println("Пользователь не существует");
                menuUp( workerHour, addWorkerHour);


            }

            try {

                System.out.println("Введите дату");
                inn = new Scanner(System.in);
                String inputDateString = inn.nextLine();
                if ((inputDateString == null || inputDateString.isEmpty())) {
                    System.out.println("Дата должна быть введена!");
                    continue;
                }

                date = LocalDate.parse(inputDateString);

                if (Helpers.getMilliSecFromDate(date) > Helpers.getMilliSecFromDate(LocalDate.now())) {
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
                addHourWithControlDate(date, hour, mas, worker);
                menuUp(workerHour, addWorkerHour);
            } catch (Exception e) {
                System.out.println("Введен неверный формат!");
            }
        }
        while (true);
    }

    private static void addHourWithControlDate(LocalDate date, int H, String mas, UserHib Us) throws IOException {
        Scanner inn;

        do {
            System.out.println("Введите сообщение");
            inn = new Scanner(System.in);
            mas = inn.nextLine();

            if (mas == null || mas.isEmpty()) {

                System.out.println("Поле должно быть заполнено!");

            } else {
                RecordHib time = new RecordHib(date, H, mas, Us);
                List<RecordHib> times = new ArrayList<>();
                times.add(time);
                myDbWork.SaveSessionRec(time);

                break;
            }
        }
        while (true);
    }

    private static void addWorker(List<UserHib> aW, List<RecordHib> addWorker) throws IOException {
        Scanner inn;
        String enterName;
        String addW = " ";
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
            if (item.getLastName().equals(enterName)) {
                System.out.println("Такой пользователь существует!");
                menuUp(aW, addWorker);
            }

        }
        System.out.println("Введите роль пользователя");
        UserRoleHib IR = inputRole();
        UserHib user = new UserHib(enterName, IR);
        aW.add(user);
     myDbWork.SaveSessionUser(user);

        System.out.println("-------------------------");
        System.out.println(user.getUserRoleHib());
        System.out.println("-------------------------");
        for (UserHib groupWork : aW) {
            if (groupWork.getUserRoleHib() == user.getUserRoleHib()) {
                System.out.println(groupWork.getLastName());
            }
        }
        System.out.println("-------------------------");
        menuUp(aW, addWorker);

    }


    private static void watchWorkerReport(List<UserHib> watchWorkerReport, List<RecordHib> rec) throws IOException {
        LocalDate startDate;
        LocalDate endDate;
        int sumHours = 0;
        int itogHour = 0;
        double itogTotalPay = 0;
        double bonus = 0;
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

        for (UserHib usList : watchWorkerReport) {
            globalUserHib = usList;
            if (rec.stream().anyMatch(x -> x.getLastName().getLastName() == usList.getLastName())) {
                System.out.println("");
                System.out.println("--------------------------------------");
                System.out.println("Сотрудник \t" + usList.getLastName());
                System.out.println("Отчет по сотруднику \t" + globalUserHib.getLastName() + "\t" + globalUserHib.getUserRoleHib() + "\t за период с \t " + startDate.toString()
                        + "\t по" + "\t" + endDate.toString());

                if (globalUserHib.getUserRoleHib() == UserRoleHib.MANAGER){
                    Manager totp = new Manager(globalUserHib, rec, startDate, endDate);
                    totp.printHourPerson();
                    System.out.println("Всего отработано часов\t" + totp.sumHours);//итоговое время по конкретному сотруднику
                    System.out.println("Всего заработано рублей\t" + totp.getTotalPays());//итоговая зп по конкретному сотруднику
                    itogHour += totp.sumHours;//итоговое время по всем независимо от роли
                    itogTotalPay += totp.getTotalPays();//итоговая з/п по всем независимо от роли

                }
                if (globalUserHib.getUserRoleHib() == UserRoleHib.EMPLOYEE){
                    Employee totp = new Employee(globalUserHib, rec, startDate, endDate,bonus);
                    totp.printHourPerson();
                    System.out.println("Всего отработано часов\t" + totp.sumHours);//итоговое время по конкретному сотруднику
                    System.out.println("Всего заработано рублей\t" + totp.getTotalPays());//итоговая зп по конкретному сотруднику
                    itogHour += totp.sumHours;//итоговое время по всем независимо от роли
                    itogTotalPay += totp.getTotalPays();//итоговая з/п по всем независимо от роли

                }
                if (globalUserHib.getUserRoleHib() == UserRoleHib.FREELANCER){
                    Freelancer totp = new Freelancer(globalUserHib, rec, startDate, endDate);
                    totp.printHourPerson();
                    System.out.println("Всего отработано часов\t" + totp.sumHours);//итоговое время по конкретному сотруднику
                    System.out.println("Всего заработано рублей\t" + totp.getTotalPays());//итоговая зп по конкретному сотруднику
                    itogHour += totp.sumHours;//итоговое время по всем независимо от роли
                    itogTotalPay += totp.getTotalPays();//итоговая з/п по всем независимо от роли

                }

            }
        }

            System.out.println("");
            System.out.println("--------------------------------------");
            System.out.println("Всего отработано часов\t" + itogHour);
            System.out.println("Всего заработано рублей\t" + itogTotalPay);

            menuUp(watchWorkerReport, rec);
        }


    private static void watchWorkerHour(List<UserHib> watchWorkerHour, List<RecordHib> workerHour) throws IOException {
        LocalDate startDate = null;
        LocalDate endDate = null;
        Scanner inn;
        do {
            try {
                System.out.println("Введите дату начала отчета");
                inn = new Scanner(System.in);
                String enterStartDate = inn.nextLine();

                if ((enterStartDate == null || enterStartDate.isEmpty())) {
                    System.out.println("Дата должна быть введена!");
                    continue;
                }
                if (!(enterStartDate == null && enterStartDate.isEmpty())) {
                    startDate = LocalDate.parse(enterStartDate);
                }

                if (Helpers.getMilliSecFromDate(startDate) > Helpers.getMilliSecFromDate(LocalDate.now())) {
                    System.out.println("Введенная дата неверная!");
                    continue;
                }
                System.out.println("Введите дату окончания отчета");
                inn = new Scanner(System.in);
                String enterEndDate = inn.nextLine();

                if ((enterEndDate == null || enterEndDate.isEmpty())) {
                    System.out.println("Дата должна быть введена!");
                    continue;
                }
                if (!(enterEndDate == null && enterEndDate.isEmpty())) {
                    endDate = LocalDate.parse(enterEndDate);
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

        int sumHours = 0;
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

            } catch (Exception e) {

            }
        }

        while (true);

        if (enterLastName.getUserRoleHib() == UserRoleHib.MANAGER) {
            Manager totp = new Manager(enterLastName, workerHour, startDate, endDate);
            System.out.println("----------------------------------------------------");
            totp.printRepPerson();
            System.out.println("----------------------------------------------------");
            System.out.println("Всего отработано часов\t" + totp.sumHours);
            System.out.println("Всего заработано рублей\t" + totp.getTotalPays());
        }
        if (enterLastName.getUserRoleHib() == UserRoleHib.EMPLOYEE) {
            Employee totp = new Employee(enterLastName, workerHour, startDate, endDate,bonus);
            System.out.println("----------------------------------------------------");
            totp.printRepPerson();
            System.out.println("----------------------------------------------------");
            System.out.println("Всего отработано часов\t" + totp.sumHours);
            System.out.println("Всего заработано рублей\t" + totp.getTotalPays());
        }
        if (enterLastName.getUserRoleHib() == UserRoleHib.FREELANCER) {
            Freelancer totp = new Freelancer(enterLastName, workerHour, startDate, endDate);
            System.out.println("----------------------------------------------------");
            totp.printRepPerson();
            System.out.println("----------------------------------------------------");
            System.out.println("Всего отработано часов\t" + totp.sumHours);
            System.out.println("Всего заработано рублей\t" + totp.getTotalPays());
    }

    menuUp(watchWorkerHour, workerHour);

    }
}