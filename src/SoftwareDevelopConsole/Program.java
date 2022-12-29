package SoftwareDevelopConsole;

import Persistence.FileRepository;
import Persistence.MemoryRepository;
import SoftwareDevelopDomain.Helpers;
import SoftwareDevelopDomain.Person.Employee;
import SoftwareDevelopDomain.Person.Freelancer;
import SoftwareDevelopDomain.Person.Manager;
import SoftwareDevelopDomain.Person.User;
import SoftwareDevelopDomain.Person.UserRole;
import SoftwareDevelopDomain.TimeRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.io.IOException;

public class Program {
    public static FileRepository fill;
    public static User polzovatel;

    public static void main(String[] args) throws IOException {
        int userRole = 0;

        fill = new FileRepository();//создаем экземпляры для возможности вызова метода FillFileUser

        var userReturn = new MemoryRepository();//создаем экземпляры для возможности вызова метода Users
        fill.fillFileUser(Collections.singletonList(userReturn.Users()), false);


        var genericReturn = new MemoryRepository();
        fill.fillFileGeneric(genericReturn.Generic(), userRole, false);

        var text = fill.readFileUser();

        controlRole(fill);
    }

    public static void controlRole(FileRepository userReturn) throws IOException//контроль вводимой роли при входе в программу
    {
        do {

            Scanner in = new Scanner(System.in);
            System.out.println("Введите ваше имя");
            String name = String.valueOf(in.nextInt());

            in.close();

            polzovatel = userReturn.userGet(name);
            if (polzovatel == null)
                System.out.println("Пользователь с таким именем не существует");
        }
        while (polzovatel == null);
        displayMenu(polzovatel.getUserRole());

    }

    private static UserRole inputRole() {
        UserRole enterUser = UserRole.DEFAULT;
        do {
            System.out.println("\n Введите 0, если менеджер \n Введите 1, если сотрудник \n Введите 2, если фрилансер");
            Scanner in = new Scanner(System.in);
            String inputRole = String.valueOf(in.nextInt());
            in.close();

            {

                if (inputRole == "0") {
                    enterUser = UserRole.MANAGER;
                    break;
                } else if (inputRole == "1") {
                    enterUser = UserRole.EMPLOYEE;
                    break;
                } else if (inputRole == "2") {
                    enterUser = UserRole.FREELANCER;
                    break;
                } else
                    System.out.println("Вы ввели несуществующую роль");

            }
        } while (enterUser.ordinal() < UserRole.MANAGER.ordinal() || enterUser.ordinal() > UserRole.FREELANCER.ordinal());

        return enterUser;
    }

    private static void displayMenu(UserRole userRole) throws IOException {
        do {
            if (userRole == UserRole.MANAGER) {
                System.out.println("Меню Руководитель");
                showManagerMenu();
                break;
            }
            if (userRole == UserRole.EMPLOYEE) {
                System.out.println("Меню Сотрудник");
                showEmployeeMenu();
                break;
            }
            if (userRole == UserRole.FREELANCER) {
                System.out.println("Меню Фрилансер");
                showFreelancerMenu();
                break;
            }
        }
        while (true);
    }

    private static void showManagerMenu() throws IOException {
        int actionManager;
        do {
            System.out.println("Выберите действие  \n " +
                    "Введите 1, если вы хотите добавить сотрудника \n " +
                    "Введите 2, если вы хотите добавить время сотруднику \n " +
                    "Введите 3, если вы хотите посмотреть отчет по всем сотрудникам (возможность выбрать период) \n " +
                    "Введите 4, если вы хотите посмотреть часы работы сотрудника \n " +
                    "Введите 0, если вы хотите выйти из программы");

            Scanner in = new Scanner(System.in);
            String enterManager = String.valueOf(in.nextInt());
            in.close();
            actionManager = Integer.parseInt(enterManager);

            if (actionManager == 1) {

                addWorker();
                break;
            } else if (actionManager == 2) {
                addWorkerHour();
                break;
            } else if (actionManager == 3) {

                watchWorkerReport();
                break;
            } else if (actionManager == 4) {

                watchWorkerHour();
                break;
            } else if (actionManager == 0) {

                System.exit(0);

                break;
            } else
                System.out.println("Вы выбрали несуществующее действие");

        }

        while ((actionManager < 1 || actionManager > 4) && actionManager != 0);
    }

    private static void showEmployeeMenu() throws IOException {
        int actionEmployee;
        do {
            System.out.println("Выберите действие  \n " +
                    "Введите 1, если вы хотите ввести часы \n " +
                    "Введите 2, если вы хотите просмотреть часы");

            Scanner in = new Scanner(System.in);
            String enterEmployee = String.valueOf(in.nextInt());
            in.close();

            actionEmployee = Integer.parseInt(enterEmployee);
            {

                if (actionEmployee == 1) {
                    addStaffHour();
                    break;
                } else if (actionEmployee == 2) {
                    watchStaffHour();
                    break;
                } else
                    System.out.println("Вы выбрали несуществующее действие");
            }
        }

        while (actionEmployee < 1 || actionEmployee > 2);
    }

    private static void showFreelancerMenu() throws IOException {
        int actionFreelancer;
        do {
            System.out.println("Выберите действие  \n " +
                    "Введите 1, если вы хотите ввести часы \n " +
                    "Введите 2, если вы хотите просмотреть часы");
            Scanner in = new Scanner(System.in);
            String enterFreelancer = String.valueOf(in.nextInt());
            in.close();
            actionFreelancer = Integer.parseInt(enterFreelancer);

            {

                if (actionFreelancer == 1) {
                    addStaffHour();
                    break;
                } else if (actionFreelancer == 2) {
                    watchStaffHour();
                    break;
                } else
                    System.out.println("Вы выбрали несуществующее действие");
            }
        }

        while (actionFreelancer < 1 || actionFreelancer > 2);
    }

    private static void menuUp() throws IOException {
        int choice;

        System.out.println("Выберите действие  \n " +
                "Введите 1, если вы хотите продолжить \n " +
                "Введите 2, если вы хотите выйти из меню");

        Scanner in = new Scanner(System.in);
        String enterChoice = String.valueOf(in.nextInt());
        in.close();
        choice = Integer.parseInt(enterChoice);

        if (choice == 1) {
            if (polzovatel.getUserRole() == UserRole.MANAGER) {
                showManagerMenu();
            }
            if (polzovatel.getUserRole() == UserRole.FREELANCER) {
                showFreelancerMenu();
            }
            if (polzovatel.getUserRole() == UserRole.EMPLOYEE) {
                showEmployeeMenu();
            }

        } else if (choice == 2) {
            System.exit(0);
        } else
            System.out.println("Вы выбрали несуществующее действие");
        System.exit(0);
    }
    private static void watchStaffHour() throws IOException {
        watchHour();
        menuUp();
    }

    private static void watchHour()
    {
        List<TimeRecord> HH = fill.readFileGeneric(polzovatel.getUserRole().ordinal());//!!!метод вернул коллекцию, сохранили в переменную

        LocalDateTime startDate;
        LocalDateTime endDate;

        do
        {
            System.out.println("Введите дату начала отчета");
            Scanner in = new Scanner(System.in);
            String D = String.valueOf(in.nextInt());
            in.close();

            if(D == null&& D.isEmpty())
            {
                System.out.println("Дата должна быть введена!");
            }
            if (!(D == null&& D.isEmpty()))
            {
                startDate = LocalDateTime.parse(D);

            }
            else
            {
                System.out.println("Введенная дата неверная!");
                continue;
            }

            System.out.println("Введите дату окончания отчета");
            Scanner inn = new Scanner(System.in);
            String DD = String.valueOf(in.nextInt());
            in.close();

            if(DD == null&& D.isEmpty())
            {
                System.out.println("Дата должна быть введена!");
            }
            if (!(DD == null&& D.isEmpty()))
            {
                endDate = LocalDateTime.parse(D);

            }
            else
            {
                System.out.println("Введенная дата неверная!");
                continue;
            }
            if (Helpers.getMillisecFromDate(endDate) < Helpers.getMillisecFromDate(startDate))
            {
                System.out.println("Вы  вводите некорректную дату");
            }
            else
                break;
        }
        while (true);


        for (var item : HH)
        {
            if (Helpers.getMillisecFromDate(item.getDate()) >= Helpers.getMillisecFromDate(startDate) && Helpers.getMillisecFromDate(item.getDate()) >= Helpers.getMillisecFromDate(endDate))

            {
                if (item.getName() == polzovatel.getName())
                {
                    System.out.println(item.getDate().toString() + "\t" + item.getName() + "\t" + item.getHours() + "\t" + item.getMessage());
                }
            }
        }
        Scanner in = new Scanner(System.in);
        in.close();//TODO
    }
    private static void addStaffHour() throws IOException {
        addHour();
        menuUp();
    }

    private static void addHour() throws IOException {
        int H;
        LocalDateTime date;
        do
        {
            System.out.println("Введите отработанное время");
            Scanner in = new Scanner(System.in);
            String enterH = String.valueOf(in.nextInt());
            in.close();
            H = Integer.parseInt(enterH);


            if (H <= 0 || H >= 24)
            {
                System.out.println("Вы вводите некорректные данные");

            }
            System.out.println("Введите дату");
            Scanner inn = new Scanner(System.in);
            String enterDate = String.valueOf(in.nextInt());
            in.close();
            date = LocalDateTime.parse(enterDate);


                if (date != LocalDateTime.MIN && Helpers.getMillisecFromDate(date) <= Helpers.getMillisecFromDate(LocalDateTime.now()) && polzovatel.getUserRole() == UserRole.EMPLOYEE)
                {
                    addHourWithControlDate(polzovatel, H, date);
                }
                else if (date != LocalDateTime.MAX && Helpers.getMillisecFromDate(date) <= Helpers.getMillisecFromDate(LocalDateTime.now()) && Helpers.getMillisecFromDate(date) >= Helpers.getMillisecFromDate(LocalDateTime.now().minusDays(2) )&& polzovatel.getUserRole() == UserRole.FREELANCER)
                {
                    addHourWithControlDate(polzovatel, H, date);
                }
                else
                {
                    System.out.println("Дата введена некорректно!");
                }

    }
        while((H <=0||H >=24));
}

    private static void addWorkerHour() throws IOException {
        User worker;
        LocalDateTime date;
        System.out.println("*************************************************");
        System.out.println("Введите пользователя");
        Scanner in = new Scanner(System.in);
        String name = String.valueOf(in.nextInt());
        in.close();

        worker = fill.userGet(name);
        do {
            if (worker == null) {
                System.out.println("Пользователь не существует");
                return;
            }

            System.out.println("Введите дату");
            Scanner inn = new Scanner(System.in);
            String inputDateString = String.valueOf(in.nextInt());
            in.close();


            if(!(inputDateString == null && inputDateString.isEmpty()))
             {
                 date = LocalDateTime.parse(inputDateString);


            } else {
                System.out.println("Введенная дата неверная!");
                continue;

            }

            System.out.println("Введите отработанное время");
            Scanner ih = new Scanner(System.in);
            String H = String.valueOf(in.nextInt());
            in.close();

            addHourWithControlDate(worker, Integer.parseInt(H), date);

            menuUp();
        }
        while (true);
    }
         private static void addHourWithControlDate(User Us, int H, LocalDateTime date) throws IOException {
            System.out.println("Введите сообщение");
            Scanner inn = new Scanner(System.in);
            String mas = String.valueOf(inn.nextInt());
            inn.close();

            var time = new TimeRecord(date, Us.getName(), H, mas);
            List<TimeRecord> times = new ArrayList<TimeRecord>();
            times.add(time);
            fill.fillFileGeneric(times, Us.getUserRole().ordinal(), true);
        }


    private static void addWorker() throws IOException {
        System.out.println("Введите имя пользователя");
        Scanner in = new Scanner(System.in);
        String userName = String.valueOf(in.nextInt());
        in.close();

        User M = fill.userGet(userName);
        if(M == null)
        {

        }
        else
        {
            System.out.println("Такой пользователь существует!");
            menuUp();
        }
        System.out.println("Введите роль пользователя");
        var IR = inputRole();
        var user = new User(userName, IR);
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);
        fill.fillFileUser(users, true);

        Map<UserRole, ArrayList<String>> groupWorkRep = new HashMap<UserRole, ArrayList<String>>();

        var groupUser = fill.readFileUser();
        for(var groupItem : groupUser)
        {
            //проверяем наличие Ключа, если его нет, добавляем и ключ, и значение
            if (!groupWorkRep.containsKey(groupItem.getUserRole()))
            {
                var itemsList = new ArrayList<String>();
                itemsList.add(groupItem.getName());
                groupWorkRep.put(groupItem.getUserRole(), itemsList);
            }
            else//иначе ключ есть, просто обновляем значение
            {
                var itemsList = groupWorkRep.get(groupItem.getUserRole());
                if(!itemsList.contains(groupItem.getName()))
                    itemsList.add(groupItem.getName());
            }
        }

        for (var groupWork : groupWorkRep.entrySet())
        {
            if (groupWork.getKey() == user.getUserRole())
            {
                System.out.println("-------------------------");
                System.out.println(groupWork.getKey());
                System.out.println("-------------------------");
            }
            if (groupWork.getKey() == user.getUserRole())
                for (var item : groupWork.getValue())
            {

                System.out.println(item);

            }
        }
        System.out.println("-------------------------");
        menuUp();
    }
    private static void watchWorkerReport() throws IOException {
        LocalDateTime startDate;
        LocalDateTime endDate;
        int itogHour = 0;
        double itogTotalPay  = 0;
        do
        {
            System.out.println("Введите дату начала отчета");
            Scanner in = new Scanner(System.in);
            String D = String.valueOf(in.nextInt());
            in.close();

            if(D == null && D.isEmpty())
            {
                System.out.println("Дата должна быть введена!");
            }
            if (!(D == null && D.isEmpty()))
            {

                startDate = LocalDateTime.parse(D);
            }
            else
            {
                System.out.println("Вы вводите некорректные данные");
                continue;
            }
            System.out.println("Введите дату окончания отчета");
            Scanner inn = new Scanner(System.in);
            String DD = String.valueOf(in.nextInt());
            in.close();

            if(DD == null && D.isEmpty())
            {
                System.out.println("Дата должна быть введена!");
            }
            if (!(DD == null && D.isEmpty()))
            {

                endDate = LocalDateTime.parse(DD);
            }
            else
            {
                System.out.println("Вы вводите некорректные данные");
                continue;
            }
            if (Helpers.getMillisecFromDate(endDate) < Helpers.getMillisecFromDate(startDate))
            {
                System.out.println("Вы  вводите некорректную дату");

            }
            else
                break;
        }
        while (true);

        ArrayList<TimeRecord> allworkrep = new ArrayList<TimeRecord>();//создали новую общую коллекцию (пустая)
        for (int indexrole = 0; indexrole < 3; indexrole++)
        {
            List<TimeRecord> allwork = fill.readFileGeneric(indexrole);//вычитываем все файлы в коллекцию allwork
            allworkrep.AddRange(allwork);//добавляем группу элементов коллекции allwork в общую коллекцию allworkrep
        }


        Dictionary<String, List<TimeRecord>> workmap = new Dictionary<String, List<TimeRecord>>();//создаем новый словарь (пока пустой), в котором тип
        //Ключа строка(фильтруем по имени, так как
        // в нашем приложении оно уникально), тип Значения Список(List<>)

        foreach (var workitem in allworkrep)//перебираем общую коллекцию и каждый ее элемент кладем в переменную workitem
        {
            if (workitem.Date >= startdate && workitem.Date <= enddate)//фильтруем дату отчета
                if (!workmap.ContainsKey(workitem.Name))//проверяем наличие Ключа, если его нет
                {
                    workmap.Add(workitem.Name, new List<TimeRecord>());// то добавляем ключ, а значение пока еще пустое!!!
                    workmap[workitem.Name].Add(workitem);//после добавления ключа, добавляем Значение по вышедобавленному ключу
                }
                else//иначе ключ есть
                {
                    workmap[workitem.Name].Add(workitem);//просто добавляем Значение по существующему ключу

                }
        }

        foreach (var sortwork in workmap)
        {
            var repHour = fill.userGet(sortwork.Key);//получаем имя-ключ из словаря и значение кладем в переменную rephour

            var HH = sortwork.Value;//значение из словаря положили в переменную HH

            double monthSalary = 0;
            double bonus = 0;
            double monthBonus = 0;

            if (repHour.getUserRole() == UserRole.MANAGER)//проверяем роль через имя
            {
                var totp = new Manager(monthSalary,repHour, HH, startDate, endDate,bonus,monthBonus);//создаем новый экземпляр типа Manager
                System.out.println("");
                System.out.println("--------------------------------------");
                System.out.println("Сотрудник" + sortwork.Key);
                totp.printRepPerson();

                System.out.println("Всего отработано {totp.sumhour}");//итоговое время по конкретному сотруднику
                System.out.println("Всего заработано {totp.TotalPay}");//итоговая зп по конкретному сотруднику
                itogHour += totp.sumHours;//итоговое время по всем независимо от роли
                itogTotalPay += totp.getTotalPay();//итоговая з/п по всем независимо от роли


            }
            else if (repHour.UserRole == UserRole.EMPLOYEE)
            {
                var totp = new Employee(monthSalary,repHour, HH, startDate, endDate,bonus);
                System.out.println("");
                System.out.println("--------------------------------------");
                System.out.println("Сотрудник" + sortwork.Key);
                totp.printRepPerson();

                System.out.println("Всего отработано" + totp.sumHours);
                System.out.println("Всего заработано" + totp.getTotalPay());
                itogHour += totp.sumHours;
                itogtotalpay += totp.getTotalPay();

            }
            else if (repHour.UserRole == UserRole.FREELANCER)
            {
                var totp = new Freelancer(repHour, HH, startDate, endDate);
                System.out.println("");
                System.out.println("--------------------------------------");
                System.out.println("Сотрудник" + sortwork.Key);
                totp.printRepPerson();

                System.out.println("Всего отработано" + totp.sumHour);
                System.out.println("Всего заработано" + totp.getTotalPay());
                itogHour += totp.sumHours;
                itogtotalpay += totp.getTotalPay();

            }

        }
        System.out.println("");
        System.out.println("--------------------------------------");
        System.out.println("Всего отработано" + itogHour);
        System.out.println("Всего заработано" + itogtotalpay);

        menuUp();
    }

    private static void watchWorkerHour() throws IOException {
        LocalDateTime startDate;
        LocalDateTime endDate;

        do
        {
            System.out.println("Введите дату начала отчета");
            Scanner in = new Scanner(System.in);
            String enterStartDate = String.valueOf(in.nextInt());
            in.close();

            if ((enterStartDate == null && enterStartDate.isEmpty()))
            {
                System.out.println("Дата должна быть введена!");

            }
            if(!(enterStartDate == null && enterStartDate.isEmpty()))
            {
                startDate = LocalDateTime.parse(enterStartDate);
            }
            else
            {
                System.out.println("Вы вводите некорректные данные");
                continue;
            }
            System.out.println("Введите дату окончания отчета");
            Scanner iN = new Scanner(System.in);
            String enterEndDate = String.valueOf(in.nextInt());
            in.close();

            if ((enterEndDate == null && enterEndDate.isEmpty()))
            {
                System.out.println("Дата должна быть введена!");
            }
            if (!(enterEndDate == null && enterEndDate.isEmpty()))
            {
                endDate = LocalDateTime.parse(enterEndDate);
            }
            else
            {
                System.out.println("Вы вводите некорректные данные");
                continue;
            }

            if(Helpers.getMillisecFromDate(endDate) < Helpers.getMillisecFromDate(startDate))
            {
                System.out.println("Вы  вводите некорректную дату");
            }
            else
                break;
        }
        while (true);

        User repHour;
        double monthSalary = 0;
        double bonus = 0;
        double monthBonus = 0;
        do
        {
            System.out.println("---------------------");
            System.out.println("Введите пользователя");

            Scanner in = new Scanner(System.in);
            String inputString = String.valueOf(in.nextInt());
            in.close();

            System.out.println("---------------------");
            repHour = fill.userGet(inputString);

            if (repHour == null)
            {
                System.out.println("Пользователь не существует");
            }
            else
            {
                break;
            }
        }
        while (true);

        var HH = fill.readFileGeneric(repHour.getUserRole().ordinal());
        if (repHour.getUserRole() == UserRole.MANAGER)
        {

            var totp = new Manager(monthSalary,repHour, HH, startDate, endDate, bonus, monthBonus);
            totp.printRepPerson();
            System.out.println("Всего отработано" + totp.sumHours);
            System.out.println("Всего заработано" + totp.getTotalPay());

            Scanner in = new Scanner(System.in);
            in.close();
        }

        else if (repHour.getUserRole() == UserRole.EMPLOYEE)
        {
            var totp = new Employee(monthSalary,repHour, HH, startDate, endDate,bonus);
            totp.printRepPerson();
            System.out.println("Всего отработано" + totp.sumHours);
            System.out.println("Всего заработано" + totp.getTotalPay());

            Scanner in = new Scanner(System.in);
            in.close();
        }
        else if (repHour.getUserRole() == UserRole.FREELANCER)
        {
            var totp = new Freelancer(repHour, HH, startDate, endDate);
            totp.printRepPerson();
            System.out.println("Всего отработано" + totp.sumHours);
            System.out.println("Всего заработано" +  totp.getTotalPay());


        }
        menuUp();

    }
}