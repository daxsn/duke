package moonchester_utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MoonchesterDate {
    // Converts String Dates to LocalDatetime objects; Option is used to determine how to format the dates
    public static LocalDateTime convertToDateTime(String dateTimeString, int option) {
        try {
            if (option == 1) {
                dateTimeString = dateTimeString + " 0000";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                return LocalDateTime.parse(dateTimeString, dateFormatter);
            } else {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                return LocalDateTime.parse(dateTimeString, dateTimeFormatter);
            }
        } catch (DateTimeParseException e) {
            System.out.println("[!] Unable to parse datetime - Please ensure that your deadline/event dates are in this format - [Addition of deadlines/events] dd/MM/yyyy HHmm or [Query for tasks] dd/MM/yyyy");
            return null;
        }
    }

    // To convert LocalDateTime to readable strings - Accessed by Tasks Objects
    public static String readableDate(LocalDateTime dateTimeObject) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy - HHmm");
        return dateTimeObject.format(formatter);
    }

    // To convert LocalDateTime to Strings based on the format used in the file
    public static String saveDateFormat(LocalDateTime dateTimeObject) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        return dateTimeObject.format(formatter);
    }

    // To check if dates given is before the second date - mainly used for Event task
    public static boolean compareDateTime(LocalDateTime date_1, LocalDateTime date_2) {
        return date_1.isBefore(date_2);
    }

    // This is used to compared date only, excluding time.
    public static boolean compareDate(LocalDateTime date_1, LocalDateTime date_2) {
        return date_1.getDayOfMonth() == date_2.getDayOfMonth()
            && date_1.getMonth() == date_2.getMonth()
            && date_1.getYear() == date_2.getYear();
    }
}
