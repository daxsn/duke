import java.util.ArrayList;
import moonchester_data.*;
import moonchester_utils.*;

public class Moonchester {
    public static void main(String[] args) throws MoonchesterException {
        ArrayList<String> lines = MoonchesterStorage.readLines();
        MoonchesterParser parser = new MoonchesterParser();
        ArrayList<Task> tasks = parser.retrieveObjects(lines);
        UserList userList = new UserList(tasks);
        MoonchesterHandler handler = new MoonchesterHandler(userList);
        handler.start();
    }
}
