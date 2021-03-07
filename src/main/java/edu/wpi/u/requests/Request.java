package edu.wpi.u.requests;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Request {
    private String requestID;
    private Timestamp dateNeeded;
    private ArrayList<String> location;
    private ArrayList<String> assignee;
    private ArrayList<Comment> comments;

    public Request(String requestID, Timestamp dateNeeded, ArrayList<String> location, ArrayList<String> assignee, Comment comment) {
        this.requestID = requestID;
        this.dateNeeded = dateNeeded;
        this.location = location;
        this.assignee = assignee;
        this.comments.add(comment);
    }


    public void editRequest(Timestamp needDate, String description, String title, ArrayList<String> location, String type, ArrayList<String> assignee, String creator) {
        this.dateNeeded = needDate;
        getPrimaryComment().description = description;
        getPrimaryComment().title = title;
        this.location = location;
        this.assignee = assignee;
        getPrimaryComment().author = creator;
    }
    public String getRequestID() {
        return requestID;
    }
    public Timestamp getDateCreated() {
        return getPrimaryComment().timestamp;
    }
    public Timestamp getDateNeeded() {
        return dateNeeded;
    }
    public void setDateNeeded(Timestamp d) {
        this.dateNeeded = d;
    }
    public Timestamp getDateCompleted() {
        if(isResolved()){
            return comments.get(comments.size()-1).timestamp;
        }
        else{
            return null;
        }
    }
    public String getDescription() {
        return getPrimaryComment().description;
    }
    public String getTitle() {
        return getPrimaryComment().title;
    }
    public ArrayList<String> getLocation() {
        return location;
    }
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
    public void setDateCreated(Timestamp dateCreated) {
        getPrimaryComment().timestamp = dateCreated;
    }
    public void setDescription(String description) {
        getPrimaryComment().description = description;
    }
    public void setTitle(String title) {
        getPrimaryComment().title = title;
    }
    public void setLocation(ArrayList<String> location) {
        this.location = location;
    }
    public ArrayList<String> getAssignee() {return assignee;}
    public void setAssignee(ArrayList<String> assignee) {
        this.assignee = assignee;
    }

    public void resolveRequest(Comment c) {
        addComment(c);
        c.type = CommentType.RESOLVE;
    }

    public boolean isResolved(){
        if(comments.size() == 0){return false;}
        return comments.get(comments.size() - 1).type == CommentType.RESOLVE;
    }

    public void addComment(Comment c) { this.comments.add(c); }

    public Comment getPrimaryComment(){
        return this.comments.get(0);
    }
}
