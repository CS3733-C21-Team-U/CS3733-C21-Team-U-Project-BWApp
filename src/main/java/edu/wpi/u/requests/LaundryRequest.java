package edu.wpi.u.requests;
import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedList;

//TODO: Private or protected fields?
public class LaundryRequest implements IRequest{
    private Request req;
    private int numLoad;
    private int washStrength;
    private int dryStrength;

    public LaundryRequest() { }

    @Override
    public String getType() {
        return "Laundry";
    }

    //For Specific Request Class
    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addLast(dryStrength);
        result.addLast(numLoad);
        result.addLast(washStrength);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){

        dryStrength = Integer.parseInt(l.get(0).toString());
        numLoad =  Integer.parseInt(l.get(1).toString());
        washStrength = Integer.parseInt(l.get(2).toString());
    }

    @Override
    public String subtableCreateQuery() {//used for add request
        String query = "insert into Laundry(requestID, dryStrength, numLoad, washStrength) values ('"+getGenericRequest().getRequestID()+"', "+dryStrength+", "+numLoad+", "+washStrength+")";
        return query;
    }

    @Override
    public String updateDBQuery() {
        String query =  "update "+getType()+" set dryStrength = "+dryStrength+", numLoad = "+numLoad+", washStrength = "+washStrength+" where requestID = '"+req.getRequestID()+"'";
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
        String[] res = new String[]{"dryStrength", "numberLoads", "washStrength"};
        return res;
    }

    @Override
    public String getSpecificDataCode() {
        return "iii";
    }

    @Override
    public void fillObject(Request r, LinkedList<Serializable> l) {
        setRequest(r);
        setSpecificData(l);
    }


}

