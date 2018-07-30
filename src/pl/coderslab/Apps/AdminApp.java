package pl.coderslab.Apps;

import pl.coderslab.DB_entities.Exercise;
import pl.coderslab.EntitiesManager.ExerciseManager;
import pl.coderslab.EntitiesManager.SolutionManager;
import pl.coderslab.EntitiesManager.UserGroupManager;
import pl.coderslab.EntitiesManager.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

import static pl.coderslab.EntitiesManager.UserManager.getNumber;

public class AdminApp {

    public static void welcomeMessage(){
        header();
        System.out.println("\n");
        System.out.println("Welcome to Admin system. Choose option");
        System.out.println("1 - manage users");
        System.out.println("2 - manage exercises");
        System.out.println("3 - manage groups");
        System.out.println("4 - assign exercise to user");
        System.out.println("0 - quit");
    }

    public static void runAdminApp() {
        welcomeMessage();

        Scanner scanner = new Scanner(System.in);
        int action = getNumber();

        while (action >= 0) {
            if (action == 1) {
                UserManager.userManager();
                System.out.println("Select next action (1-users, 2 exercises, 3 groups, 4 exercise assign");
                action=getNumber();
            }
            else if(action ==2){
                ExerciseManager.selectAction();
                System.out.println("Select next action (1-users, 2 exercises, 3 groups, 4 exercise assign");
                action=getNumber();
            }
            else if(action==3) {
                UserGroupManager.selectAction();
                System.out.println("Select next action (1-users, 2 exercises, 3 groups, 4 exercise assign");
                action=getNumber();
            }

            else if (action == 4) {
                SolutionManager.selectAction();
                System.out.println("Select next action (1-users, 2 exercises, 3 groups, 4 exercise assign");
                action=getNumber();
            }
            else if (action==0){
                System.out.println("Program ended");
                break;
            } else {
                System.out.println("Action not recognized. Try again");
                action = getNumber();
            }
        }
    }




    public static void printExercises(){
        System.out.println("List of currently available exercises:");
        ArrayList<Exercise> ex=Exercise.loadAll();
        for(int i=0; i<ex.size(); i++){
            System.out.println(ex.get(i));
        }
        System.out.println("\n");
    }

    public static void printOptions(){
        System.out.println("Welcome to Exercise Manager. Choose option");
        System.out.println("1 - add new exercise");
        System.out.println("2 - edit existing exercise");
        System.out.println("3 - delete exercise");
        System.out.println("0 - quit");
    }

    public static void header(){
        System.out.println("    #     ######   ##   ##  ###  ##    #       #####   #   #   #####   #######  #######  ##   ##          ");
        System.out.println("   ###    #    ##  # # # #   #   # #   #      #         # #   #           #     #        # # # #          ");
        System.out.println("   # #    #     #  #  #  #   #   #  #  #       #####     #     #####      #     #####    #  #  #          ");
        System.out.println("  #####   #    ##  #     #   #   #   # #            #    #          #     #     #        #     #          ");
        System.out.println(" ##   ##  ######   #     #  ###  #    ##       #####     #     #####      #     #######  #     #          ");
    }



}
