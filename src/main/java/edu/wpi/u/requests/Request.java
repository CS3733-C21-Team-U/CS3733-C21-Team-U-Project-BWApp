package edu.wpi.u.requests;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;

public class Request {
    private String requestID;
    private Timestamp dateNeeded;
    private boolean isResolved = false;
    private LinkedList<String> location;
    private LinkedList<String> assignee;
    private ArrayList<Comment> comments;

    public Request(String requestID, Timestamp dateNeeded, boolean isResolved, LinkedList<String> location, LinkedList<String> assignee, Comment comment) {
        this.requestID = requestID;
        this.dateNeeded = dateNeeded;
        this.isResolved = isResolved;
        this.location = location;
        this.assignee = assignee;
        this.comments.add(comment);
    }


    public void editRequest(Timestamp needDate, String description, String title, LinkedList<String> location, String type, LinkedList<String> assignee, String creator) {
        this.dateNeeded = needDate;
        primaryComment().description = description;
        primaryComment().title = title;
        this.location = location;
        this.assignee = assignee;
        primaryComment().author = creator;
    }
    public String getRequestID() {
        return requestID;
    }
    public Timestamp getDateCreated() {
        return primaryComment().timestamp;
    }
    public Timestamp getDateNeeded() {
        return dateNeeded;
    }
    public void setDateNeeded(Timestamp d) {
        this.dateNeeded = d;
    }
    public Timestamp getDateCompleted() {
        if(isResolved){
            return comments.get(comments.size()-1).timestamp;
        }
        else{
            return null;
        }
    }
    public String getDescription() {
        return primaryComment().description;
    }
    public String getTitle() {
        return primaryComment().title;
    }
    public LinkedList<String> getLocation() {
        return location;
    }
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
    public void setDateCreated(Timestamp dateCreated) {
        primaryComment().timestamp = dateCreated;
    }
    public void setDescription(String description) {
        primaryComment().description = description;
    }
    public void setTitle(String title) {
        primaryComment().title = title;
    }
    public void setLocation(LinkedList<String> location) {
        this.location = location;
    }
    public LinkedList<String> getAssignee() {return assignee;}
    public void setAssignee(LinkedList<String> assignee) {
        this.assignee = assignee;
    }

    public void resolveRequest(Comment c) {
        isResolved = true;
        addComment(c);
    }

    public void addComment(Comment c) { this.comments.add(c); }

    public Comment primaryComment(){
        return this.comments.get(0);
    }
}
