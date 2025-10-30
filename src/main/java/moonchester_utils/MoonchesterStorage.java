// Uncomment this when need to run from Moonchester.java 
package moonchester_utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import moonchester_data.Task;
import moonchester_data.UserList;

public class MoonchesterStorage {

    private static final String FILE_PATH = "moonchester_data/Task_List.txt";
    private static final File TASK_LIST_FILE = new File(FILE_PATH);
    MoonchesterParser parser = new MoonchesterParser();

    static {
        try {
            // Check for parent directory
            File parentDir = TASK_LIST_FILE.getParentFile();
            if (parentDir != null && parentDir.exists() == false) {
                parentDir.mkdirs();
            }

            // Create file if it doesn't exist
            if (TASK_LIST_FILE.exists() == false) {
                TASK_LIST_FILE.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("[!] Unable to create the task list file: " + e.getMessage());
        }
    }

    public static ArrayList<String> readLines() {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(TASK_LIST_FILE)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (java.io.FileNotFoundException e) {
            //System.out.println(System.getProperty("user.dir"));
            System.out.println("File not found: " + e.getMessage());
        }
        return lines;
    }

    // When user exits, this function will get called to update the file
    public void updateActiveTasks(UserList finalUserList) throws IOException {
        new FileWriter(FILE_PATH, false).close();
        for (Task item : finalUserList.getList()) {
            String converted_item = parser.convertObjects(item);
            FileWriter file_writer = new FileWriter(FILE_PATH, true);
            file_writer.write(converted_item + System.lineSeparator());
            file_writer.close();
        }
    }

}
