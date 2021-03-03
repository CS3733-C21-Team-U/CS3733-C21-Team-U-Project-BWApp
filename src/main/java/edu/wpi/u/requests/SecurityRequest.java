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

    @Override
    public String getType() {
        return null;
    }

    @Override
    public LinkedList<Serializable> getSpecificData() {
        return null;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l) {

    }

    @Override
    public String subtableCreateQuery() {
        return null;
    }

    @Override
    public String updateDBQuery() {
        return null;
    }

    @Override
    public Request getGenericRequest() {
        return null;
    }

    @Override
    public void setRequest(Request r) {

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

    }
    //Composition Pattern Type



}

