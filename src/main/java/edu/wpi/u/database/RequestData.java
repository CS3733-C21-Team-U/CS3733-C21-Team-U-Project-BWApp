package edu.wpi.u.database;

import edu.wpi.u.algorithms.Node;
import edu.wpi.u.requests.Request;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;

public class RequestData extends Data{

    public RequestData(){ // TODO: load csv's for Nodes, Requests, Assignees, and RANJoint
        connect();
        readCSV("src/main/resources/edu/wpi/u/Requests.csv", "Requests");
//        LinkedList<String> l1 = new LinkedList<String>();
//        l1.add("UPARK00101");
//        LinkedList<String> s1 = new LinkedList<String>();
//        s1.add("Kaamil");
//        Date d = new Date(900);
//        addRequest(new Request("Newest req", s1, d,null, "descript","title", l1, "type", "creator"));
        saveCSV("Requests", "src/main/resources/edu/wpi/u/Requests.csv", "Requests");
//        saveCSV("Locations", "src/main/resources/edu/wpi/u/Locations.csv", "Location");
//        saveCSV("Assignments", "src/main/resources/edu/wpi/u/Assignments.csv", "Assignments");
    }

    public void updateRequest(Request request){ // TODO: Add assignee and location stuff
        this.updRequestDescription(request.getRequestID(), request.getDescription());
        this.updRequestTitle(request.getRequestID(), request.getTitle());
        this.updRequestType(request.getRequestID(), request.getType());
    }
    public ArrayList<Request> loadActiveRequests(){ // TODO: Add assignee and location stuff
        ArrayList<Request> results = new ArrayList<Request>();
        String str = "select * from Requests where dateCompleted is null";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String id = rs.getString("requestID");
                Date created = rs.getDate("dateCreated");
                String desc = rs.getString("description");
                String title = rs.getString("title");
                String location = rs.getString("location");
                LinkedList<String> locations = new LinkedList<String>();
                try { // TODO : Move to helper function
                    String str2 = "select * from Locations where requestID=?";
                    PreparedStatement ps2= conn.prepareStatement(str2);
                    ps2.setString(1,id);
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()){
                        locations.add(rs2.getString("nodeID"));
                    }
                    rs2.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                LinkedList<String> assignees = new LinkedList<String>();
                try {
                    String str3 = "select * from Assignments where requestID=?";
                    PreparedStatement ps3 = conn.prepareStatement(str3);
                    ps3.setString(1,id);
                    ResultSet rs3 = ps3.executeQuery();
                    while (rs3.next()){
                        assignees.add(rs3.getString("userID"));
                    }
                    rs3.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                String type = rs.getString("type");
                results.add(new Request(id,assignees, created,null,desc,title,locations,type, "Test creator"));
            }
            rs.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    public void addRequest(Request request) { // TODO: Add assignee and location stuff
        //requestID varchar(50) not null , dateCreated date, dateCompleted date,description varchar(200),title varchar(50),type varchar(50),  primary key(requestID))";
        String str = "insert into Requests (requestID, dateCreated, dateCompleted, description, title, type) values (?,?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,request.getRequestID());
            ps.setDate(2, (java.sql.Date) request.getDateCreated());
            ps.setDate(3, (java.sql.Date) request.getDateCompleted());
            ps.setString(4,request.getDescription());
            ps.setString(5,request.getTitle());
            ps.setString(6,request.getType());
            ps.execute();
            // Adding data into joint tables
            for(String locationID : request.getLocation()){
                addLocation(locationID, request.getRequestID());
            }
            for(String assignmentID : request.getAssignee()){
                addAssignee(assignmentID, request.getRequestID());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addAssignee(String userID, String requestID){
        String str = "insert into Assignments(requestID, userID) values (?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID);
            ps.setString(2,userID);
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addLocation(String nodeID, String requestID){
        String str = "insert into Locations(requestID, nodeID) values (?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,requestID);
            ps.setString(2,nodeID);
            ps.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delRequest(Request request) { // TODO: Add assignee and location stuff
        String str = "update Requests set dateCompleted=? where requestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setDate(1, (java.sql.Date) request.getDateCompleted());
            ps.setString(2,request.getRequestID());
            ps.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updRequestDescription(String requestID, String description) {
        String str = "update Requests set description=? where requestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,description);
            ps.setString(2,requestID);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updRequestTitle(String requestID, String title) {
        String str = "update Requests set title=? where requestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,title);
            ps.setString(2,requestID);
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void printRequests() {
        try {
            String str = "select * from Requests";
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                String id = rset.getString("requestID");
                System.out.println("Request id: " + id);
            }
            rset.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getRequests() {}
    public void getRequestByID(String id) {
    }
}
