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
    private final static String url = "jdbc:derby:BWdb;create=true;dataEncryption=true;encryptionAlgorithm=Blowfish/CBC/NoPadding;bootPassword=bwdbpassword";


    public Database() {
        driver();
        connect();
        createTables();
    }

    //Bill Pugh solution
    private static class SingletonHelper {
        //Nested class is referenced after getDB() is called
        private static final Database db = new Database();
    }

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

                String tbl3 =
                        "create table Requests (requestID varchar(50) not null , dateCreated date, dateCompleted date,description varchar(200),title varchar(50),type varchar(50),  primary key(requestID))";
                PreparedStatement ps3 = conn.prepareStatement(tbl3);
                ps3.execute();

                String tbl4 =
                        "create table Users (userID varchar(50) not null, name varchar(50), accountName varchar(100), password varchar(100), type varchar(50), employed boolean, primary key(userID))";
                PreparedStatement ps4 = conn.prepareStatement(tbl4);
                ps4.execute();

                String tbl5 = "create table Assignments(assignmentID varchar(50) not null, requestID varchar(50) references Requests, userID varchar(50) references Users, primary key(assignmentID))";
                PreparedStatement ps5 = conn.prepareStatement(tbl5);
                ps5.execute();

                String tbl6 = "create table Locations(locationID varchar(50) not null, requestID varchar(50) references Requests, nodeID varchar(50) references Nodes, primary key(locationID))";
                PreparedStatement ps6 = conn.prepareStatement(tbl6);
                ps6.execute();
            }
        } catch (SQLException e) {
            System.out.println("Table creation failed");
            e.printStackTrace();
        }
    }

    /**
     * Will read the csv file from a given path
     * @param filePath the path to be read from
     * @param tableName the table to load the data from the csv into
     */
    public void readCSV(String filePath, String tableName){

        String tempPath = "temp.csv"; //TODO : Change path in jar file
        String str1 = "CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE ('APP', '" + tableName.toUpperCase() + "', '" + tempPath + "', ', ', null, null,1)";

        try {
            String content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
            String[] columns = content.split("\n", 2);
            //String[] attributes = content.split(","); TODO: Make table columns from header values
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
            //e.printStackTrace();
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
        if(f.delete()){
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

    public void printRequests() {
        try {
            String str = "select * from Requests";
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

    public void dropValues() {
        try {
            Statement s = conn.createStatement();
            String str = "alter table Locations drop column nodeID";
            s.execute(str);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: Test
    public void stop() {
        try{
            DriverManager.getConnection("jdbc:derby:BWdb;shutdown=true");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
