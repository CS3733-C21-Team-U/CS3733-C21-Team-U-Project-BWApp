package edu.wpi.u.database;

import java.sql.*;

public abstract class Data {
    protected Connection conn = null;
    protected ResultSet rset;
    protected String url = "jdbc:derby:BWdb";

    public Data(){

    }

    public void connect() {
        try {
            conn = DriverManager.getConnection(url);
            //conn.setAutoCommit(false);
            //conn.commit();
        } catch (Exception e) {
            System.out.println("Connection failed");
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
}
