package moonchester_data;
import java.time.LocalDateTime;
import moonchester_utils.MoonchesterDate;

public class Deadline extends Task {

    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public String getBy() {
        return MoonchesterDate.readableDate(this.by);
    }

    public LocalDateTime getByObject() {
        return this.by;
    }

    @Override
    public String printString() {
        return "[D]" + "[" + getStatusIcon() + "] " + getDescription() + "(by: " + getBy() + "hrs" +")";
    }

}