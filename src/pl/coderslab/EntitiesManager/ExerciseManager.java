package pl.coderslab.EntitiesManager;

import pl.coderslab.DB_entities.Exercise;

import java.util.ArrayList;
import java.util.Scanner;

import static pl.coderslab.EntitiesManager.UserManager.getNumber;

public class ExerciseManager {

    public static void selectAction() {
        printExercises();
        printOptions();

        Scanner scanner = new Scanner(System.in);
        int action = getNumber();

        while (action >= 0) {
            if (action == 1) {
                System.out.println("Enter the data to create new exercise");
                Exercise ex= Exercise.createExercise(scanner);
                ex.saveToDB();
                printExercises();
                System.out.println("Select action");
                action = getNumber();
            }
            else if(action ==2){
                System.out.println("Enter id to modify the exercise");
                Exercise exercise=Exercise.loadbyId(getNumber());
                System.out.println(exercise);
                System.out.println("Enter new title");
                exercise.setTitle(scanner.nextLine());
                System.out.println("Enter new description");
                exercise.setDescription(scanner.nextLine());
                exercise.saveToDB();

                printExercises();
                System.out.println("Select action");
                action=getNumber();

            }
            else if(action==3) {
                System.out.println("Select id of exercise to delete");
                Exercise ex = Exercise.loadbyId(getNumber());
                ex.delete();
                printExercises();
                System.out.println("Select action");
                action = getNumber();
            }

            else if (action == 0) {
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
        System.out.println("0 - return to main menu");
    }

}
