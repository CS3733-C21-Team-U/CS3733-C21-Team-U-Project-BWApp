package edu.wpi.u;

import static org.junit.Assert.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseTest {
    private static Connection conn = null;
    private static String url = "jdbc:derby:testDB;create=true";

    public DatabaseTest() {
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
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    public static void create(){
        try {
            String tbl1 =
                    "create table Nodes (nodeID varchar(50) not null, xcoord int, ycoord int, floor int , building varchar(50), nodeType varchar(4), longName varchar(50), shortName varchar(20), teamAssigned varchar(50), primary key (nodeID))";
            // code for creating table of Museums
            PreparedStatement ps1 = conn.prepareStatement(tbl1);
            ps1.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    private boolean testAddNode(){
        try {
            String str = "select * from Nodes";
            PreparedStatement ps = conn.prepareStatement(str);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Test
    private boolean testUpdNodeFloor(){
        return true;
    }
    @Test
    private boolean testUpdNodeCoords(){
        return true;
    }

    @Test
    private boolean testUpdNodeBuilding(){
        return true;
    }



}

