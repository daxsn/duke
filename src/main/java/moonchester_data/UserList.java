package moonchester_data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import moonchester_utils.MoonchesterDate;

public class UserList {

    private ArrayList<Task> userList = new ArrayList<>();

    public UserList() {
        userList = new ArrayList<>();
    }

    public UserList(ArrayList<Task> userList_init) {
        this.userList = userList_init;
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

    public ArrayList<Task> getList(LocalDateTime specificDate) {
        ArrayList<Task> queriedList = new ArrayList<>();
        for(Task curr : userList) {
            if (curr instanceof Deadline deadlineObject) {
                if (MoonchesterDate.compareDate(specificDate, deadlineObject.getByObject())) {
                    queriedList.add(deadlineObject);
                }
            }
            if (curr instanceof Event eventObject) {
                if (MoonchesterDate.compareDate(specificDate, eventObject.getFromObject())) {
                    queriedList.add(eventObject);
                }
            }
        }
        return queriedList;
    }

    public Task getSpecificTask(int index) {
        return this.userList.get(index-1);
    }

    public int getSize() {
        return this.userList.size();
    }



}