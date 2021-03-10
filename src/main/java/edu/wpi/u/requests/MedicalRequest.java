package edu.wpi.u.requests;

import edu.wpi.u.users.Role;

import java.io.Serializable;
import java.util.LinkedList;

public class MedicalRequest extends SpecificRequest {
    @Override
    public String getType() {
        return "Medical";
    }


    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"name", "quantity", "supplier"};
        return res;
    }
    public String getRelevantRole(){
        return String.valueOf(Role.DOCTOR);
    }

}
