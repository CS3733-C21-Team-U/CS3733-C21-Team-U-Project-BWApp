package edu.wpi.u.requests;

import edu.wpi.u.users.Role;

import java.io.Serializable;
import java.util.LinkedList;

public class LanguageRequest extends SpecificRequest {

    @Override
    public String getType() {
        return "Language";
    }


    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"language", "numInterpreters"};
        return res;
    }
    public String getRelevantRole(){
        return String.valueOf(Role.TRANSLATORS);
    }
}
