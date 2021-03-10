package edu.wpi.u.requests;
import edu.wpi.u.users.Role;

import java.io.Serializable;
import java.util.LinkedList;

//TODO: Private or protected fields?
public class LaundryRequest extends SpecificRequest {

    @Override
    public String getType() {
        return "Laundry";
    }


    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"dryStrength", "numberLoads", "washStrength"};
        return res;
    }
    public String getRelevantRole(){
        return String.valueOf(Role.NURSE);
    }

}

