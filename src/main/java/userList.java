import java.util.ArrayList;

public class userList {

    private ArrayList<Task> userList = new ArrayList<Task>();

    public userList() {
        userList = new ArrayList<>();
    }

    public void addItem (Task item) {
        userList.add(item);
    }

    public ArrayList<Task> getList () {
        return userList;
    }

    public Task getSpecificTask(int index) {
        return this.userList.get(index-1);
    }

    public int getSize() {
        return this.userList.size();
    }

}