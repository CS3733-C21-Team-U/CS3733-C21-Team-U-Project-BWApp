package edu.wpi.u.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

//TODO: Private or protected fields?
public abstract class Request {
    protected String requestID;
    protected Date dateCreated, dateCompleted;
    protected String description;
    protected String title;
    protected ArrayList<Staff> assignees;
    protected String location;
    protected String type;

    protected void resolveRequest() { //TODO: Belongs in request?

    }

    protected void editRequest(Date startDate, Date endDate, String description, String title, ArrayList<Staff> assignees, String location) {
        this.dateCreated = startDate;
        this.dateCompleted = endDate;
        this.description = description;
        this.title = title;
        this.assignees = assignees;
        this.location = location;
    }

    protected boolean getStatus() {
        return true;
    }

    public String getRequestID() {
        return requestID;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Staff> getAssignees() {
        return assignees;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAssignees(ArrayList<Staff> assignees) {
        this.assignees = assignees;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }
}
