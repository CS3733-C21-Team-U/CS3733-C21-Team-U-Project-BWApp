package edu.wpi.u.users;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


/*
Users table
Employees id references Users
Patient id references Users
 */

public abstract class User extends RecursiveTreeObject<User> {
    protected String userID;
    protected String name;
    protected String userName;
    protected String password;
    protected Role type;
    protected String phoneNumber;
    protected String email;
    protected boolean deleted;

    public User(){}

    public User(String userID, String name, String accountName, String password, String email, Role type, String phoneNumber, boolean deleted) {
        this.userID = userID;
        this.name = name;
        this.userName = accountName;
        this.password = password;
        this.type = type;
        this.deleted = deleted;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * This function will be called by UserService to update the ArrayList of Users / the active user
     * @param name
     * @param userName
     * @param password
     * @param type
     */
    public void editUser(String name, String userName, String password, String email, Role type, String phoneNumber, boolean deleted){
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.deleted = deleted;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserID() {return userID;}

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getType() {
        return type;
    }

    public void setType(Role type) {
        this.type = type;
    }
}
