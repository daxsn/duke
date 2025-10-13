// Uncomment this when need to run from Moonchester.java 
// package moonchester_utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MoonchesterStorage {

    private static String file_path = "../moonchester_data/Task_List.txt";
    private static File task_list_file = new File(file_path);

    private static void printActiveTasks() {
        try {
            Scanner scanner = new Scanner(task_list_file);
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private static void updateActiveTasks(String new_task) throws IOException {
        FileWriter file_writer = new FileWriter(file_path,true);
        file_writer.write(new_task);
        file_writer.close();
    }

    public static void main(String[] args) {
        printActiveTasks();
        try {
            updateActiveTasks("\nlol");
        } catch (IOException e) {
            System.out.println("yeet");
        }
        

    }

}
