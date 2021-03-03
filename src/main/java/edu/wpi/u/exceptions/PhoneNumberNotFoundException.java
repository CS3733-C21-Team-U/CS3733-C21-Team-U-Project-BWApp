package edu.wpi.u.exceptions;

public class PhoneNumberNotFoundException extends Exception {
    public String description;

    public PhoneNumberNotFoundException(String description){
        this.description = description;
    }
}
