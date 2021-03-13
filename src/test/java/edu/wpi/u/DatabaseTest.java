package edu.wpi.u;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.database.Data;
import edu.wpi.u.database.Database;
import edu.wpi.u.exceptions.InvalidEdgeException;
import edu.wpi.u.models.*;
import edu.wpi.u.requests.*;
import edu.wpi.u.users.Appointment;
import edu.wpi.u.users.Role;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatabaseTest {
    /**
     * RUN TESTS INDIVIDUALLY
     * When they are all run at the same time, some WILL fail because of how they are accessed
     * All tests in this class HAVE been tested indiviudally and should be working as described
     * DELETE "testDB" file after every run (under resources above .gitignore)
     */

    private static String testURL = "jdbc:derby:testDB";
    private static Database dbTest = Database.getDBTest();
    //public static Database db = Database.getDB();

    // Only used for live staging
    private static MapService mapServiceTestStaging = new MapService(testURL);
    private static RequestService requestServiceTest = new RequestService(testURL);
    private static UserService userServiceTest = new UserService(testURL);

    /**
     * Main - Uncomment and run to do any live testing, NOT NEEDED FOR UNIT TESTS
     * @throws InvalidEdgeException
     */
    /*
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

    */


    @After
    public void clearDB() {
        dbTest.dropAllValues();
    }

    /**
     * MapService Testing
     * Constraints:
     * For every edge added, it needs to consist of 2 valid nodes (in the Service), doing otherwise will error the code
     * Do not delete a node or edge that doesn't exist - AssertionError
     */

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
        mapServiceTest.addNodeWithID("UWALK00401", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("UWALK00501", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addEdge("UWALK00401_UWALK00501", "UWALK00401", "UWALK00501", Role.ADMIN);
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
    public void testUpdateNode() throws InvalidEdgeException {
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST5", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.updateNode("NODETEST5", 5, 5,"WALK","testy", "testywesty");
        assertEquals(mapServiceTest.getNodeFromID("NODETEST5").getCords()[0],5,0);
    }

    @Test
    public void testUpdateNode2() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST5B", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.updateNode("NODETEST5B", 5, 5,"WALK","testy", "testywesty");
        assertEquals(mapServiceTest.getNodeFromID("NODETEST5B").getCords()[1],5,0);
    }

    @Test
    public void testDeleteNode() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST6", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        assertEquals(mapServiceTest.deleteNode("NODETEST6"), new ArrayList<Edge>());
    }

    @Test
    public void testAddEdge() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST7", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST8", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addEdge("NODETEST7_NODETEST8", "NODETEST7", "NODETEST8", Role.ADMIN);
        assertEquals(mapServiceTest.getEdgeFromID("NODETEST7_NODETEST8").getEdgeID(),"NODETEST7_NODETEST8");
    }

    @Test
    public void testAddEdge2() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST7", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST8", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addEdge("NODETEST7_NODETEST8", "NODETEST7", "NODETEST8", Role.ADMIN);
        assertEquals(mapServiceTest.getEdgeFromID("NODETEST7_NODETEST8").getEdgeID(),"NODETEST7_NODETEST8");
    }

    @Test
    public void testUpdateEdgePermissions() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        MapService mapServiceTest = new MapService(testURL);

        mapServiceTest.addNodeWithID("NODETEST9", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST10", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addEdge("NODETEST9_NODETEST10", "NODETEST9", "NODETEST10", Role.ADMIN);

        mapServiceTest.updateEdgePermissions("NODETEST9_NODETEST10", Role.MAINTENANCE);
        assertEquals(mapServiceTest.getEdgeFromID("NODETEST9_NODETEST10").getUserPermissions(),Role.MAINTENANCE);
    }

    @Test
    public void testUpdateStartEdge() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST11", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST12", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST13", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addEdge("NODETEST11_NODETEST12", "NODETEST11", "NODETEST12", Role.ADMIN);
        mapServiceTest.updateStartEdge("NODETEST11_NODETEST12", "NODETEST13");
        assertEquals(mapServiceTest.getEdgeFromID("NODETEST13_NODETEST12").getEdgeID(), "NODETEST13_NODETEST12");
    }

    @Test
    public void testUpdateEndEdge() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST14", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST15", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST16", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addEdge("NODETEST14_NODETEST15", "NODETEST14", "NODETEST15", Role.ADMIN);
        mapServiceTest.updateEndEdge("NODETEST14_NODETEST15", "NODETEST16");
        assertEquals(mapServiceTest.getEdgeFromID("NODETEST14_NODETEST16").getEdgeID(), "NODETEST14_NODETEST16");
    }

    @Test
    public void testDeleteEdge() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST17", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST18", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addEdge("NODETEST17_NODETEST18", "NODETEST17", "NODETEST18", Role.ADMIN);
        assertEquals(mapServiceTest.deleteEdge("NODETEST17_NODETEST18"),"");
    }

    @Test
    public void testGetNodes() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST20", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST21", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        assertEquals(mapServiceTest.getNodeFromID("NODETEST20").getNodeID(), "NODETEST20");
    }

    @Test
    public void testGetEdges() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST22", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST23", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST24", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.getEdges().clear();
        mapServiceTest.addEdge("NODETEST22_NODETEST23", "NODETEST22", "NODETEST23", Role.ADMIN);
        mapServiceTest.addEdge("NODETEST23_NODETEST24", "NODETEST23", "NODETEST24", Role.ADMIN);
        assertEquals(mapServiceTest.getEdges().get(1).getEdgeID(),"NODETEST23_NODETEST24");
    }

    /**
     * RequestService Testing
     */

    @Test
    public void testAddRequest() {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        RequestService requestServiceTest = new RequestService(testURL);
        Date testDate = new GregorianCalendar(2022, 1, 1).getTime();
        ArrayList<String> staffList = new ArrayList<String>();
        staffList.add("PersonName1");
        Comment testComment = new Comment("Comment 1", "This comment is for testing purposes", "PersonName1", CommentType.PRIMARY, new Timestamp(testDate.getTime()));
        ArrayList<String> locations = new ArrayList<String>();
        locations.add("TEST1");
        ArrayList<String> specificFields = new ArrayList<>();
        specificFields.add("Machine 1");
        specificFields.add("High"); // Is this a valid string for priority??
        SpecificRequest result = new RequestFactory().makeRequest("Maintenance");
        Request newRequest = new Request("TestRequest1", new Timestamp(System.currentTimeMillis()), locations, staffList, testComment);
        result.setRequest(newRequest);
        result.setSpecificData(specificFields);
        requestServiceTest.addRequest(result);
        assertEquals(requestServiceTest.getRequests().get(0).getType(), result.getType());
    }

    @Test
    public void testUpdateRequest() throws InvalidEdgeException {
        dbTest.dropAllValues();
        RequestService requestServiceTest = new RequestService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        Date testDate = new GregorianCalendar(2022, 1, 1).getTime();
        ArrayList<String> staffList = new ArrayList<String>();
        staffList.add("PersonName1");
        ArrayList<String> locations = new ArrayList<String>();
        locations.add("TEST1");
        locations.add("TEST2");
        mapServiceTest.addNodeWithID("TEST1", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("TEST2", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        ArrayList<String> specificFields = new ArrayList<>();
        specificFields.add("Machine 1");
        specificFields.add("High"); // Is this a valid string for priority??

        Comment testComment = new Comment("Comment 1", "This comment is for testing purposes", "PersonName1", CommentType.PRIMARY, new Timestamp(testDate.getTime()));
        SpecificRequest result = new RequestFactory().makeRequest("Maintenance");
        Request newRequest = new Request("TestyWestyRequesty1", new Timestamp(System.currentTimeMillis()), locations, staffList, testComment);
        result.setRequest(newRequest);
        result.setSpecificData(specificFields);
        requestServiceTest.addRequest(result);

        Comment testComment2 = new Comment("Comment 2", "This comment is for testing purposes", "PersonName2", CommentType.PRIMARY, new Timestamp(testDate.getTime()));
        SpecificRequest result2 = new RequestFactory().makeRequest("Maintenance");
        Request newRequest2 = new Request("TestyWestyRequesty1", new Timestamp(System.currentTimeMillis()), locations, staffList, testComment2);
        result2.setRequest(newRequest2);
        result2.setSpecificData(specificFields);
        requestServiceTest.updateRequest(result2);

        //assertEquals(result2.getGenericRequest().getPrimaryComment(), testComment2);
        assertEquals(requestServiceTest.getRequests().get(0).getGenericRequest().getComments().get(1).getAuthor(), testComment2.getAuthor());
    }

    @Test
    public void testResolveRequest() {
        RequestService requestServiceTest = new RequestService(testURL);
        Date testDate = new GregorianCalendar(2022, 1, 1).getTime();
        ArrayList<String> staffList = new ArrayList<String>();
        staffList.add("PersonName1");
        Comment testComment = new Comment("Comment 1", "This comment is for testing purposes", "PersonName1", CommentType.PRIMARY, new Timestamp(testDate.getTime()));
        ArrayList<String> locations = new ArrayList<String>();
        ArrayList<String> specificFields = new ArrayList<>();
        specificFields.add("Machine 1");
        specificFields.add("High"); // Is this a valid string for priority??
        SpecificRequest result = new RequestFactory().makeRequest("Maintenance");
        Request newRequest = new Request("TestRequest1", new Timestamp(System.currentTimeMillis()), locations, staffList, testComment);
        result.setRequest(newRequest);
        result.setSpecificData(specificFields);
        requestServiceTest.addRequest(result);
        Comment testComment2 = new Comment("Comment 2", "XX", "PersonName1", CommentType.PRIMARY, new Timestamp(testDate.getTime()));
        requestServiceTest.resolveRequest(result, testComment2);
        ArrayList <Comment> listOfComments = new ArrayList<>();
        listOfComments.add(testComment);
        listOfComments.add(testComment2);
        ArrayList <Comment> requestComments = newRequest.getComments();
        assertEquals(requestComments, listOfComments );
    }

    @Test
    public void testGetRequest() {
        RequestService requestServiceTest = new RequestService(testURL);
        Date testDate = new GregorianCalendar(2022, 1, 1).getTime();
        ArrayList<String> staffList = new ArrayList<String>();
        staffList.add("PersonName1");
        Comment testComment = new Comment("Comment 1", "This comment is for testing purposes", "PersonName1", CommentType.PRIMARY, new Timestamp(testDate.getTime()));
        ArrayList<String> locations = new ArrayList<String>();
        ArrayList<String> specificFields = new ArrayList<>();
        specificFields.add("Machine 1");
        specificFields.add("High"); // Is this a valid string for priority??
        SpecificRequest result = new RequestFactory().makeRequest("Maintenance");
        SpecificRequest result2 = new RequestFactory().makeRequest("Language");
        Request newRequest = new Request("TestRequest1", new Timestamp(System.currentTimeMillis()), locations, staffList, testComment);
        Request newRequest2 = new Request("TestRequest2", new Timestamp(System.currentTimeMillis()), locations, staffList, testComment);
        result.setRequest(newRequest);
        result2.setRequest(newRequest2);
        result.setSpecificData(specificFields);
        result2.setSpecificData(specificFields);;
        ArrayList<SpecificRequest> testRequests = new ArrayList<>();
        testRequests.add(result);
        testRequests.add(result2);
        requestServiceTest.addRequest(result);
        requestServiceTest.addRequest(result2);
        assertEquals(requestServiceTest.getRequests(), testRequests);
    }

    /**
     * UserService Testing
     * Constraints:
     * A patient must have a valid locationNodeID (stored in the service)
     * A patient must have a valid parkingLocation, and recommendedParkingLocation (should be PARK nodes)
     * Only delete patients/users/guests that actually exist
     * After changing a password (ex: for a patient), you must use setPatients() in order for patients(UserService list) to be updated
     */

    @Test
    public void testAddPatient() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("UPARK0010G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.addPatient("Name1", "Name1Username", "Name1Pass", "Name1Email", Role.PATIENT, "Name1Phone",  false, tempList, "ProviderName", "UPARK0010G", "UPARK0010G");
        assertEquals(userServiceTest.getPatients().get(0).getName(), "Name1");
    }

    // TODO: Can't test anymore?
    /*
    @Test
    public void testAddLocationID() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST25A", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("NODETEST25B", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("UPARK0010G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.getPatients().clear();
        userServiceTest.addPatient("Name1B", "Name1BUsername", "Name1BPass", "Name1BEmail", Role.PATIENT, "Name1BPhone", false, tempList, "ProviderName", "UPARK0010G", "UPARK0010G");
        userServiceTest.addLocationID(userServiceTest.getPatients().get(0).getUserID(), "NODETEST25B");
        userServiceTest.setPatients();
        assertEquals(userServiceTest.getPatients().get(0).getLocationNodeID(), "NODETEST25B");
    }
    */

    @Test
    public void testAddParkingLocation() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        userServiceTest.getPatients().clear();
        mapServiceTest.addNodeWithID("UPARK0070G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        mapServiceTest.addNodeWithID("UPARK0080G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.addPatient("Name1C", "Name1CUsername", "Name1CPass", "Name1CEmail", Role.PATIENT, "Name1CPhone",false, tempList, "ProviderName", "UPARK0070G", "UPARK0010G");
        userServiceTest.addParkingLocation(userServiceTest.getPatients().get(0).getUserID(), "UPARK0080G");
        userServiceTest.setPatients();
        assertEquals(userServiceTest.getPatients().get(0).getParkingLocation(), "UPARK0080G");
    }

    @Test
    public void testAddRecommendedParkingLocation() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        userServiceTest.getPatients().clear();
        mapServiceTest.addNodeWithID("UPARK0050G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        mapServiceTest.addNodeWithID("UPARK0090G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.addPatient("Name1D", "Name1DUsername", "Name1DPass", "Name1DEmail", Role.PATIENT, "Name1DPhone",  false, tempList, "ProviderName", "UPARK0050G", "UPARK0050G");
        userServiceTest.addRecommendedParkingLocation(userServiceTest.getPatients().get(0).getUserID(), "UPARK0080G");
        userServiceTest.setPatients();
        assertEquals(userServiceTest.getPatients().get(0).getRecommendedParkingLocation(), "UPARK0080G");
    }

    @Test
    public void testAddEmployee() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.addEmployee("Name2", "Name2Username", "Name2Pass", "Name2Email", Role.NURSE, "Name2Phone", false);
        assertEquals(userServiceTest.getEmployees().get(0).getName(), "Name2");
    }

    @Test
    public void testAddEmployee2() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("NODETEST26B", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.addEmployee("Name2B", "Name2BUsername", "Name2BPass", "Name2BEmail", Role.GUEST, "Name2BPhone", false);
        assertEquals(userServiceTest.getEmployees().get(0).getType().toString(), "GUEST");
    }

    @Test
    public void testAddGuest() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        ArrayList<Appointment> tempList = new ArrayList<>();
        Timestamp t = new Timestamp(1);
        userServiceTest.addGuest("Name3", t, "For testing... ha ha, I'm tired",false);
        assertEquals(userServiceTest.getGuests().get(0).getName(), "Name3");
    }

    @Test
    public void testAddGuest2() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        ArrayList<Appointment> tempList = new ArrayList<>();
        Timestamp t = new Timestamp(1);
        userServiceTest.addGuest("Name3B", t, "For testing... ha ha, I'm tired",true); // can still be accessed while deleted
        assertEquals(userServiceTest.getGuests().get(0).getName(), "Name3B");
    }

    @Test
    public void testChangePhoneNumberPatient() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("UPARK0020G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.getPatients().clear();
        userServiceTest.addPatient("TotallyUniqueName", "TotallyUniqueName", "TotallyUniquePass", "TotallyUniqueEmail", Role.PATIENT, "TotallyUniquePhone",  false, tempList, "ProviderName", "UPARK0020G", "UPARK0020G");
        userServiceTest.setUser("TotallyUniqueName", "TotallyUniquePass", "Patients");
        userServiceTest.changePhoneNumber(userServiceTest.getPatients().get(0).getUserID(), "Name5NewPhone","Patients");
        userServiceTest.setPatients();
        assertEquals(userServiceTest.getPatients().get(0).getPhoneNumber(), "Name5NewPhone");
    }

    @Test
    public void testChangePhoneNumberEmployee() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("UPARK0040G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        userServiceTest.getEmployees().clear();
        userServiceTest.addEmployee("TotallyUniqueName2", "TotallyUniqueName2", "TotallyUniquePass2", "TotallyUniqueEmail2", Role.MAINTENANCE, "TotallyUniquePhone2", false);
        userServiceTest.setUser("TotallyUniqueName2", "TotallyUniquePass2", "Employees");
        userServiceTest.changePhoneNumber(userServiceTest.getEmployees().get(0).getUserID(), "Name5BNewPhone","Employees");
        userServiceTest.setEmployees();
        assertEquals(userServiceTest.getEmployees().get(0).getPhoneNumber(), "Name5BNewPhone");
    }

    @Test
    public void testChangeEmailPatient() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("UPARK0030G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.getPatients().clear();
        userServiceTest.addPatient("Name50", "UserName50", "Password50", "Email50", Role.PATIENT, "Phone50", false, tempList, "ProviderName", "UPARK0030G", "UPARK0030G");
        userServiceTest.setUser("TotallyUniqueName", "TotallyUniquePass", "Patients");
        userServiceTest.changeEmail(userServiceTest.getPatients().get(0).getUserID(), "NewEmail50","Patients");
        userServiceTest.setPatients();
        assertEquals(userServiceTest.getPatients().get(0).getEmail(), "NewEmail50");
    }

    @Test
    public void testChangeEmailEmployee() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("UPARK0100G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        userServiceTest.getEmployees().clear();
        userServiceTest.addEmployee("Name51", "UserName51", "Password51", "Email51", Role.MAINTENANCE, "Phone51",  false);
        userServiceTest.setUser("TotallyUniqueName", "TotallyUniquePass", "Employees");
        userServiceTest.changeEmail(userServiceTest.getEmployees().get(0).getUserID(), "NewEmail51","Employees");
        userServiceTest.setEmployees();
        assertEquals(userServiceTest.getEmployees().get(0).getEmail(), "NewEmail51");
    }

    @Test
    public void testChangePasswordPatient() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("UPARK0040G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.getPatients().clear();
        userServiceTest.addPatient("Name51", "UserName51", "Password51", "Email51", Role.PATIENT, "Phone51", false, tempList, "ProviderName", "UPARK0040G", "UPARK0040G");
        userServiceTest.setUser("TotallyUniqueName", "TotallyUniquePass", "Patients");
        userServiceTest.changePassword("UserName51", "NewPass51", "Patients");
        userServiceTest.setPatients();
        assertEquals(userServiceTest.getPatients().get(0).getPassword(), "NewPass51");
    }

    @Test
    public void testChangePasswordEmployee() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        mapServiceTest.addNodeWithID("UPARK0110G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");
        userServiceTest.getEmployees().clear();
        userServiceTest.addEmployee("Name51B", "UserName51B", "Password51B", "Email51B", Role.NURSE, "Phone51B",  false);
        userServiceTest.setUser("TotallyUniqueNameB", "TotallyUniquePassB", "Employees");
        userServiceTest.changePassword("UserName51B", "NewPass51B", "Employees");
        userServiceTest.setEmployees();
        assertEquals(userServiceTest.getEmployees().get(0).getPassword(), "NewPass51B");
    }

    @Test
    public void testDeleteEmployee() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        userServiceTest.getEmployees().clear();
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.addEmployee("Name8", "Name8Username", "Name8Pass", "Name8Email", Role.NURSE, "Name8Phone", false);
        userServiceTest.deleteEmployee(userServiceTest.getEmployees().get(0).getUserID());
        assertEquals(userServiceTest.getEmployees().size(), 0, 0);
    }

    @Test
    public void testDeleteGuest() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        userServiceTest.getGuests().clear();
        userServiceTest.addGuest("Name9", new Timestamp(0), "For testing purposes", false);
        userServiceTest.setGuests();
        userServiceTest.deleteGuest(userServiceTest.getGuests().get(0).getGuestID());
        assertEquals(userServiceTest.getGuests().size(), 0, 0);
    }

    // TODO: Delete? I think updateEmployee was revamped
    /*
    @Test
    public void testUpdateEmployee() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        userServiceTest.getEmployees().clear();
        userServiceTest.addEmployee("Name9", "Name9Username", "Name9Pass", "Name9Email", Role.NURSE, "Name9Phone", false);
        userServiceTest.updateEmployee(userServiceTest.getEmployees().get(0).getUserID(), "Name9New", "Name9UsernameNew", "Name9PassNew", "Name9EmailNew", Role.NURSE, "Name9PhoneNew",  false);
        assertEquals(userServiceTest.getEmployees().get(0).getName(),"Name9New");
    }

    @Test
    public void testUpdateEmployee2() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        userServiceTest.getEmployees().clear();
        mapServiceTest.addNodeWithID("NODETEST41B", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.addEmployee("Name9", "Name9Username", "Name9Pass", "Name9Email", Role.NURSE, "Name9Phone", "NODETEST41B", false);
        userServiceTest.updateEmployee(userServiceTest.getEmployees().get(0).getUserID(), "Name9", "Name9Username", "Name9Pass", "Name9Email", Role.NURSE, "Name9Phone",  false);
        assertEquals(userServiceTest.getEmployees().get(0).getName(),"Name9");
    }
    */

    @Test
    public void testUpdateGuest() throws InvalidEdgeException {
        Database.getDBTest().dropValues("all");
        Database.getDBTest();
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);
        userServiceTest.getGuests().clear();
        mapServiceTest.addNodeWithID("NODETEST42", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        ArrayList<Appointment> tempList = new ArrayList<>();
        userServiceTest.addGuest("Name10", new Timestamp(1), "Testing poirposes", false);
        userServiceTest.updateGuest(userServiceTest.getGuests().get(0).getGuestID(), "Name10New", new Timestamp(2), "Still testing", false);
        assertEquals(userServiceTest.getGuests().get(0).getName(),"Name10New");
    }

    /*
    @Test
    public void testAddAppointments() throws InvalidEdgeException {
        UserService userServiceTest = new UserService(testURL);
        MapService mapServiceTest = new MapService(testURL);

        // Creating nodes for patient
        mapServiceTest.addNodeWithID("NODETESTX", 1, 1, "1", "Faulkner", "WALK", "Long", "Short");
        mapServiceTest.addNodeWithID("UPARK0030G", 50, 50, "G", "Faulkner", "PARK", "ParkTest", "PT");

        // Creating appt
        Appointment appt = new Appointment("TestAppt1", "TestPatientX", "TestEmployeeX", new Timestamp(1), "Radiology");

        // Creating patient
        ArrayList<Appointment> tempList = new ArrayList<>();
        tempList.add(appt);
        userServiceTest.getPatients().clear();
        userServiceTest.addPatient("NameX", "UserNameX", "PasswordX", "EmailX", Role.PATIENT, "PhoneX", "NODETESTX", false, tempList, "ProviderName", "UPARK0030G", "UPARK0030G");

        // Creating employee
        userServiceTest.getEmployees().clear();
        userServiceTest.addEmployee("NameY", "UserNameY", "PasswordY", "EmailY", Role.NURSE, "PhoneY", "NODETESTX", false);

        appt.setEmployeeID(userServiceTest.getEmployees().get(0).getUserID());
        appt.setPatientID(userServiceTest.getPatients().get(0).getUserID());
        userServiceTest.addAppointment(appt);

        assertEquals(userServiceTest.getPatients().get(0).getAppointments().get(0).getAppointmentType(), "Radiology");

    } // TODO: Reevaluate when appointments is talked about
    */

}