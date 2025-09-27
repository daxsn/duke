import java.util.Arrays;
import java.util.Scanner;
import moonchester_data.*;
import moonchester_utils.MoonchesterException;

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

    public static void printList (UserList userList) {
        System.out.println("[+] User's List");
        int counter = 1;
        for (Task item : userList.getList()) {
            System.out.println(counter + ". " + item.printString());
            counter++;
        }
        System.out.println("____________________________________________________________");
    }

    public static void handleMarking (UserList userList, String[] userItem_split, boolean status) {
        int index = Integer.parseInt(userItem_split[1]);
        String status_description = "";
        if (status == false) {
            status_description = "not completed";
        }
        else {
            status_description = "completed";
        }
        if (userList.getSize() >= index && index > 0) {
            userList.getSpecificTask(index).setStatus(status);
            System.out.println("[+] Marked as " + status_description + " : " + userList.getSpecificTask(index).getDescription());
            System.out.println("____________________________________________________________");
        }
        else {
            System.out.println("[!] Please select a valid S/N");
            System.out.println("____________________________________________________________");
        }
    }

    // Can reuse
    public static String[] stringSplitter(String userItem, String delimiter) {
        String[] userItem_split = userItem.split(delimiter);
        return userItem_split;
    }

    // To help extract to and from
    public static String[] eventExtractor(String event_array) {
        String[] results = new String[2];
        String[] event_array_parts = event_array.split("/to");
        results[0] = event_array_parts[0].replace("/from", "").trim();
        results[1]   = event_array_parts[1].trim();
        return results;
    }
    
    // For deadline and event, to make a copy of task description and join them back
    private static String joinFromSecond(String[] task_description) {
        if (task_description.length <= 1) {
            return "";
        }
        String[] result = Arrays.copyOfRange(task_description, 1, task_description.length);
        return String.join(" ", result);
    }

    // Todo
    public static void addTodo(UserList userList, String[] splitted_string) {
        String[] task_description_array = Arrays.copyOfRange(splitted_string, 1, splitted_string.length);
        String task_description = String.join(" ", task_description_array);
        Todo new_todo = new Todo(task_description);
        userList.addItem(new_todo);
    }

    // Deadline
    public static void addDeadline(UserList userList, String task_description) {
        try {
            String[] splitted_string = stringSplitter(task_description, "/by");
            Deadline new_deadline = new Deadline(splitted_string[0], splitted_string[1].replace(" ", ""));
            userList.addItem(new_deadline);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("[!] Deadline appears to have missing parameters, please follow this format : deadline [description] /by [day]");
        }

    }

    //Event
    public static void addEvent(UserList userList, String task_description) {
        try {
            String[] description_array = stringSplitter(task_description, "/from");
            String description = description_array[0];
            String[] results = eventExtractor(description_array[1]);
            Event new_event = new Event(description, results[0], results[1]);
            userList.addItem(new_event);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("[!] Event appears to have missing parameters, , please follow this format : event [description] /from [day] [time] /to [time]");
        }

    }

    public static void main(String[] args) throws MoonchesterException{
        Scanner userCommand_Obj = new Scanner(System.in); 
        UserList userList = new UserList();
        userGreeting();
        while (true) {
            System.out.print("Command / Add : ");
            String userItem = userCommand_Obj.nextLine();
            String[] splitted_string = stringSplitter(userItem, " ");
            try {
                if (splitted_string[0].equals("list")) {
                    printList(userList);
                }
                else if (splitted_string[0].equals("exit")) {
                    break;
                }
                else if (splitted_string[0].startsWith("mark")) {
                    handleMarking(userList, splitted_string, true);
                }
                else if (splitted_string[0].startsWith("unmark")) {
                    handleMarking(userList, splitted_string, false);
                }
                else if (splitted_string[0].startsWith("todo")) {
                    addTodo(userList, splitted_string);
                }
                else if (splitted_string[0].startsWith("deadline")) {
                    String task_description = joinFromSecond(splitted_string);
                    addDeadline(userList, task_description);
                }
                else if (splitted_string[0].startsWith("event")) {
                    String task_description = joinFromSecond(splitted_string);
                    addEvent(userList, task_description);
                }
                else {
                    throw new MoonchesterException("[!] Unknown Command. Permitted Commands : todo, deadline, event, list, mark, unmark, exit");
                }
            } catch (MoonchesterException e) {
                System.out.println(e.getMessage());
            }

        }
        userExit();
        userCommand_Obj.close();
    }
}