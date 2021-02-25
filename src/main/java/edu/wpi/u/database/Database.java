package edu.wpi.u.database;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class Database {

    private static Connection conn = null;
    private final static String url = "jdbc:derby:BWdb;create=true;dataEncryption=true;encryptionAlgorithm=Blowfish/CBC/NoPadding;bootPassword=bwdbpassword";

    public Database() {
        driver();
        connect();
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
                        "create table Nodes (nodeID varchar(50) not null, xcoord int, ycoord int, floor varchar(50), building varchar(50), nodeType varchar(4), longName varchar(50), shortName varchar(20), teamAssigned varchar(50), primary key (nodeID))";

                PreparedStatement ps1 = conn.prepareStatement(tbl1);
                ps1.execute();
                String tbl2 =
                        "create table Edges (edgeID varchar(50) not null, startID varchar(50), endID varchar(50), primary key(edgeID))";
                PreparedStatement ps2 = conn.prepareStatement(tbl2);
                ps2.execute();

                String tbl3 =
                        "create table Requests (requestID varchar(50) not null , dateCreated date, dateCompleted date,description varchar(200),title varchar(50),type varchar(50),  primary key(requestID))";
                PreparedStatement ps3 = conn.prepareStatement(tbl3);
                ps3.execute();

                String tbl4 = "create table Assignments(assignmentID varchar(50) not null, requestID varchar(50) references Requests, userID varchar(50), primary key(assignmentID))";
                PreparedStatement ps4 = conn.prepareStatement(tbl4);
                ps4.execute();

                String tbl5 = "create table Locations(locationID varchar(50) not null, requestID varchar(50) references Requests, nodeID varchar(50) references Nodes, primary key(locationID))";
                PreparedStatement ps5 = conn.prepareStatement(tbl5);
                ps5.execute();

            }
        } catch (SQLException e) {
            System.out.println("Table creation failed");
            e.printStackTrace();
        }
    }

    public void printRequests() {
        try {
            String str = "select * from Requests";
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                String id = rset.getString("requestID");
                System.out.println("Request id: " + id);
            }
            rset.close();
        } catch (Exception e) {
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
            str = "drop table Assignments";
            s.execute(str);
            str = "drop table Locations";
            s.execute(str);
            str = "drop table Requests";
            s.execute(str);
        }
        catch (Exception e){
           // e.printStackTrace();
        }
    }

    public void dropValues() {
        //System.out.println("here2");
        try {
            Statement s = conn.createStatement();
            String str = "alter table Locations drop column nodeID";
            s.execute(str);
            str = "alter table Locations drop column requestID";
            s.execute(str);
            str = "alter table Assignments drop column requestID";
            s.execute(str);
            str = "delete from Nodes";
            s.execute(str);
            str = "delete from Edges";
            s.execute(str);
            str = "delete from Requests";
            s.execute(str);
            str = "delete from Locations";
            s.execute(str);
            str = "delete from Assignments";
            s.execute(str);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
    }

    public void stop() {
        dropValues();
        deleteTables();
        try{
            //conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
