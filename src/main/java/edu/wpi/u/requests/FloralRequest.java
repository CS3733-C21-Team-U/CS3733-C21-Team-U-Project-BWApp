package edu.wpi.u.requests;

import edu.wpi.u.users.Role;

import java.io.Serializable;
import java.util.LinkedList;

public class FloralRequest extends SpecificRequest {

    @Override
    public String getType() {
        return "Floral";
    }

    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"numFlowers", "recipient"};
        return res;
    }
    public String getRelevantRole(){
        return String.valueOf(Role.DEFAULT);
    }

}
