package edu.wpi.u.requests;
import java.sql.Date;

//TODO: Private or protected fields?
public class LaundryRequest implements IRequest{
    private String washer;
    private Request req;
    private int priority;

    //Composition Pattern Type
    public LaundryRequest(String washer, int priority, Request req) {
        this.washer = washer;
        this.priority = priority;
        this.req = req;
    }


    @Override
    public String displayLocations() { return this.req.displayLocation(); }

    @Override
    public String displayAssignees() { return this.req.displayAssignees(); }

    @Override
    public String getSpecificData() {
        StringBuilder s = new StringBuilder();
        s.append("Fields specific to Maintenance Requests\n");
        s.append("Washer used: " + washer + "\n");
        s.append("priority: " + priority + "\n");
        return s.toString();
        //TODO: How will UI use this data?
    }

    @Override
    public String subtableCreateQuery() {
        String[] queries = new String[2];
        return "queries";
    }

    @Override
    public String[] updateDBEntry() {
        String[] queries = new String[2];
        return queries;
    }


    @Override
    public Request getGenericRequest() {
        return null;
    }

}

