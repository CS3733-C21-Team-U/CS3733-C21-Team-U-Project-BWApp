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
//    void setDatabase(); //getting fields of specific requestType, send to DB to add or update
//    // overload to set everything if no parameters, set parameter only if it exists
//    void getSpecificData();//return formatted string for extra fields of a given request
//    //DO FIRST DUMMY
//    void loadActiveRequest(); //wait for Kaamil

    String getSpecificData();//return formatted string for extra fields of a given request
    //DO FIRST DUMMY
    String subtableCreateQuery();
    String[] updateDBEntry();
    String getRequestType();
    Request getGenericRequest();


}
