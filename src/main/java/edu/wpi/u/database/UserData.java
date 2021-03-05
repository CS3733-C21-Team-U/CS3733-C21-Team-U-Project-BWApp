package edu.wpi.u.database;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.users.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

public class UserData extends Data{

    public UserData (){
        connect();
        dropGuests(); // TODO : Stop dropping values for demos
        dropEmployee();
        this.addEmployee(new Employee("Will","William","wburke","password","test@gmail.com", Role.ADMIN,"4016491137", false));
        this.addEmployee(new Employee("staff","staff","staff","staff","staff", Role.ADMIN,"7742706792",  false));
        this.addEmployee(new Employee("admin","admin","admin","admin","admin", Role.ADMIN,"7813155706", false));
        printGuest();
        printEmployees();
    }

    /**
     * Drop all values from the Employees table
     */
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

    /**
     * Drop all values from the Guests table
     */
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

    /**
     * Prints out the usernames of all employees
     */
    public void printEmployees(){
        String str = "select * from Employees";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            System.out.println("===Employees===");
            while (rs.next()){
                System.out.println("Employee ID: " + rs.getString("userName"));
            }
            rs.close();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Prints out the usernames of all employees
     */
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
    public Role getPermissions(String userID, String type){
        String id = "";
        if (type.equals("Employees")){
            id = "employeeID";
        }
        else if(type.equals("Guests")){
            id = "guestID";
        }
        String str = "select from " + type + " where " + id +"=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return Role.valueOf(rs.getString("type"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return Role.DEFAULT;
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
                        Role.valueOf(rs.getString("type")),
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
                // (String userID, String name, String accountName, String password, String email, Role type, String phoneNumber, Node locationOfSignificance, boolean deleted, LocalDate visitDate, String visitReason
                results.add(new Guest()); // TODO : FIX
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
        String str = "update Employees set email=? where " + typeID + "=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,newEmail);
            ps.setString(2, userID);
            ps.executeUpdate();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Gets the password of a given user based on an ID
     * @param userID the user id
     * @param type the users type
     * @return Employees, Guests, or "" for table names or not found
     */
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
    public void changePassword(String username, String newPassword){
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

    public Employee setEmployee(String username, String password){
        String str = "select * from Employees where username=? and password=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String employeeID = rs.getString("employeeID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String role = rs.getString("type");
                String phonenumber = rs.getString("phonenumber");
                return new Employee(employeeID,name,username,password, email, Role.valueOf(role),phonenumber,false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Employee();
    }

    public Guest setGuest(String username, String password){
        String str = "select * from Guests where username=? and password=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String guestId = rs.getString("guestID");
                String name = rs.getString("name");
                Date visitDate = rs.getDate("visitDate");
                String visitReason = rs.getString("visitReason");
                return new Guest(guestId,name, visitDate.toLocalDate(), visitReason, false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Guest();
    }

    public Patient setPatient(String username, String password){
        String str  = "select * from Patients where username=? and password=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String patientID = rs.getString("userID");
                String name = rs.getString("name");
                Role role = Role.valueOf(rs.getString("type")); // TODO : Refactor type to role
                String phonenumber = rs.getString("phonenumber");
                String email = rs.getString("email");
                String nodeID = rs.getString("location");
                boolean deleted = rs.getBoolean("deleted");
                ArrayList<Appointment> appointments = getPatientAppointments(patientID);
                String providerName = rs.getString("providerName");
                String parkingLocation = rs.getString("parkingLocation");
                String recommendedParkingLocation = rs.getString("recommendedParkingLocation");
                return new Patient(patientID, name, username, password, email, role, phonenumber, nodeID, deleted, appointments, providerName, parkingLocation, recommendedParkingLocation);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Patient();
    }

    private ArrayList<Appointment> getPatientAppointments(String patientID) {
        ArrayList<Appointment> results = new ArrayList<>();
        String str = "select * from Appointments where patientID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, patientID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String appointmentType = rs.getString("appointmentType");
                LocalDate appointmentDate = rs.getDate("appointmentDate").toLocalDate();
                String employeeID = rs.getString("employeeID");
                results.add(new Appointment(patientID, employeeID, appointmentDate, appointmentType));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    private ArrayList<Appointment> getEmployeeAppointments(String employeeID) {
        ArrayList<Appointment> results = new ArrayList<>();
        String str = "select * from Appointments where employeeID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, employeeID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String appointmentType = rs.getString("appointmentType");
                LocalDate appointmentDate = rs.getDate("appointmentDate").toLocalDate();
                String patientID = rs.getString("patientID");
                results.add(new Appointment(patientID, employeeID, appointmentDate, appointmentType));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }


//    /**
//     * TODO : UPDATE
//     * Sets the active user on successful login
//     * @param username username of the user
//     * @param password password othe user
//     * @param type Employees or Guests (table name)
//     * @return the User object of the active user
//     */
//    public User setUser(String username, String password, String type){
//        String idColumn = "";
//        int returnType = 0; // 1 is employee, 2 is guest
//        if (type.equals("Employees")){
//            idColumn = "employeeID";
//            returnType=1;
//        }
//        else if (type.equals("Guests")) {
//            idColumn = "guestID";
//            returnType=2;
//        }
//        //employeeID varchar(50), name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phoneNumber varchar(100), deleted boolean
//        String str = "select * from "+type+" where userName=? and password=?";
//        System.out.println(str);
//        try {
//            PreparedStatement ps = conn.prepareStatement(str);
//            ps.setString(1, username);
//            ps.setString(2,password);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()){
//                String userID= rs.getString(idColumn);
//                String name = rs.getString("name");
//                String email = rs.getString("email");
//                Date date = rs.getDate("visitDate");
//                Role staffType = Role.valueOf(rs.getString("type"));
//                String phoneNumber = rs.getString("phoneNumber");
//                if (returnType == 2){
//                    //return new Guest(userID, name, staffType,phoneNumber, false, date);
//                }
//                return new Employee(userID, name,username,password,email,staffType,phoneNumber,false);
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * Function used to find a user in the database based on a userID, used to check if user account is valid
     * @param userID the user id
     * @return Employees, Guests or empty string (table names or not found)
     */
    public String findUser(String userID) {
        String str = "select * from Employees where employeeID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                rs.close();
                ps.close();
                return "Employees";
            }
            else {
                str = "select * from Guests where guestID=?";
                ps = conn.prepareStatement(str);
                ps.setString(1,userID);
                rs = ps.executeQuery();
                if (rs.next()){
                    rs.close();
                    ps.close();
                    return "Guests";
                }
                else {
                    return "";
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
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
        String str = "insert into Guests (guestID, name, userName, password, email, type, phoneNumber, deleted) values (?,?,?,?,?,?,?,?)";
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
//            java.util.Date d = guest.getAppointmentDate();
//            java.sql.Date sqld = new java.sql.Date(d.getTime());
            //ps.setDate(9,sqld);
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
        String str = "update Guests set name=? and userName=? and password=? and email=? and type=? and phoneNumber=? and deleted=? where guestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, guest.getName());
            ps.setString(2, guest.getUserName());
            ps.setString(3, guest.getPassword());
            ps.setString(4,guest.getEmail());
            ps.setString(5, String.valueOf(guest.getType()));
            ps.setString(6,guest.getPhoneNumber());
            ps.setBoolean(7,guest.isDeleted());
//            java.util.Date d = guest.getAppointmentDate();
//            java.sql.Date sqld = new java.sql.Date(d.getTime());
//            ps.setDate(8,sqld);
            ps.setString(8,guest.getUserID());
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}