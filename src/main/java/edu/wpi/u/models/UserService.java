package edu.wpi.u.models;

import edu.wpi.u.database.Database;
import edu.wpi.u.database.UserData;
import edu.wpi.u.requests.Request;
import edu.wpi.u.users.Employee;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.StaffType;
import edu.wpi.u.users.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;



public class UserService {

  //  Guest currentGuest = ud.getGuests().get(App.lastClickedGuestNumber);

    static UserData ud = new UserData();
    //ArrayList<User> users = new ArrayList<>();
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Guest> guests = new ArrayList<>();
    User activeUser = new User();
    //TODO : Add getEmps, getGuests
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
     * @param type Employees or Guests (table name)
     */
    public void setUser(String username, String password, String type) {
        this.activeUser = ud.setUser(username,password,type);
    }

    public User getActiveUser() {
        return this.activeUser;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Guest> getGuests() {
        return guests;
    }

    public void loadCSVFile(String path, String tableName){
        Database.getDB().dropValues();
        Database.getDB().readCSV(path,tableName);
        this.setGuests();
        this.setEmployees();
    }

    public void saveCSVFile(String path, String tableName){
        Database.getDB().saveCSV(tableName,path , "User"); // TODO: Provide header
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

    public String getPassword(String userID, String type){
        return ud.getPassword(userID, type);
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
     * Adds an employee to list and calls database
     * @param name the name
     * @param userName the username
     * @param password the password
     * @param type the type (Stafftype)
     * @param deleted whether or not the user is deleted
     * @param phoneNumber the phonenumber
     * @param email the email
     */
    //employeeID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phoneNumber varchar(100), deleted boolean
    public void addEmployee(String name, String userName, String password, String email, StaffType type, String phoneNumber, boolean deleted){
        Random rand = new Random();
        int employeeID = rand.nextInt();
        String id = Integer.toString(employeeID);
        //"employeeID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), employed boolean, deleted boolean
        Employee newEmployee = new Employee(id,name,userName,password,email, type, phoneNumber, deleted);
        ud.addEmployee(newEmployee);
        this.employees.add(newEmployee);
    }

    /**
     * Adds an guest to list and calls database
     * @param name the name
     * @param userName the username
     * @param password the password
     * @param email the email
     * @param type the type (Stafftype)
     * @param phoneNumber the phonenumber
     * @param appointmentDate the appointment date
     * @param deleted whether or not the user is deleted
     */
    public void addGuest(String name, String userName, String password, String email, StaffType type, String phoneNumber, Date appointmentDate, boolean deleted){
        Random rand = new Random();
        int employeeID = rand.nextInt();
        String id = Integer.toString(employeeID);
        //"employeeID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), employed boolean, deleted boolean
        Guest newGuest = new Guest(id,name,userName,password,email, type, phoneNumber, appointmentDate, deleted);
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
    public String updateEmployee(String employeeID, String name, String userName, String password, String email, StaffType type, String phoneNumber, boolean deleted){
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
     * @param userName the username
     * @param password the password
     * @param email the email
     * @param type the type (StaffType)
     * @param phoneNumber the phone number
     * @param appointmentDate the appointment date
     * @param deleted whether or not the user is deleted
     * @return "" on success and the id on failure
     */
    public String updateGuest(String guestID, String name, String userName, String password, String email, StaffType type, String phoneNumber, Date appointmentDate, boolean deleted){
        for(Guest g : this.guests){
            if(g.getUserID().equals(guestID)){
                g.editGuest(name, userName ,password,email,type, phoneNumber, appointmentDate, deleted);
                ud.updGuest(g);
                return "";
            }
        }
        return guestID;
    }
}
