package edu.wpi.u.requests;

import java.util.List;

//TODO: Private or protected fields?
public abstract class Request {
    protected Date dateCreated, dateCompleted;
    protected String description;
    protected List<Staff> assignees; //TODO: What kind of list?


    protected void resolveRequest() { //TODO: Belongs in request?

    }

    protected void editRequest() { //TODO: Keep here to be overridden?

    }

    protected boolean getStatus() {
        return true;
    }

}
