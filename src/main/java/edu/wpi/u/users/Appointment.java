package edu.wpi.u.users;

import java.time.LocalDate;

public class Appointment {

    private LocalDate appointmentDate;
    private String appointmentType; // TODO : Possibly make this an enum ie : Radiology, blood work, etc

    public Appointment(LocalDate appointmentDate, String appointmentType) {
        this.appointmentDate = appointmentDate;
        this.appointmentType = appointmentType;
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
