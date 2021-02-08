package edu.wpi.teamname;

import java.sql.*;

public class UDB {
  private Connection conn = null;
  private Statement stmt;
  private ResultSet rset;
  private String url = "jdbc:derby:UDB;create=true";

  public UDB() {
    driver();
    connect();
    init();
    insertData();
    printMuseums();
    // stop();
  }

  public void driver() {
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (Exception e) {
      System.out.println("Driver registration failed");
      e.printStackTrace();
    }
    System.out.println("Driver Registered & Database Created!");
  }

  public void connect() {
    try {
      conn = DriverManager.getConnection(url);
    } catch (Exception e) {
      System.out.println("Connection failed");
      e.printStackTrace();
    }
    System.out.println("Connection succeeded");
  }

  public void init() {
    try {
      Statement stmt = conn.createStatement();
      String tbl1 =
          "create table Museums (id int not null generated always as identity, paintings int not null, name varchar(50), phone varchar(50), primary key (id))";
      stmt.execute(tbl1);
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
      rset = stmt.executeQuery(str);
      int id = rset.getInt("id");
      String name = rset.getString("name");
      String phone = rset.getString("phone");
      while (rset.next()) {
        System.out.println("ID: " + id + " Name: " + name + " Location: " + phone + "\n");
      }
      rset.close();
    } catch (Exception e) {
      System.out.println("Failed to select from Museums");
    }
  }

  /*
  Needs to be written
  */
  public void printByName() {}

  /*
  Needs to be written
  */
  public void updatePhoneNumber(int museum_number) {}
  /*
  Only run when given the stop command of '4'
   */
  public void stop() {
    try {
      stmt = conn.createStatement();
      String str = "alter table Paintings drop column museum";
      stmt.execute(str);
      str = "drop table Paintings";
      stmt.execute(str);
      str = "drop table Museums";
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
