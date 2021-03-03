package edu.wpi.u.requests;

import java.io.Serializable;
import java.util.LinkedList;

public class FloralRequest implements IRequest {
    int numFlowers;
    String recipient;
    Request req;

    @Override
    public String getType() {
        return "Floral";
    }

    //For Specific Request Class
    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addLast(numFlowers);
        result.addLast(recipient);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){

        numFlowers = Integer.parseInt(l.get(0).toString());
        recipient =  l.get(1).toString();
    }

    @Override
    public String subtableCreateQuery() {//used for add request
        String query = "insert into "+getType()+"(requestID, "+getSpecificFields()[0]+", "+getSpecificFields()[1]+") values ('"+getGenericRequest().getRequestID()+"', "+numFlowers+", '"+recipient+"')";
        return query;
    }

    @Override
    public String updateDBQuery() {
        String query =  "update "+getType()+" set "+getSpecificFields()[0]+" = "+numFlowers+", "+getSpecificFields()[1]+" = '"+recipient+"' where requestID = '"+req.getRequestID()+"'";
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
        String[] res = new String[]{"numFlowers", "recipient"};
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
