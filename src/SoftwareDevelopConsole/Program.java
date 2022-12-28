package SoftwareDevelopConsole;

import Persistence.FileRepository;
import Persistence.MemoryRepository;
import SoftwareDevelopDomain.Person.Manager;
import SoftwareDevelopDomain.Person.User;
import SoftwareDevelopDomain.Person.UserRole;
import SoftwareDevelopDomain.TimeRecord;

import java.time.LocalDateTime;
import java.util.*;
import java.io.IOException;

public class Program {
    public  static FileRepository fill;
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
    public static void controlRole(FileRepository userReturn)//контроль вводимой роли при входе в программу
    {
        do
        {

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
    private static UserRole inputRole()
    {
        UserRole enterUser = UserRole.DEFAULT;
        do
        {
            System.out.println("\n Введите 0, если менеджер \n Введите 1, если сотрудник \n Введите 2, если фрилансер");
            Scanner in = new Scanner(System.in);
            String inputRole = String.valueOf(in.nextInt());
            in.close();


            {

                if (inputRole == "0")
                {
                    enterUser = UserRole.MANAGER;
                    break;
                }
                else if (inputRole == "1")
                {
                    enterUser = UserRole.EMPLOYEE;
                    break;
                }
                else if (inputRole == "2")
                {
                    enterUser = UserRole.FREELANCER;
                    break;
                }
                else
                    System.out.println("Вы ввели несуществующую роль");

            }
        } while ( enterUser.ordinal() < UserRole.MANAGER.ordinal()|| enterUser.ordinal() > UserRole.FREELANCER.ordinal());

        return enterUser;
    }
    private static void displayMenu(UserRole userRole)
    {
        do
        {

            if (userRole == UserRole.MANAGER)
            {
                System.out.println("Меню Руководитель");
                showManagerMenu();
                break;
            }
            if (userRole == UserRole.EMPLOYEE)
            {
                System.out.println("Меню Сотрудник");
                showEmployeeMenu();
                break;
            }
            if (userRole == UserRole.FREELANCER)
            {
                System.out.println("Меню Фрилансер");
                showFreelancerMenu();
                break;
            }

        }
        while (true);

    }
    private static void showManagerMenu()

    {
       int actionManager;
        do
        {
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

                if (actionManager == 1)
                {

                    addWorker();
                    break;
                }
                else if (actionManager== 2)
                {
                    addWorkerHour();
                    break;
                }
                else if (actionManager == 3)
                {

                    watchWorkerReport();
                    break;
                }
                else if(actionManager == 4)
                {

                    watchWorkerHour();
                    break;
                }

                else if (actionManager== 0)
                {

                    System.exit(0);

                    break;
                }
                else
                    System.out.println("Вы выбрали несуществующее действие");

        }

        while ((actionManager < 1 || actionManager > 4) && actionManager != 0);
    }
    private static void showEmployeeMenu()
    {
int actionEmployee;
        do
        {
            System.out.println("Выберите действие  \n " +
                    "Введите 1, если вы хотите ввести часы \n " +
                    "Введите 2, если вы хотите просмотреть часы");

            Scanner in = new Scanner(System.in);
            String enterEmployee = String.valueOf(in.nextInt());
            in.close();

            actionEmployee = Integer.parseInt(enterEmployee);
            {

                if (actionEmployee== 1)
                {
                    addStaffHour();
                    break;
                }
                else if (actionEmployee== 2)
                {
                    watchStaffHour();
                    break;
                }
                else
                    System.out.println("Вы выбрали несуществующее действие");
            }
        }

       while (actionEmployee < 1 || actionEmployee > 2);
    }
    private static void showFreelancerMenu()
    {
int actionFreelancer;
        do
        {
            System.out.println("Выберите действие  \n " +
                    "Введите 1, если вы хотите ввести часы \n " +
                    "Введите 2, если вы хотите просмотреть часы");
            Scanner in = new Scanner(System.in);
            String enterFreelancer = String.valueOf(in.nextInt());
            in.close();
            actionFreelancer = Integer.parseInt(enterFreelancer);

            {

                if (actionFreelancer== 1)
                {
                    addStaffHour();
                    break;
                }
                else if (actionFreelancer== 2)
                {
                    watchStaffHour();
                    break;
                }
                else
                    System.out.println("Вы выбрали несуществующее действие");
            }
        }

       while (actionFreelancer < 1 || actionFreelancer > 2);
    }
    private static void menuUp()
    {
        int choice;

        System.out.println("Выберите действие  \n " +
                "Введите 1, если вы хотите продолжить \n " +
                "Введите 2, если вы хотите выйти из меню");

        Scanner in = new Scanner(System.in);
        String enterChoice = String.valueOf(in.nextInt());
        in.close();
            choice = Integer.parseInt(enterChoice);

            if (choice == 1)
            {
                if (polzovatel.getUserRole() == UserRole.MANAGER)
                {
                    showManagerMenu();
                }
                if (polzovatel.getUserRole() == UserRole.FREELANCER)
                {
                    showFreelancerMenu();
                }
                if (polzovatel.getUserRole() == UserRole.EMPLOYEE)
                {
                    showEmployeeMenu();
                }

            }
            else if (choice == 2)
            {
                System.exit(0);
            }
            else
                System.out.println("Вы выбрали несуществующее действие");
        System.exit(0);
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


    private static void AddWorker() throws IOException {
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

        Map<UserRole, ArrayList<String>> groupworkrep = new HashMap<UserRole, ArrayList<String>>();

        var groupUser = fill.readFileUser();
        for(var groupitem : groupUser)
        {
            if (!groupworkrep.containsKey(groupitem.getUserRole()))//проверяем наличие Ключа, если его нет
            {
                groupworkrep.put(groupitem.getUserRole(), new ArrayList<String>());// то добавляем ключ, а значение пока еще пустое!!!
                //groupworkrep[groupitem.getUserRole()] (groupitem.getName());//после добавления ключа, добавляем Значение по вышедобавленному ключу
                var tt =groupworkrep.get(groupitem.getUserRole());
                groupworkrep.put(groupitem.getUserRole(),groupworkrep.get(groupitem.getUserRole()) = groupitem.getName() );
            }
            else//иначе ключ есть
            {
               // groupworkrep[groupitem.getUserRole()].add(groupitem.getName());//просто добавляем Значение по существующему ключу

            }
        }

        for (var groupwork : groupworkrep)
        {
            if (groupwork.Key == user.getUserRole())
            {
                System.out.println("-------------------------");
                System.out.println(groupwork.Key);
                System.out.println("-------------------------");
            }
            if (groupwork.Key == user.getUserRole())
                for (var item : groupwork.Value)
            {

                System.out.println(item);

            }
        }
        System.out.println("-------------------------");
        menuUp();
    }
}