package edu.wpi.u.requests;

import javafx.fxml.FXML;

import java.io.Serializable;
import java.util.LinkedList;

public class ReligiousRequest implements IRequest{

    int priority;
    String religion;
    Request req;

    @Override
    public String getType() {
        return "Religious";
    }

    //For Specific Request Class
    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addLast(priority);
        result.addLast(religion);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){

        try{
            priority = Integer.parseInt(l.get(0).toString());        }
        catch(Exception e){
            priority =  999;
        }
        religion =  l.get(1).toString();
    }

    @Override
    public String subtableCreateQuery() {//used for add request
        String query = "insert into "+getType()+"(requestID, "+getSpecificFields()[0]+", "+getSpecificFields()[1]+") values ('"+getGenericRequest().getRequestID()+"', "+priority+", '"+religion+"')";
        return query;
    }

    @Override
    public String updateDBQuery() {
        String query =  "update "+getType()+" set "+getSpecificFields()[0]+" = "+priority+", "+getSpecificFields()[1]+" = '"+religion+"' where requestID = '"+req.getRequestID()+"'";
        return query;
    }

    @Override
    public Request getGenericRequest() {
        return req;
    }

    @Override
    public void setRequest(Request r) {
        this.req = r;
    }


    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"priority", "religion"};
        return res;
    }

    @Override
    public String getSpecificDataCode() {
        return "is";
    }

    @Override
    public void fillObject(Request r, LinkedList<Serializable> l) {
        setRequest(r);
        setSpecificData(l);
    }


}
