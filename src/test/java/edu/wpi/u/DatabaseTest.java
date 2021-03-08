package edu.wpi.u;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.database.Data;
import edu.wpi.u.database.Database;
import edu.wpi.u.exceptions.InvalidEdgeException;
import edu.wpi.u.models.*;
import edu.wpi.u.requests.*;
import edu.wpi.u.users.Role;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatabaseTest {

    private static String testURL = "jdbc:derby:testDB";

    private static Database dbTest = Database.getDBTest();
    private static MapService mapServiceTestStaging = new MapService(testURL);
    private static RequestService requestServiceTest = new RequestService(testURL);
    private static UserService userServiceTest = new UserService(testURL);

//    private Node node1 = new Node("TESTNODE1", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");
//    private Node node2 = new Node("TESTNODE2", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");
//    private Node node3 = new Node("TESTNODE3", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");
//    private Node node4 = new Node("TESTNODE4", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");


    public void main(String[] args) throws InvalidEdgeException {
        // Permissions for testing
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.ADMIN);

        // Nodes for testing
        Node node1 = new Node("TESTNODE1", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");
        Node node2 = new Node("TESTNODE2", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");
        Node node3 = new Node("TESTNODE3", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");
        Node node4 = new Node("TESTNODE4", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");

        // Edges for testing
        Edge edge1 = new Edge("TESTNODE1_TESTNODE2", node1, node2, roles);
        Edge edge2 = new Edge("TESTNODE3_TESTNODE4", node3, node4, roles);

        // Locations for testing
        ArrayList<String> locations = new ArrayList<String>();
        locations.add("TEST1");
        locations.add("Test2");

        // Date for testing
        Date testDate = new GregorianCalendar(2022, 1, 1).getTime();

        // Stafflist for testing
        ArrayList<String> staffList = new ArrayList<String>();
        staffList.add("PersonName1"); // TODO: does the list of staff represent their names or id's (auto generated)

        // Comment for testing
        Comment testComment = new Comment("Comment 1", "This comment is for testing purposes", "PersonName1", CommentType.PRIMARY, new Timestamp(testDate.getTime()));


        // Request for testing
        ArrayList<String> specificFields = new ArrayList<>();
        specificFields.add("Machine 1");
        specificFields.add("High"); // Is this a valid string for priority??
        SpecificRequest result = new RequestFactory().makeRequest("Maintenance");
        Request newRequest = new Request("TestRequest1", new Timestamp(System.currentTimeMillis()), locations, staffList, testComment);
        result.setRequest(newRequest);
        result.setSpecificData(specificFields);

        mapServiceTestStaging.addNode(node1.getNodeID(),node1.getCords()[0],node1.getCords()[1], node1.getFloor(),node1.getBuilding(),node1.getNodeType(), node1.getLongName(), node1.getShortName());
        mapServiceTestStaging.addNode(node2.getNodeID(),node2.getCords()[0],node2.getCords()[1], node2.getFloor(),node2.getBuilding(),node2.getNodeType(), node2.getLongName(), node2.getShortName());
        mapServiceTestStaging.addNode(node3.getNodeID(),node3.getCords()[0],node3.getCords()[1], node3.getFloor(),node3.getBuilding(),node3.getNodeType(), node3.getLongName(), node3.getShortName());
        mapServiceTestStaging.addNode(node4.getNodeID(),node4.getCords()[0],node4.getCords()[1], node4.getFloor(),node4.getBuilding(),node4.getNodeType(), node4.getLongName(), node4.getShortName());

        userServiceTest.addEmployee("PersonName1", "UserName1", "password1", "email1", Role.ADMIN, "1111111111", "TEST1",false);
        userServiceTest.addEmployee("PersonName2", "UserName2", "password2", "email2", Role.MAINTENANCE, "2222222222", "TEST2",false);
        userServiceTest.addEmployee("PersonName3", "UserName3", "password", "email3", Role.ADMIN, "3333333333", "TEST3",false);

        requestServiceTest.addRequest(result);
        ArrayList<SpecificRequest> testRequests = requestServiceTest.getRd().loadActiveRequests();

    }

    @Test
    public void testNodeFromID() throws InvalidEdgeException {
        Node node1 = new Node("TESTNODE1", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNode(node1.getNodeID(),node1.getCords()[0],node1.getCords()[1], node1.getFloor(),node1.getBuilding(),node1.getNodeType(), node1.getLongName(), node1.getShortName());
        assertEquals(mapServiceTest.getNodeFromID(node1.getNodeID()).getNodeID(),node1.getNodeID());
    }

    @Test
    public void testEdgeFromID() throws InvalidEdgeException {
        MapService mapServiceTest = new MapService(testURL);
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.ADMIN);
        mapServiceTest.addNodeWithID("UWALK00401", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("UWALK00501", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addEdge("UWALK00401_UWALK00501", "UWALK00401", "UWALK00501", roles);
        assertEquals(mapServiceTest.getEdgeFromID("UWALK00401_UWALK00501").getEdgeID(),"UWALK00401_UWALK00501");
    }

    @Test
    public void testAddNode1() throws InvalidEdgeException {
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST3", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        assertEquals(mapServiceTest.getNodeFromID("NODETEST3").getNodeID(),"NODETEST3"); // Node was found in db/data structure
    }

    @Test
    public void testAddNode2() throws InvalidEdgeException {
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNode(1,1,"1","Faulkner", "TEST","longname","shortname");
        assertEquals(mapServiceTest.getNodeFromID("UTEST00101").getNodeID(),"UTEST00101"); // Node was found in db/data structure
    }

    @Test
    public void testAddNodeWithID() throws InvalidEdgeException {
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST4", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        assertEquals(mapServiceTest.getNodeFromID("NODETEST4").getNodeID(),"NODETEST4"); // Node was found in db/data structure
    }

    @Test
    public void testUpdateNode(){}

    @Test
    public void testDeleteNode(){}

    @Test
    public void testAddEdge(){}

    @Test
    public void testUpdateEdgePermissions(){}

    @Test
    public void testUpdateStartEdge(){}

    @Test
    public void testUpdateEndEdge(){}

    @Test
    public void testDeleteEdge(){}

    @Test
    public void testGetNodes(){}

    @Test
    public void testGetEdges(){}



}
