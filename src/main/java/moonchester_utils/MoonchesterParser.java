package moonchester_utils;

import java.util.ArrayList;
import moonchester_data.*;

public class MoonchesterParser {
    // From the file, it converts Strings into Task objects
    public ArrayList<Task> retrieveObjects(ArrayList<String> lines) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (String line : lines) {
            tasks.add(formatObjects(line));
        }
        return tasks;
    }

    // Convert text format into objects so that it can be read by the program
    private Task formatObjects(String line) {
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
                // Deadline : description, deadline
                Deadline new_deadline = new Deadline(description, MoonchesterDate.convertToDateTime(parts[parts.length - 1].trim(), 0));
                new_deadline.setStatus(isDone);
                return new_deadline;
            case "E":
                // Event : description, from, to
                Event new_event = new Event(description, MoonchesterDate.convertToDateTime(parts[3].trim(), 0), MoonchesterDate.convertToDateTime(parts[parts.length - 1].trim(), 0));
                new_event.setStatus(isDone);
                return new_event;
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }
    }
    

    // Converts Task objects to String objects to save into the file
    public String convertObjects(Task task) {
        int status = 0;
        if (task.getStatusIcon() == "X") {
            status = 1;
        }
        if (task instanceof Todo) {
            // Format Todo
            return "T" +" | "+ status + " | " + task.getDescription();
        }

        if (task instanceof Deadline) {
            // Format Deadline
            Deadline deadlineTask = (Deadline) task;
            return "D" +" | "+ status + " | " + deadlineTask.getDescription() + " | " + MoonchesterDate.saveDateFormat(deadlineTask.getByObject());
        }

        if (task instanceof Event) {
            // Format Event
            Event eventTask = (Event) task;
            return "E" + " | " + status + " | " + eventTask.getDescription() + " | " + MoonchesterDate.saveDateFormat(eventTask.getFromObject()) + " | " + MoonchesterDate.saveDateFormat(eventTask.getToObject());
        }
        
        return "";
    }

}
