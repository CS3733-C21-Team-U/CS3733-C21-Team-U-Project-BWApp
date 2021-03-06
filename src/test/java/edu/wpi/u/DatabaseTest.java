package edu.wpi.u;

import edu.wpi.u.database.Database;
import edu.wpi.u.models.*;
import org.junit.Test;

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

    private static String testURL = "jdbc:derby:testDB;bootUser=admin;bootPassword=testdbpassword";

    // TODO: Modify Data classes to support different url
    private static Database dbTest = Database.getDBTest();
    private static MapService mapServiceTest = new MapService(testURL);

    private static UserService userServiceTest = new UserService();
    private static MapInteractionModel mapInteractionModelTest = new MapInteractionModel();
    private static RequestService requestServiceTest = new RequestService();
    private static AdminToolStorage AdminStorageTest = new AdminToolStorage();
    private static PathHandling PathHandlingTest = new PathHandling();
    private static UndoRedoService undoRedoServiceTest = new UndoRedoService();

    public static void main(String[] args) {

    }


}
