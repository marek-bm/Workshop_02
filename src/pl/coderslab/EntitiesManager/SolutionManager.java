package pl.coderslab.EntitiesManager;

import pl.coderslab.DB_entities.Solution;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static pl.coderslab.EntitiesManager.UserManager.getNumber;

public class SolutionManager {

    //method printing all assigned exercises
    public static void printSolutions(){
        System.out.println("List of current solutions:");
        ArrayList<Solution> s=Solution.loadAll();
        for(int i=0; i<s.size(); i++){
            System.out.println(s.get(i));
        }
        System.out.println("\n");
    }

    //pop-up at the beginning og the program
    public static void printOptions(){
        System.out.println("Welcome to Solution Manager. Choose option");
        System.out.println("1 - assign exercise to user");
        System.out.println("2 - display users exercises");
        System.out.println("0 - return to main menu");
    }


    //method making changes in the exersices assignment
    public static void selectAction() {
        //welcome display
        printOptions();

        //choose option
        Scanner scanner = new Scanner(System.in);
        int action = getNumber();

        while (action >= 0) {
            if (action == 1) {

                //1. dispaly all users
                UserManager.printUsers();
                //2.ask for user id
                System.out.println("Enter user id to assing exercise");
                int userId=getNumber();
                //3. display all exersices
                ExerciseManager.printExercises();
                //4. ask for exercise id to assign to selected user
                System.out.println("Enter exercise id you want to assign to user");
                int exerciseId=UserManager.getNumber();
                String date=LocalDate.now().toString();

                Solution solution=new Solution();
                solution.setUser_id(userId);
                solution.setExercise_id(exerciseId);
                solution.setCreated(date);

                solution.saveToDB();
                System.out.println("Exercise "+exerciseId+ " assigned to user "+userId);

                System.out.println("Select action");
                action = getNumber();
            }
            else if(action ==2){
                System.out.println("Enter user id to display assigned exercises");
                int userID=getNumber();
                ArrayList<Solution> array= Solution.loadAllByUserId(userID);

                System.out.println("User "+userID+" has following exercises");
                for(int i=0; i<array.size();i++){
                    System.out.println(array.get(i));
                }
                System.out.println("\n");

                System.out.println("Select action");
                action=getNumber();
            }

            else if (action == 0) {
                break;
            } else {
                System.out.println("Action not recognized. Try again");
                action = getNumber();
            }
        }
    }
}
