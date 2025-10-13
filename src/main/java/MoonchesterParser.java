
/*

What should the parser do?

- Read task from Task_List.txt via MoonchesterStorage and PARSE it such that it returns an ArrayList<>()
- Converts task from front-end format to back-end format
- Original location of this class -> moonchester_utils
- It is currently outside on the root folder because of testing purposes

*/ 
import java.util.ArrayList;
import moonchester_data.*;
import moonchester_utils.*;

public class MoonchesterParser {
    public ArrayList<Task> retrieveObjects(ArrayList<String> lines) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (String line : lines) {
            tasks.add(formatObjects(line));
        }
        return tasks;
    }

    private Task formatObjects(String line) {
        /*  
        TODO
        - First cut check : when split based on |, first 2 are consistent. Need to cater for Deadline and Event where there are more |
        - To consider : Event August 6th 2-4pm -> E | 0 | project meeting | Aug 6th 2 | 4pm
        */ 
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();
        switch (type) {
            case "T":
                Todo new_todo = new Todo(description);
                new_todo.setStatus(isDone);
                return new_todo;
            case "D":
                System.out.println("Deadline");
                Deadline new_deadline = new Deadline(description, "yeet");
                return new_deadline;
            case "E":
                Event new_event = new Event(description, "yeet", "beet");
                return new_event;
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }
    }
    
    public String convertObjects(Task task) {
        /*
        TODO
        - When user provides a new task : Format according to the type of task and return to the call
         */
        return task.getDescription() + " | " + task.getStatusIcon();
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        ArrayList<String> lines = MoonchesterStorage.readLines();

        MoonchesterParser parser = new MoonchesterParser();
        ArrayList<Task> tasks = parser.retrieveObjects(lines);

        System.out.println("=== Parsed Tasks ===");
        for (Task t : tasks) {
            System.out.println(t);
        }

        System.out.println("\n=== Re-Formatted Lines ===");
        for (Task t : tasks) {
            System.out.println(parser.format(t));
        }
    }
    

}
