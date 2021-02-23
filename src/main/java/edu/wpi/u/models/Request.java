package edu.wpi.u.models;

import java.util.ArrayList;
import java.util.Date;

public class  Request {
    String title;
    String description;
    Date dateCreated;
    Date dateCompleted;

    public Request(String title, String description, Date dateCreated, Date dateCompleted) {
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateCompleted = dateCompleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public ArrayList<String> getStaff() {
        return staff;
    }

    public void setStaff(ArrayList<String> staff) {
        this.staff = staff;
    }
}
