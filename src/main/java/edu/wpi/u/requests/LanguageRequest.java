package edu.wpi.u.requests;

import java.io.Serializable;
import java.util.LinkedList;

public class LanguageRequest implements IRequest{
    private String language;
    private int numInterpreters;
    private Request req;

    @Override
    public String getType() {
        return "Language";
    }

    //For Specific Request Class
    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addLast(language);
        result.addLast(numInterpreters);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){
        language = l.get(0).toString();
        try{
            numInterpreters = Integer.parseInt(l.get(1).toString());        }
        catch(Exception e){
            numInterpreters =  999;
        }
    }

    @Override
    public String subtableCreateQuery() {//used for add request
        String query = "insert into "+getType()+"(requestID, "+getSpecificFields()[0]+", "+getSpecificFields()[1]+") values ('"+getGenericRequest().getRequestID()+"', '"+language+"', "+numInterpreters+")";
        return query;
    }

    @Override
    public String updateDBQuery() {
        String query =  "update "+getType()+" set "+getSpecificFields()[0]+" = "+language+", "+getSpecificFields()[1]+" = "+numInterpreters+" where requestID = '"+req.getRequestID()+"'";
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
        String[] res = new String[]{"language", "numInterpreters"};
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
