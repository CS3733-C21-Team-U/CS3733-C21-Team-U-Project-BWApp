package com.authy2fa.lib;

import javax.servlet.http.HttpServletRequest;

public class RequestParametersValidator {

    public RequestParametersValidator(HttpServletRequest request){

    }
    public boolean validatePresence(String name, String email, String password, String countryCode, String phoneNumber) {
        return true;
    }

    public boolean validateEmail(String email) {
        return true;
    }
}
