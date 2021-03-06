package edu.wpi.u.requests;
import java.io.Serializable;
import java.util.LinkedList;

//TODO: Private or protected fields?
public class SecurityRequest extends SpecificRequest {

    @Override
    public String getType() {
        return "Security";
    }


    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"responseRequired", "threatLevel"};
        return res;
    }



}

