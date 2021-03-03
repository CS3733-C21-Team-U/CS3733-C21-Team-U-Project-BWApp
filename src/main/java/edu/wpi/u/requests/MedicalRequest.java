package edu.wpi.u.requests;

import java.io.Serializable;
import java.util.LinkedList;

public class MedicalRequest implements IRequest{
    String name;
    String quantity;
    String supplier;

    Request req;

    @Override
    public String getType() {
        return "AudioVisual";
    }

    //For Specific Request Class
    @Override
    public LinkedList<Serializable> getSpecificData() {
        LinkedList<Serializable> result = new LinkedList<Serializable>();
        result.addLast(name);
        result.addLast(quantity);
        result.addLast(supplier);
        return result;
    }

    @Override
    public void setSpecificData(LinkedList<Serializable> l){
        name = l.get(0).toString();
        quantity =  l.get(1).toString();
        supplier = l.get(2).toString();
    }

    @Override
    public String subtableCreateQuery() {//used for add request
        String query = "insert into "+getType()+"(requestID, "+getSpecificFields()[0]+", "+getSpecificFields()[1]+","+getSpecificFields()[2]+") values ('"+getGenericRequest().getRequestID()+"', '"+name+"', '"+quantity+"', '"+supplier+"')";
        return query;
    }

    @Override
    public String updateDBQuery() {
        String query =  "update "+getType()+" set "+getSpecificFields()[0]+" = "+name+", "+getSpecificFields()[1]+" = '"+quantity+"', "+getSpecificFields()[2]+" = '"+supplier+"' where requestID = '"+req.getRequestID()+"'";
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
        String[] res = new String[]{"name", "quantity", "supplier"};
        return res;
    }

    @Override
    public String getSpecificDataCode() {
        return "sss";
    }

    @Override
    public void fillObject(Request r, LinkedList<Serializable> l) {
        setRequest(r);
        setSpecificData(l);
    }

}
