package edu.wpi.u.requests;

import java.sql.Date;
import java.sql.PreparedStatement;

//TODO: Private or protected fields?
public class MaintenanceRequest implements IRequest {
    private String machineUsed;
    private Request req;
    private int priority;

    //some pattern type idk
    public MaintenanceRequest(String machine, int priority, Request req) {
        this.machineUsed = machine;
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
        s.append("Machine used: " + machineUsed + "\n");
        s.append("priority: " + priority + "\n");
        return s.toString();
        //TODO: How will UI use this data?
    }


    @Override
    public String subtableCreateQuery() {//used for add request
        StringBuilder query2 = new StringBuilder();
        query2.append("insert into Maintenance (requestID, machineUsed, priority) values ('");
        query2.append(
                req.getRequestID() + "', '"
                        + machineUsed + "', "
                        + priority + ")");
        return query2.toString();
    }

    @Override
    public String[] updateDBEntry() {
        String[] queries = new String[2];
        return queries;
    }

    @Override
    public String getRequestType() {
        return "Maintenance";
    }

    @Override
    public Request getGenericRequest() { return this.req; }

}

