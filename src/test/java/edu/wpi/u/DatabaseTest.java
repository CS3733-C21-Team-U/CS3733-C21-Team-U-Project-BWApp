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

    static DatabaseManager dm = new DatabaseManager("jdbc:derby:testDB;create=true");

    public DatabaseTest() {
    }
    @Test
    private boolean testAddNode(){
        Node n = new Node("Test", 5, 10, 3, "test", "test", "test", "test", "u");
        dm.addNode("Test", 5, 10, 3, "test", "test", "test", "test");
        assertEquals(dm.getNode("Test"), n);
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

