package edu.wpi.u.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

/*
Twillio covid screenings
 */

public class Database {
    private static Connection conn = null;
    //private final static String url = "jdbc:derby:BWdb;create=true;dataEncryption=true;encryptionAlgorithm=Blowfish/CBC/NoPadding;username=app;bootPassword=password";
    private final static String url = "jdbc:derby:BWdb;create=true";

    private Database() {
        driver();
        connect();
        makeCSVDependant(false);
        //dropValues("all");
        createTables();
    }

    //Bill Pugh solution
    private static class SingletonHelper {
        //Nested class is referenced after getDB() is called
        private static final Database db = new Database();
    }

    /**
     * Singleton helper to keep Database instance singular
     *
     * @return the database class reference
     */
    public static Database getDB() {
        return SingletonHelper.db;
    }

    /**
     * Creates an instance of the database Driver to allow connection
     */
    public static void driver() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        } catch (Exception e) {
            System.out.println("Driver registration failed");
            e.printStackTrace();
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

                String tbl3 = "create table Requests (requestID varchar(50) not null , dateCreated date, dateCompleted date,description varchar(250),title varchar(100),type varchar(50), dateNeeded date, specificData varchar(250), primary key(requestID))";

                PreparedStatement ps3 = conn.prepareStatement(tbl3);
                ps3.execute();

                String tbl4 =
                        "create table Employees (employeeID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phoneNumber varchar(100), locationNodeID varchar(50) references Nodes, deleted boolean, primary key(employeeID))";
                PreparedStatement ps4 = conn.prepareStatement(tbl4);
                ps4.execute();

                String tbl7 =
                        "create table Guests (guestID varchar(50) not null, name varchar(50), visitDate timestamp, visitReason varchar(250), deleted boolean, primary key(guestID))";
                PreparedStatement ps7 = conn.prepareStatement(tbl7);
                ps7.execute();
                // TODO : REQUEST WILL HAVE PARKING LOCATION
                String tblPatient =
                        "create table Patients (patientID varchar(50) not null, name varchar(50), userName varchar(100), password varchar(100), email varchar(250), type varchar(50), phoneNumber varchar(100), locationNodeID varchar(50) references Nodes, deleted boolean, providerName varchar(50), parkingLocation varchar(50) references Nodes, recommendedParkingLocation varchar(100), primary key(patientID))";
                PreparedStatement psPatient = conn.prepareStatement(tblPatient);
                psPatient.execute();

                //TODO : MOVE INTO ASSIGNMENTS
                String tblAppointments =
                        "create table Appointments(appointmentID varchar(50) not null, appointmentDate timestamp, appointmentType varchar(100), patientID varchar(50) references Patients, employeeID varchar(50) references Employees , primary key(appointmentID))";
                PreparedStatement psAppointments = conn.prepareStatement(tblAppointments);
                psAppointments.execute();

                String tbl5 = "create table Assignments(assignmentID varchar(50) not null, requestID varchar(50) references Requests, userID varchar(50) references Employees, primary key(assignmentID))";
                PreparedStatement ps5 = conn.prepareStatement(tbl5);
                ps5.execute();

                String tbl6 = "create table Locations(locationID varchar(50) not null, requestID varchar(50) references Requests, nodeID varchar(50) references Nodes, primary key(locationID))";
                PreparedStatement ps6 = conn.prepareStatement(tbl6);
                ps6.execute();
                // TODO : Change from arrayList to String
                String permissionsInit = "create table Permissions(permissionID varchar(50) not null, edgeID varchar(50) references Edges, userType varchar(50), primary key(permissionID))";
                PreparedStatement psPerm = conn.prepareStatement(permissionsInit);
                psPerm.execute();
                //Service Request Tables
//                String tblMaintenance = "create table Maintenance(requestID varchar(50) references Requests, machineUsed varchar(50), priority int, primary key(requestID))";
//                PreparedStatement maintenanceRQ = conn.prepareStatement(tblMaintenance);
//                maintenanceRQ.execute();
//
//                String tblLaundry = "create table Laundry(requestID varchar(50) references Requests, dryStrength int, numLoad int, washStrength int, primary key(requestID))";
//                PreparedStatement LaundryRQ = conn.prepareStatement(tblLaundry);
//                LaundryRQ.execute();
//
//                String tblSanitation = "create table Sanitation(requestID varchar(50) references Requests, hazardLevel int, spillType varchar(50), primary key(requestID))";
//                PreparedStatement SanitationRQ = conn.prepareStatement(tblSanitation);
//                SanitationRQ.execute();
//
//                String tbAudioVisual = "create table AudioVisual(requestID varchar(50) references Requests, isAudio int, name varchar(50), primary key(requestID))";
//                PreparedStatement AudioVisualRQ = conn.prepareStatement(tbAudioVisual);
//                AudioVisualRQ.execute();
//
//                String tbFloral = "create table Floral(requestID varchar(50) references Requests, numFlowers int, recipient varchar(50), primary key(requestID))";
//                PreparedStatement FloralRQ = conn.prepareStatement(tbFloral);
//                FloralRQ.execute();
//
//                String tbMedical = "create table Medical(requestID varchar(50) references Requests, name varchar(50), quantity varchar(50), supplier varchar(50), primary key(requestID))";
//                PreparedStatement MedicalRQ = conn.prepareStatement(tbMedical);
//                MedicalRQ.execute();
//
//                String tbReligious = "create table Religious(requestID varchar(50) references Requests, priority int, religion varchar(50), primary key(requestID))";
//                PreparedStatement ReligiousRQ = conn.prepareStatement(tbReligious);
//                ReligiousRQ.execute();
//
//                String tbComputer = "create table Computer(requestID varchar(50) references Requests, electronicType varchar(50), priority int , primary key(requestID))";
//                PreparedStatement computerRQ = conn.prepareStatement(tbComputer);
//                computerRQ.execute();
//
//                String tbSecurity = "create table Security(requestID varchar(50) references Requests, threatLevel varchar(50), responseRequired varchar(50) , primary key(requestID))";
//                PreparedStatement SecurityRQ = conn.prepareStatement(tbSecurity);
//                SecurityRQ.execute();
//
//                String tbLanguage = "create table Language(requestID varchar(50) references Requests, language varchar(50), numInterpreters int , primary key(requestID))";
//                PreparedStatement languageRQ = conn.prepareStatement(tbLanguage);
//                languageRQ.execute();
//
//                String gift = "create table Gift(requestID varchar(50) references Requests, contents varchar(50), mass int, primary key(requestID))";
//                PreparedStatement giftRQ = conn.prepareStatement(gift);
//                giftRQ.execute();


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
    public void readCSV(String filePath, String tableName) {

        String tempPath = "temp.csv"; //TODO : Change path in jar file
        String str1 = "CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE ('APP', '" + tableName.toUpperCase() + "', '" + tempPath + "', ', ', null, null,1)";

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] columns = content.split("\n", 2);
            //String[] attributes = content.split(","); TODO: Make table columns from header values
            columns[1] += "\n";
            File temp = new File(tempPath);
            if (temp.createNewFile()) {
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
        } catch (Exception e) {
            System.out.println("Path: " + filePath);
            //e.printStackTrace();
        }
    }

    /**
     * Saves a csv to a given file path
     *
     * @param tableName the table to be saved to a csv
     * @param filePath  the path that the csv file will be written to
     * @param header    header of the csv file (the first line)
     */
    public void saveCSV(String tableName, String filePath, String header) {
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
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
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

    /**
     * Drops a table or all if the tableName = "all"
     *
     * @param tableName the table to drop or "all"
     */
    public void dropValues(String tableName) {
        try {
            Statement s = conn.createStatement();
            String str = "alter table Locations drop column requestID";
            s.execute(str);
            str = "alter table Assignments drop column requestID";
            s.execute(str);
            str = "delete from " + tableName;
            s.execute(str);
            if (str.equals("all")) {
                str = "alter table Locations drop column requestID";
                s.execute(str);
                str = "alter table Assignments drop column requestID";
                s.execute(str);
                str = "delete from Nodes";
                s.execute(str);
                str = "delete from Edges";
                s.execute(str);
                str = "delete from Requests";
                s.execute(str);
                str = "delete from Locations";
                s.execute(str);
                str = "delete from Assignments";
                s.execute(str);
//                str = "delete from Maintenance";
//                s.execute(str);
//                str = "delete from Laundry";
//                s.execute(str);
            }
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
    }

    /**
     * Deletes all tables TODO : Update to have newly created sprint 2&3 tables
     */
    public void deleteTables() {
        //System.out.println("here2");
        try {
            Statement s = conn.createStatement();
            String str = "drop table Nodes";
            s.execute(str);
            str = "drop table Edges";
            s.execute(str);
            str = "drop table Requests";
            s.execute(str);
            str = "drop table Locations";
            s.execute(str);
            str = "drop table Assignments";
            s.execute(str);
//            str = "drop tabl";
//            s.execute(str);
//            str = "drop Laundry";
//            s.execute(str);
            //str = "delete from Security";
            //s.execute(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //TODO: Test

    /**
     * Saves all tables to respective CSV files (ie: Nodes table saved to Nodes.csv)
     */
    public void saveAll() {
        saveCSV("Requests", "Requests.csv", "Test");
        saveCSV("Assignments", "Assignments.csv", "Test");
        saveCSV("Locations", "Locations.csv", "Test");
        saveCSV("Nodes", "MapUAllNodes.csv", "Test");
        saveCSV("Edges", "MapUAllEdges.csv", "Test");
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
                // DriverManager.getConnection("jdbc:derby:BWdb;shutdown=true");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

