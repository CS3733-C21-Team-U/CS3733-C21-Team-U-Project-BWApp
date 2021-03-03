package edu.wpi.u.requests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;

public class Request {
    private String requestID;
    private Date dateCreated;

    public Date getDateNeeded() {
        return dateNeeded;
    }
    public void setDateNeeded(Date d) {
        this.dateNeeded = d;
    }

    private Date dateNeeded;
    private Date dateCompleted;
    private String description;
    private String title;
    private LinkedList<String> location;
    private String type;
    private LinkedList<String> assignee;
    private String creator;
    private LinkedList<String> comments;

    public Request(String requestID,LinkedList<String> assignee, Date dateCreated, Date dateNeeded, String description, String title, LinkedList<String> location, String type, String creator) {
        this.requestID = requestID;
        this.assignee = assignee;
        this.dateCreated = dateCreated;
        this.dateNeeded = dateNeeded;
        this.description = description;
        this.title = title;
        this.location = location;
        this.type = type;
        this.creator = creator;
    }
    public void resolveRequest() {} //TODO: Belongs in request?
    public void editRequest(Date endDate, String description, String title, LinkedList<String> location, String type, LinkedList<String> assignee, String creator) {
        this.dateCompleted = endDate;
        this.description = description;
        this.title = title;
        this.location = location;
        this.type = type;
        this.assignee = assignee;
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
    public LinkedList<String> getLocation() {
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
    public void setLocation(LinkedList<String> location) {
        this.location = location;
    }
    public void setType(String type) {
        this.type = type;
    }
    public LinkedList<String> getAssignee() {return assignee;}
    public void setAssignee(LinkedList<String> assignee) {
        this.assignee = assignee;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String displayAssignees() {
        String aAssignee = this.assignee.getFirst();
        int numAssigned = this.assignee.size();
        String out = "Assigned: " + aAssignee + " + " + numAssigned + " others";
        return out;
    }

    public String displayLocation() {
        String aLocation = this.location.getFirst();
        int numAssigned = this.location.size();
        String out = "Assigned: " + aLocation + " + " + numAssigned + " others";
        return out;
    }

    public void addComment(String c) { this.comments.add(c); }
}
