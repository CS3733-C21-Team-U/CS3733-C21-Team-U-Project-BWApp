package edu.wpi.u.users;

public class User {
    protected String userID;
    protected String name;
    protected String accountName;
    protected String password;
    protected StaffType type;
    protected boolean employed;

    public User(){

    }

    public User(String userID, String name, String accountName, String password, StaffType role, boolean employed) {
        this.userID = userID;
        this.name = name;
        this.accountName = accountName;
        this.password = password;
        this.type = role;
        this.employed = employed;
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StaffType getType() {
        return type;
    }

    public void setType(StaffType type) {
        this.type = type;
    }

    public boolean getEmployed() {
        return employed;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }
}
