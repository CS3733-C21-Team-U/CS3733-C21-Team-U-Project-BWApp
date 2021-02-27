package edu.wpi.u.models;

import edu.wpi.u.database.UserData;
import edu.wpi.u.users.User;
import java.util.ArrayList;

public class UserService {

    static UserData ud = new UserData();
    ArrayList<User> users = new ArrayList<>();
    User activeUser = new User();

    public UserService() {
        this.users = ud.loadUsers();
    }
    
    public void setUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public User getActiveUser() {
        return this.activeUser;
    }
}
