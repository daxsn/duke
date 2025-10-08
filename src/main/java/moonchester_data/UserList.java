package moonchester_data;

import java.util.ArrayList;

public class UserList {

    private ArrayList<Task> userList = new ArrayList<>();

    public UserList() {
        userList = new ArrayList<>();
    }

    public void addItem (Task item) {
        userList.add(item);
        System.out.println("[+] Added : " + item.printString());
        System.out.println("[*] You have a total of " + this.getSize() + " task(s)");
        System.out.println("____________________________________________________________");
    }

    public void deleteItem (int item_index, Task item) {
        userList.remove(item_index);
        System.out.println("[+] Deleted : " + item.printString());
        System.out.println("[*] You have a total of " + this.getSize() + " task(s)");
        System.out.println("____________________________________________________________");
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