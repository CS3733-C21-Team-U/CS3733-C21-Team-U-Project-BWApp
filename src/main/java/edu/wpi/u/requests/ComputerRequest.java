package edu.wpi.u.requests;

import java.io.Serializable;
import java.util.LinkedList;

public class ComputerRequest implements IRequest{
    String electronicType;
    int priority;
    Request req;

    @Override
    public String getType() {
        return "Computer";
    }

    //For Specific Request Class
    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addLast(electronicType);
        result.addLast(priority);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){
        electronicType = l.get(0).toString();

        try{
            priority =  Integer.parseInt(l.get(1).toString());
        }
        catch(Exception e){
            priority =  999;
        }
    }

    @Override
    public String subtableCreateQuery() {//used for add request
        String query = "insert into "+getType()+"(requestID, "+getSpecificFields()[0]+", "+getSpecificFields()[1]+") values ('"+getGenericRequest().getRequestID()+"', '"+electronicType+"', "+priority+")";
        return query;
    }

    @Override
    public String updateDBQuery() {
        String query =  "update "+getType()+" set "+getSpecificFields()[0]+" = '"+electronicType+"', "+getSpecificFields()[1]+" = "+priority+" where requestID = '"+req.getRequestID()+"'";
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
        String[] res = new String[]{"electronicType", "priority"};
        return res;
    }

    @Override
    public String getSpecificDataCode() {
        return "si";
    }

    @Override
    public void fillObject(Request r, LinkedList<Serializable> l) {
        setRequest(r);
        setSpecificData(l);
    }
}
