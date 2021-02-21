package edu.wpi.u.database;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class Database {

    private static Connection conn = null;
    private final static String url = "jdbc:derby:BWdb;create=true";

    public Database() {
        driver();
        connect();
        //deleteTables();
        init();
        //insertNodeData();
        //insertEdgeData();
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

    public static boolean isTableEmpty() {
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, "ADMIN", "NODES", null);
            return !rs.next();
            // nowhere to put rs.close
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void insertNodeData(){
        String str = "";
        Path p = Paths.get("src/","main","resources","edu","wpi","u","OutsideMapNodes.csv");
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
                //ps.executeUpdate
                ps.execute();
            }
        } catch (Exception e) {
            System.out.println("Failed to insert into table");
            e.printStackTrace();
        }
    }

    public static void insertEdgeData() {
        String str = "";
        Path p = Paths.get("src/","main","resources","edu","wpi","u","OutsideMapEdges.csv");
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

    public static void deleteTables() {
        try {
            String str = "drop table Nodes";
            Statement s = conn.createStatement();
            s.execute(str);
            str = "drop table Edges";
            s.execute(str);
        }
        catch (Exception e){
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

    public static void stop() {
//        saveNodesCSV();
//        saveEdgesCSV();
        dropValues();
        deleteTables();
        try{
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    /*
     try {
         DriverManager.getConnection
            ("jdbc:derby:;shutdown=true");
      } catch (SQLException ex) {
         if (((ex.getErrorCode() == 50000) &&
            ("XJ015".equals(ex.getSQLState())))) {
               System.out.println("Derby shut down
                  normally");
         } else {
            System.err.println("Derby did not shut down
               normally");
            System.err.println(ex.getMessage());
         }
      }
   }
     */
    }
}