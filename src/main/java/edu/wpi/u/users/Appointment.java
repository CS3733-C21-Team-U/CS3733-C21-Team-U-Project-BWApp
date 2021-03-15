package edu.wpi.u.users;

import java.sql.Timestamp;
import java.time.LocalDate;


// todo : Remove this class from Patients // not used
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
