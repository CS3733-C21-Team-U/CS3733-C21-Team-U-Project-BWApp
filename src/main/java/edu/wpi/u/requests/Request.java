package edu.wpi.u.requests;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Request {
    private String requestID;
    private Timestamp dateNeeded;
    private ArrayList<String> locations;
    private ArrayList<String> assignees;
    private ArrayList<Comment> comments = new ArrayList<>();

    public Request(String requestID, Timestamp dateNeeded, ArrayList<String> locations, ArrayList<String> assignees, Comment comment) {
        this.requestID = requestID;
        this.dateNeeded = dateNeeded;
        this.locations = locations;
        this.assignees = assignees;
        this.comments.add(comment);
    }

    public Request(String requestID, Timestamp dateNeeded, ArrayList<String> locations, ArrayList<String> assignees, ArrayList<Comment> comments) {
        this.requestID = requestID;
        this.dateNeeded = dateNeeded;
        this.locations = locations;
        this.assignees = assignees;
        this.comments = comments;
    }


    public void editRequest(Timestamp needDate, String description, String title, ArrayList<String> location, ArrayList<String> assignee, String creator) {
        this.dateNeeded = needDate;
        getPrimaryComment().description = description;
        getPrimaryComment().title = title;
        this.locations = location;
        this.assignees = assignee;
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
    public ArrayList<String> getLocations() {
        return locations;
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
    public void setLocations(ArrayList<String> locations) {
        this.locations = locations;
    }
    public ArrayList<String> getAssignees() {return assignees;}
    public void setAssignees(ArrayList<String> assignees) {
        this.assignees = assignees;
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

    public ArrayList<Comment> getComments() { return this.comments; }

    public void setComments(ArrayList<Comment> comments) { this.comments = comments; }

    public Comment getPrimaryComment(){
        return this.comments.get(0);
    }


    /*
    * Comment:
    * Title
    *
    * Description
    *
    * By who, date
    *
    * */


    //UPDATE: title, description
    //title chnaged from "booty" to "Charlie"
    //description chnaged from "booty" to "Charlie"
    private String updateRecord(String fieldName, String oldVal, String newVal){
        String str;
        str = fieldName + " changed from " + oldVal + " to " + newVal;
        return str;
    }
}
