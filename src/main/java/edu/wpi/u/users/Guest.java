package edu.wpi.u.users;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

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

    public StringProperty guestIDfxProperty() {
        return guestIDfx;
    }

    @Override
    public StringProperty namefxProperty() {
        return namefx;
    }

    @Override
    public String getTypefx() {
        return typefx.get();
    }

    @Override
    public StringProperty typefxProperty() {
        return typefx;
    }

    public LongProperty visitDatefxProperty() {
        return visitDatefx;
    }

    public StringProperty visitReasonfxProperty() {
        return visitReasonfx;
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

    public String getVisitReason() {
        return visitReason;
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
