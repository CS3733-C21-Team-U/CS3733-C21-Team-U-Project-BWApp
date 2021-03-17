package edu.wpi.u.users;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public abstract class User extends RecursiveTreeObject<User> {
    protected String userID;
    protected String name;
    protected String userName;
    protected String password;
    protected Role type;
    protected String phoneNumber;
    protected String email;
    protected boolean deleted;

    protected StringProperty userIDfx;
    protected StringProperty namefx;
    protected StringProperty userNamefx;
    protected StringProperty passwordfx;
    protected StringProperty typefx;
    protected StringProperty phoneNumberfx;
    protected StringProperty emailfx;
    protected BooleanProperty deletedfx;

    private String guestID;
    private Timestamp visitDate;
    private String visitReason;

    protected StringProperty guestIDfx;
    protected LongProperty visitDatefx;
    protected StringProperty visitReasonfx;

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

    public User(StringProperty userIDfx, StringProperty namefx, StringProperty userNamefx, StringProperty passwordfx, StringProperty typefx, StringProperty phoneNumberfx, StringProperty emailfx, BooleanProperty deletedfx) {
        this.userIDfx = userIDfx;
        this.namefx = namefx;
        this.userNamefx = userNamefx;
        this.passwordfx = passwordfx;
        this.typefx = typefx;
        this.phoneNumberfx = phoneNumberfx;
        this.emailfx = emailfx;
        this.deletedfx = deletedfx;
    }

    public User(String guestID, String guestName, Timestamp visitDate, String visitReason) {
        this.guestID = guestID;
        this.visitDate = visitDate;
        this.visitReason = visitReason;
    }

    /**
     * Poor workaround for JFX Tree table guest view
     * todo : change this
     * @param guestIDfx guest id for jfx
     * @param namefx name for jfx
     * @param visitDatefx visit date for jfx
     * @param visitReasonfx visit reason for jfx
     */
    public User (StringProperty guestIDfx, StringProperty namefx, LongProperty visitDatefx, StringProperty visitReasonfx){
        this.guestIDfx = guestIDfx;
        this.namefx = namefx;
        this.visitDatefx = visitDatefx;
        this.visitReasonfx = visitReasonfx;
    }

    public StringProperty guestIDfxProperty() {
        return guestIDfx;
    }

    public LongProperty visitDatefxProperty() {
        return visitDatefx;
    }

    public StringProperty visitReasonfxProperty() {
        return visitReasonfx;
    }

    public String getGuestID() {
        return guestID;
    }

    public Timestamp getVisitDate() {
        return visitDate;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public String getUserIDfx() {
        return userIDfx.get();
    }

    public StringProperty userIDfxProperty() {
        return userIDfx;
    }

    public StringProperty namefxProperty() {
        return namefx;
    }

    public String getUserNamefx() {
        return userNamefx.get();
    }

    public StringProperty userNamefxProperty() {
        return userNamefx;
    }

    public StringProperty passwordfxProperty() {
        return passwordfx;
    }

    public String getTypefx() {
        return typefx.get();
    }

    public StringProperty typefxProperty() {
        return typefx;
    }

    public StringProperty phoneNumberfxProperty() {
        return phoneNumberfx;
    }

    public StringProperty emailfxProperty() {
        return emailfx;
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

    public String getUserID() {
        return userID;
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

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
