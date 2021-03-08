package edu.wpi.u.requests;

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

}
