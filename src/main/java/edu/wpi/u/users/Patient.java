package edu.wpi.u.users;

import java.util.Date;
import java.util.LinkedList;

public class Patient extends User{
    protected LinkedList<Appointment> appointments = new LinkedList<Appointment>();
    protected String providerName;
    protected String recommendedSelfParkLocation;
    protected String parkingSpotNumber;
    //TODO: provider name, appointment date/time, recommended self park location, way to save where they park their vehicle
    //TODO: Link to radiology and blood
    public Patient() {
    }

    public Patient(String userID, String name, String accountName, String password, String email, StaffType type, String phoneNumber, boolean deleted, LinkedList<Appointment> appointments, String providerName, String recommendedSelfParkLocation, String parkingSpotNumber) {
        super(userID, name, accountName, password, email, type, phoneNumber, deleted);
        this.appointments = appointments;
        this.providerName = providerName;
        this.recommendedSelfParkLocation = recommendedSelfParkLocation;
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public LinkedList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(LinkedList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getRecommendedSelfParkLocation() {
        return recommendedSelfParkLocation;
    }

    public void setRecommendedSelfParkLocation(String recommendedSelfParkLocation) {
        this.recommendedSelfParkLocation = recommendedSelfParkLocation;
    }

    public String getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(String parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }
}
