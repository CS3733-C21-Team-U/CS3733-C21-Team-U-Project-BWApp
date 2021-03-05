package edu.wpi.u.users;

import java.time.LocalDate;

public class Appointment {

    private String patientID;
    private String employeeID;
    private LocalDate appointmentDate;
    private String appointmentType; // TODO : Possibly make this an enum ie : Radiology, blood work, etc

    public Appointment(String patientID, String employeeID, LocalDate appointmentDate, String appointmentType) {
        this.patientID = patientID;
        this.employeeID = employeeID;
        this.appointmentDate = appointmentDate;
        this.appointmentType = appointmentType;
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

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
}
