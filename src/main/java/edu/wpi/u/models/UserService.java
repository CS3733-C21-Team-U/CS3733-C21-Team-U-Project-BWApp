package edu.wpi.u.models;

import edu.wpi.u.database.UserData;
import edu.wpi.u.users.User;

import java.util.ArrayList;

public class UserService {

    static UserData ud = new UserData();
    ArrayList<User> users = new ArrayList<>();

    public UserService() {
        this.users = ud.loadUsers();
    }
}
