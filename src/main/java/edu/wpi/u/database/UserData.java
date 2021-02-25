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
    public void updUser(User user, LinkedList<Parameters> updParams){

    }
}
