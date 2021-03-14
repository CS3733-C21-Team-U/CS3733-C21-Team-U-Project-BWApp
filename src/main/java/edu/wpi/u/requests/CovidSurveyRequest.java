package edu.wpi.u.requests;

import edu.wpi.u.users.Role;

public class CovidSurveyRequest extends SpecificRequest{
    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"riskLevel","temperature"};
        return res;
    }

    @Override
    public String getType() {
        return "CovidSurvey";
    }

    @Override
    public String getRelevantRole() {
        return String.valueOf(Role.DOCTOR);
    }
}
