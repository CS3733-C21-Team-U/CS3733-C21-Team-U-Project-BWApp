package edu.wpi.u.users;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

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
    boolean deleted
 */
    public Employee(String userID, String name, String accountName, String password, String email, Role type, String phoneNumber, String locationNodeID, boolean deleted) {
        super(userID, name, accountName, password, email, type, phoneNumber, locationNodeID, deleted);
    }

    public Employee(StringProperty userIDfx, StringProperty namefx, StringProperty userNamefx, StringProperty passwordfx, StringProperty typefx, StringProperty phoneNumberfx, StringProperty emailfx, BooleanProperty deletedfx, StringProperty locationNodeIDfx) {
        super(userIDfx, namefx, userNamefx, passwordfx, typefx, phoneNumberfx, emailfx, deletedfx, locationNodeIDfx);
    }

    @Override
    public void editUser(String name, String userName, String password, String email, Role type, String phoneNumber, boolean deleted) {
        super.editUser(name, userName, password, email, type, phoneNumber, deleted);
    }

    //TODO: Override editUser()
}

