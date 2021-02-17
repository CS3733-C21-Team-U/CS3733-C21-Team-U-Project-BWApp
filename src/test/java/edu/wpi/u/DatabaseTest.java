package edu.wpi.u;

import static org.junit.Assert.*;
import org.junit.Test;

public class DatabaseTest {

    private static String url = "jdbc:derby:testDB;create=true";

    public DatabaseTest() {
    }
    @Test
    private boolean testAddNode(){
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
    private boolean testUpdNodeBuilding(){
        return true;
    }

}
}
