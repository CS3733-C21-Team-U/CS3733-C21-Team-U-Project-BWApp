package edu.wpi.u.requests;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Date;

public class Request {
    private String requestID;
    private Timestamp dateCreated;

    public Timestamp getDateNeeded() {
        return dateNeeded;
    }
    public void setDateNeeded(Timestamp d) {
        this.dateNeeded = d;
    }

    private Timestamp dateNeeded;
    private Timestamp dateCompleted;
    private String description;
    private String title;
    private LinkedList<String> locations;
    private LinkedList<String> assignees;
    private String creator;
    private LinkedList<String> comments;

    public Request(String requestID, Timestamp dateCreated, Timestamp dateNeeded, Timestamp dateCompleted, String description, String title, LinkedList<String> locations, LinkedList<String> assignees, String creator) {
        this.requestID = requestID;
        this.dateCreated = dateCreated;
        this.dateNeeded = dateNeeded;
        this.dateCompleted = dateCompleted;
        this.description = description;
        this.title = title;
        this.locations = locations;
        this.assignees = assignees;
        this.creator = creator;
        //this.comments = comments;
    }

    public void resolveRequest() {} //TODO: Belongs in request?
    public void editRequest(Timestamp endDate, String description, String title, LinkedList<String> location, String type, LinkedList<String> assignee, String creator) {
        this.dateCompleted = endDate;
        this.description = description;
        this.title = title;
        this.locations = location;
        this.assignees = assignee;
        this.creator = creator;
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
    public LinkedList<String> getLocations() {
        return locations;
    }
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }
    public void setDateCompleted(Timestamp dateCompleted) {
        this.dateCompleted = dateCompleted;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setLocations(LinkedList<String> locations) {
        this.locations = locations;
    }
    public LinkedList<String> getAssignees() {return assignees;}
    public void setAssignees(LinkedList<String> assignees) {
        this.assignees = assignees;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String displayAssignees() {
        String aAssignee = this.assignees.getFirst();
        int numAssigned = this.assignees.size();
        String out = "Assigned: " + aAssignee + " + " + numAssigned + " others";
        return out;
    }

    public String displayLocation() {
        String aLocation = this.locations.getFirst();
        int numAssigned = this.locations.size();
        String out = "Assigned: " + aLocation + " + " + numAssigned + " others";
        return out;
    }

    public void addComment(String c) { this.comments.add(c); }
}
