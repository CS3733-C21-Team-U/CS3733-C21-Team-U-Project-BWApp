package edu.wpi.teamname;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;
import java.util.*;
import static java.lang.System.exit;

public class BWdb {

  private static Connection conn = null; // used for connection
  private static Statement stmt; // used for sql statements
  private static ResultSet rset; // used to store returns from sql queries
  private static String url =
          "jdbc:derby:BWdb;user=admin;password=admin;create=true"; // link of embedded database
  private static int option; // used to know what function to run


  public static void main(String[] args) throws SQLException {
    BWdb db = new BWdb();
  }

  public BWdb() throws SQLException {
    driver();
    connect();
    init();
    dropValues();
    insertNodeData();
    insertEdgeData();
    printNodes();
    // stop();
  }

  public static void driver() {
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
      // registers the driver for the database
    } catch (Exception e) {
      System.out.println("Driver registration failed");
      e.printStackTrace();
    }
  }

  public static void connect() {
    try {
      conn = DriverManager.getConnection(url);
      // conn = DriverManager.getConnection("jdbc:derby:BWdb;shutdown=true");
      // establishes a connection to the database
    } catch (Exception e) {
      System.out.println("Connection failed");
      e.printStackTrace();
    }
  }

  public static void init() {
    try {
      DatabaseMetaData dmd = conn.getMetaData();
      ResultSet rs = dmd.getTables(null, "ADMIN", "NODES", null);
      if (!rs.next()) {
        String tbl1 =
                "create table Nodes (nodeID varchar(50) not null, xcoord int, ycoord int, floor int , building varchar(50), nodeType varchar(4), longName varchar(50), shortName varchar(20), teamAssigned varchar(50), primary key (nodeID))";
        // code for creating table of Museums
        PreparedStatement ps1 = conn.prepareStatement(tbl1);
        ps1.execute();
        String tbl2 =
                "create table Edges (edgeID varchar(50) not null, startID varchar(50), endID varchar(50), primary key(edgeID))";
        PreparedStatement ps2 = conn.prepareStatement(tbl2);
        ps2.execute();
      }
    } catch (SQLException e) {
      System.out.println("Table creation failed");
      e.printStackTrace();
    }
  }

  public static void dropValues() {
    try{
      String str = "delete from Nodes";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.execute();
      str = "delete from Edges";
      ps.execute();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public static void insertNodeData() throws SQLException {
    String str ="";

    try  {
      BufferedReader br = Files.newBufferedReader(Paths.get("OutsideMapNodes.csv"));

      String DELIMITER = ",";

      // read the file line by line
      String line;
      while ((line = br.readLine()) != null) {
        str = "insert into Nodes (nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, teamAssigned) values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(str);

        // convert line into columns
        String[] columns = line.split(DELIMITER);
        ps.setString(1, columns[0]);
        ps.setInt(2, Integer.parseInt(columns[1]));
        ps.setInt(3, Integer.parseInt(columns[2]));
        ps.setInt(4, Integer.parseInt(columns[3]));
        ps.setString(5, columns[4]);
        ps.setString(6, columns[5]);
        ps.setString(7, columns[6]);
        ps.setString(8, columns[7]);
        ps.setString(9, columns[8]);
        ps.execute();


        // print all columns
        //System.out.println("User["+ String.join(", ", columns) +"]");
      }

    } catch (Exception e) {
      System.out.println("Failed to insert into table");
      e.printStackTrace();
    }
  }

  public static void insertEdgeData() throws SQLException {
    String str ="";

    try  {
      BufferedReader br = Files.newBufferedReader(Paths.get("OutsideMapEdges.csv"));

      String DELIMITER = ",";

      // read the file line by line
      String line;
      while ((line = br.readLine()) != null) {
        str = "insert into Edges (edgeID, startID, endID) values (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(str);

        // convert line into columns
        String[] columns = line.split(DELIMITER);
        ps.setString(1, columns[0]);
        ps.setString(2, columns[1]);
        ps.setString(3, columns[2]);
        ps.execute();


        // print all columns
        //System.out.println("User["+ String.join(", ", columns) +"]");
      }

    } catch (Exception e) {
      System.out.println("Failed to insert into table");
      e.printStackTrace();
    }
  }

  public static void printNodes() {
    try {
      String str = "select * from Nodes";
      PreparedStatement ps = conn.prepareStatement(str);
      rset = ps.executeQuery();
      // store the data inside of a ResultSet object
      while (rset.next()) { // iterates through the object row by row
        String nodeID = rset.getString("nodeID");
        int xcoord = rset.getInt("xcoord");// gets value at column name
        System.out.println("ID:" + nodeID + " " + "xcoord:" + xcoord + "\n");
      }
      rset.close(); // close the object
    } catch (Exception e) {
      System.out.println("Failed to select from Museums");
      e.printStackTrace();
    }
  }

  public static void saveNodesCSV() {
    try {
      String str = "SELECT * FROM Nodes";

      Statement statement = conn.createStatement();

      ResultSet result = statement.executeQuery(str);

      BufferedWriter fileWriter = new BufferedWriter(new FileWriter("OutsideMapNodes.csv"));

      // write header line containing column names
      fileWriter.write("nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, teamAssigned");

      while (result.next()) {
        String nodeID = result.getString("nodeID");
        int xcoord = result.getInt("xcoord");
        int ycoord = result.getInt("ycoord");
        int floor = result.getInt("floor");
        String building = result.getString("building");
        String nodeType = result.getString("nodeType");
        String longName = result.getString("longName");
        String shortName = result.getString("shortName");
        String teamAssigned = result.getString("teamAssigned");

        String line = String.format("\"%s\",%s,%.1f,%s,%s",
                nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, teamAssigned);

        fileWriter.newLine();
        fileWriter.write(line);
      }

      statement.close();
      fileWriter.close();

    } catch (SQLException e) {
      System.out.println("Datababse error:");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("File IO error:");
      e.printStackTrace();
    }

  }

  public static void saveEdgesCSV() {
    try {
      String str = "SELECT * FROM Edges";

      Statement statement = conn.createStatement();

      ResultSet result = statement.executeQuery(str);

      BufferedWriter fileWriter = new BufferedWriter(new FileWriter("OutsideMapNodes.csv"));

      // write header line containing column names
      fileWriter.write("edgeID, startID, endID");

      while (result.next()) {
        String edgeID = result.getString("edgeID");
        String startID = result.getString("startID");
        String endID = result.getString("endID");

        String line = String.format("\"%s\",%s,%.1f,%s,%s",
                edgeID, startID, endID);

        fileWriter.newLine();
        fileWriter.write(line);
      }

      statement.close();
      fileWriter.close();

    } catch (SQLException e) {
      System.out.println("Datababse error:");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("File IO error:");
      e.printStackTrace();
    }

  }
}