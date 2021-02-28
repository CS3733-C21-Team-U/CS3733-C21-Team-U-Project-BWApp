package edu.wpi.u.database;

import edu.wpi.u.users.Employee;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserData extends Data{

    public UserData (){
        connect();
    }

    public ArrayList<User> loadUsers(){
        return null;
    }


    public boolean checkUserName(String username){
        String str = "select * from Employees where userName=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                ps.close();
                return true;
            }
            else {
                str = "select * from Guests where userName=?";
                ps = conn.prepareStatement(str);
                ps.setString(1,username);
                rs = ps.executeQuery();
                ps.close();
                return rs.next();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPassword(String password){
        String str = "select * from Employees where password=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                ps.close();
                return true;
            }
            else {
                str = "select * from Guests where password=?";
                ps = conn.prepareStatement(str);
                ps.setString(1,password);
                rs = ps.executeQuery();
                ps.close();
                return rs.next();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a user to the table Users
     * @param employee the object containing all the information on the user
     */
    public void addEmployee(Employee employee){
        //employeeID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phoneNumber varchar(100), deleted boolean, primary key(employeeID))";
        String str = "insert into Employees (employeeID, name, userName, password, email, type, phoneNumber, deleted) values (?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,employee.getUserID());
            ps.setString(2,employee.getName());
            ps.setString(3, employee.getUserName());
            ps.setString(4, employee.getPassword());
            ps.setString(5,employee.getEmail());
            ps.setString(6,String.valueOf(employee.getType()));// StaffType.valueOf(string) to get ENUM type
            ps.setString(7,employee.getPhoneNumber());
            ps.setBoolean(8,false);
            ps.execute();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Function to add a guest to the Guests table
     * @param guest the object containing all of the information on the user
     */
    public void addGuest(Guest guest){
        //guestID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phonenumber varchar(100), deleted boolean, appointmentDate date, primary key(guestID))";
        String str = "insert into Guests (guestID, name, userName, password, email, type, phoneNumber, deleted, appointmentDate) values (?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,guest.getUserID());
            ps.setString(2,guest.getName());
            ps.setString(3, guest.getUserName());
            ps.setString(4, guest.getPassword());
            ps.setString(5,guest.getEmail());
            ps.setString(6,String.valueOf(guest.getType()));// StaffType.valueOf(string) to get ENUM type
            ps.setString(7,guest.getPhoneNumber());
            ps.setBoolean(8,true);
            java.util.Date d = guest.getAppointmentDate();
            java.sql.Date sqld = new java.sql.Date(d.getTime());
            ps.setDate(9,sqld);
            ps.execute();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Marks a user as deleted by setting the deleted field to false
     * @param employee the object containing all of the information on the user
     */
    public void delEmployee(Employee employee){
        String str ="update Employees set deleted=? where employeeID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setBoolean(1,true);
            ps.setString(2,employee.getUserID());
            ps.executeUpdate();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Marks a user as deleted by setting the deleted field to false
     * @param guest the object containing all of the information on the user
     */
    public void delGuest(Guest guest){
        String str ="update Guests set deleted=? where guestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setBoolean(1,true);
            ps.setString(2,guest.getUserID());
            ps.executeUpdate();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Will update the field of a user in the database
     * @param employee the object containing all of the information on the user
     */
    public void updEmployee(Employee employee){
        //"employeeID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), employed boolean, deleted boolean
        String str = "update Employees set name=? and userName=? and password=? and email=? and type=? and deleted=? and phoneNumber=? where employeeID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getUserName());
            ps.setString(3, employee.getPassword());
            ps.setString(4,employee.getEmail());
            ps.setString(5, String.valueOf(employee.getType()));
            ps.setBoolean(6,employee.isDeleted());
            ps.setString(7,employee.getPhoneNumber());
            ps.setString(8,employee.getUserID());
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Will update the field of a user in the database
     * @param guest the object containing all of the information on the user
     */
    public void updGuest(Guest guest){
       //guestID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phonenumber varchar(100), deleted boolean, appointmentDate date, primary key(guestID))";
        String str = "update Guests set name=? and userName=? and password=? and email=? and type=? and phoneNumber=? and deleted=? and appointmentDate=? where employeeID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, guest.getName());
            ps.setString(2, guest.getUserName());
            ps.setString(3, guest.getPassword());
            ps.setString(4,guest.getEmail());
            ps.setString(5, String.valueOf(guest.getType()));
            ps.setString(6,guest.getPhoneNumber());
            ps.setBoolean(7,guest.isDeleted());
            java.util.Date d = guest.getAppointmentDate();
            java.sql.Date sqld = new java.sql.Date(d.getTime());
            ps.setDate(8,sqld);
            ps.setString(9,guest.getUserID());
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
