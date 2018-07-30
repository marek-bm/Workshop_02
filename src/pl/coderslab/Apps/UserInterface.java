package pl.coderslab.Apps;

import pl.coderslab.DB_entities.Solution;
import pl.coderslab.DB_entities.User;
import pl.coderslab.EntitiesManager.UserManager;

import java.util.ArrayList;
import java.util.Scanner;
import static pl.coderslab.EntitiesManager.UserManager.getNumber;

public class UserInterface {

    public static void printOptions(){
        System.out.println("Choose option");
        System.out.println("1 - add new solution");
        System.out.println("2 - view submitted solutions");
        System.out.println("0 - quit");
    }

    public static void selectAction() {
        hearedStudent();
        System.out.println("Enter your id to start");
        int user_Id=UserManager.getNumber();
        User loggedUser=User.loadById(user_Id);
        System.out.println("Welcome "+loggedUser.getUsername()+ " ID: "+ loggedUser.getId()+"\n");

        printOptions();

        Scanner scanner = new Scanner(System.in);
        int action = getNumber();

        while (action >= 0) {
            if (action == 1) {
                //count not sumbitted solutions
                int counter=countNullExercises(user_Id);

                if(counter>0){
                    //display empty exercises
                    displayNullExercises(user_Id);

                    System.out.println("Enter solutionId which you  want to update (0 for return)");
                    int solutionId=getNumber();

                    while(solutionId!=0){Solution s=Solution.loadById(solutionId);
                        s.updateSolution(scanner, solutionId);
                        displayNullExercises(user_Id);
                    }

                } else {
                    System.out.println("You don't have any unsubmitted solutions");
                }

                System.out.println("Select action");
                action = getNumber();
            }
            else if(action ==2){

                ArrayList<Solution> list= Solution.loadAllByUserId(user_Id);

                for(int i=0; i<list.size();i++){
                    System.out.println(list.get(i));
                }
                System.out.println("\n");
                System.out.println("Select action");
                action=getNumber();
            }
            else if (action == 0) {
                System.out.println("Program ended");
                break;
            } else {
                System.out.println("Action not recognized. Try again");
                action = getNumber();
            }
        }

    }

    public static void displayNullExercises(int user_id){
        ArrayList<Solution> exercises=Solution.loadAllByUserId(user_id);
        for(int i=0; i<exercises.size();i++){
            String update=exercises.get(i).getUpdated();
            if (update==null){
                System.out.println(exercises.get(i));
            }
        }

    }
    public static int countNullExercises(int user_id){
        ArrayList<Solution> exercises=Solution.loadAllByUserId(user_id);
        int counter=0;
        for(int i=0; i<exercises.size();i++){
            String update=exercises.get(i).getUpdated();
            if (update==null){
                counter++;
            }
        }
        return counter;
    }

    public static void hearedStudent(){
        System.out.println(
                "  #####   #######  #     #  #####    #######  #     #  #######            #####   #     #   #####   #######  #######  #     #          \n" +
                " #     #     #     #     #  #    #   #        ##    #     #              #     #   #   #   #     #     #     #        ##   ##          \n" +
                " #           #     #     #  #     #  #        # #   #     #              #          # #    #           #     #        # # # #          \n" +
                "  #####      #     #     #  #     #  #####    #  #  #     #               #####      #      #####      #     #####    #  #  #          \n" +
                "       #     #     #     #  #     #  #        #   # #     #                    #     #           #     #     #        #     #          \n" +
                " #     #     #     #     #  #    #   #        #    ##     #              #     #     #     #     #     #     #        #     #          \n" +
                "  #####      #      #####   #####    #######  #     #     #               #####      #      #####      #     #######  #     #          \n" +
                "       ");
    }

}
