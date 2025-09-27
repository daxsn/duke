package moonchester_data;
import moonchester_utils.MoonchesterException;

public class Task {
    protected String description;
    protected boolean status;

    public Task(String description) throws MoonchesterException{
        if (description.trim().isEmpty() == true || description == null) {
            throw new MoonchesterException("[!] Description cannot be empty.");
        }
        this.description = description;
        this.status = false;
    }

    // Getters

    public String getDescription() {
        return this.description;
    }

    public String getStatusIcon() {
        if(this.status == true) {
            return "X";
        }
        return " ";
    }

    // Setters
    public void setStatus(boolean status){
        this.status = status;
    }

    // For other subclasses to override
    public String printString() {
        return "[" + getStatusIcon() + "] " + getDescription();
    }

}