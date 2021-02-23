package edu.wpi.u.requests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;

public class Request {
    protected String requestID;
    protected Date dateCreated, dateCompleted;
    protected String description;
    protected String title;
    protected LinkedList<String> location;
    protected String type;
    protected LinkedList<String> assignee;
    protected String creator;

    public Request(String requestID,LinkedList<String> assignee, Date dateCreated, Date dateCompleted, String description, String title, LinkedList<String> location, String type, String creator) {
        this.requestID = requestID;
        this.assignee = assignee;
        this.dateCreated = dateCreated;
        this.dateCompleted = dateCompleted;
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
}
