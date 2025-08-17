import java.util.Scanner;

public class Moonchester {

    public static void userGreeting () {
        String greetingMessage = """
        ____________________________________________________________
        Hello! I am Moonchester, your personal chatbot!
        What can I do for you?
        ____________________________________________________________
        """;
        System.out.print(greetingMessage);
    }

    public static void userExit () {
        String exitMessage = """
        ____________________________________________________________
        Hope to see you again soon, goodbye!
        ____________________________________________________________
        """;
        System.out.print(exitMessage);
    }

    public static void main(String[] args) {
        while (true) {
            userGreeting();
            Scanner userCommand_Obj = new Scanner(System.in); 
            System.out.print("Command: ");
            String userCommand = userCommand_Obj.nextLine(); 
            System.out.println("User's command: " + userCommand); 
            if (userCommand.equals("bye")) {
                break;
            }
        }
        userExit();

    }
}