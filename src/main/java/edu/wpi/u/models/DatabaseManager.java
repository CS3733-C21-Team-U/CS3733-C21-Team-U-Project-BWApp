package edu.wpi.u.models;

import java.sql.*;

public class DatabaseManager {

    private static Connection conn = null;
    private static String url =
            "jdbc:derby:BWdb;user=admin;password=admin";

    public static void main(String[] args) {
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

    /*
    All functions will return 1 on success & return 0 on failure
     */
    public int addNode(String node_id, int x, int y, int floor, String building, String longname, String shortname) {
        try {
            String str = "insert into Nodes (nodeID, xcoord, ycoord, floor, building, longname, shortname, 'U') values (?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, node_id);
            ps.setInt(2, x);
            ps.setInt(3, y);
            ps.setInt(4, floor);
            ps.setString(5, building);
            ps.setString(6, longname);
            ps.setString(7, shortname);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add node");
            return 0;
        }
        return 1;
    }

    public int updCoords(String node_id, int new_x, int new_y) {
        try {
            String str = "update Nodes set nodeID=? where nodeID=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, node_id);
            ps.setString(2, node_id);
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
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update coordinates");
            return 0;
        }
        return 1;
    }

    public int delNode(String node_id) {
        try {
            String str = "delete from Nodes where node_id=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, node_id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete node");
            return 0;
        }
        return 1;
    }

    public int addEdge(String edge_id, String start_node_id, String end_node_id) {
        try {
            String str = "insert into Edges (edgeId, startID, endID) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, edge_id);
            ps.setString(2, start_node_id);
            ps.setString(3, end_node_id);
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
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update start ID");
            return 0;
        }
        return 1;
    }

    /*
    Write function to validate nodes and edges
     */
}
