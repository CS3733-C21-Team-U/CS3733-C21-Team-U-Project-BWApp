package edu.wpi.u.requests;

import java.io.Serializable;
import java.util.LinkedList;

public class GiftRequest implements IRequest{
    String contents;
    int mass;
    Request req;

    @Override
    public String getType() {
        return "Gift";
    }

    //For Specific Request Class
    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addLast(contents);
        result.addLast(mass);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){
        contents = l.get(0).toString();
        try{
            mass = Integer.parseInt(l.get(1).toString());        }
        catch(Exception e){
            mass =  999;
        }
    }

    @Override
    public String subtableCreateQuery() {//used for add request
        String query = "insert into "+getType()+"(requestID, "+getSpecificFields()[0]+", "+getSpecificFields()[1]+") values ('"+getGenericRequest().getRequestID()+"', '"+contents+"', "+mass+")";
        return query;
    }

    @Override
    public String updateDBQuery() {
        String query =  "update "+getType()+" set "+getSpecificFields()[0]+" = '"+contents+"', "+getSpecificFields()[1]+" = "+mass+" where requestID = '"+req.getRequestID()+"'";
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
        String[] res = new String[]{"contents", "mass"};
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
