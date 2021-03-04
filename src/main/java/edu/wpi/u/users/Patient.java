package edu.wpi.u.users;

import java.util.LinkedList;

public class Patient extends User{
    protected LinkedList<Appointment> appointments = new LinkedList<>();
    /*
    // TODO : Add table
    // Patient table -> Appointment Table <- Employee table
    Make requests,
    Path find,

     */
    protected String providerName;
    protected String parkingLocation;
    //TODO: provider name, appointment date/time, recommended self park location, way to save where they park their vehicle
    //TODO: Link to radiology and blood
    //TODO: Override editUser()
    public Patient() {
    }

    public Patient(String userID, String name, String accountName, String password, String email, Role type, String phoneNumber, boolean deleted, LinkedList<Appointment> appointments, String providerName, String recommendedSelfParkLocation, String parkingLocation) {
        //super(userID, name, accountName, password, email, type, phoneNumber, deleted);
        this.appointments = appointments;
        this.providerName = providerName;
        this.parkingLocation = parkingLocation;
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

    public String getParkingLocation() {
        return parkingLocation;
    }

    public void setParkingLocation(String parkingLocation) {
        this.parkingLocation = parkingLocation;
    }
}
