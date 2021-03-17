package edu.wpi.u.users;

import java.sql.Timestamp;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String employeeID;
    private Timestamp appointmentDate;
    private String appointmentType;

    public Appointment(String appointmentID, String patientID, String employeeID, Timestamp appointmentDate, String appointmentType) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.employeeID = employeeID;
        this.appointmentDate = appointmentDate;
        this.appointmentType = appointmentType;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public Timestamp getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentType() {
        return appointmentType;
    }
}
