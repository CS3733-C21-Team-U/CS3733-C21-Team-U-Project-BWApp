package edu.wpi.u.users;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Guest extends User{
    private String guestID;
    private String name;
    private Timestamp visitDate;
    private String visitReason;
    private boolean deleted;
    //TODO : Maybe add a visitTime field for guest coming to hospital?
    //TODO : Maybe make Guest only have a phone number for account and use 2fa // maybe not need to store in db
    public Guest() {}

    public Guest(String guestID, String name, Timestamp visitDate, String visitReason, boolean deleted) {
        this.guestID = guestID;
        this.name = name;
        this.visitDate = visitDate;
        this.visitReason = visitReason;
        this.deleted = deleted;
    }

    public void editGuest(String name, Timestamp visitDate, String visitReason, boolean deleted) {
        this.name = name;
        this.visitDate = visitDate;
        this.visitReason = visitReason;
        this.deleted = deleted;
    }

    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Timestamp visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
