package edu.wpi.u.database;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.MapManager;
import edu.wpi.u.users.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MapData extends Data{
    public MapData(){
        connect();
        getLongnames();
    }

    public int addNode(String node_id, double x, double y, String floor, String building, String node_type, String longname, String shortname) {
        try {
            String str =
                    "insert into Nodes (nodeID, xcoord, ycoord, floor, building, nodeType, longname, shortname, teamAssigned) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, node_id);
            ps.setDouble(2, x);
            ps.setDouble(3, y);
            ps.setString(4, floor);
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

    public int updateCoords(String node_id, double new_x, double new_y) {
        try {
            String str = "update Nodes set xcoord=?, ycoord=? where nodeID=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setDouble(1, new_x);
            ps.setDouble(2, new_y);
            ps.setString(3,node_id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update coordinates");
            return 0;
        }
        return 1;
    }

    public HashMap<String, String> getLongnames(){
        HashMap<String,String> result = new HashMap<>();
        String str = "select longName,nodeID from Nodes where nodeType !=? and nodeType !=?";
        try{
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, "HALL");
            ps.setString(2, "WALK");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                result.put(rs.getString("longName"), rs.getString("nodeID"));
            }
            rs.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public int updFloor(String node_id, String new_floor_number) {
        try {
            String str = "update Nodes set floor=? where nodeID=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, new_floor_number);
            ps.setString(2, node_id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update floor #");
            return 0;
        }
        return 1;
    }

    public int updateNodeType(String node_id, String newNodeType) {
        try {
            String str = "update Nodes set nodeType=? where nodeID=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, newNodeType);
            ps.setString(2, node_id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update node type in the database");
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

    public int deleteNode(String node_id) {
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
            ps.setDouble(1, x);
            ps.setDouble(2, y);
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
            System.out.println("Edges are being added to the Database");
            String str = "insert into Edges (edgeID, startID, endID) values (?,?,?)";
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

    public int updateEdgeStart(String edge_id, String new_start_node_id) {
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

    public int updateEdgeEnd(String edge_id, String new_end_node_id) {
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

    public int deleteEdge(String edge_id) {
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

    public void loadGraph(MapManager mm){
        try{
            Statement ps = conn.createStatement();
            String str = "select * from Nodes";
            rset = ps.executeQuery(str);
            while (rset.next()) {
                String id = rset.getString("nodeID");
                double x = rset.getDouble("xcoord");
                double y = rset.getDouble("ycoord");
                String floor = rset.getString("floor");
                String building = rset.getString("building");
                String nodeType = rset.getString("nodeType");
                String longName = rset.getString("longName");
                String shortName = rset.getString("shortName");
                mm.addNode(id,x,y,floor,building,nodeType,longName,shortName,"u");
                String key = nodeType + floor;
                String stringIndex = id.substring(5,8);
                int index = Integer.valueOf(stringIndex);
                if(!App.mapService.currentIDNumber.containsKey(key)){
                    App.mapService.currentIDNumber.put(key, index);
                }else if(App.mapService.currentIDNumber.get(key) < index){
                    App.mapService.currentIDNumber.put(key, index);
                }
            }
            rset.close();
            String str2 = "select * from Edges";
            PreparedStatement ps2 = conn.prepareStatement(str2);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()){
                String id = rs2.getString("edgeID");
                String start = rs2.getString("startID");
                String end = rs2.getString("endID");
                ArrayList<Role> perms = this.getUserTypes(id);
                 mm.addEdge(id,start,end, perms);
            }
            rs2.close();
        }
        catch (Exception e){
            System.out.println("Failed to load graph");
            e.printStackTrace();
        }
    }

    public boolean isNode(String node_id) {
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

    public boolean isEdge(String edge_id) {
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

    public Node getNode(String node_id){
        try {
            String str = "select * from Nodes where nodeID=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ResultSet rs = ps.executeQuery();
            Node n = new Node(rs.getString("nodeId"), rs.getDouble("xcoord"), rs.getDouble("ycoord"), rs.getString("floor"), rs.getString("building"), rs.getString("node_type"), rs.getString("longname"), rs.getString("shortname"), "u");
            rs.close();
            return n;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates Returns list of users who have permission to inputted edge
     * @param edgeID - Desired edge
     * @return Arraylist of Strings, representing types of users with permission
     */
    public ArrayList<Role> getUserTypes(String edgeID) {
        ArrayList<Role> userTypes = new ArrayList<>();
        try {
            String str = "select * from Permissions where edgeID=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, edgeID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userTypes.add(Role.valueOf(rs.getString("userType")));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userTypes;
    }

    /**
     * Updates multiple user types assosciated with the edge at once by deleting
     * the edge's past permissions and adding new ones specified by the list
     * @param edgeID - Desired edge
     * @param permissions - new permission to be added
     */
    public void updatePermissions(String edgeID, ArrayList<Role> permissions){
        try {
            String str = "delete from Permissions where edgeID=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,edgeID);
            ps.execute();
            // Add function
            for(Role user: permissions){
                addPermission(edgeID, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update permissions.");
        }
    }

    /**
     * Adds a single specified user type to the table for the specified edge (new permission)
     * @param edgeID - Desired edge
     * @param staffType - New permission to be added
     */
    public void addPermission(String edgeID, Role staffType){
        try {
            String str = "insert into Permissions (permissionID, edgeID, userType) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1,"" + edgeID + "_" + String.valueOf(staffType));
            ps.setString(2, edgeID);
            ps.setString(3, String.valueOf(staffType));
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add permission.");
        }
    }

    /**
     * Removes a single permission from the table
     * @param edgeID - Edge in position
     */
    public void removePermission(String edgeID){
        try {
            String str = "delete from Permissions where edgeID=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, edgeID);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to remove permission.");
        }
    }
}
