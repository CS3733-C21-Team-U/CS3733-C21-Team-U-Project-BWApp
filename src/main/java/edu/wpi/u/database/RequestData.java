package edu.wpi.u.database;

import edu.wpi.u.requests.*;
//import edu.wpi.u.requests.Request;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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


    public void updateRequest(SpecificRequest obj){
        Request request= obj.getGenericRequest();
        updateField("Requests", "requestID", request.getRequestID(), "description", request.getDescription());
        updateField("Requests", "requestID", request.getRequestID(), "title", request.getTitle());
        updateField("Requests", "requestID", request.getRequestID(), "type", obj.getType());
        updateField("Requests", "requestID", request.getRequestID(), "specificData", obj.specificsStorageString());
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

    public ArrayList<Request> getResolvedRequests(){
        ArrayList<Request> result = new ArrayList<>();
        String str = "select * from Requests where dateCompleted is not null";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                /*
                 String requestID,
                 Timestamp dateCreated,
                 Timestamp dateNeeded,
                 Timestamp dateCompleted,
                 String description,
                 String title,
                 LinkedList<String> locations,
                 LinkedList<String> assignees,
                 String creator) {

                 */
                result.add(new Request(rs.getString("requestId"),
                        rs.getTimestamp("dateCreated"),
                        rs.getTimestamp("dateNeeded"),
                        rs.getTimestamp("dateCompleted"),
                        rs.getString("description"),
                        rs.getString("title"),
                        getAssignees(rs.getString("requestId")),
                        getLocations(rs.getString("requestId")),
                        rs.getString("creator")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns a list of employeeID's for a given requestID
     * @param requestID the request ID
     * @return list of assignees
     */
    public Timestamp getAssignees(String requestID){
        Timestamp result = new ArrayList<>();
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
     * Updates the list of locatioons for a given requestID
     * @param requestID the request id
     * @param locations the list of locations
     */
    public void updLocations(String requestID, LinkedList<String> locations){

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
    public void updAssignees(String requestID, LinkedList<String> assignees){
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
        String requestQuery = "select * from Requests where dateCompleted is null";
        try{
            PreparedStatement ps = conn.prepareStatement(requestQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String id = rs.getString("requestID");
                Date created = rs.getDate("dateCreated");
                Timestamp needed = rs.getDate("dateNeeded");
                String desc = rs.getString("description");
                String title = rs.getString("title");
                String type = rs.getString("type");
                String specificData = rs.getString("specificData");
                LinkedList<String> locations = new LinkedList<String>();
//                try { // TODO : UNCOMMENT AND FIX
//                    String str2 = "select * from Locations where requestID=?";
//                    PreparedStatement ps2= conn.prepareStatement(str2);
//                    ps2.setString(1,id);
//                    ResultSet rs2 = ps2.executeQuery();
//                    while (rs2.next()){
//                        locations.add(rs2.getString("nodeID"));
//                    }
//                    rs2.close();
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }
                Timestamp assignees = new LinkedList<String>();
//                try {
//                    String str3 = "select * from Assignments where requestID=?";
//                    PreparedStatement ps3 = conn.prepareStatement(str3);
//                    ps3.setString(1,id);
//                    ResultSet rs3 = ps3.executeQuery();
//                    while (rs3.next()){
//                        assignees.add(rs3.getString("userID"));
//                    }
//                    rs3.close();
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }

                RequestFactory rf = new RequestFactory();
                SpecificRequest result = rf.makeRequest(type);
                Request r = new Request(id,assignees, created,needed,desc,title,locations,type, "Test creator");
                result.readStorageString(specificData);
                result.setRequest(r);
                results.add(result);
            }
            rs.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    public void addRequest(SpecificRequest obj) { // TODO: Add to interface IRequest instead
       //
        //requestID varchar(50) not null , dateCreated date, dateCompleted date,description varchar(200),title varchar(50),type varchar(50),  primary key(requestID))";
        String str = "insert into Requests (requestID, dateCreated, dateCompleted, description, title, type, dateNeeded, specificData) values (?,?,?,?,?,?,?,?)";
        try{
            Request req = obj.getGenericRequest();
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,req.getRequestID());
            java.util.Date d = req.getDateCreated();
            java.sql.Date sqld = new java.sql.Date(d.getTime());
            ps.setDate(2, sqld);
            ps.setDate(3,null);
            ps.setString(4,req.getDescription());
            ps.setString(5,req.getTitle());
            ps.setString(6,obj.getType());
            java.util.Date d2 = req.getDateNeeded();
            java.sql.Date sqld2 = new java.sql.Date(d2.getTime());
            ps.setDate(7,sqld2);
            ps.setString(8,obj.specificsStorageString());
            ps.execute();
            // Adding data into joint tables
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
        printTableItem("Requests", "specificData");
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
     * Resolves a request
     * @param requestID the request id
     * @param time the time is was completed
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
