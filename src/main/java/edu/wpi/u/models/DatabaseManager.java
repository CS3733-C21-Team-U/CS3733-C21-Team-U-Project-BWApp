package edu.wpi.u.models;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Comparator;

public class DatabaseManager {

  private static Connection conn = null;
  private static ResultSet rset;
  private static String url = "jdbc:derby:BWdb;user=admin;password=admin;create=true";

  public static void main(String[] args) throws SQLException, IOException {
    DatabaseManager db = new DatabaseManager();
  }

  public DatabaseManager() throws IOException, SQLException {
    /*
    Run these next 4 only on boot/start
     */
    driver();
    connect();
    init();
    insertNodeData();
    insertEdgeData();

    // Prints nodes

    printNodes();
    /*
    Only run these two below on exit / finish
     */
    saveNodesCSV();
    saveEdgesCSV();
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
    } catch (Exception e) {
      System.out.println("Connection failed");
      e.printStackTrace();
    }
  }

  public static void init() {
    try {
      if (isTableEmpty()) {
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
    try {
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
    String str = "";
    Path p = Paths.get("src/", "main", "resources", "edu", "wpi", "u", "OutsideMapNodes.csv");
    if (isTableEmpty()) {
      try {
        BufferedReader br = Files.newBufferedReader(p);

        String DELIMITER = ",";

        // read the file line by line
        // String line;
        br.readLine(); // this will read the first line
        String line1 = null;
        while ((line1 = br.readLine()) != null) {
          str =
              "insert into Nodes (nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, teamAssigned) values (?,?,?,?,?,?,?,?,?)";
          PreparedStatement ps = conn.prepareStatement(str);

          // convert line into columns
          String[] columns = line1.split(DELIMITER);
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
        }
      } catch (Exception e) {
        System.out.println("Failed to insert into table");
        e.printStackTrace();
      }
    }
  }

  public static void insertEdgeData() throws SQLException {
    String str = "";
    Path p = Paths.get("src/", "main", "resources", "edu", "wpi", "u", "OutsideMapEdges.csv");
    if (isTableEmpty()) {
      try {
        BufferedReader br = Files.newBufferedReader(p);

        String DELIMITER = ",";

        // read the file line by line
        // String line;
        br.readLine(); // this will read the first line
        String line1 = null;
        while ((line1 = br.readLine()) != null) {
          str = "insert into Edges (edgeID, startID, endID) values (?,?,?)";
          PreparedStatement ps = conn.prepareStatement(str);

          // convert line into columns
          String[] columns = line1.split(DELIMITER);
          ps.setString(1, columns[0]);
          ps.setString(2, columns[1]);
          ps.setString(3, columns[2]);
          ps.execute();
        }

        System.out.println();

      } catch (Exception e) {
        System.out.println("Failed to insert into table");
        e.printStackTrace();
      }
    }
  }

  public static boolean isTableEmpty() {
    try {
      DatabaseMetaData dmd = conn.getMetaData();
      ResultSet rs = dmd.getTables(null, "ADMIN", "NODES", null);
      return !rs.next();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static void printNodes() {
    try {
      String str = "select * from Nodes";
      PreparedStatement ps = conn.prepareStatement(str);
      rset = ps.executeQuery();
      // store the data inside of a ResultSet object
      while (rset.next()) { // iterates through the object row by row
        String nodeID = rset.getString("nodeID");
        int xcoord = rset.getInt("xcoord"); // gets value at column name
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

      BufferedWriter fileWriter =
          new BufferedWriter(new FileWriter("src/main/resources/edu/wpi/u/OutsideMapNodes.csv"));

      // write header line containing column names
      fileWriter.write(
          "nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, teamAssigned");

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

        String line =
            String.format(
                "%s,%d,%d,%d,%s,%s,%s,%s,%s",
                nodeID,
                xcoord,
                ycoord,
                floor,
                building,
                nodeType,
                longName,
                shortName,
                teamAssigned);

        fileWriter.newLine();
        fileWriter.write(line);
      }

      statement.close();
      fileWriter.close();

    } catch (SQLException e) {
      System.out.println("Database error:");
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

      BufferedWriter fileWriter =
          new BufferedWriter(new FileWriter("src/main/resources/edu/wpi/u/OutsideMapEdges.csv"));

      // write header line containing column names
      fileWriter.write("edgeID, startID, endID");

      while (result.next()) {
        String edgeID = result.getString("edgeID");
        String startID = result.getString("startID");
        String endID = result.getString("endID");

        String line = String.format("%s,%s,%s", edgeID, startID, endID);

        fileWriter.newLine();
        fileWriter.write(line);
      }

      statement.close();
      fileWriter.close();

    } catch (SQLException e) {
      System.out.println("Database error:");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("File IO error:");
      e.printStackTrace();
    }
  }

  public int addNode(
      String node_id,
      int x,
      int y,
      int floor,
      String building,
      String node_type,
      String longname,
      String shortname) {
    try {
      // nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, teamAssigned
      String str =
          "insert into Nodes (nodeID, xcoord, ycoord, floor, building, nodeType, longname, shortname, teamAssigned) values (?,?,?,?,?,?,?,?,?)";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, node_id);
      ps.setInt(2, x);
      ps.setInt(3, y);
      ps.setInt(4, floor);
      ps.setString(5, building);
      ps.setString(6, node_type);
      ps.setString(7, longname);
      ps.setString(8, shortname);
      ps.setString(9, "u");
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to add node");
      return 0;
    }
    return 1;
  }

  public int updCoords(String node_id, int new_x, int new_y) {
    try {
      String str = "update Nodes set xcoord=?, ycoord=? where nodeID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setInt(1, new_x);
      ps.setInt(2, new_y);
      ps.setString(3,node_id);
      ps.setString(2, node_id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to update coordinates");
      return 0;
    }
    return 1;
  }

  public int updFloor(String node_id, int new_floor_number) {
    try {
      String str = "update Nodes set floor=? where nodeID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setInt(1, new_floor_number);
      ps.setString(2, node_id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to update floor #");
      return 0;
    }
    return 1;
  }

  public int updBuilding(String node_id, String new_building) {
    try {
      String str = "update Nodes set building=? where nodeID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, new_building);
      ps.setString(2, node_id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to update building");
      return 0;
    }
    return 1;
  }

  public int updLongname(String node_id, String new_longname) {
    try {
      String str = "update Nodes set longname=? where nodeID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, new_longname);
      ps.setString(2, node_id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to update longname");
      return 0;
    }
    return 1;
  }

  public int updShortname(String node_id, String new_shortname) {
    try {
      String str = "update Nodes set shortname=? where nodeID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, new_shortname);
      ps.setString(2, node_id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to update coordinates");
      return 0;
    }
    return 1;
  }

  public int delNode(String node_id) {
    try {
      String str = "delete from Nodes where nodeID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, node_id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to delete node");
      return 0;
    }
    return 1;
  }

  public int delNodeCoord(int x, int y) {
    try {
      String str = "delete from Nodes where xcoord=? and ycoord=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setInt(1, x);
      ps.setInt(2, y);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to delete node");
      return 0;
    }
    System.out.println("Successful delete");
    return 1;
  }

  public int addEdge(String edge_id, String start_node_id, String end_node_id) {
    try {
      String str = "insert into Edges (edgeId, startID, endID) values (?,?,?)";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, edge_id);
      ps.setString(2, start_node_id);
      ps.setString(3, end_node_id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to add edge");
      return 0;
    }
    return 1;
  }

  public int updEdgeStart(String edge_id, String new_start_node_id) {
    try {
      String str = "update Edges set startID=? where edgeID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, new_start_node_id);
      ps.setString(2, edge_id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to update start ID");
      return 0;
    }
    return 1;
  }

  public int updEdgeEnd(String edge_id, String new_end_node_id) {
    try {
      String str = "update Edges set endID=? where edgeID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, new_end_node_id);
      ps.setString(2, edge_id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to update end ID");
      return 0;
    }
    return 1;
  }

  public int delEdge(String edge_id) {
    try {
      String str = "delete from Edges where edgeID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, edge_id);
      ps.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failed to update start ID");
      return 0;
    }
    return 1;
  }

  public int delEdgeByNodes(String start_node_id, String end_node_id){
    try {
      String str = "delete from Edges where startID=?, endID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, start_node_id);
      ps.setString(2, end_node_id);
      ps.execute();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return 0;
    }
  return 1;
  }

  public static boolean isNode(String node_id) {
    try {
      String str = "select nodeID from Nodes where nodeID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, node_id);
      rset = ps.executeQuery();
      return rset.next();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean isEdge(String edge_id) {
    try {
      String str = "select edgeID from Edges where edgeID=?";
      PreparedStatement ps = conn.prepareStatement(str);
      ps.setString(1, edge_id);
      rset = ps.executeQuery();
      return rset.next();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static void stop() {
    saveNodesCSV();
    saveEdgesCSV();
    dropValues();
  }
}
