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

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.*;
import java.io.IOException;

public class Program {
    public static FileRepository fill;
    public static User polzovatel;

    public static void main(String[] args) throws IOException {
        int userRole = 0;

        fill = new FileRepository();//создаем экземпляры для возможности вызова метода FillFileUser

        var userReturn = new MemoryRepository();//создаем экземпляры для возможности вызова метода Users
        fill.fillFileUser(userReturn.Users(), false);


        var genericReturn = new MemoryRepository();
        fill.fillFileGeneric(genericReturn.Generic(), userRole, false);

        var text = fill.readFileUser();

        controlRole(fill);
    }

    public static void controlRole(FileRepository userReturn) throws IOException//контроль вводимой роли при входе в программу
    {
        Scanner inpt;
      do {
          try {
                System.out.println("Введите ваше имя");
                inpt = new Scanner(System.in);
                String name = inpt.nextLine();

                polzovatel = userReturn.userGet(name);
                if (polzovatel == null)
                    System.out.println("Пользователь с таким именем не существует");
            }
            catch (Exception e){

                var tt = e;
            }
        }
        while (polzovatel == null);
        displayMenu(polzovatel.getUserRole());
    }

    private static UserRole inputRole() {
        UserRole enterUser = UserRole.DEFAULT;
        Scanner inp ;
        do {
            System.out.println("\n Введите 0, если менеджер \n Введите 1, если сотрудник \n Введите 2, если фрилансер");
            inp = new Scanner(System.in);
            String inputRole = inp.nextLine();

            {

                if (inputRole.equals("0")) {
                    enterUser = UserRole.MANAGER;
                    break;
                } else if (inputRole.equals("1")) {
                    enterUser = UserRole.EMPLOYEE;
                    break;
                } else if (inputRole.equals("2")) {
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
        Scanner inp;
        do {
            System.out.println("Выберите действие  \n " +
                    "Введите 1, если вы хотите добавить сотрудника \n " +
                    "Введите 2, если вы хотите добавить время сотруднику \n " +
                    "Введите 3, если вы хотите посмотреть отчет по всем сотрудникам (возможность выбрать период) \n " +
                    "Введите 4, если вы хотите посмотреть часы работы сотрудника \n " +
                    "Введите 0, если вы хотите выйти из программы");

            inp = new Scanner(System.in);
            String enterManager = inp.nextLine();

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
        Scanner inp = null;
        do {
            System.out.println("Выберите действие  \n " +
                    "Введите 1, если вы хотите ввести часы \n " +
                    "Введите 2, если вы хотите просмотреть часы");

            inp = new Scanner(System.in);
            String enterEmployee = inp.nextLine();

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
        Scanner inp = null;
        do {
            System.out.println("Выберите действие  \n " +
                    "Введите 1, если вы хотите ввести часы \n " +
                    "Введите 2, если вы хотите просмотреть часы");
            inp = new Scanner(System.in);
            String enterFreelancer = inp.nextLine();

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
        Scanner inp = null;
        System.out.println("Выберите действие  \n " +
                "Введите 1, если вы хотите продолжить \n " +
                "Введите 2, если вы хотите выйти из меню");

        inp = new Scanner(System.in);
        String enterChoice = inp.nextLine();
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

        LocalDate startDate;
        LocalDate endDate;
        Scanner inp = null;

        do
        {
            System.out.println("Введите дату начала отчета");
            inp = new Scanner(System.in);
            String D = inp.nextLine();


            if(D == null&& D.isEmpty())
            {
                System.out.println("Дата должна быть введена!");
            }
            if (!(D == null&& D.isEmpty()))
            {
                startDate = LocalDate.parse(D);

            }
            else
            {
                System.out.println("Введенная дата неверная!");
                continue;
            }

            System.out.println("Введите дату окончания отчета");
            inp = new Scanner(System.in);
            String DD = inp.nextLine();


            if(DD == null&& D.isEmpty())
            {
                System.out.println("Дата должна быть введена!");
            }
            if (!(DD == null&& D.isEmpty()))
            {
                endDate = LocalDate.parse(D);

            }
            else
            {
                System.out.println("Введенная дата неверная!");
                continue;
            }
            if (Helpers.getMilliSecFromDate(endDate) < Helpers.getMilliSecFromDate(startDate))
            {
                System.out.println("Вы  вводите некорректную дату");
            }
            else
                break;
        }
        while (true);

        for (var item : HH)
        {
            if (Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(startDate) && Helpers.getMilliSecFromDate(item.getDate()) >= Helpers.getMilliSecFromDate(endDate))

            {
                if (item.getName().equals(polzovatel.getName()))
                {
                    System.out.println(item.getDate().toString() + "\t" + item.getName() + "\t" + item.getHours() + "\t" + item.getMessage());
                }
            }
        }
//        Scanner in = new Scanner(System.in);
    }
    private static void addStaffHour() throws IOException {
        addHour();
        menuUp();
    }

    private static void addHour() throws IOException {
        int H;
        LocalDate date;
        Scanner inp = null;
        do
        {
            System.out.println("Введите отработанное время");
            inp = new Scanner(System.in);
            String enterH = inp.nextLine();

            H = Integer.parseInt(enterH);


            if (H <= 0 || H >= 24)
            {
                System.out.println("Вы вводите некорректные данные");

            }
            System.out.println("Введите дату");
            inp = new Scanner(System.in);
            String enterDate = inp.nextLine();

            date = LocalDate.parse(enterDate);


                if (date != LocalDate.MIN && Helpers.getMilliSecFromDate(date) <= Helpers.getMilliSecFromDate(LocalDate.now()) && polzovatel.getUserRole() == UserRole.EMPLOYEE)
                {
                    addHourWithControlDate(polzovatel, H, date);
                }
                else if (date != LocalDate.MAX && Helpers.getMilliSecFromDate(date) <= Helpers.getMilliSecFromDate(LocalDate.now()) && Helpers.getMilliSecFromDate(date) >= Helpers.getMilliSecFromDate(LocalDate.now().minusDays(2) )&& polzovatel.getUserRole() == UserRole.FREELANCER)
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
        LocalDate date;

        Scanner inn ;
        System.out.println("*************************************************");
        System.out.println("Введите пользователя");
        inn = new Scanner(System.in);
        String name = inn.nextLine();

        worker = fill.userGet(name);

        do {
            if (worker == null) {
                System.out.println("Пользователь не существует");
                return;
            }

            System.out.println("Введите дату");
            inn = new Scanner(System.in);
            String inputDateString = inn.nextLine();


            if(!(inputDateString == null && inputDateString.isEmpty()))
             {
                 date = LocalDate.parse(inputDateString);//2007-12-03T10:15:30


            } else {
                System.out.println("Введенная дата неверная!");
                continue;

            }

            System.out.println("Введите отработанное время");
            inn = new Scanner(System.in);
            String H = inn.nextLine();

            addHourWithControlDate(worker, Integer.parseInt(H), date);

            menuUp();
          }
        while (true);

    }
         private static void addHourWithControlDate(User Us, int H, LocalDate date) throws IOException {
            Scanner inn = null;
            System.out.println("Введите сообщение");
            inn= new Scanner(System.in);
            String mas = inn.nextLine();

            var time = new TimeRecord(date, Us.getName(), H, mas);
            List<TimeRecord> times = new ArrayList<TimeRecord>();
            times.add(time);
            fill.fillFileGeneric((ArrayList<TimeRecord>) times, Us.getUserRole().ordinal(), true);
          }


    private static void addWorker() throws IOException {
        Scanner inn = null;
        System.out.println("Введите имя пользователя");
        inn = new Scanner(System.in);
        String userName = inn.nextLine();

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
        fill.fillFileUser(users, true);//режим true- введенный пользователь добавляется в файл

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
        LocalDate startDate;
        LocalDate endDate;
        int itogHour = 0;
        double itogTotalPay  = 0;
        Scanner inn = null;
        do
        {
            System.out.println("Введите дату начала отчета");
            inn = new Scanner(System.in);
            String D = inn.nextLine();


            if(D == null && D.isEmpty())
            {
                System.out.println("Дата должна быть введена!");
            }
            if (!(D == null && D.isEmpty()))
            {

                startDate = LocalDate.parse(D);
            }
            else
            {
                System.out.println("Вы вводите некорректные данные");
                continue;
            }
            System.out.println("Введите дату окончания отчета");
            inn = new Scanner(System.in);
            String DD = inn.nextLine();


            if(DD == null && D.isEmpty())
            {
                System.out.println("Дата должна быть введена!");
            }
            if (!(DD == null && D.isEmpty()))
            {

                endDate = LocalDate.parse(DD);
            }
            else
            {
                System.out.println("Вы вводите некорректные данные");
                continue;
            }
            if (Helpers.getMilliSecFromDate(endDate) < Helpers.getMilliSecFromDate(startDate))
            {
                System.out.println("Вы  вводите некорректную дату");

            }
            else
                break;
        }
        while (true);

        ArrayList<TimeRecord> allWorkRep = new ArrayList<TimeRecord>();//создали новую общую коллекцию (пустая)
        for (int indexRole = 0; indexRole < 3; indexRole++)
        {
            List<TimeRecord> allWork = fill.readFileGeneric(indexRole);//вычитываем все файлы в коллекцию allWork
            allWorkRep.addAll(allWork);//добавляем группу элементов коллекции allWork в общую коллекцию allWorkRep
        }


        Map<String, List<TimeRecord>> workMap = new HashMap<String, List<TimeRecord>>();//создаем новый словарь (пока пустой), в котором тип
        //Ключа строка(фильтруем по имени, так как
        // в нашем приложении оно уникально), тип Значения Список(List<>)

        for (var workItem : allWorkRep)//перебираем общую коллекцию и каждый ее элемент кладем в переменную workItem
        {
            if (Helpers.getMilliSecFromDate(workItem.getDate()) >= Helpers.getMilliSecFromDate(startDate) && Helpers.getMilliSecFromDate(workItem.getDate()) <= Helpers.getMilliSecFromDate(endDate))//фильтруем дату отчета
                if (!workMap.containsKey(workItem.getName()))//проверяем наличие Ключа, если его нет
                {
                    var itemList = new ArrayList<TimeRecord>();
                    itemList.add((workItem));
                    workMap.put(workItem.getName(),itemList);

                }
                else//иначе ключ есть
                {
                    List<TimeRecord> items = workMap.get(workItem.getName());
                    if(!items.contains(workItem.getName())){
                        items.add(workItem);
                    }
                }
        }

        for (var sortWork : workMap.entrySet())
        {
            var repHour = fill.userGet(sortWork.getKey());//получаем имя-ключ из словаря и значение кладем в переменную rephour

            var HH = sortWork.getValue();//значение из словаря положили в переменную HH

            double monthSalary = 0;
            double bonus = 0;
            double monthBonus = 0;

            if (repHour.getUserRole() == UserRole.MANAGER)//проверяем роль через имя
            {
                var totp = new Manager(monthSalary,repHour, HH, startDate, endDate,bonus,monthBonus);//создаем новый экземпляр типа Manager
                System.out.println("");
                System.out.println("--------------------------------------");
                System.out.println("Сотрудник" + sortWork.getKey());
                totp.printRepPerson();

                System.out.println("Всего отработано" + totp.sumHours);//итоговое время по конкретному сотруднику
                System.out.println("Всего заработано" + totp.getTotalPay());//итоговая зп по конкретному сотруднику
                itogHour += totp.sumHours;//итоговое время по всем независимо от роли
                itogTotalPay += totp.getTotalPay();//итоговая з/п по всем независимо от роли


            }
            else if (repHour.getUserRole() == UserRole.EMPLOYEE)
            {
                var totp = new Employee(monthSalary,repHour, HH, startDate, endDate,bonus);
                System.out.println("");
                System.out.println("--------------------------------------");
                System.out.println("Сотрудник" + sortWork.getKey());
                totp.printRepPerson();

                System.out.println("Всего отработано" + totp.sumHours);
                System.out.println("Всего заработано" + totp.getTotalPay());
                itogHour += totp.sumHours;
                itogTotalPay += totp.getTotalPay();

            }
            else if (repHour.getUserRole() == UserRole.FREELANCER)
            {
                var totp = new Freelancer(repHour, HH, startDate, endDate);
                System.out.println("");
                System.out.println("--------------------------------------");
                System.out.println("Сотрудник" + sortWork.getKey());
                totp.printRepPerson();

                System.out.println("Всего отработано" + totp.sumHours);
                System.out.println("Всего заработано" + totp.getTotalPay());
                itogHour += totp.sumHours;
                itogTotalPay += totp.getTotalPay();

            }

        }
        System.out.println("");
        System.out.println("--------------------------------------");
        System.out.println("Всего отработано" + itogHour);
        System.out.println("Всего заработано" + itogTotalPay);

        menuUp();
    }

    private static void watchWorkerHour() throws IOException {
        LocalDate startDate;
        LocalDate endDate;
        Scanner inn = null;
        do
        {
            System.out.println("Введите дату начала отчета");
            inn = new Scanner(System.in);
            String enterStartDate = inn.nextLine();


            if ((enterStartDate == null && enterStartDate.isEmpty()))
            {
                System.out.println("Дата должна быть введена!");

            }
            if(!(enterStartDate == null && enterStartDate.isEmpty()))
            {
                startDate = LocalDate.parse(enterStartDate);
            }
            else
            {
                System.out.println("Вы вводите некорректные данные");
                continue;
            }
            System.out.println("Введите дату окончания отчета");
            inn = new Scanner(System.in);
            String enterEndDate = inn.nextLine();


            if ((enterEndDate == null && enterEndDate.isEmpty()))
            {
                System.out.println("Дата должна быть введена!");
            }
            if (!(enterEndDate == null && enterEndDate.isEmpty()))
            {
                endDate = LocalDate.parse(enterEndDate);
            }
            else
            {
                System.out.println("Вы вводите некорректные данные");
                continue;
            }

            if(Helpers.getMilliSecFromDate(endDate) < Helpers.getMilliSecFromDate(startDate))
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
        Scanner inp = null;
        do
        {
            System.out.println("---------------------");
            System.out.println("Введите пользователя");

            inp = new Scanner(System.in);
            String inputString = inp.nextLine();


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

//            Scanner in = new Scanner(System.in);//TODO
        }

        else if (repHour.getUserRole() == UserRole.EMPLOYEE)
        {
            var totp = new Employee(monthSalary,repHour, HH, startDate, endDate,bonus);
            totp.printRepPerson();
            System.out.println("Всего отработано" + totp.sumHours);
            System.out.println("Всего заработано" + totp.getTotalPay());

//            Scanner in = new Scanner(System.in);//TODO
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