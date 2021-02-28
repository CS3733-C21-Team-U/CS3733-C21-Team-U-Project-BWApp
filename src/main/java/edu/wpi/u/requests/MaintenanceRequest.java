package edu.wpi.u.requests;
import java.sql.Date;
import java.util.ArrayList;

//TODO: Private or protected fields?
public class MaintenanceRequest implements IRequest {
    private String machine;
    private Request req;
    private int priority;

    //Composition Pattern Type
    public MaintenanceRequest(String machine, int priority, Request req) {
        this.machine = machine;
        this.priority = priority;
        this.req = req;
    }


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

}

