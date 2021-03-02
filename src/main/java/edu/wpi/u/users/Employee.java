package edu.wpi.u.users;

public class Employee extends User{

    //employeeID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phoneNumber varchar(100), deleted boolean

    public Employee(String employeeID, String name, String userName, String password, String email,StaffType type, String phoneNumber,boolean deleted){
        super(employeeID, name,userName,password,email,type, phoneNumber, deleted);
        // String userID, String name, String accountName, String password, StaffType type, boolean deleted, String phoneNumber
    }

    public static void main(String[] args) {
        //Employee e = new Employee("test","test","test","test",StaffType.ADMIN,true,true, "1919");
    }

    //TODO: Override editUser()
}

