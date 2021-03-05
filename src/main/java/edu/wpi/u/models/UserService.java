package edu.wpi.u.models;

import edu.wpi.u.database.Database;
import edu.wpi.u.database.UserData;
import edu.wpi.u.users.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class UserService {

  //  Guest currentGuest = ud.getGuests().get(App.lastClickedGuestNumber);

    static UserData ud = new UserData();
    //ArrayList<User> users = new ArrayList<>();
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Guest> guests = new ArrayList<>();
    ArrayList<Patient> patients = new ArrayList<>();
    User activeUser; //= new Employee("testid", "testname", "testusername", "testpassword", "testemail",  Role.MAINTENANCE, "9118332323", false);
    //TODO : Add getEmployees, getGuests
    public UserService() {
        this.setEmployees();
        this.setGuests();
    }

    public void setEmployees() {this.employees = ud.getEmployees();}

    public void setGuests() {this.guests = ud.getGuests();}

    /**
     * Sets the active user of the application
     * @param username username of the user
     * @param password password of the user
     * @param tableName Employees or Guests (table name)
     */
    public void setUser(String username, String password, String tableName) {
        if (tableName.equals("Employees")){
            this.activeUser = ud.setEmployee(username, password);
        }
        else if (tableName.equals("Patients")){
            this.activeUser = ud.setPatient(username, password);
        }
        else {
            this.activeUser = ud.setGuest(username);
        }
    }

    /**
     * Gets an instance of the active user
     * @return active user
     */
    public User getActiveUser() {
        return this.activeUser;
    }

    public ArrayList<Patient> getPatients(){
        return patients;
    }

    /**
     * Gets a list of all of the employees
     * @return list of employees
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Gets a list of all the guests
     * @return list of guests
     */
    public ArrayList<Guest> getGuests() {
        return guests;
    }

    /**
     * Loads the CSV file into the table
     * @param path the path to the file
     * @param tableName the table to be loaded into
     */
    public void loadCSVFile(String path, String tableName){
        Database.getDB().dropValues(tableName);
        Database.getDB().readCSV(path,tableName);
        this.setGuests();
        this.setEmployees();
    }

    /**
     * Saves the CSV file to the path
     * @param path the path to the file
     * @param tableName the table to be saved
     */
    public void saveCSVFile(String path, String tableName){
        Database.getDB().saveCSV(tableName,path , "User"); // TODO: Provide header
    }

    public void changePhoneNumer(String userID, String newPhoneNumber, String type){
        this.getActiveUser().setPhoneNumber(newPhoneNumber);
        ud.changePhoneNumber(userID,newPhoneNumber, type);
    }

    /**
     * Changes the email of the user
     * @param userID id of the user
     * @param newEmail the new email
     * @param type Employees or Guests (table name)
     */
    public void changeEmail(String userID, String newEmail, String type){
        this.getActiveUser().setEmail(newEmail);
        ud.changeEmail(userID,newEmail,type);
    }

    /**
     * Changes the password of the user
     * @param username username of the user
     * @param newPassword the new password
     * @param type Employees or Guests (table name)
     */
    public void changePassword(String username, String newPassword, String type){
        this.getActiveUser().setPassword(newPassword);
        ud.changePassword(username,newPassword, type);
    }

    /**
     * Validates a username
     * @param username the username to be validated
     * @return Employees or Guests (table name)
     */
    public String checkUsername(String username) {
        System.out.println("Value of check username: " + ud.checkUsername(username));
        return ud.checkUsername(username);
    }

    /**
     * Validates a password
     * @param password the password to be validated
     * @return Employees or Guests (table name)
     */
    public String checkPassword(String password) {
        return ud.checkPassword(password);
    }

    /**
     * Validates the phone number of a given username
     * @param username the username to be validated
     * @return the phonenumber of the username
     */
    public String checkPhoneNumber(String username) {
        return ud.checkPhoneNumber(username);
    }

    /**
     *  Gets the password of the user
     * @param userID id of the user
     * @param type position of user
     * @return the password of the user
     */
    public String getPassword(String userID, String type){
        return ud.getPassword(userID, type);
    }

    /**
     * Adds an employee to list and calls database
     * @param name the name
     * @param userName the username
     * @param password the password
     * @param type the type (Stafftype)
     * @param deleted whether or not the user is deleted
     * @param phoneNumber the phonenumber
     * @param email the email
     */
    public void addEmployee(String name, String userName, String password, String email, Role type, String phoneNumber, String locationNodeID, boolean deleted){
        Random rand = new Random();
        int employeeID = rand.nextInt();
        String id = Integer.toString(employeeID);
        //(userID, name, accountName, password, email, type, phoneNumber, locationNodeID, deleted)
        Employee newEmployee = new Employee(id,name,userName,password,email, type, phoneNumber, locationNodeID, deleted);
        ud.addEmployee(newEmployee);
        this.employees.add(newEmployee);
    }

    /**
     * Adds an guest to list and calls database
     * @param name the name
     * @param deleted whether or not the user is deleted
     */
    public void addGuest(String name, LocalDate visitDate, String visitReason, boolean deleted){
        Random rand = new Random();
        int employeeID = rand.nextInt();
        String id = Integer.toString(employeeID);
        Guest newGuest = new Guest(); // TODO : FIX
        ud.addGuest(newGuest);
        this.guests.add(newGuest);
    }

    /**
     * Removes employee from list and calls database
     * @param employeeID id of employee
     * @return empty string if success, employeeID on failure
     */
    public String deleteEmployee(String employeeID) {
        for (Employee e : this.employees) {
            if (e.getUserID().equals(employeeID)) {
                this.employees.remove(e);
                //this.users.remove(e);
                ud.delEmployee(e);
                return "";
            }
            return "";
        }
        return employeeID;
    }

    /**
     * Removes guest from list and calls database
     * @param guestID id of guest
     * @return empty string if success, guestID on failure
     */
    public String deleteGuest(String guestID) {
        for (Guest g : this.guests) {
            if (g.getUserID().equals(guestID)) {
                this.guests.remove(g);
                //this.users.remove(g);
                ud.delGuest(g);
                return "";
            }
            return "";
        }
        return guestID;
    }

    /**
     * Updates the list of employees and calls database
     * @param employeeID the id
     * @param name the name
     * @param userName the username
     * @param password the password
     * @param email the email
     * @param type the type (Stafftype)
     * @param phoneNumber the phonenumber
     * @param deleted whether or not the user is deleted
     * @return "" on success and the id on failure
     */
    public String updateEmployee(String employeeID, String name, String userName, String password, String email, Role type, String phoneNumber, boolean deleted){
        for(Employee e : this.employees){
            if(e.getUserID().equals(employeeID)){
                e.editUser(name, userName ,password,email,type, phoneNumber, deleted);
                ud.updEmployee(e);
                return "";
            }
        }
        return employeeID;
    }

    /**
     * Updates the list of guests and calls database
     * @param guestID the id
     * @param name the name
     * @param visitDate the visit date
     * @param visitReason the visit reason
     * @param deleted whether or not the user is deleted
     * @return "" on success and the id on failure
     */
    public String updateGuest(String guestID, String name, LocalDate visitDate, String visitReason, boolean deleted){
        for(Guest g : this.guests){
            if(g.getUserID().equals(guestID)){
                g.editGuest(name, visitDate, visitReason, deleted);
                ud.updGuest(g);
                return "";
            }
        }
        return guestID;
    }
}
