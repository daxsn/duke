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
        for (String item : userList.getList()) {
            System.out.println(counter + ". " + item);
            counter++;
        }
        System.out.println("____________________________________________________________");
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
            else {
                userList.addItem(userItem);
                System.out.println("[+] Added : " + userItem);
                System.out.println("____________________________________________________________");
            }
        }
        userExit();

    }
}