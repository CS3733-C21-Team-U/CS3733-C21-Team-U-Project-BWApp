package edu.wpi.u.requests;

import java.sql.Date;

//all methods below used as getters for each respective table

public interface IRequest {
    String displayLocations();
    String displayAssignees();
    String getSpecificData();//return formatted string for extra fields of a given request
    //DO FIRST DUMMY
    String subtableCreateQuery();
    String[] updateDBEntry();
    Request getGenericRequest();


}
