// Uncomment this when need to run from Moonchester.java 
package moonchester_utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import moonchester_data.*;

public class MoonchesterStorage {

    // Rmb to change this
    //private static String file_path = "Z:/School/Year_3/TIC2002/Project/duke/src/main/java/moonchester_data/Task_List.txt";
    private static String file_path = "moonchester_data/Task_List.txt";
    private static File task_list_file = new File(file_path);
    MoonchesterParser parser = new MoonchesterParser();

    public static ArrayList<String> readLines() {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(task_list_file)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (java.io.FileNotFoundException e) {
            //System.out.println(System.getProperty("user.dir"));
            System.out.println("File not found: " + e.getMessage());
        }
        return lines;
    }

    public void updateActiveTasks(UserList finalUserList) throws IOException {
        new FileWriter(file_path, false).close();
        for (Task item : finalUserList.getList()) {
            String converted_item = parser.convertObjects(item);
            FileWriter file_writer = new FileWriter(file_path, true);
            file_writer.write(converted_item + System.lineSeparator());
            file_writer.close();
        }
    }

}
