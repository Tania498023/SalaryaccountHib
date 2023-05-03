package myApp.App;

import myApp.SoftwareDevelopDomain.Helpers;
import myApp.models.DateContener;

import java.time.LocalDate;
import java.util.Scanner;

public class InputOutput {

    public InputOutput() {

    }

    public DateContener inputDateReport() {
        LocalDate startDate = null;
        LocalDate endDate = null;

        Scanner inn;
        do {
            try {
                System.out.println("Введите дату начала отчета");
                inn = new Scanner(System.in);
                String enterStartDate = inn.nextLine();

                if (enterStartDate == null || enterStartDate.isEmpty()) {
                    System.out.println("Дата должна быть введена!");
                    continue;
                }
                if (!(enterStartDate == null && enterStartDate.isEmpty())) {
                    startDate = LocalDate.parse(enterStartDate);

                }
//                else {
//                    System.out.println("Вы вводите некорректные данные");
//                    continue;
//                }
                if (Helpers.getMilliSecFromDate(startDate) > Helpers.getMilliSecFromDate(LocalDate.now())) {
                    System.out.println("Введенная дата неверная!");
                    continue;
                }
                System.out.println("Введите дату окончания отчета");
                inn = new Scanner(System.in);
                String enterEndtDate = inn.nextLine();

                if (enterEndtDate == null || enterEndtDate.isEmpty()) {
                    System.out.println("Дата должна быть введена!");
                    continue;
                }
                if (!(enterEndtDate == null && enterEndtDate.isEmpty())) {
                    endDate = LocalDate.parse(enterEndtDate);

                }
//                else {
//                    System.out.println("Вы вводите некорректные данные");
//                    continue;
//                }
                if (Helpers.getMilliSecFromDate(endDate) < Helpers.getMilliSecFromDate(startDate)) {
                    System.out.println("Вы  вводите некорректную дату");

                } else
                    break;
            } catch (Exception e) {
                System.out.println("Введен неверный формат!");
            }
        }
        while (true);
        DateContener dateContener = new DateContener(startDate,endDate);
        return dateContener;
    }
//    public Integer inputWorkerMenu(){
//        int actionWorker = 0;
//        Scanner inp;
//
//            System.out.println("Выберите действие  \n " +
//                    "Введите 1, если вы хотите ввести часы \n " +
//                    "Введите 2, если вы хотите просмотреть часы");
//            try {
//
//                inp = new Scanner(System.in);
//                String enterEmployee = inp.nextLine();
//                 actionWorker = Integer.parseInt(enterEmployee);
//            } catch (Exception e) {
//                System.out.println("Вы ввели неверный формат!");
//            }
//
//
//        return actionWorker;
//    }
}