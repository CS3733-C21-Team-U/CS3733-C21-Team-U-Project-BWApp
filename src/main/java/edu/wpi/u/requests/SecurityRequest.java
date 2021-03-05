package edu.wpi.u.requests;
import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedList;

//TODO: Private or protected fields?
public class SecurityRequest implements IRequest {
    private String threatLevel;
    private Request req;
    private String responseRequired;


    @Override
    public String getType() {
        return "Security";
    }

    //For Specific Request Class
    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addLast(responseRequired);
        result.addLast(threatLevel);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){
        responseRequired = l.get(0).toString();
        threatLevel =  l.get(1).toString();
    }

    @Override
    public String subtableCreateQuery() {//used for add request
        String query = "insert into "+getType()+"(requestID, "+getSpecificFields()[0]+", "+getSpecificFields()[1]+") values ('"+getGenericRequest().getRequestID()+"', '"+responseRequired+"', '"+threatLevel+"')";
        return query;
    }

    @Override
    public String updateDBQuery() {
        String query =  "update "+getType()+" set "+getSpecificFields()[0]+" = '"+responseRequired+"', "+getSpecificFields()[1]+" = '"+threatLevel+"' where requestID = '"+req.getRequestID()+"'";
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
        String[] res = new String[]{"responseRequired", "threatLevel"};
        return res;
    }

    @Override
    public String getSpecificDataCode() {
        return "ss";
    }

    @Override
    public void fillObject(Request r, LinkedList<Serializable> l) {
        setRequest(r);
        setSpecificData(l);
    }




}

