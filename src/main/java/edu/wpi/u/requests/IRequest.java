package edu.wpi.u.requests;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedList;

//all methods below used as getters for each respective table

public interface IRequest {
    String displayLocations();
    String displayAssignees();
    LinkedList<Serializable> getSpecificData();//return formatted string for extra fields of a given request
    void setSpecificData(LinkedList<Serializable> l);
    //DO FIRST DUMMY
    String subtableCreateQuery();
    String updateDBQuery();
    Request getGenericRequest();



}
