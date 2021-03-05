package edu.wpi.u.users;

public class Admin extends User{

    public Admin() {
    }

    public Admin(String userID, String name, String accountName, String password, String email, String phoneNumber, String locationNodeID, boolean deleted) {
        super(userID, name, accountName, password, email, Role.ADMIN, phoneNumber, locationNodeID, deleted);
    }


}
