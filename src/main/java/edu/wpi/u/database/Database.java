package edu.wpi.u.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class Database {
    private static Connection conn = null;
    // Url for live code
    private final static String url = "jdbc:derby:BWdb;create=true";
    // Url for testing
    private final static String testURL = "jdbc:derby://localhost:1527/BWdb;create=true";
    //private final static String url = "jdbc:derby:BWdb;create=true;dataEncryption=true;encryptionAlgorithm=Blowfish/CBC/NoPadding;username=app;bootPassword=password";

    /**
     *  Constructor for database that is used for DB
     */
    private Database() {
        driver();
        connect();
        makeCSVDependant(false);
        createTables();
    }

//    /**
//     * Constructor for database that is called in 2nd singleton to create a second DB for testing
//     * @param urlIn - URL of testing db, has to be defined in this file
//     */
//    public Database(String urlIn) {
//        driver();
//        connect(urlIn);
//        makeCSVDependant(false);
//        createTables();
//    }

    /**
     * Singleton class for live DB (BWDB)
     */
    private static class SingletonHelper {
        //Nested class is referenced after getDB() is called
        private static final Database db = new Database();
    }

    /**
     * Singleton helper to keep Database instance singular
     * @return the database class reference
     */
    public static Database getDB() {
        return SingletonHelper.db;
    }

    /**
     * Single class for testing DB
     */
    private static class SingletonHelperTest {
        //Nested class is referenced after getDB() is called
        private static final Database dbStaging = new Database();
    }

    /**
     * Singleton helped to keep Test DB instance singular
     * @return the database class reference
     */
    public static Database getDBTest() {
        return SingletonHelperTest.dbStaging;
    }

    /**
     * Creates an instance of the database Driver to allow connection
     */
    public static void driver() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (Exception e) {
            try{
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            }catch (Exception d){{
                d.printStackTrace();
                System.out.println("Driver registration failed");
            }}
        }
    }

    /**
     * Creates the connection to the database by mounting the driver
     */
    public static void connect() {
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(true);
        } catch (Exception e) {
//            try {
//                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//                conn = DriverManager.getConnection(url);
//            }catch (Exception b){
//                System.out.println("Connection to embedded failed");
//                b.printStackTrace();
//            }
//            System.out.println("Connection to remote failed");
            e.printStackTrace();
        }
    }

    /**
     * Creates the connection to the database by passing in a url, mainly used for url of testing db
     * @param urlIn - URL of database, used for url of testDB
     */
    public static void connect(String urlIn) {
        try {
            conn = DriverManager.getConnection(urlIn);
            conn.setAutoCommit(true);
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    /**
     * Creates all of the tables in the database
     */
    public static void createTables() {
        try {
            if (isTableEmpty()) {
                String tbl1 =
                        "create table Nodes (nodeID varchar(50) not null, xcoord int, ycoord int, floor varchar(50), building varchar(50), nodeType varchar(4), longName varchar(50), shortName varchar(20), teamAssigned varchar(50), primary key (nodeID))";

                PreparedStatement ps1 = conn.prepareStatement(tbl1);
                ps1.execute();
                String tbl2 =
                        "create table Edges (edgeID varchar(50) not null, startID varchar(50), endID varchar(50), primary key(edgeID))";
                PreparedStatement ps2 = conn.prepareStatement(tbl2);
                ps2.execute();

                String tbl3 = "create table Requests (requestID varchar(50) not null ,  type varchar(50), dateNeeded timestamp, specificData varchar(250), resolved boolean, primary key(requestID))";
                PreparedStatement ps3 = conn.prepareStatement(tbl3);
                ps3.execute();

                String tbl4 =
                        "create table Employees (employeeID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phoneNumber varchar(100), locationNodeID varchar(50) references Nodes, deleted boolean, preferredContactMethod varchar(50), primary key(employeeID))";
                PreparedStatement ps4 = conn.prepareStatement(tbl4);
                ps4.execute();

                String tbl7 =
                        "create table Guests (guestID varchar(50) not null, name varchar(50), visitDate timestamp, visitReason varchar(250), deleted boolean, primary key(guestID))";
                PreparedStatement ps7 = conn.prepareStatement(tbl7);
                ps7.execute();
                // TODO : REQUEST WILL HAVE PARKING LOCATION
                String tblPatient =
                        "create table Patients (patientID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phoneNumber varchar(100), locationNodeID varchar(50) references Nodes, deleted boolean, providerName varchar(50), parkingLocation varchar(50), recommendedParkingLocation varchar(100), primary key(patientID))";
                PreparedStatement psPatient = conn.prepareStatement(tblPatient);
                psPatient.execute();

                //TODO : MOVE INTO ASSIGNMENTS
                String tblAppointments =
                        "create table Appointments(appointmentID varchar(50) not null , appointmentDate timestamp, appointmentType varchar(100), patientID varchar(50) references Patients, employeeID varchar(50) references Employees , primary key(appointmentID))";
                PreparedStatement psAppointments = conn.prepareStatement(tblAppointments);
                psAppointments.execute();

                String tbl5 = "create table Assignments(assignmentID varchar(50) not null, request varchar(50) references Requests, employeeID varchar(50), primary key(assignmentID))";
                PreparedStatement ps5 = conn.prepareStatement(tbl5);
                ps5.execute();

                String tbl6 = "create table Locations(locationID varchar(50) not null, request varchar(50) references Requests, nodeID varchar(50) references Nodes, primary key (locationID))";
                PreparedStatement ps6 = conn.prepareStatement(tbl6);
                ps6.execute();
                // TODO : Change from arrayList to String
                String permissionsInit = "create table Permissions(permissionID varchar(50) not null, edgeID varchar(50) references Edges, userType varchar(50), primary key(permissionID))";
                PreparedStatement psPerm = conn.prepareStatement(permissionsInit);
                psPerm.execute();

                String commentstbl = "create table Comments(request varchar(50) references Requests, title varchar(100), description varchar(500), author varchar(50), type varchar(50), created timestamp)";
                PreparedStatement commentStatement = conn.prepareStatement(commentstbl);
                commentStatement.execute();

                String covidSurveyResult = "create table covidSurveyResult(id int generated always as identity , symptomatic boolean, nonsymptomatic boolean, dateOfResults date)";
                PreparedStatement covidSurveyResultStatement = conn.prepareStatement(covidSurveyResult);
                covidSurveyResultStatement.execute();

            }
        } catch (Exception e) {
            System.out.println("Table creation failed");
            e.printStackTrace();
        }
    }

    /**
     * Will read the csv file from a given path
     *
     * @param filePath  the path to be read from
     * @param tableName the table to load the data from the csv into
     */
    public void readCSV(String filePath, String tableName){

        String tempPath = "temp.csv"; //TODO : Change path in jar file
        String str1 = "CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE ('APP', '" + tableName.toUpperCase() + "', '" + tempPath + "', ', ', null, null,1)";

        try {
            String content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
            String[] columns = content.split("\n", 2);
            columns[1] += "\n";
            File temp = new File(tempPath);
            if(temp.createNewFile()){
                System.out.println("File created");
            }
            FileWriter myWriter = new FileWriter(tempPath);
            myWriter.write(columns[1]);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement p = conn.prepareStatement(str1);
            p.execute();
        }
        catch (Exception e){
            System.out.println("Path: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * Saves a csv to a given file path
     * @param tableName the table to be saved to a csv
     * @param filePath the path that the csv file will be written to
     * @param header header of the csv file (the first line)
     */
    public void saveCSV(String tableName, String filePath, String header){
        File f = new File(filePath);
        if (f.delete()) {
            System.out.println("File deleted when saving"); //TODO : Used to be "file deleted"
        }
        String str = "CALL SYSCS_UTIL.SYSCS_EXPORT_TABLE ('APP','" + tableName.toUpperCase() + "','" + filePath + "',',',null,null)";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Wants new file");
            e.printStackTrace();
        }

        try {
            String content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
            FileWriter fw = new FileWriter(filePath);
            fw.write(header);
            fw.write("\n");
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This will print all of the requests from a Request table
     *
     * @param aTable table name
     */
    public void printRequestTable(String aTable) {
        try {
            String str = "select * from " + aTable;
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rset = ps.executeQuery();
            while (rset.next()) {
                String id = rset.getString("requestID");
                System.out.println("Request id: " + id);
            }
            rset.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if the Nodes table is populated and assumes the rest of the tables are created if it is
     *
     * @return true if no tables have been created, false otherwise
     */
    public static boolean isTableEmpty() {
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, "APP", "NODES", null);
            return !rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void dropAllValues(){
        try {
            String str;
            Statement s = conn.createStatement();
            str = "delete from covidSurveyResult";
            s.execute(str);
            str = "delete from Comments";
            s.execute(str);
            str = "delete from Permissions";
            s.execute(str);
            str = "delete from Locations";
            s.execute(str);
            str = "delete from Assignments";
            s.execute(str);
            str = "delete from Appointments";
            s.execute(str);
            str = "delete from Patients";
            s.execute(str);
            str = "delete from Guests";
            s.execute(str);
            str = "delete from Employees";
            s.execute(str);
            str = "delete from Requests";
            s.execute(str);
            str = "delete from Edges";
            s.execute(str);
            str = "delete from Nodes";
            s.execute(str);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Drops a table or all if the tableName = "all"
     *
     * @param tableName the table to drop or "all"
     */
    public void dropValues(String tableName) {
        try {
//            Statement s = conn.createStatement();
//            String str = "alter table Locations drop column requestID";
//            s.execute(str);
//            str = "alter table Assignments drop column requestID";
//            s.execute(str);
//            str = "delete from " + tableName;
//            s.execute(str);
//            if (str.equals("all")){
//                str = "delete from Comments";
//                s.execute(str);
//                str = "delete from Permissions";
//                s.execute(str);
//                str = "delete from Locations";
//                s.execute(str);
//                str = "delete from Assignments";
//                s.execute(str);
//                str = "delete from Appointments";
//                s.execute(str);
//                str = "delete from Patients";
//                s.execute(str);
//                str = "delete from Guests";
//                s.execute(str);
//                str = "delete from Employees";
//                s.execute(str);
//                str = "delete from Requests";
//                s.execute(str);
//                str = "delete from Edges";
//                s.execute(str);
//                str = "delete from Nodes";
//                s.execute(str);
//            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * Deletes all tables
     */
    public void deleteTables() {
        //System.out.println("here2");
        try {
            Statement s = conn.createStatement();
            String str = "drop table Comments";
            s.execute(str);
            str = "drop table Permissions";
            s.execute(str);
            str = "drop table Locations";
            s.execute(str);
            str = "drop table Assignments";
            s.execute(str);
            str = "drop table Appointments";
            s.execute(str);
            str = "drop table Patients";
            s.execute(str);
            str = "drop table Guests";
            s.execute(str);
            str = "drop table Employees";
            s.execute(str);
            str = "drop table Requests";
            s.execute(str);
            str = "drop table Edges";
            s.execute(str);
            str = "drop table Nodes";
            s.execute(str);
            str = "drop table covidSurveyResult";
            s.execute(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all tables to respective CSV files (ie: Nodes table saved to Nodes.csv)
     */
    public void saveAll() {
        saveCSV("Requests", "Requests.csv", "Test");
        saveCSV("Assignments", "Assignments.csv", "Test");
        saveCSV("Locations", "Locations.csv", "Test");
        saveCSV("Nodes", "MapUAllNodes.csv", "Test");
        saveCSV( "Edges", "MapUAllEdges.csv","Test");
        saveCSV( "Comments", "Comments.csv","Test");
    }

    /**
     * Will make the database not persistent in between sessions and reliant on the loading of CSV files on app startup
     *
     * @param yes whether or not to make Database CSV dependent
     */
    public void makeCSVDependant(boolean yes) {

        if (!yes) return;
        //dropValues("all");
        deleteTables();
        readCSV("Requests.csv", "Requests");
        readCSV("Locations.csv", "Locations");
        readCSV("Assignments.csv", "Assignments");
        readCSV("OutsideMapNodes.csv", "Nodes");
        readCSV("OutsideMapEdges.csv", "Edges");
    }

    /**
         * Stops the database on app shutdown
         * TODO : Enable and test
         */
    public void stop () {
            try {
                DriverManager.getConnection("jdbc:derby://localhost:1527/BWdb;shutdown=true");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
