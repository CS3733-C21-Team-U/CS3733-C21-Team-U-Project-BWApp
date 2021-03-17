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

    /**
     * returns string used in update comments
     * @param needDate
     * @param description
     * @param title
     * @param location
     * @param assignee
     * @return
     */
    public String editRequest(Timestamp needDate, String description, String title, ArrayList<String> location, ArrayList<String> assignee) {
        String result = "";
        if(!title.equals(getTitle())){
            result = result.concat("Title changed from '"+getTitle()+"' to '"+title+"' \n");
            getPrimaryComment().title = title;
        }
        if(!description.equals(getDescription())){
            result = result.concat("Description changed from '"+getDescription()+"' to '"+description+"' \n");
            getPrimaryComment().description = description;
        }
        if(!needDate.equals(dateNeeded)){
            result = result.concat("Due Date changed from '"+dateNeeded.toString()+"' to '"+needDate.toString()+"' \n");
            this.dateNeeded = needDate;
        }
        if(!location.equals(this.locations)){
            result = result.concat("Locations were updated \n");
            this.locations = location;
        }
        if(!assignee.equals(this.assignees)){
            result = result.concat("Assignees were updated \n");
            this.assignees = assignee;
        }
        return result;
    }

    public String getRequestID() {
        return requestID;
    }
    
    public Timestamp getDateCreated() {
        return getPrimaryComment().timestamp;
    }

    public String getCreator() {
        return getPrimaryComment().author;
    }

    public Timestamp getDateNeeded() {
        return dateNeeded;
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

    public void resolveRequest(Comment c) {
        addComment(c);
        c.type = CommentType.RESOLVE;
    }

    public boolean isResolved(){
        if(comments.size() == 0){return false;}
        System.out.println(getTitle() +":"+comments.get(comments.size() - 1).type);
        for(Comment s: comments){
            if(s.type == CommentType.RESOLVE){return true;}
        }
        return false;
        //return comments.get(comments.size() - 1).type == CommentType.RESOLVE;
    }

    public void addComment(Comment c) { this.comments.add(c); }

    public ArrayList<Comment> getComments() { return this.comments; }

    public void setComments(ArrayList<Comment> comments) { this.comments = comments; }

    public Comment getPrimaryComment() {
        if(this.comments != null && this.comments.size() != 0) {
            return this.comments.get(0);
        }else{
            System.out.println("Request is missing primary comment!");
            return null;
        }
    }

    public String getAuthor(){
        return getPrimaryComment().author;
    }


}
