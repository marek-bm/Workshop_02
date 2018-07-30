package pl.coderslab.EntitiesManager;

import pl.coderslab.DB_entities.UsersGroup;

import java.util.ArrayList;
import java.util.Scanner;

import static pl.coderslab.EntitiesManager.UserManager.getNumber;

public class UserGroupManager {
    public static void printGroups(){
        System.out.println("List of currently available groups:");
        ArrayList<UsersGroup> gr=UsersGroup.loadAllGroups();
        for(int i=0; i<gr.size(); i++){
            System.out.println(gr.get(i));
        }
        System.out.println("\n");
    }

    public static void printOptions(){
        System.out.println("Welcome to Groups Manager. Choose option");
        System.out.println("1 - create new group");
        System.out.println("2 - edit existing group");
        System.out.println("3 - delete group");
        System.out.println("0 - return to main menu");
    }

    public static void selectAction() {
        printGroups();
        printOptions();

        Scanner scanner = new Scanner(System.in);
        int action = getNumber();

        while (action >= 0) {
            if (action == 1) {
                System.out.println("Enter name of new group");
                UsersGroup ug=new UsersGroup();
                ug.setName(scanner.nextLine());
                ug.saveToDB();
                printGroups();

                System.out.println("Select action");
                action = getNumber();
            }
            else if(action ==2){
                System.out.println("Enter group id to modify");
                int groupId=getNumber();
                if(groupId!=0){
                    UsersGroup group=UsersGroup.loadById(groupId);
                    System.out.println("Enter new name of new group");
                    String newName=scanner.nextLine();
                    group.setName(newName);
                    group.saveToDB();
                    printGroups();
                }

                else {
                    System.out.println("Group doesn't exist");
                }

                printGroups();
                System.out.println("Select action");
                action=getNumber();
            }
            else if(action==3) {
                System.out.println("Select id of group to delete");
                UsersGroup ug = UsersGroup.loadById(getNumber());
                ug.delete();
                System.out.println("Group deleted");
                printGroups();
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

}
