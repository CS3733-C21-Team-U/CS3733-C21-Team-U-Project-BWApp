package edu.wpi.u.models;

import edu.wpi.u.database.Database;
import edu.wpi.u.database.UserData;
import edu.wpi.u.requests.Request;
import edu.wpi.u.users.StaffType;
import edu.wpi.u.users.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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

    public void loadCSVFile(String path, String tableName){
        Database.getDB().dropValues();
        Database.getDB().readCSV(path,tableName);
        this.users = ud.loadUsers();
    }

    public void saveCSVFile(String path, String tableName){
        Database.getDB().saveCSV(tableName,path , "User"); // TODO: Provide header
    }

    public void addUser(String name, String accountName, String password, StaffType type, boolean employed){
        Random rand = new Random();
        int userID = rand.nextInt();
        String id = Integer.toString(userID);
        User newUser = new User(id,name,accountName,password,type,employed);
        ud.addUser(newUser);
        this.users.add(newUser);
    }

    public String deleteUser(String userID) {
        for(User u : this.users){
            if(u.getUserID().equals(userID)){
                u.setEmployed(false);
                this.users.remove(u);
                ud.delUser(u);
                return "";
            }
        }
        return "";
    }

    public String updateUser(String userID, String name, String accountName, String password, StaffType type, boolean employed){
        for(User u : this.users){
            if(u.getUserID().equals(userID)){
                if(!employed){
                    this.users.remove(u);
                }
                u.editUser(name, accountName,password,type,employed);
                ud.updUser(u);
                return "";
            }
        }
        return userID;
    }

}