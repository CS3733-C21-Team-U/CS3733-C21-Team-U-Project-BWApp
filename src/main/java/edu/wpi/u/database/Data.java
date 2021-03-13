package edu.wpi.u.database;

import java.sql.*;
import java.util.LinkedList;

public abstract class Data {
    protected Connection conn = null;
    protected ResultSet rset;
    //protected String url = "jdbc:derby:BWdb;bootUser=admin;bootPassword=bwdbpassword";
    protected String url = "jdbc:derby:BWdb";
    protected String testUrl = "jdbc:derby://localhost:1527/BWdb";

    protected static Database db;

    public Data(){

    }

    /**
     * Creates the connection to the database by mounting the driver
     */
    public void connect() {
        try {
            conn = DriverManager.getConnection(testUrl);
        } catch (Exception e) {
            try {
                conn = DriverManager.getConnection(url);
            }catch (Exception d){
                d.printStackTrace();
                System.out.println("Connection failed");
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates the connection to the database with given URL (necessary for testing db)
     * @param testURL
     */
    public void testConnect(String testURL) {
        try {
            conn = DriverManager.getConnection(testURL);
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    public boolean updateField(String tableName, String idField, String id, String field, String val) {
        try {
            String str = "update "+tableName+" set "+field+"='"+val+"' where "+idField+"='"+id+"'";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update " + tableName + "'s " + field );
            return false;
        }
        return true;
    }

    public boolean updateField(String tableName, String idField, String id, String field, int val) {
        try {
            String str = "update "+tableName+" set "+field+"="+val+" where "+idField+"="+id;
            PreparedStatement ps = conn.prepareStatement(str);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update " + tableName + "'s " + field );
            return false;
        }
        return true;
    }

    public int addToJoinTable(String tableName, String id1, String id2) {
        try {
            System.out.println("Edges are being added to the Database");
            String str = "insert into "+tableName+" (edgeId, startID, endID) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, id1+"_"+id2);
            ps.setString(2, id1);
            ps.setString(3, id2);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add edge");
            return 0;
        }
        return 1;
    }

    public void updJoinedList(String searchIDName, String joinTable, String searchID, String addID, LinkedList<String> newList){
        String str = "delete from "+joinTable+" where "+searchIDName+"=?";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, searchID);
            ps.execute();
            for (String node : newList) {
                addToJoinTable(joinTable, searchID, addID);
            }
            ps.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printTableItem(String tableName, String columnName) {
        try {
            String str = "select * from " + tableName;
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                String id = rset.getString(columnName);
                System.out.println(columnName + ": " + id);
            }
            rset.close();
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
        }
    }

}
