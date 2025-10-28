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

    private static final String file_path;
    private static final File task_list_file;
    static {
        String baseDir = System.getProperty("user.dir");
        String resolvedPath;

        // Handle both Gradle and direct VS Code runs
        if (baseDir.endsWith("java")) {
            resolvedPath = baseDir + "/moonchester_data/Task_List.txt";
        } else {
            resolvedPath = baseDir + "/src/main/java/moonchester_data/Task_List.txt";
        }

        file_path = resolvedPath;
        task_list_file = new File(file_path);
    }
    
    MoonchesterParser parser = new MoonchesterParser();

    // Reads each line of the file and returns the array
    public static ArrayList<String> readLines() {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(task_list_file)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return lines;
    }

    // When user exits, this function will get called to update the file
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
