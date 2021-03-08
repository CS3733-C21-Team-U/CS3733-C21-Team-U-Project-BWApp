package edu.wpi.u.users;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String employeeID;
    private Timestamp appointmentDate;
    private String appointmentType; // TODO : Possibly make this an enum ie : Radiology, blood work, etc

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

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public Timestamp getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Timestamp appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
}
