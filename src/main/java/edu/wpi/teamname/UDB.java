package edu.wpi.teamname;


import java.sql.*;
import java.util.Scanner;
import java.util.*;
import static java.lang.System.exit;

public class UDB {

  private static Connection conn = null; // used for connection
  private static Statement stmt; // used for sql statements
  private static ResultSet rset; // used to store returns from sql queries
  private static String url =
      "jdbc:derby:UDB;user=admin;password=admin;create=true"; // link of embedded database
  private static int option; // used to know what function to run


  public static void main(String[] args) { // java -jar path username admin option
    if(!args[0].equals("admin") || !args[1].equals("admin")){
      System.out.println("Invalid credentials: "+ args[0] + " " + args[1]);
      System.out.println("Please enter in the form <username> <password> <option>");
      exit(1);
    }
    Scanner s = new Scanner(System.in);
    if (args.length > 2 && Integer.parseInt(args[2]) < 5){
      option = Integer.parseInt(args[2]);
    }
    else {
      System.out.println(
              "1-Report Museum Information\n"
                      + "2-Report Paintings in Museums\n"
                      + "3-Update Museum Phone Number\n"
                      + "4-Exit Program");
      System.out.println("Please enter an option");
      option = s.nextInt();
    }
    driver();
    connect();
    init();
    insertData();
    while (option != 4) {
      switch (option) {
        case 1:
          printMuseums();
          System.out.println("Enter another option?");
          option = s.nextInt();
          break;
        case 2:
          printByName();
          System.out.println("Enter another option?");
          option = s.nextInt();
          break;
        case 3:
          updatePhoneNumber();
          System.out.println("Enter another option?");
          option = s.nextInt();
          break;
        default:
          System.out.println(
                  "1-Report Museum Information\n"
                          + "2-Report Paintings in Museums\n"
                          + "3-Update Museum Phone Number"
                          + "4-Exit Program");
          option = s.nextInt();
          break;
      }
    }
    System.out.println("Exiting the program");
    stop();
  }

  public UDB(List<String> p) {
    String[] s = p.toArray(new String[0]);
    main(s);
  }

  public static void driver() {
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      // registers the driver for the database
    } catch (Exception e) {
      System.out.println("Driver registration failed");
      e.printStackTrace();
    }
  }

  public static void connect() {
    try {
      conn = DriverManager.getConnection(url);
      // establishes a connection to the database
    } catch (Exception e) {
      System.out.println("Connection failed");
      e.printStackTrace();
    }
  }

  public static void init() {
    try {
      DatabaseMetaData dmd = conn.getMetaData();
      ResultSet rs = dmd.getTables(null, "ADMIN", "MUSEUMS", null);
      if (!rs.next()) {
        String tbl1 =
            "create table Museums (id int not null generated always as identity, paintings int not null, name varchar(50), phone varchar(50), primary key (id))";
        // code for creating table of Museums
        PreparedStatement ps1 = conn.prepareStatement(tbl1);
        ps1.execute();
        String tbl2 =
            "create table Paintings (id int not null generated always as identity, name varchar(50), artist varchar(50), museum int not null references Museums, primary key(id))";
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
      String test = "select name from Museums";
      PreparedStatement t = conn.prepareStatement(test);
      ResultSet rs = t.executeQuery();
      if (option == 4){
        rs.close();
      }
      if (!rs.next()) {
        String str =
            "insert into Museums("
                + "paintings, name, phone) values "
                + "(6, 'Smithsonian', '914990'), "
                + "(4, 'Acropolis', '839000'), "
                + "(2, 'British Museum', '914997'), "
                + "(5, 'Metropolitan', '914910'), "
                + "(3, 'Vatican', '914933')";
        PreparedStatement ps = conn.prepareStatement(str);
        ps.execute();
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
        PreparedStatement ps2 = conn.prepareStatement(str);
        ps2.execute();
      }

    } catch (Exception e) {
      System.out.println("Failed to insert into table");
      e.printStackTrace();
    }
  }

  public static void printMuseums() {
    try {
      String str = "select * from Museums";
      PreparedStatement ps = conn.prepareStatement(str);
      rset = ps.executeQuery();
      // store the data inside of a ResultSet object
      while (rset.next()) { // iterates through the object row by row
        int id = rset.getInt("id"); // gets value at column id
        String name = rset.getString("name"); // gets value at column name
        String phone = rset.getString("phone"); // gets value at column phone
        System.out.println("ID:" + id + " Name:" + name + " Phone number:" + phone + "\n");
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