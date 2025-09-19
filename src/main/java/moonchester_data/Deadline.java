package moonchester_data;

public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }
    @Override
    public String printString() {
        return "[D]" + "[" + getStatusIcon() + "] " + getDescription() + "(by: " + this.by + ")";
    }

}