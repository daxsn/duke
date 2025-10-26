package moonchester_data;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    // Prints the task details
    @Override
    public String printString() {
        return "[T]" + "[" + getStatusIcon() + "] " + getDescription();
    }

}