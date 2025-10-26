package moonchester_data;
import java.time.LocalDateTime;
import moonchester_utils.MoonchesterDate;

public class Deadline extends Task {

    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    // Convert into readable date
    public String getBy() {
        return MoonchesterDate.readableDate(this.by);
    }

    // For comparisons with other date objects
    public LocalDateTime getByObject() {
        return this.by;
    }

    // Prints the task details
    @Override
    public String printString() {
        return "[D]" + "[" + getStatusIcon() + "] " + getDescription() + "(by: " + getBy() + "hrs" +")";
    }

}