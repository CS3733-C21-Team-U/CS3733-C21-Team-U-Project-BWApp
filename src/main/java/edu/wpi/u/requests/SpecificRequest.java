package edu.wpi.u.requests;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.u.App;

import java.sql.Timestamp;
import java.util.ArrayList;

public abstract class SpecificRequest extends RecursiveTreeObject<SpecificRequest> {

    private Request req;
    private ArrayList<String> specificFields;


    /**
     * This updates all allowed fields and constructs a comment based on what has changed.
     * @param title
     * @param description
     * @param needDate
     * @param locations
     * @param assignee
     * @param specifics
     */
    public Comment updateRequest(String title, String description, Timestamp needDate, ArrayList<String> locations,
                              ArrayList<String> assignee,  ArrayList<String> specifics){
        String updateDescription = req.editRequest(needDate, description, title, locations,
                assignee);

        for(int i=0; i<specifics.size(); i++){
            if(!specifics.get(i).equals(specificFields.get(i))){
                updateDescription = updateDescription.concat(getSpecificFields()[i]+" changed from '"+specificFields.get(i)+"' to '"+specifics.get(i)+"' \n");
            }
        }
        if(updateDescription.length() > 1)updateDescription = updateDescription.substring(0, updateDescription.length()-1);
        setSpecificData(specifics);
        return new Comment("UPDATE", updateDescription, App.userService.getActiveUser().getUserName(), CommentType.UPDATE);
    }

    public abstract String[] getSpecificFields();
    public abstract String getType();
    public abstract String getRelevantRole();

    public ArrayList<String> getSpecificData() {
        return specificFields;
    }
    public SpecificRequest setSpecificData(ArrayList<String> l){
        this.specificFields = l;
        return this;
    }
    public Request getGenericRequest() {
        return req;
    }
    public SpecificRequest setRequest(Request r) {
        this.req = r;
        return this;
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
    public SpecificRequest readStorageString(String specificFields){
        ArrayList<String> result = new ArrayList<String>();
        for(int i = 0; i < getSpecificFields().length; i++){
            String str = specificFields.split("&")[i];
            str = str.split("%")[1];
            result.add(i, str);
        }
        return setSpecificData(result);
    }

}
