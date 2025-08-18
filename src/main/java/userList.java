import java.util.ArrayList;

public class userList {

    private ArrayList<String> userList = new ArrayList<String>();

    public userList() {
        userList = new ArrayList<>();
    }

    public void addItem (String item) {
        userList.add(item);
    }

    public ArrayList<String> getList () {
        return userList;
    }

}