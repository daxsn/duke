public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }
    @Override
    public String printString() {
        return "[T]" + "[" + getStatusIcon() + "] " + getDescription();
    }

}