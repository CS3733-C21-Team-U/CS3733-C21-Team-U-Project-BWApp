package edu.wpi.u;

import edu.wpi.u.database.Database;
import edu.wpi.u.exceptions.InvalidEdgeException;
import edu.wpi.u.models.*;
import edu.wpi.u.requests.*;
import edu.wpi.u.users.Role;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DatabaseTest {

    /*
    Goal: Create a staging area that allows DAOs and service objects to be tested on a test DB

    Test DB: Can be filled, saved, accessed and modified as needed separate from the live database.

    Changes to live code: Create another constructor for the database class that takes in the URL as an argument
    - Call the original url in the live branch of code when instantiating live db
    - Call the staging url in staging area when instantiating testing db

    Additions to staging area: Staging area is defined as the testing class that is separate from the live code
    - Staging area will create a new instance of the database class, this time with a different url
    - Staging area will test DAO, service objects and the database itself ideally
    - Note: DAO = data access object

     */

    private static String testURL = "jdbc:derby:testDB";

    private static Database dbTest = Database.getDBTest();
    private static MapService mapServiceTest = new MapService(testURL);
    private static RequestService requestServiceTest = new RequestService(testURL);
    private static UserService userServiceTest = new UserService(testURL);

    // private static MapInteractionModel mapInteractionModelTest = new MapInteractionModel(); // for UI stuff?
    // private static AdminToolStorage AdminStorageTest = new AdminToolStorage(); // not needed
    // private static PathHandling PathHandlingTest = new PathHandling(); // not even used yet
    // private static UndoRedoService undoRedoServiceTest = new UndoRedoService(); // used in UI, could be added for testing

    public static void main(String[] args) throws InvalidEdgeException {
        // Locations for testing
        ArrayList<String> locations = new ArrayList<String>();
        locations.add("TEST1234");

        // Stafflist for testing
        ArrayList<String> staffList = new ArrayList<String>();
        staffList.add("BillWurke"); // TODO: does the list of staff represent their names or id's (auto generated)

        // Comment for testing
        Comment testComment = new Comment("WillComment", "This comment is for testing purposes", "BillWurke", CommentType.PRIMARY, new Timestamp(0));

        // Request for testing
        ArrayList<String> specificFields = new ArrayList<>();
        specificFields.add("Machine 1");
        specificFields.add("High"); // Is this a valid string for priority??
        SpecificRequest result = new RequestFactory().makeRequest("Maintenance");
        Request newRequest = new Request("TestWill", new Timestamp(System.currentTimeMillis()), locations, staffList, testComment);
        result.setRequest(newRequest);
        result.setSpecificData(specificFields);

        mapServiceTest.addNode("TEST1234",50,50,"1", "Faulkner", "HALL", "LOOOOONG", "SHORT");
        mapServiceTest.addNode("TEST5678",100,100,"1", "Faulkner", "HALL", "LOOOOONG", "SHORT");
        userServiceTest.addEmployee("BillWurke", "willdabeast29", "cookies123", "billwurke7@gmail.com", Role.ADMIN, "4016491137", "TEST1234",false);


        requestServiceTest.addRequest(result);

    }


}
