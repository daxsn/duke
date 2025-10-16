package moonchester_data;

public class Event extends Task {

    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    @Override
    public String printString() {
        return "[E]" + "[" + getStatusIcon() + "] " + getDescription() + "(from: " + this.from + " to: "+ this.to + ")";
    }

}