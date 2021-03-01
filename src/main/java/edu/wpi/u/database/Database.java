package edu.wpi.u.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class Database {
    private static Connection conn = null;
    private final static String url = "jdbc:derby:BWdb;create=true;dataEncryption=true;encryptionAlgorithm=Blowfish/CBC/NoPadding;bootPassword=bwdbpassword";


    public Database() {
        driver();
        connect();
        makeCSVDependant(false);
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

    public static void driver() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        } catch (Exception e) {
            System.out.println("Driver registration failed");
            e.printStackTrace();
        }
    }

    public static void connect() {
        try {
            conn = DriverManager.getConnection(url);
            //conn.setAutoCommit(false);
            //conn.commit();
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    public static void createTables() { //TODO : Rename to createTables()
        try {
            if (isTableEmpty()) {
                String tbl1 =
                        "create table Nodes (nodeID varchar(50) not null, xcoord int, ycoord int, floor int , building varchar(50), nodeType varchar(4), longName varchar(50), shortName varchar(20), teamAssigned varchar(50), primary key (nodeID))";

                PreparedStatement ps1 = conn.prepareStatement(tbl1);
                ps1.execute();
                String tbl2 =
                        "create table Edges (edgeID varchar(50) not null, startID varchar(50), endID varchar(50), primary key(edgeID))";
                PreparedStatement ps2 = conn.prepareStatement(tbl2);
                ps2.execute();

                String tbl3 =
                        "create table Requests (requestID varchar(50) not null , dateCreated date, dateCompleted date,description varchar(200),title varchar(50),type varchar(50),  primary key(requestID))"; //TODO: Delete Type
                PreparedStatement ps3 = conn.prepareStatement(tbl3);
                ps3.execute();

                String tbl4 = "create table Assignments(assignmentID varchar(50) not null, requestID varchar(50) references Requests, userID varchar(50), primary key(assignmentID))";
                PreparedStatement ps4 = conn.prepareStatement(tbl4);
                ps4.execute();

                String tbl5 = "create table Locations(locationID varchar(50) not null, requestID varchar(50) references Requests, nodeID varchar(50) references Nodes, primary key(locationID))";
                PreparedStatement ps5 = conn.prepareStatement(tbl5);
                ps5.execute();

                //Service Request Tables
                String tblMaintenance = "create table Maintenance(requestID varchar(50) references Requests, machineUsed varchar(50), priority int, primary key(requestID))";
                PreparedStatement maintenanceRQ = conn.prepareStatement(tblMaintenance);
                maintenanceRQ.execute();

                String tblLaundry = "create table Laundry(requestID varchar(50), washer varchar(50), Foreign Key requestID references Requests(requestID))";
                PreparedStatement LaundryRQ = conn.prepareStatement(tblLaundry);
                LaundryRQ.execute();

                String tblSecurity = "create table Security(requestID varchar(50), threatLevel varchar(50), primary key(requestID), Foreign Key requestID references Requests(requestID))";
                PreparedStatement SecurityRQ = conn.prepareStatement(tblSecurity);
                SecurityRQ.execute();
            }
        } catch (Exception e) {
            System.out.println("Table creation failed");
            e.printStackTrace();
        }
    }

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

    public void printRequests(String aTable) { //what is happening here?
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
        //System.out.println("here2");
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
            str = "delete from Maintenance";
            s.execute(str);
            str = "delete from Laundry";
            s.execute(str);
            //str = "delete from Security";
            //s.execute(str);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
    }

    public void deleteTables() {
        //System.out.println("here2");
        try {
            Statement s = conn.createStatement();
            String str = "drop Nodes";
            s.execute(str);
            str = "drop Edges";
            s.execute(str);
            str = "drop Requests";
            s.execute(str);
            str = "drop Locations";
            s.execute(str);
            str = "drop Assignments";
            s.execute(str);
            str = "drop Maintenance";
            s.execute(str);
            str = "drop Laundry";
            s.execute(str);
            //str = "delete from Security";
            //s.execute(str);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
    }



    public void saveAll(){
        saveCSV( "Requests", "Requests.csv","Test");
        saveCSV( "Assignments", "Assignments.csv","Test");
        saveCSV( "Locations", "Locations.csv","Test");
        saveCSV("Nodes", "OutsideMapNodes.csv", "Test");
        saveCSV( "Edges", "OutsideMapEdges.csv","Test");
        saveCSV( "Maintenance","Maintenance.csv", "Test");
        //saveCSV( "Laundry", "Laundry.csv","Test");
        //saveCSV( "Laundry", "Security.csv","Test");
    }

    public void makeCSVDependant(boolean yes){

        if(!yes) return;
        dropValues();
        deleteTables();
        readCSV("Requests.csv", "Requests");
        readCSV("Locations.csv", "Locations");
        readCSV("Assignments.csv", "Assignments");
        readCSV("OutsideMapNodes.csv", "Nodes");
        readCSV("OutsideMapEdges.csv", "Edges");
        readCSV("Maintenance.csv", "Maintenance");
        //readCSV("Laundry.csv", "Laundry");



    }



    public void stop() {

    }
}
