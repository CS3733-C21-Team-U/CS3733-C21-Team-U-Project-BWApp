package edu.wpi.u.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public abstract class Data {
    protected Connection conn = null;
    protected ResultSet rset;
    protected String url = "jdbc:derby:BWdb;bootPassword=bwdbpassword";
    protected static Database db;

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

    public void readCSV(String filePath, String tableName){

        String tempPath = "src/main/resources/edu/wpi/u/temp.csv";
        String str1 = "CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE ('APP', '" + tableName.toUpperCase() + "', '" + tempPath + "', ', ', null, null,1)";

        try {
            String content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
            String[] columns = content.split("\n", 2);
            //String[] attributes = content.split(","); TODO: Make table columns from header values
            columns[1] += "\n";
            File temp = new File(tempPath);
            if(temp.createNewFile()){
                System.out.println("File created");
            }
            FileWriter myWriter = new FileWriter(tempPath);
            myWriter.write(columns[1]);
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement p = conn.prepareStatement(str1);
            p.execute();
        }
        catch (Exception e){
            System.out.println("Rewrite being weird");
            e.printStackTrace();
        }
    }

    public void saveCSV(String tableName, String filePath, String header){
        File f = new File(filePath);
        if(f.delete()){
            System.out.println("File deleted");
        }
        String str = "CALL SYSCS_UTIL.SYSCS_EXPORT_TABLE ('APP','" + tableName.toUpperCase() + "','" + filePath + "',',',null,null)";
        try {
            PreparedStatement ps = conn.prepareStatement(str);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Wants new file");
            e.printStackTrace();
        }

        try {
            String content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
            FileWriter fw = new FileWriter(filePath);
            fw.write(header);
            fw.write("\n");
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean updateField(String tableName, String idField, String id, String field, String val) {
        try {
            String str = "update " + tableName + " set" + field + "=? where " + idField + "=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setString(1, val);
            ps.setString(2, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update " + tableName + "'s " + field );
            return false;
        }
        return true;
    }

    public boolean updateField(String tableName, String idField, String id, String field, int val) {
        try {
            String str = "update " + tableName + " set" + field + "=? where " + idField + "=?";
            PreparedStatement ps = conn.prepareStatement(str);
            ps.setInt(1, val);
            ps.setString(2, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update " + tableName + "'s " + field );
            return false;
        }
        return true;
    }

    public void deleteTables() {
        try {
            String str = "drop table Nodes";
            Statement s = conn.createStatement();
            s.execute(str);
            str = "drop table Edges";
            s.execute(str);
            str = "drop table Requests";
            s.execute(str);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dropValues() {
        try {
            String str = "delete from Nodes";
            Statement ps = conn.createStatement();
            ps.execute(str);
            str = "delete from Edges";
            ps.execute(str);
            str = "delete from Requests";
            ps.execute(str);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void stop() {
        dropValues();
        deleteTables();
        try{
            System.out.println("herro");
            //conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
