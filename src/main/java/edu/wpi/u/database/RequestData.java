package edu.wpi.u.database;

import edu.wpi.u.algorithms.Node;
import edu.wpi.u.requests.*;
//import edu.wpi.u.requests.Request;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

public class RequestData extends Data{

    public RequestData(){ // TODO: load csv's for Nodes, Requests, Assignees, and RANJoint
        connect();
        System.out.println("# of request items: " + getNumTableItems("Requests"));
        System.out.println("# of maintenance items: " + getNumTableItems("Maintenance"));
        //readCSV("Requests.csv", "Requests");
       // readCSV("Locations.csv", "Locations");
      //  readCSV("Assignments.csv", "Assignments");
        printRequests();
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


    public void updateRequest(Request request){ // TODO: Move to interface
        //requestID, dateCreated, dateCompleted, description, title, type
        System.out.println("Can anyone even hear me??????????????????????????????????");
       // if(request.getDateCompleted() != null) this.resolveRequest(request);
        this.updRequestDescription(request.getRequestID(), request.getDescription());
        this.updRequestTitle(request.getRequestID(), request.getTitle());
        this.updRequestType(request.getRequestID(), request.getType());
        this.updLocations(request.getRequestID(), request.getLocation());
        this.updAssignees(request.getRequestID(), request.getAssignee());
    }

   /* public void updateRequest(Request request){ // TODO: Move to interface
        *//*
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
    public ArrayList<IRequest> loadActiveRequests(){ // TODO: refactor for IRequest
        //go through requests, make a request object
        //go to subtable based on type(stored in field of request db)
        //set unique fields into correct object
        //If we do factory, this is where we do it

        ArrayList<IRequest> results = new ArrayList<>();
        String requestQuery = "select * from Requests where dateCompleted is null";
        try{
            PreparedStatement ps = conn.prepareStatement(requestQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                IRequest result = null;
                String id = rs.getString("requestID");
                Date created = rs.getDate("dateCreated");
                String desc = rs.getString("description");
                String title = rs.getString("title");
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

                Request r = new Request(id,assignees, created,null,desc,title,locations,type, "Test creator");
                switch (type){
                    case "Maintenance":
                        System.out.println("Found m Request");

                        String subtableQuery = "select * from Maintenance where requestID=?";
                        PreparedStatement specificTable = conn.prepareStatement(subtableQuery);
                        specificTable.setString(1,id);
                        ResultSet ans = specificTable.executeQuery();
                        if (!ans.next()) {
                            System.out.println("Item does not exist in Maintenance");
                            break;
                        }
                        System.out.println(ans.getString(2));

                        result = new MaintenanceRequest(ans.getString(2),
                                ans.getInt(3), r);
                        specificTable.close();
                        break;
//                    case "Laundry":
//                        result = new LaundryRequest("machine", 1, newRequest);
//                        break;
//                    case "Security":
//                        result = new SecurityRequest("machine", 1, newRequest);
//                        break;
                    default:
                       // result = new MaintenanceRequest("ans.getString(2)", 2, r);
                        System.out.println("Type does not exist!");

                }
                results.add(result);
            }
            //rs.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
   public void addRequest(IRequest request) { // TODO: Add to interface IRequest instead
       //
        //requestID varchar(50) not null , dateCreated date, dateCompleted date,description varchar(200),title varchar(50),type varchar(50),  primary key(requestID))";
        String str = "insert into Requests (requestID, dateCreated, dateCompleted, description, title, type) values (?,?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,request.getGenericRequest().getRequestID());
            java.util.Date d = request.getGenericRequest().getDateCreated();
            java.sql.Date sqld = new java.sql.Date(d.getTime());
            ps.setDate(2, sqld);
            ps.setDate(3,null);
            ps.setString(4,request.getGenericRequest().getDescription());
            ps.setString(5,request.getGenericRequest().getTitle());
            ps.setString(6,request.getGenericRequest().getType());
            ps.execute();
            // Adding data into joint tables
            for(String locationID : request.getGenericRequest().getLocation()){
                addLocation(locationID, request.getGenericRequest().getRequestID());
            }
            for(String assignmentID : request.getGenericRequest().getAssignee()){
                addAssignee(assignmentID, request.getGenericRequest().getRequestID());
            }

            //Now place into specific subtable based on class
            System.out.println(request.subtableCreateQuery());
            PreparedStatement ps1 = conn.prepareStatement(request.subtableCreateQuery());
            ps1.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        printTableItem("Maintenance", "machineUsed");
       printTableItem("Maintenance", "priority");



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
    public void resolveRequest(String requestID, Date date) { // TODO: Add resolve comment
        String str = "update Requests set dateCompleted=? where requestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
           /* java.util.Date d = request.getDateCompleted();
            java.sql.Date sqld = new java.sql.Date(d.getTime());*/
            ps.setDate(1, date);
            ps.setString(2,requestID);
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
            ps.execute();
            ps.close();
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
            ps.execute();
            ps.close();
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
