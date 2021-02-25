package edu.wpi.u.database;

import java.util.LinkedList;

public class Parameters {
    private String fieldName;
    private String fieldToUpd;

    public Parameters(String fieldName, String fieldToUpd) {
        this.fieldName = fieldName;
        this.fieldToUpd = fieldToUpd;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldToUpd() {
        return fieldToUpd;
    }

    public void setFieldToUpd(String fieldToUpd) {
        this.fieldToUpd = fieldToUpd;
    }
    /*
    Parse Parameter object for easy 
     */
    public String parseParams(){
        // TODO
        return null;
    }
}
