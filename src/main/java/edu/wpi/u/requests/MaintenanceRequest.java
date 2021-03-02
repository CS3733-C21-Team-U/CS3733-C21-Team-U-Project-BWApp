package edu.wpi.u.requests;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.LinkedList;

//TODO: Private or protected fields?
public class MaintenanceRequest implements IRequest {
    private String machineUsed;
    private Request req;
    private int priority;

    public MaintenanceRequest() { }

    @Override
    public String displayLocations() { return this.req.displayLocation(); }
    @Override
    public String displayAssignees() { return this.req.displayAssignees(); }

    //For Specific Request Class
    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addFirst(priority);
        result.addFirst(machineUsed);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){
        machineUsed = (String)l.get(0);
        priority = (int)l.get(1);
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
    public String updateDBQuery() {
        String query = "update Maintenance set machineUsed = '"+machineUsed+"', priority = "+priority+" where requestID = '"+req.getRequestID()+"'";
        return query;
    }

    @Override
    public Request getGenericRequest() { return this.req; }

    @Override
    public void setRequest(Request r) {
        this.req = r;
    }

    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"machineUsed", "priority"};
        return res;
    }

    @Override
    public String getSpecificDataCode() {
        return "si";
    }

    @Override
    public void fillObject(Request r, LinkedList<Serializable> l) {
        setRequest(r);
        setSpecificData(l);
    }

}

