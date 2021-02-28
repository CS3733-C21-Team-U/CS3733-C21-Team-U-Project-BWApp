package edu.wpi.u.users;

import java.util.Date;

public class Guest extends User{
    protected Date appointmentDate;
    
    public Guest(String userID, String name, String accountName, String password, StaffType type,boolean deleted, Date appointmentDate, String phoneNumber, String email){
        super(userID, name,accountName,password,type,deleted, phoneNumber, email);
        this.appointmentDate = appointmentDate;
    }

    public static void main(String[] args) {
        //Guest g = new Guest("testg","testg","testg","testg",StaffType.PATIENT,true,new Date(800), "1919");
    }
}
