package moonchester_utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import moonchester_data.Deadline;
import moonchester_data.Event;
import moonchester_data.Task;
import moonchester_data.Todo;
import moonchester_data.UserList;

public class MoonchesterHandler {
    private final Scanner scanner;
    private final UserList userList;
    public MoonchesterStorage storage;

    public MoonchesterHandler(UserList userList) {
        this.userList = userList;
        this.scanner = new Scanner(System.in);
        this.storage = new MoonchesterStorage();
    }

    public void start() {
        userGreeting();

        while (true) {
            System.out.print("Command / Add : ");
            String userItem = scanner.nextLine();
            String[] splittedString = stringSplitter(userItem, " ");

            try {
                String command = splittedString[0].toLowerCase();
                switch (command) {
                    case "list" : printList(); break;
                    case "exit" : { userExit(); return; }
                    case "mark" : handleMarking(splittedString, true); break;
                    case "unmark" : handleMarking(splittedString, false); break;
                    case "todo" : addTodo(splittedString); break;
                    case "deadline" : addDeadline(joinFromSecond(splittedString)); break;
                    case "event" : addEvent(joinFromSecond(splittedString)); break;
                    case "date": queryList(splittedString[1]); break;
                    case "delete" : {
                        try {
                            handleDelete(Integer.parseInt(splittedString[1]));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new MoonchesterException("[!] Please select a valid S/N");
                        } catch (IndexOutOfBoundsException e) {
                            throw new MoonchesterException("[!] Please select a valid S/N");
                        }
                        break;
                    }
                    default : throw new MoonchesterException("[!] Unknown Command. Permitted Commands : todo, deadline, event, list, mark, unmark, exit");
                }
            } catch (MoonchesterException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    // Program greeting
    private void userGreeting() {
        String greetingMessage = """
        ____________________________________________________________
        Hello! I am Moonchester, your personal chatbot!
        What can I do for you?
        """;
        System.out.print(greetingMessage);
    }
    // When the user exits, it will print a banner AND saves the task list to the file
    private void userExit() {
        String exitMessage = """
        Hope to see you again soon, goodbye!
        ____________________________________________________________
        """;
        try {
            // Only updates the task list AFTER the user exits the program
            storage.updateActiveTasks(userList);
        } catch (java.io.IOException e) {
            System.err.println("[!] Error saving tasks: " + e.getMessage());
        }
        System.out.print(exitMessage);
        scanner.close();
    }

    // Helper method to split the user-input
    private String[] stringSplitter(String userItem, String delimiter) {
        String[] userItem_split = userItem.split(delimiter);
        return userItem_split;
    }

    // Helper method to join task description
    private static String joinFromSecond(String[] task_description) {
        if (task_description.length <= 1) {
            return "";
        }
        String[] result = Arrays.copyOfRange(task_description, 1, task_description.length);
        return String.join(" ", result);
    }


    // Prints out the existing User List
    private void printList() {
        System.out.println("[+] User's List");
        int counter = 1;
        for (Task item : userList.getList()) {
            System.out.println(counter + ". " + item.printString());
            counter++;
        }
        System.out.println("____________________________________________________________");
    }

    // Method overloading - Prints out specific userlist
    private void printList(ArrayList<Task> queriedList, String dateString) {
        System.out.println("[+] Events/Deadlines occuring on " + dateString);
        int counter = 1;
        for (Task item : queriedList) {
            System.out.println(counter + ". " + item.printString());
            counter++;
        }
        System.out.println("____________________________________________________________");
    }

    // Handles the marking mechanism
    private void handleMarking(String[] userItemSplit, boolean status) {
        try {
            int index = Integer.parseInt(userItemSplit[1]);
            if (index <= 0 || index > userList.getSize()) {
                throw new MoonchesterException("[!] Please select a valid S/N");
            }

            userList.getSpecificTask(index).setStatus(status);
            String statusDescription = status ? "completed" : "not completed";
            System.out.println("[+] Marked as " + statusDescription + " : " + userList.getSpecificTask(index).getDescription());
            System.out.println("____________________________________________________________");

        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid task number.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[!] Please specify which task to mark or unmark.");
        }
    }


    // Handles delete mechanism
    private void handleDelete(int array_index) {
        try {
            userList.deleteItem(array_index - 1, userList.getSpecificTask(array_index));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[!] Ensure that the S/N is valid.");
        }
        
    }


    // Extract the dates from event userinput based on /to and /from
    private String[] eventExtractor(String eventArray) {
        String[] results = new String[2];
        String[] eventArrayParts = eventArray.split("/to");
        results[0] = eventArrayParts[0].replace("/from", "").trim();
        results[1] = eventArrayParts[1].trim();
        return results;
    }


    // Handles adding a Todo task
    private void addTodo(String[] splitted_string) {
        String[] task_description_array = Arrays.copyOfRange(splitted_string, 1, splitted_string.length);
        String task_description = String.join(" ", task_description_array);
        Todo new_todo = new Todo(task_description);
        userList.addItem(new_todo);
    }


    // Handles adding a Deadline task
    private void addDeadline(String taskDescription) {
        try {
            String[] parts = stringSplitter(taskDescription, "/by");
            LocalDateTime date = MoonchesterDate.convertToDateTime(parts[1].trim(), 0);
            if (date == null) {
                return;
            }
            Deadline newDeadline = new Deadline(parts[0], date);
            userList.addItem(newDeadline);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("[!] Deadline appears to have missing parameters, please follow this format: deadline [description] /by [day]");
        }
    }


    // Handles adding an Event task
    private void addEvent(String task_description) {
        try {
            String[] description_array = stringSplitter(task_description, "/from");
            String description = description_array[0];
            String[] results = eventExtractor(description_array[1]);
            // Compare fromDate and toDate
            LocalDateTime fromDate = MoonchesterDate.convertToDateTime(results[0], 0);
            LocalDateTime toDate = MoonchesterDate.convertToDateTime(results[1], 0);
            if (fromDate == null || toDate == null) {
                return;
            }

            if (!MoonchesterDate.compareDateTime(fromDate, toDate)) {
                System.err.println("[!] From date is AFTER to date - Please ensure that /from is earlier than /to");
                return;
            }

            Event newEvent = new Event(description, fromDate, toDate);
            userList.addItem(newEvent);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("[!] Event appears to have missing parameters, , please follow this format : event [description] /from [day] [time] /to [time]");
        }

    }


    // Queries for Tasks in the userlist based on the date given
    private void queryList(String dateString) {
        // This function returns the list of tasks on a given date
        // Format - dd/MM/yyyy
        LocalDateTime convertedDate = MoonchesterDate.convertToDateTime(dateString, 1);
        if(convertedDate == null) {
            return;
        }
        ArrayList<Task> queriedList = userList.getList(convertedDate);
        printList(queriedList, dateString);
    }


}
