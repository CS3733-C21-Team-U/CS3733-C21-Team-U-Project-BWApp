package edu.wpi.u.requests;
import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedList;

//TODO: Private or protected fields?
public class SecurityRequest implements IRequest {
    private String threatLevel;
    private Request req;
    private int priority;

    public SecurityRequest(){};
    //Composition Pattern Type

    @Override
    public String displayLocations() { return this.req.displayLocation(); }

    @Override
    public String displayAssignees() { return this.req.displayAssignees(); }

    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addFirst(priority);
        result.addFirst(threatLevel);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){
        priority = (int)l.get(0);
        threatLevel = (String)l.get(1);
    }

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

    @Override
    public void setRequest(Request r) {
        this.req = r;
    }

    @Override
    public String[] getSpecificFields() {
        return new String[0];
    }

    @Override
    public String getSpecificDataCode() {
        return null;
    }

    @Override
    public void fillObject(Request r, LinkedList<Serializable> l) {
        setRequest(r);
        setSpecificData(l);
    }

}

