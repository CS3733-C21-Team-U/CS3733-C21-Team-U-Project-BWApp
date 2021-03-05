package edu.wpi.u.users;

import edu.wpi.u.algorithms.Node;

import java.time.LocalDate;

public class Guest extends User{
    private LocalDate visitDate;
    private String visitReason;
    //TODO : Maybe add a visitTime field for guest coming to hospital?
    //TODO : Maybe make Guest only have a phone number for account and use 2fa // maybe not need to store in db
    public Guest() {}

    public Guest(LocalDate visitDate, String visitReason) {
        this.visitDate = visitDate;
        this.visitReason = visitReason;
    }

    public Guest(String userID, String name, Role type, String phoneNumber, Node locationOfSignificance, boolean deleted, LocalDate visitDate, String visitReason) {
        //super(userID, name, type, phoneNumber, locationOfSignificance, deleted);
        this.visitDate = visitDate;
        this.visitReason = visitReason;
    }

    public void editGuest(String name, String userName, String password, String email, Role type, String phoneNumber, boolean deleted) {
        super.editUser(name, userName, password, email, type, phoneNumber, deleted);
    }
}
