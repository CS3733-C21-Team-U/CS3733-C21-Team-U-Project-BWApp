package edu.wpi.u.database;

import java.sql.PreparedStatement;

public class RequestData extends Data{
    //Load from CSV into table - if any
    //send Request data from tables into Java objects
    //accessors/deleters
/*
    protected String requestID;
    protected Date dateCreated, dateCompleted;
    protected String description;
    protected String title;
    protected ArrayList<Staff> assignees;
    protected String location;
    protected String type; // TODO: make enum
 */
    public void addRequest(Request req) {
        try{
            String str = "insert into Request (requestID, dateCreated, dateCompleted, description, title, location, type) values (?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,req.getRequestID());
            ps.setDate(2, (java.sql.Date) req.getDateCreated());
            ps.setDate(3, (java.sql.Date) req.getDateCompleted());
            ps.setString(4,req.getDescription());
            ps.setString(5,req.getTitle());
            ps.setString(6,req.getLocation());
            ps.setString(7,req.getType());
            ps.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void delRequest() {

    }
    public void updRequest() {

    }
    public void getRequests() {

    }
    public void getRequestByID(String id) {

    }
}
