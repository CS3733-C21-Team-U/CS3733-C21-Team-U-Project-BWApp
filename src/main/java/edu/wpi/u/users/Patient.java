package edu.wpi.u.users;

import java.util.ArrayList;
import java.util.LinkedList;

public class Patient extends User{
    protected ArrayList<Appointment> appointments = new ArrayList<>();
    protected String locationNodeID;
    protected String providerName;
    protected String parkingLocation;
    protected String recommendedParkingLocation;
    /*TODO: provider name, appointment date/time, recommended self park location, way to save where they park their vehicle
    //TODO: Link to radiology and blood
    //TODO: Override editUser()

    // Patient table -> Appointment Table <- Employee table
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

    public Patient(String userID, String name, String accountName, String password, String email, Role type, String phoneNumber, String locationNodeID, boolean deleted, ArrayList<Appointment> appointments, String providerName, String parkingLocation, String recommendedParkingLocation) {
        super(userID, name, accountName, password, email, type, phoneNumber, locationNodeID, deleted);
        this.appointments = appointments;
        this.providerName = providerName;
        this.parkingLocation = parkingLocation;
        this.recommendedParkingLocation = recommendedParkingLocation;
    }

    @Override
    public void editUser(String name, String userName, String password, String email, Role type, String phoneNumber, boolean deleted) {
        super.editUser(name, userName, password, email, type, phoneNumber, deleted);
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
