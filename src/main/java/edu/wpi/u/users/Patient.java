package edu.wpi.u.users;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.LinkedList;

public class Patient extends User{
    protected ArrayList<Appointment> appointments = new ArrayList<>();
    //protected String locationNodeID;
    protected String providerName;
    protected String parkingLocation;
    protected String recommendedParkingLocation;
    /*
    Patient table -> Appointment Table <- Employee table
     */
    public Patient() {
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
    boolean deleted,
    ArrayList<Appointment> appointments,
    String providerName,
    String parkingLocation,
    String recommendedParkingLocation) {
 */
    public Patient(String userID, String name, String accountName, String password, String email, Role type, String phoneNumber, boolean deleted, ArrayList<Appointment> appointments, String providerName, String parkingLocation, String recommendedParkingLocation) {
        super(userID, name, accountName, password, email, type, phoneNumber, deleted);
        this.appointments = appointments;
        this.providerName = providerName;
        this.parkingLocation = parkingLocation;
        this.recommendedParkingLocation = recommendedParkingLocation;
    }

    public Patient(StringProperty userIDfx, StringProperty namefx, StringProperty userNamefx, StringProperty passwordfx, StringProperty typefx, StringProperty phoneNumberfx, StringProperty emailfx, BooleanProperty deletedfx) {
        super(userIDfx, namefx, userNamefx, passwordfx, typefx, phoneNumberfx, emailfx, deletedfx);
    }

    public void editPatient(String name, String userName, String password, String email, Role type, String phoneNumber) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.type = type;
        this.phoneNumber = phoneNumber;
        //this.locationNodeID = locationNodeID;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getParkingLocation() {
        return parkingLocation;
    }

    public void setParkingLocation(String parkingLocation) {
        this.parkingLocation = parkingLocation;
    }

    public String getRecommendedParkingLocation() {
        return recommendedParkingLocation;
    }

    public void setRecommendedParkingLocation(String recommendedParkingLocation) {
        this.recommendedParkingLocation = recommendedParkingLocation;
    }
}
