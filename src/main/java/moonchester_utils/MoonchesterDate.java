package moonchester_utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MoonchesterDate {
    public static LocalDateTime convertToDateTime(String dateTimeString) {
        try {
            DateTimeFormatter convertedObject = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, convertedObject);
            return dateTime;
        } catch (DateTimeParseException e) {
            System.out.println("[!] Unable to parse datetime - Please ensure that your deadline/event dates are in this format - dd/mm/yyyy hhmm");
            return null;
        }
    }

    public static String readableDate(LocalDateTime dateTimeObject) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy - HHmm");
        return dateTimeObject.format(formatter);
    }

    public static String saveDateFormat(LocalDateTime dateTimeObject) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        return dateTimeObject.format(formatter);
    }

    public static boolean compareDates(LocalDateTime date_1, LocalDateTime date_2) {
        return date_1.isBefore(date_2);
    }

    // public static void main(String[] args) {
    // // The method should accept a string
    // String date_string = "12/12/2025 1800";
    // String date_string_2 = "12/12/2025 1900";
    // LocalDateTime updated = convertToDateTime(date_string);
    // LocalDateTime updated_2 = convertToDateTime(date_string_2);
    // compareDates(updated, updated_2);
    // }
}
