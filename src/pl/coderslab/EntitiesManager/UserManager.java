package pl.coderslab.EntitiesManager;

import pl.coderslab.DB_entities.User;

import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {

    public static void printUsers(){
        System.out.println("List of currently available users:");
        ArrayList<User> u=User.loadAll();
        for(int i=0; i<u.size(); i++){
            System.out.println(u.get(i));
        }
        System.out.println("\n");
    }

    public static void printOptions(){
        System.out.println("Welcome to user manager. Choose option");
        System.out.println("1 - add new user");
        System.out.println("2 - edit existing user");
        System.out.println("3 - delete user");
        System.out.println("0 - return to main menu");
    }

    public static void userManager(){
        printUsers();
        printOptions();
        selectAction();


    }


    public static int getNumber(){
        Scanner scan= new Scanner(System.in);

        while (!scan.hasNextInt()) {
            scan.next();
            System.out.print("Incorrect data. Try again");
        }
        int input = scan.nextInt();
        return input;
    }

    public static void selectAction(){
        Scanner scanner=new Scanner(System.in);
        int action=getNumber();
        while(action>=0){
            if(action==1){
                System.out.println("Enter the date to create new user");
                User user=User.createUser(scanner);
                user.saveToDB();
                printUsers();
                System.out.println("Select action");
                action=getNumber();
            }
            else if(action ==2){
                System.out.println("Enter id of user");
                User usr=User.loadById(getNumber());
                System.out.println(usr);
                usr=User.modifyUser(usr,scanner);
                System.out.println(usr);
                usr.saveToDB();
                printUsers();
                System.out.println("Select action");
                action=getNumber();

            }
            else if(action==3){
                System.out.println("Select id of user to delete");
                User user=User.loadById(getNumber());
                user.delete();
                printUsers();
                System.out.println("Select action");
                action=getNumber();

            }
            else if (action==0){
                break;
            }
            else{
                System.out.println("Action not recognized. Try again");
                action=getNumber();
            }
        }
    }

}








