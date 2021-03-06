package edu.wpi.u.requests;

import java.io.Serializable;
import java.util.LinkedList;

public class GiftRequest extends SpecificRequest {

    @Override
    public String getType() {
        return "Gift";
    }

    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"contents", "mass"};
        return res;
    }

}
