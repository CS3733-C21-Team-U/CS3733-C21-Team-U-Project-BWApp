package edu.wpi.u.users;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class Patient extends User{
    protected ArrayList<Appointment> appointments = new ArrayList<>(); // todo : remove
    protected String providerName;
    protected String parkingLocation;
    protected String recommendedParkingLocation;

    public Patient() {
    }

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
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getParkingLocation() {
        return parkingLocation;
    }

    public String getRecommendedParkingLocation() {
        return recommendedParkingLocation;
    }
}
