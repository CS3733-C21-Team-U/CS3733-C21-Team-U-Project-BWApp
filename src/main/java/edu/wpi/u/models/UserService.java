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

    static UserData ud = new UserData();
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Guest> guests = new ArrayList<>();
    User activeUser = new User();
    //TODO : Add getEmps, getGuests
    public UserService() {
        this.users = ud.loadUsers();
    }
    
    public void setUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public User getActiveUser() {
        return this.activeUser;
    }
    // TODO :Add getters and setters for lists of emps and guests
    public ArrayList<User> getUsers(){
        return this.users;
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
        this.users = ud.loadUsers();
    }

    public void saveCSVFile(String path, String tableName){
        Database.getDB().saveCSV(tableName,path , "User"); // TODO: Provide header
    }

    /**
     * Adds an employee to list and calls databsase
     * @param name
     * @param userName
     * @param password
     * @param type
     * @param deleted
     * @param phoneNumber
     * @param email
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
     * 
     * @param name
     * @param userName
     * @param password
     * @param email
     * @param type
     * @param phoneNumber
     * @param appointmentDate
     * @param deleted
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
     * @param employeeID
     * @return
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
