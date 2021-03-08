package edu.wpi.u.users;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Guest extends User{
    private String guestID;
    private String name;
    private Role type;
    private Timestamp visitDate;
    private String visitReason;
    private boolean deleted;

    private StringProperty guestIDfx;
    private StringProperty namefx;
    private StringProperty typefx;
    private LongProperty visitDatefx;
    private StringProperty visitReasonfx;

    //TODO : Maybe add a visitTime field for guest coming to hospital?
    //TODO : Maybe make Guest only have a phone number for account and use 2fa // maybe not need to store in db
    public Guest() {}

    public Guest(String guestID, String name, Role type, Timestamp visitDate, String visitReason, boolean deleted) {
        this.guestID = guestID;
        this.name = name;
        this.type = type;
        this.visitDate = visitDate;
        this.visitReason = visitReason;
        this.deleted = deleted;
    }

    public Guest (StringProperty guestIDfx, StringProperty namefx, StringProperty typefx, LongProperty visitDatefx, StringProperty visitReasonfx){
        this.guestIDfx = guestIDfx;
        this.namefx = namefx;
        this.typefx = typefx;
        this.visitDatefx = visitDatefx;
        this.visitReasonfx = visitReasonfx;
    }

    @Override
    public Role getType() {
        return type;
    }

    @Override
    public void setType(Role type) {
        this.type = type;
    }

    public String getGuestIDfx() {
        return guestIDfx.get();
    }

    public StringProperty guestIDfxProperty() {
        return guestIDfx;
    }

    public void setGuestIDfx(String guestIDfx) {
        this.guestIDfx.set(guestIDfx);
    }

    @Override
    public String getNamefx() {
        return namefx.get();
    }

    @Override
    public StringProperty namefxProperty() {
        return namefx;
    }

    public void setNamefx(String namefx) {
        this.namefx.set(namefx);
    }

    @Override
    public String getTypefx() {
        return typefx.get();
    }

    @Override
    public StringProperty typefxProperty() {
        return typefx;
    }

    public void setTypefx(String typefx) {
        this.typefx.set(typefx);
    }

    public long getVisitDatefx() {
        return visitDatefx.get();
    }

    public LongProperty visitDatefxProperty() {
        return visitDatefx;
    }

    public void setVisitDatefx(long visitDatefx) {
        this.visitDatefx.set(visitDatefx);
    }

    public String getVisitReasonfx() {
        return visitReasonfx.get();
    }

    public StringProperty visitReasonfxProperty() {
        return visitReasonfx;
    }

    public void setVisitReasonfx(String visitReasonfx) {
        this.visitReasonfx.set(visitReasonfx);
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
