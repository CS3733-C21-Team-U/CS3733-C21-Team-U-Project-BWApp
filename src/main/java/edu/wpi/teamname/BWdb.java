package edu.wpi.teamname;


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


  public static void main(String[] args) {
    BWdb db = new BWdb();
  }

  public BWdb() {
    driver();
    connect();
    init();
    insertData();
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

  public static void insertData() {
    try {
        // TODO: fix this statement
      String str = ""; // COMMENT OUT WHEN UNCOMMENTING BELOW

      /*
      String str =
            "call SYSCS_UTIL.SYSCS_IMPORT_TABLE_BULK"
        + " (null, 'NODES', 'src/main/resources/edu/wpi/teamname/OutsideMapNodes.csv', ',', null, null, 0, 1";
      */


        PreparedStatement ps = conn.prepareStatement(str);
        ps.execute();
        str =
            "insert into Edges("
                + "edgeID, startID, endID) values "
                + "('U1234_U5678', 'U1234', 'U5678')";
        PreparedStatement ps2 = conn.prepareStatement(str);
        ps2.execute();


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
        String nodeID = rset.getString("nodeID"); // gets value at column name
        System.out.println("ID:" + nodeID + "\n");
      }
      rset.close(); // close the object
    } catch (Exception e) {
      System.out.println("Failed to select from Museums");
      e.printStackTrace();
    }
  }

  public static void printByName() {
    // prints name of each museum followed by the paintings in them
    try {
      String str2;
      ResultSet rset2;
      String str = "select * from Paintings"; // we want all paintings info
      PreparedStatement ps1 = conn.prepareStatement(str);
      rset = ps1.executeQuery();
      while (rset.next()) {
        int museum_id = rset.getInt("museum"); // get the id from the museum
        String name = rset.getString("name");
        String artist = rset.getString("artist");
        str2 =
            "select name from Museums where id=?"; // will get the museum name given it equals the
        // corresponding 'Paintings' museum id
        PreparedStatement ps = conn.prepareStatement(str2);
        ps.setInt(1, museum_id);
        rset2 = ps.executeQuery();
        if (rset2.next()) {
          System.out.printf(
              "%-5s %-5s %-5s",
              "Museum: " + rset2.getString("name"),
              " Name of Painting: " + name,
              " Artist: " + artist + "\n");
        }
        rset2.next();
      }
      rset.close();
    } catch (Exception e) {
      System.out.println("Failed to get museums and paintings");
      e.printStackTrace();
    }
  }

  public static void updatePhoneNumber() {
    try {
      Scanner s = new Scanner(System.in);
      System.out.println("Please enter the museum name (Case sensitive)");
      String museum = s.nextLine();
      System.out.println("Please enter a new phone number");
      int new_phone_number = s.nextInt();
      String str = "update Museums set phone=? where name=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setInt(1, new_phone_number);
      ps.setString(2, museum);
      ps.execute();
    } catch (Exception e) {
      System.out.println("Failed to update phone number");
      e.printStackTrace();
    }
  }

  public static void stop() {
    try {
      stmt = conn.createStatement();
      String str = "alter table Paintings drop column museum"; // drops the foreign key
      stmt.execute(str);
      str = "drop table Paintings"; // drops the table
      stmt.execute(str);
      str = "drop table Museums"; // drops the table
      stmt.execute(str);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println("Failed to drop tables");
      e.printStackTrace();
    }
    exit(0);
  }
}