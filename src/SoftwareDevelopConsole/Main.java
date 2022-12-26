package SoftwareDevelopConsole;

import Persistence.FileRepository;
import Persistence.MemoryRepository;
import SoftwareDevelopDomain.Person.User;

public class Main {
    public  static FileRepository fill;
    public static User polzovatel;

    public static void main(String[] args){
        int userRole = 0;

        fill = new FileRepository();//создаем экземпляры для возможности вызова метода FillFileUser

        var userReturn = new MemoryRepository();//создаем экземпляры для возможности вызова метода Users
        fill.fillFileUser(userReturn.Users(), false);


        var genericreturn = new MemoryRepository();
        fill.FillFileGeneric(genericreturn.Generic(), userRole, false);

        var text = fill.ReadFileUser();

        ControlRole(fill);
    }

}