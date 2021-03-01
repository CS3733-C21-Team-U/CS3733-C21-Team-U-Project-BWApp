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
    public Date getDateCreated() { return (Date) this.req.getDateCreated(); }

    @Override
    public Date getDateCompleted() { return (Date) this.req.getDateCompleted(); }

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
    public String[] createDBEntry() {
        String[] queries = new String[2];
        return queries;
    }

    @Override
    public String[] updateDBEntry() {
        String[] queries = new String[2];
        return queries;
    }

    @Override
    public String getRequestType() {
        return null;
    }

    @Override
    public Request getGenericRequest() {
        return null;
    }

}

