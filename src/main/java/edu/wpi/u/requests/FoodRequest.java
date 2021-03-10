package edu.wpi.u.requests;

import edu.wpi.u.users.Role;

public class FoodRequest extends SpecificRequest{
    @Override
    public String[] getSpecificFields() {
        String[] res = new String[]{"meal", "allergies"};
        return res;
    }

    @Override
    public String getType() {
        return "Food";
    }

    @Override
    public String getRelevantRole() {
        return String.valueOf(Role.NURSE);
    }
}
