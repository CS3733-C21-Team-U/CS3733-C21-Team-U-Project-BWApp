package edu.wpi.u;

import static org.junit.Assert.*;

import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.DatabaseManager;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseTest {

    DatabaseManager dm = new DatabaseManager("jdbc:derby:testDB;create=true");

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
        Node n = new Node("Test", 5, 10, 3, "test", "test", "test", "test", "u");
        try {
            String str = "select * from Nodes";
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String nodeID = rs.getString("nodeID");
                int xcoord = rs.getInt("xcoord");
                int ycoord = rs.getInt("ycoord");
                int floor = rs.getInt("floor");
                String building = rs.getString("building");
                String nodeType = rs.getString("nodeType");
                String longName = rs.getString("longName");
                String shortName = rs.getString("shortName");
                String teamAssigned = rs.getString("teamAssigned");
                Node test = new Node(nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName,teamAssigned);
                assertEquals(n,test);
            }
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

