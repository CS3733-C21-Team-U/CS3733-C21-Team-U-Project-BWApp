package edu.wpi.u.database;

import edu.wpi.u.requests.*;
//import edu.wpi.u.requests.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class RequestData extends Data{

    public RequestData(){ // TODO: load csv's for Nodes, Requests, Assignees, and RANJoint
        connect();
    }

    /**
     * Constructor that is being used to connect to test DB
     * @param testURL
     */
    public RequestData(String testURL){
        testConnect(testURL);
    }

    /**
     * Updates a request by using its ID
     * @param specificRequest the new request object
     */
    public void updateRequest(SpecificRequest specificRequest){
        Request request= specificRequest.getGenericRequest();

        String str = "update Requests set dateNeeded=? where requestID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setTimestamp(1, request.getDateNeeded());
            ps.setString(2,request.getRequestID());
            ps.execute();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        updateField("Requests", "requestID", request.getRequestID(), "type", specificRequest.getType());
        updateField("Requests", "requestID", request.getRequestID(), "specificData", specificRequest.specificsStorageString());
        updLocations(request.getRequestID(), request.getLocations());
        updAssignees(request.getRequestID(), request.getAssignees());

        String updComment = "update Comments set title=?, description=?, author=?, created=? where type=? and request=?";
        try{
            PreparedStatement ps = conn.prepareStatement(updComment);
            ps.setString(1, request.getTitle());
            ps.setString(2, request.getDescription());
            ps.setString(3, request.getAuthor());
            ps.setTimestamp(4, request.getDateCreated());
            ps.setString(5, String.valueOf(CommentType.PRIMARY));
            ps.setString(6, request.getRequestID());
            ps.execute();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of comments for a given request
     * @param requestID the request id
     * @return list of comments
     */
    public ArrayList<Comment> getComments(String requestID){
        ArrayList<Comment> result = new ArrayList<>();
        result.add(getPrimaryComment(requestID));
        try {
            String str = "select * from Comments where request=? and type!=? order by created ASC";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID);
            ps.setString(2,"PRIMARY");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                result.add(new Comment(rs.getString("title"), rs.getString("description"), rs.getString("author"), CommentType.valueOf(rs.getString("type")), rs.getTimestamp("created")));
            }
            rs.close();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Gets the primary comment given a requestid
     * @param requestID the request id
     * @return the comment object
     */
    public Comment getPrimaryComment(String requestID){
        String str = "select * from Comments where request=? and type=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID);
            ps.setString(2,"PRIMARY");
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Comment(rs.getString("title"),rs.getString("description"), rs.getString("author"),CommentType.valueOf(rs.getString("type")), rs.getTimestamp("created"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of employeeID's for a given requestID
     * @param requestID the request ID
     * @return list of assignees
     */
    public ArrayList<String> getAssignees(String requestID){
        ArrayList<String> result = new ArrayList<>();
        String str = "select * from Assignments where request=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, requestID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                result.add(rs.getString("employeeID"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns a list of nodeID's for a given requestID
     * @param requestID the request id
     * @return list of locations
     */
    public ArrayList<String> getLocations(String requestID){
        ArrayList<String> result = new ArrayList<>();
        String str = "select * from Locations where request=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, requestID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                result.add(rs.getString("nodeID"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    /**
     * Updates the list of locatioons for a given requestID
     * @param requestID the request id
     * @param locations the list of locations
     */
    public void updLocations(String requestID, ArrayList<String> locations){

        //Take whole list: do new one
        String str = "delete from Locations where request=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, requestID);
            ps.execute();
            for (String node : locations) {
                try{
                    addLocation(node,requestID);
                }
                catch (Exception e){
                    System.out.println("Add Location Failed");
                }

            }
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Updates the list of assignees for a given requestID
     * @param requestID the request id
     * @param assignees the list of assignees for that request
     */
    public void updAssignees(String requestID, ArrayList<String> assignees){
        /*
        Take whole list: do new one
         */
        String str = "delete from Assignments where request=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, requestID);
            ps.execute();
            for (String assignee : assignees) {
                addAssignee(assignee,requestID);
            }
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * The pulling of database information into java class objects
     * @return list of IRequest objects
     */
    public ArrayList<SpecificRequest> loadActiveRequests(){ // TODO: refactor for IRequest
        ArrayList<SpecificRequest> results = new ArrayList<>();
        String requestQuery = "select * from Requests";
        try{
            PreparedStatement ps = conn.prepareStatement(requestQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String id = rs.getString("requestID");
                String type = rs.getString("type");
                Timestamp dateNeeded = rs.getTimestamp("dateNeeded");
                String specificData = rs.getString("specificData");
                ArrayList<String> locations = getLocations(id);
                ArrayList<String> assignees = getAssignees(id);
                ArrayList<Comment> comments = getComments(id);
                Request r = new Request(id, dateNeeded, locations, assignees, comments);
                SpecificRequest result = new RequestFactory().makeRequest(type).setRequest(r).readStorageString(specificData);
                results.add(result);
            }
            rs.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Adds a request to the database
     * @param specificRequest the request to add
     */
    public void addRequest(SpecificRequest specificRequest) { // TODO: Add to interface IRequest instead
        String str = "insert into Requests (requestID, type, dateNeeded, specificData, resolved) values (?,?,?,?,?)";
        try{
            Request req = specificRequest.getGenericRequest();
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,req.getRequestID());
            ps.setString(2, specificRequest.getType());
            ps.setTimestamp(3,req.getDateNeeded());
            ps.setString(4,specificRequest.specificsStorageString());
            ps.setBoolean(5, false);
            ps.execute();
            for(Comment comment : req.getComments()){
                addCommentToRequest(req.getRequestID(), comment);
            }
            for(String locationID : req.getLocations()){
                addLocation(locationID, req.getRequestID());
            }
            for(String assignmentID : req.getAssignees()){
                addAssignee(assignmentID, req.getRequestID());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //printTableItem("Requests", "specificData");
    }

    /**
     * Adds an assignee to a request
     * @param employeeID user id of the assigned person
     * @param requestID request id
     */
    public void addAssignee(String employeeID, String requestID){
        String str = "insert into Assignments(assignmentID, request, employeeID) values (?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID+"_"+employeeID);
            ps.setString(2,requestID);
            ps.setString(3,employeeID);
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a location to a request
     * @param nodeID node id
     * @param requestID the request id
     */
    public void addLocation(String nodeID, String requestID){
        String str = "insert into Locations(locationID, request, nodeID) values (?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID+"_"+nodeID);
            ps.setString(2,requestID);
            ps.setString(3,nodeID);
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a comment to a request
     * @param requestID - the request taken in
     * @param comment - the comment that will be connected with it using Comments table
     */
    public void addCommentToRequest(String requestID, Comment comment){
        String str = "insert into Comments(request, title, description, author, type, created) values (?,?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID);
            ps.setString(2,comment.getTitle());
            ps.setString(3,comment.getDescription());
            ps.setString(4,comment.getAuthor());
            ps.setString(5,comment.getType().toString());
            ps.setTimestamp(6,comment.getTimestamp());
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resolves a request
     * @param requestID the id of the request
     * @param comment the comment resolving the request
     */
    public void resolveRequest(String requestID, Comment comment) { // TODO: Add resolve comment
        addCommentToRequest(requestID,comment);
        String str = "update Requests set resolved=true where requestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID);
            ps.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
