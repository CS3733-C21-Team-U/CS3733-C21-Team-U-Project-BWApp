package edu.wpi.u.database;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.users.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class UserData extends Data{

    public UserData (){
        connect();
        //dropGuests(); // TODO : Stop dropping values for demos
//        dropEmployee();
        //userID, name, accountName, password, email, type, phoneNumber, locationNodeID, deleted
        //addEmployee(new Employee("debug", "debug", "bob", "12345", "debug", Role.DOCTOR, "9148394600", false));
        //addPatient(new Patient("debug","debug","debug","debug","debug", Role.PATIENT,"9998887777","UDEPT00101",false,new ArrayList<Appointment>(),"debug","UHALL00101", "debug"));
        /*
        String guestID,
        String name,
        Role type,
        Timestamp visitDate,
        String visitReason,
        boolean deleted)
         */
        //addGuest(new Guest("testDebug", "debug", Role.GUEST, new Timestamp(System.currentTimeMillis()), "This is a reason for visit", false));
        /*
        String guestID,
        String name,
        Timestamp visitDate,
        String visitReason,
        boolean deleted

        String userID,
        String name,
        String accountName,
        String password,
        String email,
        Role type,
        String phoneNumber,
        String locationNodeID,
        boolean deleted,
        ArrayList<Appointment> appointments,
        String providerName,
        String parkingLocation,
        String recommendedParkingLocation


         StringProperty userIDfx,
         StringProperty namefx,
         StringProperty userNamefx,
         StringProperty passwordfx,
         StringProperty typefx,
         StringProperty phoneNumberfx,
         StringProperty emailfx,
         BooleanProperty deletedfx,
         StringProperty locationNodeIDfx) {
         printPatients();
         printGuest();
         printEmployees();
*/

    }

    /**
     * Hashmap of password , usernames for easier verification
     * @return the hashmap of all users passwords and usernames
     */
    public HashMap<String,String> setEasyValidate(){
        HashMap<String, String> result = new HashMap<>();
        String str = "select userName,password from Patients";
        String str2 = "select userName,password from Employees";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            PreparedStatement ps2 = conn.prepareStatement(str2);
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps.executeQuery();
            while (rs.next()){
                result.put(rs.getString("password"), rs.getString("username"));
            }
            rs.close();
            ps.close();
            while (rs2.next()){
                result.put(rs2.getString("password"), rs2.getString("username"));
            }
            rs2.close();
            ps2.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
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
     * Prints out the patient ids of all patients
     */
    public void printPatients(){
        String str = "select * from Patients";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            System.out.println("===Patients===");
            while (rs.next()){
                System.out.println("Patients id: " + rs.getString("patientID"));
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
    public void printEmployees(){
        String str = "select * from Employees";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            System.out.println("===Employees===");
            while (rs.next()){
                System.out.println("Employee userName: " + rs.getString("userName"));
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
     * Creates a new patient via createAccount portal
     * @param patient the patient object created
     * @return Patient with that username already exists or Patient with that password already exists or Patient added
     */
    public String createPatient(Patient patient){
        if (checkUsername(patient.getUserName()).equals("Patients")){
            return "Patient with that username already exists";
        }
        else if (checkPassword(patient.getPassword()).equals("Patients")){
            return "Patient with that password already exists";
        }
        else {
            addPatient(patient);
            return "Patient added";
        }
    }

    /**
     * Creates a new employee via createAccount portal
     * @param employee the employee object created
     * @return Employee with that username already exists or Employee with that password already exists or Employee added
     */
    public String createEmployee(Employee employee){
        if (checkUsername(employee.getUserName()).equals("")){
            return "Employee with that username already exists";
        }
        else {
            /*
            employeeID, name, userName, password, email, type, phoneNumber, deleted)
             */
            addEmployee(employee);
            return "Employee added";
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
     * Gets a list of patients from database
     * @return list of patients
     */
    public ArrayList<Patient> getPatients() {
        ArrayList<Patient> results = new ArrayList<>();
        String str = "select * from Patients where deleted=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setBoolean(1,false);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                // todo : check
                results.add(new Patient(
                        rs.getString("patientID"),
                        rs.getString("name"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("email"),
                        Role.valueOf(rs.getString("type")),
                        rs.getString("phonenumber"),
                        rs.getBoolean("deleted"),
                        getPatientAppointments(rs.getString("patientID")),
                        rs.getString("providerName"),
                        rs.getString("parkingLocation"),
                        rs.getString("recommendedParkingLocation")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Gets a hashmap of employees based on a certain type
     * @param type the type
     * @return the hashmap of employee names
     */
    public HashMap<String, String> getEmployeeNamesByType(String type){
        HashMap<String,String> result = new HashMap<>();
        String str = "select employeeID,name from Employees where type=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                result.put(rs.getString("employeeID"), rs.getString("name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Gets list of employees from database
     * @return list of employees
     */
    public ArrayList<Employee> getEmployees(){
        ArrayList<Employee> results = new ArrayList<>();
        String str = "select * from Employees where deleted=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setBoolean(1,false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                results.add(new Employee(rs.getString("employeeID"),
                        rs.getString("name"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("email"),
                        Role.valueOf(rs.getString("type")),
                        rs.getString("phoneNumber"),
                        //rs.getString("locationNodeID"),
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
        String str = "select * from Guests where deleted=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setBoolean(1,false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                results.add(new Guest(rs.getString("guestID"), rs.getString("name"),  Role.GUEST, rs.getTimestamp("visitDate"), rs.getString("visitReason"), false)); // TODO : FIX
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Changes the phonenumbr of a user
     * @param userID the id of the user
     * @param newPhoneNumber the phone number of the user
     * @param type Employees or Patients (table name)
     */
    public void changePhoneNumber(String userID, String newPhoneNumber, String type){
        String typeID ="";
        if (type.equals("Employees")){
            typeID = "employeeID"; //TODO: Extract this out to a helper function
        }
        else {
            typeID = "patientID";
        }
        String str = "update " + type + " set phonenumber=? where " + typeID + "=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, newPhoneNumber);
            ps.setString(2, typeID);
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Changes the email of a user
     * @param userID id of the user
     * @param newEmail the new email
     * @param type Employees or Patients (table name)
     */
    public void changeEmail(String userID, String newEmail, String type){
        String typeID ="";
        if (type.equals("Employees")){
            typeID = "employeeID"; //TODO: Extract this out to a helper function
        }
        else {
            typeID = "patientID";
        }
        String str = "update "+ type +" set email=? where " + typeID + "=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, newEmail);
            ps.setString(2, typeID);
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
     * @return Employees or Patients (table name) or "" if not found
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
     * @param type Employees or Patients (table name)
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
     * Sets the employee user in the UserService class by fetching from the database
     * @param username the username
     * @param password the password
     * @return the employee with the username and password
     */
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
                //String locationNodeID = rs.getString("locationNodeID");
                // TODO : Where to put rs.close and ps.close ?
                return new Employee(employeeID,name,username,password, email, Role.valueOf(role),phonenumber, false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Employee();
    }

    /**
     * Sets an employee based on and id
     * @param employeeID employee id
     * @return the employee
     */
    public Employee setEmployee(String employeeID){
        String str = "select * from Employees where employeeID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,employeeID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String name = rs.getString("name");
                String username = rs.getString("userName");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String role = rs.getString("type");
                String phonenumber = rs.getString("phonenumber");
                return new Employee(employeeID, name,username,password,email,Role.valueOf(role), phonenumber, false);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new Employee();
    }

    /**
     * Sets the guest user in the UserService class by fetching from the database
     * TODO : Find a way to have guests with the same name ? Maybe by also passing in a otp or description of visit
     * @param name the name of the Guest
     * @return the guest with the name
     */
    public Guest setGuest(String name){
        String str = "select * from Guests where name=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String guestId = rs.getString("guestID");
                Timestamp visitDate = rs.getTimestamp("visitDate");
                String visitReason = rs.getString("visitReason");
                System.out.println(new Guest(guestId,name,Role.GUEST, visitDate, visitReason, false));
                return new Guest(guestId,name,Role.GUEST, visitDate, visitReason, false);
            }
            else { // No Guest in system (new guest)

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Guest();
    }

    /**
     * Sets the guest user in the UserService class by fetching from the database
     * @param username the username
     * @param password the password
     * @return the patient with the username and password
     */
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
                //String nodeID = rs.getString("location");
                boolean deleted = rs.getBoolean("deleted");
                ArrayList<Appointment> appointments = getPatientAppointments(patientID);
                String providerName = rs.getString("providerName");
                String parkingLocation = rs.getString("parkingLocation");
                String recommendedParkingLocation = rs.getString("recommendedParkingLocation");
                // todo : check
                return new Patient(patientID, name, username, password, email, role, phonenumber, deleted, appointments, providerName, parkingLocation, recommendedParkingLocation);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Patient();
    }

    /**
     * Sets the patient by an id
     * @param patientID the id of the patient
     * @return the patient object
     */
    public Patient setPatient(String patientID){
        String str = "select * from Patients where patientID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,patientID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String name = rs.getString("name");
                String username = rs.getString("userName");
                String password = rs.getString("password");
                Role role = Role.valueOf(rs.getString("type"));
                String phonenumber = rs.getString("phonenumber");
                String email = rs.getString("email");
                //String nodeID = rs.getString("location");
                boolean deleted = rs.getBoolean("deleted");
                ArrayList<Appointment> appointments = getPatientAppointments(patientID);
                String providerName = rs.getString("providerName");
                String parkingLocation = rs.getString("parkingLocation");
                String recommendedParkingLocation = rs.getString("recommendedParkingLocation");
                // todo : check
                return new Patient(patientID, name, username, password, email, role, phonenumber, deleted, appointments, providerName, parkingLocation, recommendedParkingLocation);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Patient();
    }

    /**
     * Retrieves a patients appointments
     * @param patientID the id of the patient
     * @return list of Appointments
     */
    private ArrayList<Appointment> getPatientAppointments(String patientID) {
        ArrayList<Appointment> results = new ArrayList<>();
        String str = "select * from Appointments where patientID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, patientID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String appointmentID = rs.getString("appointmentID");
                String appointmentType = rs.getString("appointmentType");
                Timestamp appointmentDate = rs.getTimestamp("appointmentDate");
                String employeeID = rs.getString("employeeID");
                results.add(new Appointment(appointmentID, patientID, employeeID, appointmentDate, appointmentType));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Retrieves a employees appointments
     * @param employeeID the id of the employee
     * @return list of Appointments
     */
    private ArrayList<Appointment> getEmployeeAppointments(String employeeID) {
        ArrayList<Appointment> results = new ArrayList<>();
        String str = "select * from Appointments where employeeID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, employeeID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String appointmentID = rs.getString("appointmentID");
                String appointmentType = rs.getString("appointmentType");
                Timestamp appointmentDate = rs.getTimestamp("appointmentDate");
                String patientID = rs.getString("patientID");
                results.add(new Appointment(appointmentID, patientID, employeeID, appointmentDate, appointmentType));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Function used to find a user in the database based on a userID, used to check if user account is valid
     * @param userID the user id
     * @return Employees or Patients (table name) or "" if not found
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
     * @return Employees or Patients (table name) or "" if not found
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
                //System.out.println("Not in employees");
                String str2 = "select * from Patients where userName=?";
                PreparedStatement ps2 = conn.prepareStatement(str2);
                ps2.setString(1,username);
                ResultSet rs2 = ps2.executeQuery();
                if(rs2.next()){
                    rs2.close();
                    ps2.close();
                    return "Patients";
                }
                else{
                    //System.out.println("Not in Patients");
                    rs2.close();
                    ps2.close();
                    return "";
                }
            }
        }
        catch (Exception e){
            System.out.println("Checking username failed");
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Checks if the database has the phone number matched with the given username
     * @param phoneNumber phone number to be checked
     * @return the phone number of the user or "" if the username doesn't exist
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
                String str2 = "select * from Patients where phoneNumber=?";
                PreparedStatement ps2 = conn.prepareStatement(str2);
                ps2.setString(1,phoneNumber);
                ResultSet rs2 = ps2.executeQuery();
                if(rs2.next()){
                    return "Patients";
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
     * @return Employees or Patients (table name) or "" for not found
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
                String str2 = "select * from Patients where password=?";
                PreparedStatement ps2 = conn.prepareStatement(str2);
                ps2.setString(1,password);
                ResultSet rs2 = ps2.executeQuery();
                if(rs2.next()){
                    rs2.close();
                    ps2.close();
                    return "Patients";
                }
                else{
                    System.out.println("Not in Patients");
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
     * Adds a location (nodeID) to the patient
     * @param patientID patient id
     * @param nodeID node id
     */
    public void addLocationID(String patientID, String nodeID){
        String str = "update Patients set locationNodeID=? where patientID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, nodeID);
            ps.setString(2, patientID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a parking location to the patient location
     * @param patientID id of the patient
     * @param parkingLocation location of the patient
     */
    public void addPatientParkingLocation(String patientID, String parkingLocation){
        String str = "update Patients set parkingLocation=? where patientID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, parkingLocation);
            ps.setString(2,patientID);
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a recommended parking location
     * @param patientID id of the patient
     * @param recommendedParkingLocation the recommended parking location
     */
    public void addPatientRecommendedParkingLocation (String patientID, String recommendedParkingLocation){
        String str = "update Patients set recommendedParkingLocation=? where patientID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,recommendedParkingLocation);
            ps.setString(2,patientID);
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a patient to the table Patients
     * @param patient the object containing all the information on the user
     */
    public void addPatient(Patient patient) {
        String str = "insert into Patients (patientID, name, userName, password, email, type, phonenumber, deleted, providerName, parkingLocation, recommendedParkingLocation) values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            addAppointments(patient.getAppointments());
            ps.setString(1,patient.getUserID()); // id
            ps.setString(2,patient.getName()); // name
            ps.setString(3,patient.getUserName()); // username
            ps.setString(4,patient.getPassword()); //password
            ps.setString(5,patient.getEmail()); //email
            ps.setString(6,String.valueOf(patient.getType())); // role/type
            ps.setString(7,patient.getPhoneNumber()); // phonenumer
            //ps.setString(8,patient.getLocationNodeID()); //location
            ps.setBoolean(8,false); // deleted
            ps.setString(9,patient.getProviderName()); // provider name
            ps.setString(10,patient.getParkingLocation()); // park location
            ps.setString(11,patient.getRecommendedParkingLocation()); // recommended park location
            ps.execute();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a single appointment
     * @param appointment the appointment
     */
    public void addAppointment(Appointment appointment){
        String str = "insert into Appointments (appointmentID, appointmentDate, appointmentType, patientID, employeeID) values (?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,appointment.getAppointmentID());
            ps.setTimestamp(2,appointment.getAppointmentDate());
            ps.setString(3,appointment.getAppointmentType());
            ps.setString(4,appointment.getPatientID());
            ps.setString(5,appointment.getEmployeeID());
            ps.execute();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Used to add a list of appointments
     * @param appointments list of appointments
     */
    public void addAppointments(ArrayList<Appointment> appointments){
        for (Appointment appointment: appointments){
            if (appointment != null){
                addAppointment(appointment);
            }
        }
    }

    /**
     * Adds a user to the table Users
     * @param employee the object containing all the information on the user
     */
    public void addEmployee(Employee employee){
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
            //ps.setString(8,employee.getLocationNodeID());
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
        String str = "insert into Guests (guestID, name, visitDate, visitReason, deleted) values (?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,guest.getGuestID());
            ps.setString(2,guest.getName());
            ps.setTimestamp(3, guest.getVisitDate());
            ps.setString(4, guest.getVisitReason());
            ps.setBoolean(5,false);
            ps.execute();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Marks a user as deleted by setting the deleted field to false
     * @param patient the patient
     */
    public void delPatient(Patient patient){
        String str = "update Patients set deleted=? where patientID=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setBoolean(1,true);
            ps.setString(2,patient.getUserID());
        }catch (Exception e){
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
     * @param patient the patient
     */
    public void updPatient(Patient patient){
        String str = "update Patients set name=?,userName=? , password=? , email=? , type=? , phonenumber=? , deleted=? , providerName=? , parkingLocation=? , recommendedParkingLocation=? where patientID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,patient.getName());
            ps.setString(2,patient.getUserName());
            ps.setString(3,patient.getPassword());
            ps.setString(4,patient.getEmail());
            ps.setString(5,String.valueOf(patient.getType()));
            ps.setString(6,patient.getPhoneNumber());
            //ps.setString(7,patient.getLocationNodeID());
            ps.setBoolean(7,patient.isDeleted());
            ps.setString(8,patient.getProviderName());
            ps.setString(9,patient.getParkingLocation());
            ps.setString(10,patient.getRecommendedParkingLocation());
            ps.setString(11,patient.getUserID());
            ps.execute();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Will update the field of a user in the database
     * @param employee the object containing all of the information on the user
     */
    public void updEmployee(Employee employee){
        String str = "update Employees set name=? , userName=? , password=? , email=? , type=? , deleted=? , phoneNumber=? where employeeID=?";
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
        String str = "update Guests set name=? and visitDate=? and visitReason=? and deleted=? where guestID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, guest.getName());
            ps.setTimestamp(2,guest.getVisitDate());
            ps.setString(3,guest.getVisitReason());
            ps.setBoolean(4, guest.isDeleted());
            ps.setString(5, guest.getGuestID());
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}