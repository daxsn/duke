import java.util.Scanner;

public class Moonchester {

    public static void userGreeting () {
        String greetingMessage = """
        ____________________________________________________________
        Hello! I am Moonchester, your personal chatbot!
        What can I do for you?
        """;
        System.out.print(greetingMessage);
    }

    public static void userExit () {
        String exitMessage = """
        Hope to see you again soon, goodbye!
        ____________________________________________________________
        """;
        System.out.print(exitMessage);
    }

    public static void printList (userList userList) {
        System.out.println("[+] User's List");
        int counter = 1;
        for (Task item : userList.getList()) {
            System.out.println(counter + ". " + item.printString());
            counter++;
        }
        System.out.println("____________________________________________________________");
    }

    public static void handleMarking (userList userList, String userItem, boolean status) {
        String delimiter = " ";
        String[] userItem_split = userItem.split(delimiter);
        int index = Integer.parseInt(userItem_split[1]);
        if (userList.getSize() >= index && index > 0) {
            userList.getSpecificTask(index).setStatus(status);
            System.out.println("[+] Marked as completed: " + userList.getSpecificTask(index).getDescription());
            System.out.println("____________________________________________________________");
        }
        else {
            System.out.println("[!] Please select a valid S/N");
            System.out.println("____________________________________________________________");
        }
    }

    public static void main(String[] args) {
        Scanner userCommand_Obj = new Scanner(System.in); 
        userList userList = new userList();
        userGreeting();
        while (true) {
            System.out.print("Command / Add : ");
            String userItem = userCommand_Obj.nextLine();
            if (userItem.equals("list")) {
                printList(userList);
            }
            else if (userItem.equals("bye")) {
                break;
            }
            else if (userItem.startsWith("mark")) {
                handleMarking(userList, userItem, true);
            }
            else if (userItem.startsWith("unmark")) {
                handleMarking(userList, userItem, false);
            }
            else {
                Task new_task = new Task(userItem);
                userList.addItem(new_task);
                System.out.println("[+] Added : " + userItem);
                System.out.println("____________________________________________________________");
            }
        }
        userExit();

    }
}