package edu.wpi.u.database;

import java.sql.*;

public abstract class Data {
    protected Connection conn = null;
    protected ResultSet rset;
    protected String url = "jdbc:derby:BWdb;bootPassword=bwdbpassword";
    protected static Database db;

    public Data(){

    }

    /**
     * Creates the connection to the database by mounting the driver
     */
    public void connect() {
        try {
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }
    public boolean updateField(String tableName, String idField, String id, String field, String val) {
        try {
            String str = "update " + tableName + " set" + field + "=? where " + idField + "=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, val);
            ps.setString(2, id);
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
            String str = "update " + tableName + " set" + field + "=? where " + idField + "=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setInt(1, val);
            ps.setString(2, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update " + tableName + "'s " + field );
            return false;
        }
        return true;
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

/*
move readCSV to database
move saveCSV to database
 */
}
