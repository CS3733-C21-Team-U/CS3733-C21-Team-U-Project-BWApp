package edu.wpi.u.users;

import java.sql.Time;
import java.util.Date;

public class Guest extends User{
    //TODO : Maybe add a visitTime field for guest coming to hospital?
    //guestID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phonenumber varchar(100), deleted boolean, appointmentDate date, primary key(guestID))";
    public Guest(String guestID, String name, String userName, String password, String email, StaffType type,String phoneNumber, boolean deleted){
        super(guestID, name,userName,password,email,type,phoneNumber, deleted);
    }

    public static void main(String[] args) {
        //Guest g = new Guest("testg","testg","testg","testg",StaffType.PATIENT,true,new Date(800), "1919");
    }

    public void editGuest(String name, String userName, String password, String email, StaffType type, String phoneNumber, Date appointmentDate, boolean deleted) {
        super.editUser(name, userName, password, email, type, phoneNumber, deleted);
    }
}
