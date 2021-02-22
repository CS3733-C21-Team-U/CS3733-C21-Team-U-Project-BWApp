package edu.wpi.u.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

//TODO: Private or protected fields?
public abstract class Request {
    protected String requestID;
    protected Date dateCreated, dateCompleted;
    protected String description;
    protected ArrayList<Staff> assignees;
    protected String location;//nodeID of where the request is

    protected void resolveRequest() { //TODO: Belongs in request?

    }

    protected void editRequest() { //TODO: Keep here to be overridden?

    }

    protected boolean getStatus() {
        return true;
    }

}
