package edu.wpi.u.users;

import java.time.LocalDate;

public class Guest extends User{
    private LocalDate visitDate;
    //TODO : Maybe add a visitTime field for guest coming to hospital?
    //TODO : Maybe make Guest only have a phonenumber for account and use 2fa // maybe not need to store in db

    public Guest(String userID, String name, Role type, String phoneNumber, boolean deleted, LocalDate visitDate) {

        this.visitDate = visitDate;
    }

    public static void main(String[] args) {}

    public void editGuest(String name, String userName, String password, String email, Role type, String phoneNumber, boolean deleted) {
        super.editUser(name, userName, password, email, type, phoneNumber, deleted);
    }
}
