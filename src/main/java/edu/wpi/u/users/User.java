package edu.wpi.u.users;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.u.algorithms.Node;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

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
    protected String locationNodeID; // TODO MOVE

    protected StringProperty userIDfx;
    protected StringProperty namefx;
    protected StringProperty userNamefx;
    protected StringProperty passwordfx;
    protected StringProperty typefx;
    protected StringProperty phoneNumberfx;
    protected StringProperty emailfx;
    protected BooleanProperty deletedfx;
    protected StringProperty locationNodeIDfx;

    public User(){}

    public User(String userID, String name, String accountName, String password, String email, Role type, String phoneNumber, String locationNodeID, boolean deleted) {
        this.userID = userID;
        this.name = name;
        this.userName = accountName;
        this.password = password;
        this.type = type;
        this.deleted = deleted;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.locationNodeID = locationNodeID;
    }

    public User(StringProperty userIDfx, StringProperty namefx, StringProperty userNamefx, StringProperty passwordfx, StringProperty typefx, StringProperty phoneNumberfx, StringProperty emailfx, BooleanProperty deletedfx, StringProperty locationNodeIDfx) {
        this.userIDfx = userIDfx;
        this.namefx = namefx;
        this.userNamefx = userNamefx;
        this.passwordfx = passwordfx;
        this.typefx = typefx;
        this.phoneNumberfx = phoneNumberfx;
        this.emailfx = emailfx;
        this.deletedfx = deletedfx;
        this.locationNodeIDfx = locationNodeIDfx;
    }

    public String getUserIDfx() {
        return userIDfx.get();
    }

    public StringProperty userIDfxProperty() {
        return userIDfx;
    }

    public void setUserIDfx(String userIDfx) {
        this.userIDfx.set(userIDfx);
    }

    public String getNamefx() {
        return namefx.get();
    }

    public StringProperty namefxProperty() {
        return namefx;
    }

    public void setNamefx(String namefx) {
        this.namefx.set(namefx);
    }

    public String getUserNamefx() {
        return userNamefx.get();
    }

    public StringProperty userNamefxProperty() {
        return userNamefx;
    }

    public void setUserNamefx(String userNamefx) {
        this.userNamefx.set(userNamefx);
    }

    public String getPasswordfx() {
        return passwordfx.get();
    }

    public StringProperty passwordfxProperty() {
        return passwordfx;
    }

    public void setPasswordfx(String passwordfx) {
        this.passwordfx.set(passwordfx);
    }

    public String getTypefx() {
        return typefx.get();
    }

    public StringProperty typefxProperty() {
        return typefx;
    }

    public void setTypefx(String typefx) {
        this.typefx.set(typefx);
    }

    public String getPhoneNumberfx() {
        return phoneNumberfx.get();
    }

    public StringProperty phoneNumberfxProperty() {
        return phoneNumberfx;
    }

    public void setPhoneNumberfx(String phoneNumberfx) {
        this.phoneNumberfx.set(phoneNumberfx);
    }

    public String getEmailfx() {
        return emailfx.get();
    }

    public StringProperty emailfxProperty() {
        return emailfx;
    }

    public void setEmailfx(String emailfx) {
        this.emailfx.set(emailfx);
    }

    public boolean isDeletedfx() {
        return deletedfx.get();
    }

    public BooleanProperty deletedfxProperty() {
        return deletedfx;
    }

    public void setDeletedfx(boolean deletedfx) {
        this.deletedfx.set(deletedfx);
    }

    public String getLocationNodeIDfx() {
        return locationNodeIDfx.get();
    }

    public StringProperty locationNodeIDfxProperty() {
        return locationNodeIDfx;
    }

    public void setLocationNodeIDfx(String locationNodeIDfx) {
        this.locationNodeIDfx.set(locationNodeIDfx);
    }

    /**
     * This function will be called by UserService to update the ArrayList of Users / the active user
     * @param name the name
     * @param userName the username
     * @param password the password
     * @param type the role type
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

    public String getLocationNodeID() {
        return locationNodeID;
    }

    public void setLocationNodeID(String locationNodeID) {
        this.locationNodeID = locationNodeID;
    }

    public String getUserID() {
        return userID;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
