package edu.wpi.u.users;

import java.sql.Time;
import java.util.Date;

public class Guest extends User{
    //TODO : Maybe add a visitTime field for guest coming to hospital?
    //TODO : Maybe make Guest only have a phonenumber for account and use 2fa // maybe not need to store in db
    public Guest(String guestID, String name, String userName, String password, String email, StaffType type,String phoneNumber, boolean deleted){
        super(guestID, name,userName,password,email,type,phoneNumber, deleted);
    }

    public static void main(String[] args) {
    }

    public void editGuest(String name, String userName, String password, String email, StaffType type, String phoneNumber, boolean deleted) {
        super.editUser(name, userName, password, email, type, phoneNumber, deleted);
    }
}
