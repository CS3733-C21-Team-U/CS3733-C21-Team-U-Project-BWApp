package edu.wpi.u.database;

import edu.wpi.u.requests.*;
//import edu.wpi.u.requests.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class RequestData extends Data{

    public RequestData(){ // TODO: load csv's for Nodes, Requests, Assignees, and RANJoint
        connect();
        printTableItem("Requests", "title");
        LinkedList<String> l1 = new LinkedList<String>();
        l1.add("UPARK00101");
        LinkedList<String> s1 = new LinkedList<String>();
        s1.add("Mary");
        Date d = new Date(900);
        //addRequest(new Request("Newest req", s1, d,null, "descript","title", l1, "type", "creator"));
      //  addRequest(new Request("Maintenance456", s1, d,null, "It seems that the shower head on A4 is leaky","Leaky Shower", l1, "Maintenance", "Kaamil"));
    }


    public void updateRequest(SpecificRequest obj){//TODO: UPDATE PRIMARY COMMENT INSTEAD OF REQUEST FIELDS
        Request request= obj.getGenericRequest();

        String str = "update Requests set dateNeeded=? where requestID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setTimestamp(1, request.getDateNeeded());
            ps.setString(1,request.getRequestID());
            ps.execute();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        updateField("Requests", "requestID", request.getRequestID(), "type", obj.getType());
        updateField("Requests", "requestID", request.getRequestID(), "specificData", obj.specificsStorageString());

        Comment c = new Comment("Update", "Update Description goes here", "Kaamil", CommentType.UPDATE, new Timestamp(System.currentTimeMillis()));
        request.addComment(c);//TODO: Find a place to make and compile Update comment in order to make detailed comments
        addCommentToRequest(request.getRequestID(), c);
    }

   /* public void updateRequest(Request request){ //
        requestID, dateCreated, dateCompleted, description, title, type
         *//*
        System.out.println("Can anyone even hear me??????????????????????????????????");
        if(request.getDateCompleted() != null) this.resolveRequest(request);
        this.updRequestDescription(request.getRequestID(), request.getDescription());
        this.updRequestTitle(request.getRequestID(), request.getTitle());
        this.updRequestType(request.getRequestID(), request.getType());
        this.updLocations(request.getRequestID(), request.getLocation());
        this.updAssignees(request.getRequestID(), request.getAssignee());
    }*/

    /**
     * Returns whether or not a request is active
     * @param requestID the request id
     * @return true if the request is active, false if it is complete
     */
    public boolean getActiveStatus(String requestID){
        String str = "select dateCompleted from Requests where requestID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, requestID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                rs.close();
                ps.close();
                return false;
            }
            rs.close();
            ps.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Returns a list of resolved requests
     * @return list of resolved requests
     */
    public ArrayList<Request> getResolvedRequests(){
        ArrayList<Request> result = new ArrayList<>();
        String str = "select * from Requests where dateCompleted is not null";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
//                result.add(new Request(rs.getString("requestId"), todo : fix
//                        rs.getTimestamp("dateCreated"),
//                        rs.getTimestamp("dateNeeded"),
//                        rs.getTimestamp("dateCompleted"),
//                        rs.getString("description"),
//                        rs.getString("title"),
//                        getAssignees(rs.getString("requestId")),
//                        getLocations(rs.getString("requestId")),
//                        rs.getString("creator")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns a list of comments for a given request
     * @param requestID the request id
     * @return list of comments
     */
    public ArrayList<Comment> getComments(String requestID){
        ArrayList<Comment> result = new ArrayList<>();
        try {
            String str = "select * from Comments where requestID=? order by created DESC";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID);
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
     * Returns a list of employeeID's for a given requestID
     * @param requestID the request ID
     * @return list of assignees
     */
    public ArrayList<String> getAssignees(String requestID){
        ArrayList<String> result = new ArrayList<>();
        String str = "select * from Assignments where requestID=?";
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
        String str = "select * from Locations where requestID=?";
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
        String str = "delete from Locations where requestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, requestID);
            ps.execute();
            for (String node : locations) {
                addLocation(node,requestID);
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
        String str = "delete from Assignments where requestID=?";
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
        //go through requests, make a request object
        //go to subtable based on type(stored in field of request db)
        //set unique fields into correct object
        //If we do factory, this is where we do it
        ArrayList<SpecificRequest> results = new ArrayList<>();
        String requestQuery = "select * from Requests where resolved=false";
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
                SpecificRequest result = new RequestFactory().makeRequest(type);
                result.setRequest(r);
                result.readStorageString(specificData);
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
        String str = "insert into Requests (requestID, type, dateNeeded, specificData) values (?,?,?,?,?,?,?,?)";
        try{
            Request req = specificRequest.getGenericRequest();
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,req.getRequestID());
            ps.setString(2, specificRequest.getType());
            ps.setTimestamp(3,req.getDateNeeded());
            ps.setString(4,specificRequest.specificsStorageString());
            ps.execute();
            for(Comment comment : req.getComments()){
                addCommentToRequest(req.getRequestID(), comment);
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
        String str = "insert into Assignments(assignmentID, requestID, userID) values (?,?,?)";
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
        String str = "insert into Locations(locationID, requestID, nodeID) values (?,?,?)";
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
     *
     * @param requestID - the request taken in
     * @param c - the comment that will be connected with it using Comments table
     */

    public void addCommentToRequest(String requestID, Comment c){
        String str = "insert into Comments(requestID, title, description, author, type, created) values (?,?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID);
            ps.setString(2,c.getTitle());
            ps.setString(3,c.getDescription());
            ps.setString(4,c.getDescription());
            ps.setString(5,c.getType().toString());
            ps.setTimestamp(6,c.getTimestamp());
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @param requestID
     * @param time
     */
    public void resolveRequest(String requestID, long time) { // TODO: Add resolve comment
        String str = "update Requests set dateCompleted=? where requestID=?";
        try {
            Timestamp t = new Timestamp(time);
            PreparedStatement ps = conn.prepareStatement(str);
//            java.sql.Date d = new java.sql.Date(time);
            ps.setTimestamp(1, t);
            ps.setString(2,requestID);
            ps.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Deletes a location assignment for a request
     * @param requestID the request id
     * @param nodeID the node id
     */
    public void deleteLocation(String requestID, String nodeID){
        String str = "delete * from Locations where requestID=? and nodeID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID);
            ps.setString(2,nodeID);
            ps.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Deletes an assignment for a request
     * @param requestID the request id
     * @param employeeID the user id
     */
    public void deleteAssignment(String requestID, String employeeID){
        String str = "delete * from Assignments where requestID=? and userID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID);
            ps.setString(2,employeeID);
            ps.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Updates the type of request of a given requestID
     * @param requestID the requestId
     * @param type the new type of request
     */
    public void updRequestType(String requestID, String type) {
        String str = "update Requests set type=? where requestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,type);
            ps.setString(2,requestID);
            ps.execute();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * For debugging
     * @param tableName
     * @return number of entries in table
     */
    public int getNumTableItems(String tableName) {

        try {
            String countItems = "select count(*) from " + tableName;
            PreparedStatement c = conn.prepareStatement(countItems);
            ResultSet res = c.executeQuery();
            if(res.next())
                return res.getInt(1);
        } catch (Exception e){
            e.printStackTrace();

        }
        return -999;
    }
//    public void getRequests() {}
//    public void getRequestByID(String id) {
//    }
}
