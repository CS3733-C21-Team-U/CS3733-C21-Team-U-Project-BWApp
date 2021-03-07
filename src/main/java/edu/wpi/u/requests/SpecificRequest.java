package edu.wpi.u.requests;
import java.util.ArrayList;

public abstract class SpecificRequest {

    private Request req;
    private ArrayList<String> specificFields;

    public abstract String[] getSpecificFields();
    public abstract String getType();


    public ArrayList<String> getSpecificData() {
        return specificFields;
    }
    public void setSpecificData(ArrayList<String> l){
        this.specificFields = l;
    }
    public Request getGenericRequest() {
        return req;
    }
    public void setRequest(Request r) {
        this.req = r;
    }
    public void fillObject(Request r, ArrayList<String> l) {
        setRequest(r);
        setSpecificData(l);
    }
    public String specificsStorageString(){
        String str = "";
        for(int i = 0; i < getSpecificFields().length; i++){
            if(i != 0){str = str.concat("&");}
            str = str.concat( getSpecificFields()[i] + "%" + getSpecificData().get(i));
        }
        return str;
    }

    public void readStorageString(String specificFields){
        ArrayList<String> result = new ArrayList<String>();
        for(int i = 0; i < getSpecificFields().length; i++){
            String str = specificFields.split("&")[i];
            str = str.split("%")[1];
            result.add(i, str);
        }
        setSpecificData(result);
    }
}
