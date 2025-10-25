package moonchester_data;
import java.time.LocalDateTime;
import moonchester_utils.MoonchesterDate;

public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return MoonchesterDate.readableDate(this.from);
    }

    public String getTo() {
        return MoonchesterDate.readableDate(this.to);
    }

    public LocalDateTime getToObject() {
        return this.to;
    }

    public LocalDateTime getFromObject() {
        return this.from;
    }

    @Override
    public String printString() {
        return "[E]" + "[" + getStatusIcon() + "] " + getDescription() + "(from: " + getFrom() + "hrs" + " to: "+ getTo() + "hrs" +")";
    }

}