package edu.wpi.teamname;

import java.sql.*;

public class UDB {

  private Connection conn = null; // used for connection
  private Statement stmt; // used for sql statements
  private ResultSet rset; // used to store returns from sql queries
  private String url =
      "jdbc:derby:UDB;create=true;user=admin;password=admin"; // link of embedded database
  private static int option; // used to know what function to run

  private int flag; // flag for detecting if first time running 

  public static void main(String args[]) {
    // Input logic
  }

  public UDB() {
    /*
    Always run driver() & connect().
    init(), insertData() only run on creation
    stop() only run on exit (option 4)
    All other functions are options based
     */
    driver();
    connect();
    // createAdmin();
    // init();
    // insertData();
    // printMuseums();
    // printByName();
    // stop();
  }

  public void driver() {
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      // registers the driver for the database
    } catch (Exception e) {
      System.out.println("Driver registration failed");
      e.printStackTrace();
    }
    System.out.println("Driver Registered & Database Created!");
  }

  public void connect() {
    try {
      conn = DriverManager.getConnection(url);
      // establishes a connection to the database
    } catch (Exception e) {
      System.out.println("Connection failed");
      e.printStackTrace();
    }
    System.out.println("Connection succeeded");
  }

  public void createAdmin() {
    try {
      stmt = conn.createStatement();
      String str = "call syscs_util.syscs_create_user('admin', 'admin')";
      stmt.execute(str);
    } catch (Exception e) {
      System.out.println("Failed to create user");
      e.printStackTrace();
    }
  }

  public void init() {
    try {
      Statement stmt = conn.createStatement(); // creates statement
      String tbl1 =
          "create table Museums (id int not null generated always as identity, paintings int not null, name varchar(50), phone varchar(50), primary key (id))";
      // code for creating table of Museums
      stmt.execute(tbl1); // executes the sql in tbl1 string
      String tbl2 =
          "create table Paintings (id int not null generated always as identity, name varchar(50), artist varchar(50), museum int not null references Museums, primary key(id))";
      stmt.execute(tbl2);
    } catch (Exception e) {
      System.out.println("Table creation failed");
      e.printStackTrace();
    }
    System.out.println("Table creation succeeded!");
  }

  public void insertData() {
    try {
      Statement stmt = conn.createStatement();
      String str =
          "insert into Museums("
              + "paintings, name, phone) values "
              + "(6, 'Smithsonian', '914'), "
              + "(4, 'Acropolis', '839'), "
              + "(2, 'British Museum', '4600'), "
              + "(5, 'Metropolitan', '911'), "
              + "(3, 'Vatican', '90909')";
      stmt.execute(str);
      str =
          "insert into Paintings("
              + "name, artist, museum) values "
              + "('Painting 1', 'Artist 1', 1), "
              + "('Painting 2', 'Artist 2', 2), "
              + "('Painting 3', 'Artist 3', 3),"
              + "('Painting 4', 'Artist 4', 4),"
              + "('Painting 5', 'Artist 5', 5),"
              + "('Painting 6', 'Artist 6', 1), "
              + "('Painting 7', 'Artist 7', 1), "
              + "('Painting 8', 'Artist 8', 4),"
              + "('Painting 9', 'Artist 9', 4),"
              + "('Painting 10', 'Artist 10', 5),"
              + "('Painting 11', 'Artist 11', 1), "
              + "('Painting 12', 'Artist 12', 2), "
              + "('Painting 13', 'Artist 13', 4),"
              + "('Painting 14', 'Artist 14', 2),"
              + "('Painting 15', 'Artist 15', 2),"
              + "('Painting 16', 'Artist 16', 1), "
              + "('Painting 17', 'Artist 17', 1), "
              + "('Painting 18', 'Artist 18', 3),"
              + "('Painting 19', 'Artist 19', 4),"
              + "('Painting 20', 'Artist 20', 5)";
      stmt.execute(str);
    } catch (Exception e) {
      System.out.println("Failed to insert into table");
      e.printStackTrace();
    }
    System.out.println("Successfully inserted into database!");
  }

  public void printMuseums() {
    try {
      stmt = conn.createStatement();
      String str = "select * from Museums";
      // gets all column data from Museums
      rset = stmt.executeQuery(str);
      // store the data inside of a ResultSet object
      while (rset.next()) { // iterates through the object row by row
        int id = rset.getInt("id"); // gets value at column id
        String name = rset.getString("name"); // gets value at column name
        String phone = rset.getString("phone"); // gets value at column phone
        System.out.println("ID:" + id + " Name:" + name + " Location:" + phone + "\n");
      }
      rset.close(); // close the object
    } catch (Exception e) {
      System.out.println("Failed to select from Museums");
    }
  }

  public void printByName() {
    // prints name of each museum followed by the paintings in them
    try {
      String str2;
      ResultSet rset2;
      stmt = conn.createStatement();
      String str = "select * from Paintings"; // we want all paintings info
      rset = stmt.executeQuery(str);
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
          System.out.println(
              "Museum: "
                  + rset2.getString("name")
                  + " Name of Painting: "
                  + name
                  + " Artist: "
                  + artist);
        }
        rset2.next();
      }
    } catch (Exception e) {
      System.out.println("Failed to get museums and paintings");
      e.printStackTrace();
    }
  }

  /*
  Needs to be written
  */
  public void updatePhoneNumber(String museum, int new_phone_number) {
    try {
      String str = "update Museums set phone=? where name=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setInt(1, new_phone_number);
      ps.setString(1, museum);
      ps.execute();
    } catch (Exception e) {
      System.out.println("Failed to update phone number");
      e.printStackTrace();
    }
  }
  /*
  Only run when given the stop command of '4'
   */
  public void stop() {
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
    System.out.println("Tables dropped & STMT, & CONN closed!");
  }
}
