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

    private static final String filePath = "moonchester_data/Task_List.txt";
    private static final File taskListFile = new File(filePath);
    MoonchesterParser parser = new MoonchesterParser();

    static {
        try {
            // Check for parent directory
            File parentDir = taskListFile.getParentFile();
            if (parentDir != null && parentDir.exists() == false) {
                parentDir.mkdirs();
            }

            // Create file if it doesn't exist
            if (taskListFile.exists() == false) {
                taskListFile.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("[!] Unable to create the task list file: " + e.getMessage());
        }
    }

    public static ArrayList<String> readLines() {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(taskListFile)) {
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
        new FileWriter(filePath, false).close();
        for (Task item : finalUserList.getList()) {
            String convertedItem = parser.convertObjects(item);
            FileWriter fileWriter = new FileWriter(filePath, true);
            fileWriter.write(convertedItem + System.lineSeparator());
            fileWriter.close();
        }
    }

}
