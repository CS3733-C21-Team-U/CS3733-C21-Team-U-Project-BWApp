package edu.wpi.u.database;

import edu.wpi.u.users.Employee;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.StaffType;
import edu.wpi.u.users.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class UserData extends Data{

    public UserData (){
        connect();
    }

    public UserData(String testURL){
        testConnect(testURL);
    }

    public void dropEmployee() {
        String str = "delete from Employees";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.execute();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void dropGuests() {
        String str = "delete from Guests";
        try {
                PreparedStatement ps = conn.prepareStatement(str);
                ps.execute();
                ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printGuest(){
        String str = "select * from Guests";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            System.out.println("===Guests===");
            while (rs.next()){
                System.out.println("Guest ID: " + rs.getString("userName"));
            }
            rs.close();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Returns the StaffType for a user
     * @param userID users id
     * @param type Employees or Guests (table name)
     * @return the type of the user
     */
    public StaffType getPermissions(String userID, String type){
        String id = "";
        if (type.equals("Employees")){
            id = "employeeID";
        }
        else if(type.equals("Guests")){
            id = "guestID";
        }
        String str = "select type from " + type + " where " + id +"=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return StaffType.valueOf(rs.getString("type"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return StaffType.DEFUALT;
    }

    /**
     * Gets list of employees from database
     * @return list of employees
     */
    public ArrayList<Employee> getEmployees(){
        ArrayList<Employee> results = new ArrayList<>();
        String str = "select * from Employees";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                results.add(new Employee(rs.getString("employeeID"),
                        rs.getString("name"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("email"),
                        StaffType.valueOf(rs.getString("type")),
                        rs.getString("phoneNumber"),
                        rs.getBoolean("deleted")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Gets a list of guests from the database
     * @return list of guests
     */
    public ArrayList<Guest> getGuests(){
        ArrayList<Guest> results = new ArrayList<>();
        String str = "select * from Guests";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                results.add(new Guest(rs.getString("guestID"),
                        rs.getString("name"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("email"),
                        StaffType.valueOf(rs.getString("type")),
                        rs.getString("phoneNumber"),
                        rs.getDate("appointmentDate"),
                        rs.getBoolean("deleted")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Changes the email of a user
     * @param userID id of the user
     * @param newEmail the new email
     * @param type Employees or Guests (table name)
     */
    public void changeEmail(String userID, String newEmail, String type){
        String typeID ="";
        if (type.equals("Employees")){
            typeID = "employeeID"; //TODO: Extract this out to a helper function
        }
        else if (type.equals("Guests")){
            typeID = "guestID";
        }
        String str = "update " + type + " set email=? where " + typeID + "=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,newEmail);
            ps.setString(2, typeID);
            ps.executeUpdate();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getPassword(String userID, String type)  {
        String typeID ="";
        String password = "";
        if (type.equals("Employees")){
            typeID = "employeeID"; //TODO: Extract this out to a helper function
        }
        else if (type.equals("Guests")){
            typeID = "guestID";
        }
        String str = "select password from" + type + " where " + typeID + "=?";
        try{
          PreparedStatement ps = conn.prepareStatement(str);
          ps.setString(1, userID);
          rset = ps.executeQuery();
          password = rset.getString("password");
        } catch(Exception e){
            e.printStackTrace();
        }
        return password;
    }

    /**
     * Changes a users password
     * @param username username of user
     * @param newPassword the new password
     * @param type Employees or Guests (table name)
     */
    public void changePassword(String username, String newPassword, String type){
        String str = "update " + type + " set password=? where userName=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,newPassword);
            ps.setString(2,username);
            ps.executeUpdate();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Sets the active user on successful login
     * @param username username of the user
     * @param password password othe user
     * @param type Employees or Guests (table name)
     * @return the User object of the active user
     */
    public User setUser(String username, String password, String type){
        String idColumn = "";
        int returnType = 0; // 1 is employee, 2 is guest
        if (type.equals("Employees")){
            idColumn = "employeeID";
            returnType=1;
        }
        else if (type.equals("Guests")) {
            idColumn = "guestID";
            returnType=2;
        }
        //employeeID varchar(50), name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phoneNumber varchar(100), deleted boolean
        String str = "select * from "+type+" where userName=? and password=?";
        System.out.println(str);
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String userID= rs.getString(idColumn);
                String name = rs.getString("name");
                String email = rs.getString("email");
                StaffType staffType = StaffType.valueOf(rs.getString("type"));
                String phoneNumber = rs.getString("phoneNumber");
                if (returnType == 2){
                    Date appointmentDate = rs.getDate("appointmentDate");
                    return new Guest(userID, name,username,password,email,staffType,phoneNumber,appointmentDate, false);
                }
                return new Employee(userID, name,username,password,email,staffType,phoneNumber,false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new User();
    }

    /**
     * Checks if the database has the username
     * @param username username to be checked
     * @return type for user of setting the users type Employees or Guests (table name)
     * TODO : Replace check with the ID -> Current system doesnt allow for users with same password
     */
    public String checkUsername(String username){
        String str = "select * from Employees where userName=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                rs.close();
                ps.close();
                return "Employees";
            }
            else {
                rs.close();
                ps.close();
                System.out.println("Not in employees");
                String str2 = "select * from Guests where userName=?";
                PreparedStatement ps2 = conn.prepareStatement(str2);
                ps2.setString(1,username);
                ResultSet rs2 = ps2.executeQuery();
                if(rs2.next()){
                    rs2.close();
                    ps2.close();
                    return "Guests";
                }
                else{
                    System.out.println("Not in Guests");
                    rs2.close();
                    ps2.close();
                    return "";
                }
            }
        }
        catch (Exception e){
            System.out.println("Exception");
            e.printStackTrace();
            return "";
        }
    }

    /*
    public String checkUsername(String username){
        String str = "select * from Employees where username=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return "Employees";
            }
            else {
                String str2 = "select * from Guests where username=?";
                PreparedStatement ps2 = conn.prepareStatement(str2);
                ps2.setString(1,username);
                ResultSet rs2 = ps2.executeQuery();
                if(rs2.next()){
                    return "Guests";
                }
                else{
                    return "";
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
     */

    /**
     * Checks if the database has the phone number matched with the given username
     * @param phoneNumber phone number to be checked
     * @return the phone number of the user or " " if the username doesn't exist
     */
    public String checkPhoneNumber(String phoneNumber){
        String str = "select * from Employees where phoneNumber=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                ps.close();
               return "Employees";
            }
            else {
                String str2 = "select * from Guests where phoneNumber=?";
                PreparedStatement ps2 = conn.prepareStatement(str2);
                ps2.setString(1,phoneNumber);
                ResultSet rs2 = ps2.executeQuery();
                if(rs2.next()){
                    return "Guests";
                }
                else{
                    return "";
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Checks to see if the password is valid
     * @param password the password to be checked
     * @return type for user of setting the users type Employees or Guests (table name)
     */
    public String checkPassword(String password){
        String str = "select * from Employees where password=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return "Employees";
            }
            else {
                rs.close();
                ps.close();
                System.out.println("Not in employees");
                String str2 = "select * from Guests where password=?";
                PreparedStatement ps2 = conn.prepareStatement(str2);
                ps2.setString(1,password);
                ResultSet rs2 = ps2.executeQuery();
                if(rs2.next()){
                    rs2.close();
                    ps2.close();
                    return "Guests";
                }
                else{
                    System.out.println("Not in Guests");
                    rs2.close();
                    ps2.close();
                    return "";
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
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
