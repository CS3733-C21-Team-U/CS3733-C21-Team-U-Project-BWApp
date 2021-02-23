package edu.wpi.u.database;

import edu.wpi.u.requests.Request;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;

public class RequestData extends Data{
    //Load from CSV into table - if any
    //send Request data from tables into Java objects
    //accessors/deleters

    public RequestData(){ // TODO: load csv's for Nodes, Requests, Assignees, and RANJoint
        connect();
        readCSV("src/main/resources/edu/wpi/u/Requests.csv", "Requests");
        //Request r = new Request("Testtttyyy", new Date(1994- 2- 1), new Date(1994-23-10),"Charlie is dumb" , "Title", "place", "cool");
        //addRequest(r);
        saveCSV("Requests", "src/main/resources/edu/wpi/u/Requests.csv", "Requests");
        //printRequests();
    }

    public void updateRequest(Request request){ // TODO: Add assignee and location stuff
        this.updRequestDescription(request.getRequestID(), request.getDescription());
        this.updRequestTitle(request.getRequestID(), request.getTitle());
        //this.updRequestLocation(request.getRequestID(), request.getLocation()); TODO: Change to list
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
                LinkedList<String> test = new LinkedList<String>();  //TODO: Change to list
                test.add(location);
                LinkedList<String> test2 = new LinkedList<String>(); //TODO: Change to list
                test2.add("Test assignee");
                String type = rs.getString("type");
                results.add(new Request(id,test2, created,null,desc,title,test,type, "Test creator"));
            }
            rs.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public void addRequest(Request request) { // TODO: Add assignee and location stuff
        String str = "insert into Requests (requestID, dateCreated, dateCompleted, description, title, location, type) values (?,?,?,?,?,?,?)";
        String str2 = "insert into Assignees (assigneeID, name) values (?, ?)";
        String str3 = "insert into RANJoint (requestID, assigneeID, nodeID) values (?,?,?)";
        // NODES IN locations LinkedList SHOULD ALREADY EXIST -> might neeed to maek sure they do?

        try{
            PreparedStatement ps = conn.prepareStatement(str);
            PreparedStatement ps2 = conn.prepareStatement(str2);
            PreparedStatement ps3 = conn.prepareStatement(str3);

            ps.setString(1,request.getRequestID());
            ps.setDate(2, (java.sql.Date) request.getDateCreated());
            ps.setDate(3, (java.sql.Date) request.getDateCompleted());
            ps.setString(4,request.getDescription());
            ps.setString(5,request.getTitle());
            //ps.setString(6,request.getLocation());
            ps.setString(7,request.getType());

            // TODO: update other tables as necessary

            ps.execute();
            ps2.execute();
            ps3.execute();

        }
        catch (Exception e){
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
    public void updRequestLocation(String requestID, String location) {
        String str = "update Requests set location=? where requestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,location);
            ps.setString(2,requestID);
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

    public void getRequests() {

    }
    public void getRequestByID(String id) {
    }
}
