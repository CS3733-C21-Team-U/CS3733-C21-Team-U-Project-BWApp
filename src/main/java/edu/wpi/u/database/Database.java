package edu.wpi.u.database;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class Database {

    private static Connection conn = null;
    private final static String url = "jdbc:derby:BWdb;create=true;dataEncryption=true;encryptionAlgorithm=Blowfish/CBC/NoPadding;bootPassword=bwdbpassword";
    /*
    connect 'jdbc:derby:myEncryptedDatabaseName;create=true;
    dataEncryption=true;encryptionAlgorithm=Blowfish/CBC/NoPadding;
    bootPassword=mySuperSecretBootPassword';

    connect 'jdbc:derby:myEncryptedDatabaseName;
    bootPassword=mySuperSecretBootPassword';
     */
    public Database() {
        driver();
        connect();
        deleteTables();
        init();
    }

    public static void driver() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        } catch (Exception e) {
            System.out.println("Driver registration failed");
            e.printStackTrace();
        }
    }

    public static void connect() {
        try {
            conn = DriverManager.getConnection(url);
            //conn.setAutoCommit(false);
            //conn.commit();
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    public static void init() { //TODO : Rename to createTables()
        try {
            if (isTableEmpty()) {
                String tbl1 =
                        "create table Nodes (nodeID varchar(50) not null, xcoord int, ycoord int, floor int , building varchar(50), nodeType varchar(4), longName varchar(50), shortName varchar(20), teamAssigned varchar(50), primary key (nodeID))";

                PreparedStatement ps1 = conn.prepareStatement(tbl1);
                ps1.execute();
                String tbl2 =
                        "create table Edges (edgeID varchar(50) not null, startID varchar(50), endID varchar(50), primary key(edgeID))";
                PreparedStatement ps2 = conn.prepareStatement(tbl2);
                ps2.execute();

                String tbl3 =
                        "create table Requests (requestID varchar(50) not null , dateCreated date, dateCompleted date,description varchar(200),title varchar(50),location varchar(50),type varchar(50),  primary key(requestID))";
                PreparedStatement ps3 = conn.prepareStatement(tbl3);
                ps3.execute();

                String tbl4 = "create table Assignees(assigneeID varchar(50) not null, name varchar(50), primary key(assigneeID))";
                PreparedStatement ps4 = conn.prepareStatement(tbl4);
                ps4.execute();

                String tbl5 = "create table RANJoint(requestID varchar(50) not null , assigneeID varchar(50), nodeID varchar(50) not null)";
                PreparedStatement ps5 = conn.prepareStatement(tbl5);
                ps5.execute();

            }
        } catch (SQLException e) {
            System.out.println("Table creation failed");
            e.printStackTrace();
        }
    }

    public static boolean isTableEmpty() {
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, "APP", "NODES", null);
            return !rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deleteTables() {
        try {
            String str = "drop table Nodes";
            Statement s = conn.createStatement();
            s.execute(str);
            str = "drop table Edges";
            s.execute(str);
            str = "drop table Requests";
            s.execute(str);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
