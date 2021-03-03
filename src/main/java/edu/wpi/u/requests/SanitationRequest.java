package edu.wpi.u.requests;

import java.io.Serializable;
import java.util.LinkedList;

public class SanitationRequest implements IRequest{
    String spillType;
    int hazardLevel;
    Request req;

    public void SanitationRequest(){

    }

    @Override
    public String getType() {
        return "Sanitation";
    }

    //For Specific Request Class
    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addLast(hazardLevel);
        result.addLast(spillType);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){

        try{
            hazardLevel = Integer.parseInt(l.get(0).toString());        }
        catch(Exception e){
            hazardLevel =  999;
        }
        spillType =  l.get(1).toString();
    }

    @Override
    public String subtableCreateQuery() {//used for add request
        String query = "insert into Sanitation(requestID, hazardLevel, spillType) values ('"+getGenericRequest().getRequestID()+"', "+hazardLevel+", '"+spillType+"')";
        return query;
    }

    @Override
    public String updateDBQuery() {
        String query =  "update "+getType()+" set hazardLevel = "+hazardLevel+", spillType = '"+spillType+"' where requestID = '"+req.getRequestID()+"'";
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
        String[] res = new String[]{"hazardLevel", "spillType"};
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
