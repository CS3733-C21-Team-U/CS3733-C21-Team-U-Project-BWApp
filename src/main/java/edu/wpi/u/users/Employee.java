package edu.wpi.u.users;

public class Employee extends User{

    public Employee(String userID, String name, String accountName, String password, StaffType type,boolean deleted, String phoneNumber, String email){
        super(userID, name,accountName,password,type,deleted, phoneNumber, email);
        // String userID, String name, String accountName, String password, StaffType type, boolean deleted, String phoneNumber
    }

    public static void main(String[] args) {
        //Employee e = new Employee("test","test","test","test",StaffType.ADMIN,true,true, "1919");
    }
}

