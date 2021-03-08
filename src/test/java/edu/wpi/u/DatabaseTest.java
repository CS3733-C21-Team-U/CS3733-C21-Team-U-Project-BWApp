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
    private static MapService mapServiceTest = new MapService(testURL);
    private static RequestService requestServiceTest = new RequestService(testURL);
    private static UserService userServiceTest = new UserService(testURL);
    private Node node1 = new Node("TESTNODE1", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");
    private Node node2 = new Node("TESTNODE2", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");
    private Node node3 = new Node("TESTNODE3", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");
    private Node node4 = new Node("TESTNODE4", 100, 100, "1", "Faulkner", "HALL", "LOOOOONG", "SHORT","U");

    public static void main(String[] args) throws InvalidEdgeException {
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

        mapServiceTest.addNode(node1.getNodeID(),node1.getCords()[0],node1.getCords()[1], node1.getFloor(),node1.getBuilding(),node1.getNodeType(), node1.getLongName(), node1.getShortName());
        mapServiceTest.addNode(node2.getNodeID(),node2.getCords()[0],node2.getCords()[1], node2.getFloor(),node2.getBuilding(),node2.getNodeType(), node2.getLongName(), node2.getShortName());
        mapServiceTest.addNode(node3.getNodeID(),node3.getCords()[0],node3.getCords()[1], node3.getFloor(),node3.getBuilding(),node3.getNodeType(), node3.getLongName(), node3.getShortName());
        mapServiceTest.addNode(node4.getNodeID(),node4.getCords()[0],node4.getCords()[1], node4.getFloor(),node4.getBuilding(),node4.getNodeType(), node4.getLongName(), node4.getShortName());

        userServiceTest.addEmployee("PersonName1", "UserName1", "password1", "email1", Role.ADMIN, "1111111111", "TEST1",false);
        userServiceTest.addEmployee("PersonName2", "UserName2", "password2", "email2", Role.MAINTENANCE, "2222222222", "TEST2",false);
        userServiceTest.addEmployee("PersonName3", "UserName3", "password", "email3", Role.ADMIN, "3333333333", "TEST3",false);

        requestServiceTest.addRequest(result);
        ArrayList<SpecificRequest> testRequests = requestServiceTest.getRd().loadActiveRequests();

    }

    @Test
    public void testNodeFromID() throws InvalidEdgeException {
//        MapService mapServiceTest1 = new MapService(testURL);
//        mapServiceTest1.addNode(node1.getNodeID(),node1.getCords()[0],node1.getCords()[1], node1.getFloor(),node1.getBuilding(),node1.getNodeType(), node1.getLongName(), node1.getShortName());
//        assertTrue(mapServiceTest1.getNodeFromID(node1.getNodeID()).equals(node1));
    }

    @Test
    public void testEdgeFromID(){}

    @Test
    public void testAddNode1(){}

    @Test
    public void testAddNode2(){}

    @Test
    public void testAddNodeWithID(){}

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
