package edu.wpi.u.requests;

import java.io.Serializable;
import java.util.LinkedList;

public class AudioVisualRequest implements IRequest {
    int isAudio;
    String name;
    Request req;

    public AudioVisualRequest() {

    }
    @Override
    public String getType() {
        return "AudioVisual";
    }

    //For Specific Request Class
    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addLast(isAudio);
        result.addLast(name);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){

        isAudio = Integer.parseInt(l.get(0).toString());
        name =  l.get(1).toString();
    }

    @Override
    public String subtableCreateQuery() {//used for add request
        String query = "insert into "+getType()+"(requestID, "+getSpecificFields()[0]+", "+getSpecificFields()[1]+") values ('"+getGenericRequest().getRequestID()+"', "+isAudio+", '"+name+"')";
        return query;
    }

    @Override
    public String updateDBQuery() {
        String query =  "update "+getType()+" set "+getSpecificFields()[0]+" = "+isAudio+", "+getSpecificFields()[1]+" = '"+name+"' where requestID = '"+req.getRequestID()+"'";
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
        String[] res = new String[]{"isAudio", "name"};
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
