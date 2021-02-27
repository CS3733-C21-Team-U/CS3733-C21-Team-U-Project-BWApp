package edu.wpi.u.database;

import edu.wpi.u.users.User;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedList;

public class UserData extends Data{

    public UserData (){
        connect();
    }

    public ArrayList<User> loadUsers(){
        return null;
    }

    /**
     * Adds a user to the table Users
     * @param user the object containing all the information on the user
     */
    public void addUser(User user){
        String str = "insert into Users (userID, name, accountName, password, type, employed) values (?,?,?,?,?,?)";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,user.getUserID());
            ps.setString(2,user.getName());
            ps.setString(3, user.getAccountName());
            ps.setString(4, user.getPassword());
            ps.setString(5,String.valueOf(user.getType()));// StaffType.valueOf(string) to get ENUM type
            ps.setBoolean(6,true);
            ps.execute();
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Marks a user as deleted by setting the employed field to false
     * @param user the object containing all of the information on the user
     */
    public void delUser(User user){
        String str ="update Users set empoloyed=? where userID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setBoolean(1,false);
            ps.setString(2,user.getUserID());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Will update the field of a user in the database
     * @param user
     * @param updParams
     */
    public void updUser(User user){
        String str = "update Users set name=? and accountName=? and password=? and type=? and employed=? where userID=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, user.getName());
            ps.setString(2, user.getAccountName());
            ps.setString(3, user.getPassword());
            ps.setString(4, String.valueOf(user.getType()));
            ps.setBoolean(5,user.getEmployed());
            ps.setString(6,user.getUserID());
            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
