package edu.wpi.u.requests;
import java.sql.Date;

//TODO: Private or protected fields?
public class SecurityRequest implements IRequest {
    private String threatLevel;
    private Request req;
    private int priority;

    //Composition Pattern Type
    public SecurityRequest(String threatLevel, int priority, Request req) {
        this.threatLevel = threatLevel;
        this.priority = priority;
        this.req = req;
    }

    @Override
    public String displayLocations() { return this.req.displayLocation(); }

    @Override
    public String displayAssignees() { return this.req.displayAssignees(); }

    @Override
    public String getSpecificData() { return null; }

    @Override
    public String subtableCreateQuery() {
        String[] queries = new String[2];
        return "queries";
    }

    @Override
    public String updateDBQuery() {
        String[] queries = new String[2];
        return "queries";
    }

    @Override
    public Request getGenericRequest() {
        return null;
    }

}

