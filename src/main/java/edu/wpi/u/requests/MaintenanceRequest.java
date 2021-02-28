package edu.wpi.u.requests;
import edu.wpi.u.database.RequestData;

import java.sql.Date;
import java.util.ArrayList;

import static edu.wpi.u.database.Database.getDB;

//TODO: Private or protected fields?
public class MaintenanceRequest implements IRequest {
    private String machine;
    private Request req;
    private int priority;

    //some pattern type idk
    public MaintenanceRequest(String machine, int priority, Request req) {
        this.machine = machine;
        this.priority = priority;
        this.req = req;
    }

    //Getters for generic request fields
    @Override
    public java.sql.Date getDateCreated() { return (java.sql.Date) this.req.getDateCreated(); }
    @Override
    public java.sql.Date getDateCompleted() { return (Date) this.req.getDateCompleted(); }
    @Override
    public String getDescription() { return this.req.getDescription(); }
    @Override
    public String getRequestID() { return this.req.getRequestID(); }
    @Override
    public String getTitle() { return this.req.getTitle(); }
    @Override
    public String displayLocations() { return this.req.displayLocation(); }
    @Override
    public String displayAssignees() { return this.req.displayAssignees(); }

    //For Specific Request Class
    @Override
    public String getSpecificData() {
        StringBuilder s = new StringBuilder();
        s.append("Fields specific to Maintenance Requests\n");
        s.append("Machine used: " + machine + "\n");
        s.append("priority: " + priority + "\n");
        return s.toString();
        //TODO: How will UI use this data?
    }


    @Override
    public String[] createDBEntry() {//used for add request
        //send all fields to database
        //using fields, first insert into Requests table
        //second, insert into Maintenance table
        String str = "insert into Requests (requestID, dateCreated, dateCompleted, description, title, type) values ("+getRequestID()+","+req.getDateCreated()+",?,?,?,?)";
        try{
            //
            PreparedStatement ps = conn.prepareStatement(str);
            ps.execute();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        String str = "insert into Maintenance (requestID, machine, priority) values (?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,request.getRequestID());
            java.util.Date d = request.getDateCreated();
            java.sql.Date sqld = new java.sql.Date(d.getTime());
            ps.setDate(2, sqld);
            ps.setDate(3,null);
            ps.setString(4,request.getDescription());
            ps.setString(5,request.getTitle());
            ps.setString(6,request.getRequestType());
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

        /* public void addRequest(IRequest request) { // TODO: Add to interface IRequest instead
        //requestID varchar(50) not null , dateCreated date, dateCompleted date,description varchar(200),title varchar(50),type varchar(50),  primary key(requestID))";
        String str = "insert into Requests (requestID, dateCreated, dateCompleted, description, title, type) values (?,?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,request.getRequestID());
            java.util.Date d = request.getDateCreated();
            java.sql.Date sqld = new java.sql.Date(d.getTime());
            ps.setDate(2, sqld);
            ps.setDate(3,null);
            ps.setString(4,request.getDescription());
            ps.setString(5,request.getTitle());
            ps.setString(6,request.getRequestType());
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
    }*/
    }

    @Override
    public void updateDBEntry() { }

    @Override
    public String getRequestType() {
        return "Maintenance";
    }

    @Override
    public Request getGenericRequest() { return this.req; }

}

