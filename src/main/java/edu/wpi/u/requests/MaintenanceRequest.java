package edu.wpi.u.requests;

import edu.wpi.u.users.Role;

import java.io.Serializable;
import java.util.LinkedList;

//TODO: Private or protected fields?
public class MaintenanceRequest extends SpecificRequest {

    @Override
    public String getType() {
        return "Maintenance";
    }


    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"machineUsed", "priority"};
        return res;
    }
    public String getRelevantRole(){
        return String.valueOf(Role.MAINTENANCE);
    }

}

