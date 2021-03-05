package edu.wpi.u.users;

public class Employee extends User{

    // TODO : Add employee specific fields
    /*
    ADMINS are employees
    Radiology doctor
    Janitor
    Security guard
    Nurse
    etc
     */

    public Employee() {

    }
/*
    String userID,
    String name,
    String accountName,
    String password,
    String email,
    Role type,
    String phoneNumber,
    String locationNodeID,
    boolean deleted) {

 */
    public Employee(String userID, String name, String accountName, String password, String email, Role type, String phoneNumber, String locationNodeID, boolean deleted) {
        super(userID, name, accountName, password, email, type, phoneNumber, locationNodeID, deleted);
    }

    @Override
    public void editUser(String name, String userName, String password, String email, Role type, String phoneNumber, boolean deleted) {
        super.editUser(name, userName, password, email, type, phoneNumber, deleted);
    }

    //TODO: Override editUser()
}

