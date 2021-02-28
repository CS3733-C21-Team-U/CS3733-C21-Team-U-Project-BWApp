package edu.wpi.u.requests;

import java.sql.Date;

//all methods below used as getters for each respective table

public interface IRequest {
    Date getDateCreated();
    Date getDateCompleted();
    String getDescription();
    String getRequestID();
    String getTitle();
    String displayLocations();
    String displayAssignees();





}
