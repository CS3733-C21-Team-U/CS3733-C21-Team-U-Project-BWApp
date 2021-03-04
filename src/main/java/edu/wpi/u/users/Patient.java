package edu.wpi.u.users;

import java.util.Date;
import java.util.LinkedList;

public class Patient extends User{
    protected LinkedList<Appointment> appointments = new LinkedList<Appointment>();
    public Patient() {
    }

    public Patient(String userID, String name, String accountName, String password, String email, StaffType type, String phoneNumber, boolean deleted) {
        super(userID, name, accountName, password, email, type, phoneNumber, deleted);
    }


}
