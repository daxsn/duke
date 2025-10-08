import moonchester_data.*;
import moonchester_utils.*;

public class Moonchester {
    public static void main(String[] args) throws MoonchesterException {
        UserList userList = new UserList();
        MoonchesterHandler handler = new MoonchesterHandler(userList);
        handler.start();
    }
}
