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

        //readCSV("Requests.csv", "Requests");
       // readCSV("Locations.csv", "Locations");
      //  readCSV("Assignments.csv", "Assignments");
        printTableItem("Requests", "title");
        LinkedList<String> l1 = new LinkedList<String>();
        l1.add("UPARK00101");
        LinkedList<String> s1 = new LinkedList<String>();
        s1.add("Mary");
        Date d = new Date(900);
        //addRequest(new Request("Newest req", s1, d,null, "descript","title", l1, "type", "creator"));
      //  addRequest(new Request("Maintenance456", s1, d,null, "It seems that the shower head on A4 is leaky","Leaky Shower", l1, "Maintenance", "Kaamil"));

        //saveCSV("Requests", "Requests.csv", "Requests");
        //saveCSV("Locations", "Locations.csv", "Location");
      //  saveCSV("Assignments", "Assignments.csv", "Assignments");
        //printRequests();
    }


    public void updateRequest(SpecificRequest obj){//TODO: UPDATE PRIMARY COMMENT INSTEAD OF REQUEST FIELDS
        Request request= obj.getGenericRequest();
        //requestID, dateCreated, dateCompleted, description, title, type
       // System.out.println("Can anyone even hear me??????????????????????????????????");
       // if(request.getDateCompleted() != null) this.resolveRequest(request);
        updateField("Requests", "requestID", request.getRequestID(), "description", request.getDescription());
        updateField("Requests", "requestID", request.getRequestID(), "title", request.getTitle());
        updateField("Requests", "requestID", request.getRequestID(), "type", obj.getType());
        //this.updLocations(request.getRequestID(), request.getLocation());
       // this.updAssignees(request.getRequestID(), request.getAssignee());
        updateField("Requests", "requestID", request.getRequestID(), "specificData", obj.specificsStorageString());

        Comment c = new Comment("Update", "Update Description goes here", "Kaamil", CommentType.UPDATE, new Timestamp(System.currentTimeMillis()));
        request.addComment(c);//TODO: Find a place to make and compile Update comment in order to make detailed comments
        addCommenttoRequest(request.getRequestID(), c);
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

    public void updLocations(String requestId, LinkedList<String> locations){

        //Take whole list: do new one
        String str = "delete from Locations where requestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, requestId);
            ps.execute();
            for (String node : locations) {
                addLocation(node,requestId);
            }
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updAssignees(String requestId, LinkedList<String> assignees){
        /*
        Take whole list: do new one
         */
        String str = "delete from Assignments where requestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, requestId);
            ps.execute();
            for (String assignee : assignees) {
                addAssignee(assignee,requestId);
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
                Date needed = rs.getDate("dateNeeded");
                String desc = rs.getString("description");
                String title = rs.getString("title");
                String type = rs.getString("type");
                String specificData = rs.getString("specificData");
                ArrayList<String> locations = new ArrayList<String>();
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
                ArrayList<String> assignees = new ArrayList<String>();
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
                ArrayList<Comment> comments = new ArrayList<Comment>();
                try {
                    String str4 = "select * from Comments where requestID=? order by created DESC";
                    PreparedStatement ps4 = conn.prepareStatement(str4);
                    ps4.setString(1,id);
                    ResultSet rs4 = ps4.executeQuery();
                    while (rs4.next()){
                        comments.add(new Comment(rs4.getString("title"), rs4.getString("description"), rs4.getString("author"), CommentType.valueOf(rs4.getString("type")), rs4.getTimestamp("created")));
                    }
                    rs4.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                RequestFactory rf = new RequestFactory();
                SpecificRequest result = rf.makeRequest(type);
                Request r = new Request(id, new Timestamp(created.getTime()), locations, assignees, comments);
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
            for(String locationID : req.getLocation()){
                addLocation(locationID, req.getRequestID());
            }
            for(String assignmentID : req.getAssignee()){
                addAssignee(assignmentID, req.getRequestID());
            }
            for(Comment c : req.getComments()){
                addCommenttoRequest(req.getRequestID(), c);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        printTableItem("Requests", "specificData");
    }

    public void addAssignee(String userID, String requestID){
        String str = "insert into Assignments(assignmentID, requestID, userID) values (?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID+"_"+userID);
            ps.setString(2,requestID);
            ps.setString(3,userID);
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
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
//requestID , title , description, author, type, created timestamp;
    public void addCommenttoRequest(String requestID, Comment c){
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
            PreparedStatement ps = conn.prepareStatement(str);
           /* java.util.Date d = request.getDateCompleted();
            java.sql.Date sqld = new java.sql.Date(d.getTime());*/
            java.sql.Date d = new java.sql.Date(time);
            ps.setDate(1, d);
            ps.setString(2,requestID);
            ps.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

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
    public void deleteAssignment(String requestID, String userID){
        String str = "delete * from Assignments where requestID=? and userID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID);
            ps.setString(2,userID);
            ps.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
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
