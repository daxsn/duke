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
                    case "list": 
                        printList(); 
                        break;
                    case "exit": 
                        userExit(); 
                        return;
                    case "mark":
                    case "m":
                        handleMarking(splittedString, true); 
                        break;
                    case "unmark":
                    case "um":
                        handleMarking(splittedString, false); 
                        break;
                    case "todo":
                    case "t":
                        addTodo(splittedString); 
                        break;
                    case "deadline":
                    case "d":
                        addDeadline(joinFromSecond(splittedString)); 
                        break;
                    case "event":
                    case "e":
                        addEvent(joinFromSecond(splittedString)); 
                        break;
                    case "date":
                    case "da":
                        queryDate(splittedString[1]); 
                        break;
                    case "find":
                    case "f": 
                        queryKeyword(splittedString[1]); 
                        break;
                    case "delete":
                    case "del":
                        try {
                            handleDelete(Integer.parseInt(splittedString[1]));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new MoonchesterException("[!] Please select a valid S/N");
                        } catch (IndexOutOfBoundsException e) {
                            throw new MoonchesterException("[!] Please select a valid S/N");
                        }
                        break;
                    default:
                        throw new MoonchesterException("[!] Unknown Command. Permitted Commands: todo (t), deadline (d), event (e), list, mark(m), unmark(um), delete (del), find (f), date(da),exit");
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
        __  __                        _               _            
        |  \\/  | ___   ___  _ __   ___| |__   ___  ___| |_ ___ _ __ 
        | |\\/| |/ _ \\ / _ \\| '_ \\ / __| '_ \\ / _ \\/ __| __/ _ \\ '__|
        | |  | | (_) | (_) | | | | (__| | | |  __/\\__ \\ ||  __/ |   
        |_|  |_|\\___/ \\___/|_| |_|\\___|_| |_|\\___||___/\\__\\___|_|   
                                                                    
        Hello! I am Moonchester, your personal chatbot!
        List of commands:
        Command/Shortcut
        1. list
        2. todo/t [description]
        3. deadline/d [description] /by [date - dd/MM/yyyy HHmm or ddd]
        4. event/e [description] /from [date - dd/MM/yyyy HHmm or dd/MM/yyyy or ddd] /to [date - dd/MM/yyyy HHmm or dd/MM/yyyy or ddd]
        5. find/f [keyword]
        6. date/da [date - dd/MM/yyyy HHmm or dd/MM/yyyy]
        7. delete/del [S/N of list]
        8. mark/m [S/N of list]
        9. unmark/um [S/N of list]
        10. exit
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
        String[] userItemSplit = userItem.split(delimiter);
        return userItemSplit;
    }

    // Helper method to join task description
    private static String joinFromSecond(String[] taskDescription) {
        if (taskDescription.length <= 1) {
            return "";
        }
        String[] result = Arrays.copyOfRange(taskDescription, 1, taskDescription.length);
        return String.join(" ", result);
    }


    // Prints out the existing User List
    private void printList() {
        System.out.println("[+] User's List");
        int counter = 1;
        for (Task item : userList.getList()) {
            // %-4s%s%n -> Left align the number string with width of 4 characters, %s%s is for the string and %n is newline
            System.out.printf("%-4s%s%n", counter + ".", item.printString());
            counter++;
        }
        System.out.println("____________________________________________________________");
    }

    private void printListDate(ArrayList<Task> queriedList, String dateString) {
        System.out.println("[+] Events/Deadlines occuring on " + dateString);
        int counter = 1;
        for (Task item : queriedList) {
            System.out.println(counter + ". " + item.printString());
            counter++;
        }
        System.out.println("____________________________________________________________");
        System.out.println("Press \"ENTER\" to view your master list - You will need to view your master list to mark/unmark them :)");
        System.out.print("____________________________________________________________");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        printList();
    }
    private void printListKeyword(ArrayList<Task> queriedList, String keyword) {
        System.out.println("[+] Keyword found in the following tasks: [" + keyword + "]");
        int counter = 1;
        for (Task item : queriedList) {
            System.out.println(counter + ". " + item.printString());
            counter++;
        }
        System.out.println("____________________________________________________________");
        System.out.println("Press \"ENTER\" to view your master list - You will need to view your master list to mark/unmark them :)");
        System.out.print("____________________________________________________________");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        printList();
    }

    // Handles the marking mechanism
    private void handleMarking(String[] userItemSplit, boolean status) {
        try {
            int index = Integer.parseInt(userItemSplit[1]);
            if (index <= 0 || index > userList.getSize()) {
                throw new MoonchesterException("[!] Please select a valid S/N");
            }

            userList.getSpecificTask(index).setStatus(status);
            String statusDescription;

            if (status == true) {
                statusDescription = "completed";
            } else {
                statusDescription = "not completed";
            }
            System.out.println("[+] Marked as " + statusDescription + " : " + userList.getSpecificTask(index).getDescription());
            System.out.println("____________________________________________________________");

        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid task number.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[!] Please specify which task to mark or unmark.");
        }
    }


    // Handles delete mechanism
    private void handleDelete(int arrayIndex) {
        try {
            userList.deleteItem(arrayIndex - 1, userList.getSpecificTask(arrayIndex));
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
        String[] taskDescriptionArray = Arrays.copyOfRange(splitted_string, 1, splitted_string.length);
        String taskDescription = String.join(" ", taskDescriptionArray);
        Todo new_todo = new Todo(taskDescription);
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
            System.err.println("[!] Deadline appears to have missing parameters, please follow this format: deadline [description] /by [dd/MM/yyyy HHmm]");
        }
    }


    // Handles adding an Event task
    private void addEvent(String taskDescription) {
        try {
            String[] description_array = stringSplitter(taskDescription, "/from");
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
            System.err.println("[!] Event appears to have missing parameters, , please follow this format : event [description] /from [dd/MM/yyyy HHmm] /to [dd/MM/yyyy HHmm]");
        }

    }


    /**
     * Retrieves the tasks based on a date found in the description and prints the list
     * 
     * @param dateString Date specified by the user
     */
    private void queryDate(String dateString) {
        // This function returns the list of tasks on a given date
        // Format - dd/MM/yyyy
        LocalDateTime convertedDate = MoonchesterDate.convertToDateTime(dateString, 1);
        if(convertedDate == null) {
            return;
        }
        ArrayList<Task> queriedList = userList.getList(convertedDate);
        printListDate(queriedList, dateString);
    }

    /**
     * Retrieves the tasks based on a keyword found in the description and prints the list
     * 
     * @param keyword Keyword specified by the user
     */
    private void queryKeyword(String keyword) {
        ArrayList<Task> queriedList = userList.getList(keyword);
        printListKeyword(queriedList,keyword);
    }

}
