package edu.wpi.u;

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
    - Staging area will utilize DAO and service objects to test the DB and its related classes as needed
    - Note: DAO = data access object

     */


}
