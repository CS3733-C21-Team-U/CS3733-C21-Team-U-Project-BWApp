package edu.wpi.u.models;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.database.RequestData;
import edu.wpi.u.requests.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class RequestService {


  static RequestData rd = new RequestData();
  ArrayList<Request> activeRequests = new ArrayList<>();

  public RequestService() {
    System.out.println("Constructor for Request SERVICE");
  }

  /*
  Saves the model data into csvs and drops tables
   */
  public void saveAndExitDB(){
    rd.stop();
  }

  public void loadCSVFile(String path, String tableName){
    rd.dropValues();
    rd.readCSV(path,tableName);
  }

  public void saveCSVFile(String path, String tableName){
    rd.dropValues();
    rd.saveCSV(path,tableName, "test"); // TODO: Provide header
  }
  /*
  Make sure x & y are positive integers within the map coordinate range
  */
  public String addRequest(String requestID, Date dateCreated, Date dateCompleted, String description, String title, String location, String type) {
    //Scucess
    Request newRequest = new Request(requestID, dateCreated, dateCompleted, description, title, location, type);
    this.activeRequests.add(newRequest);
    rd.addRequest(newRequest);
    return "";
    //Fail
    //return requestID;
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
  }

  public String updateRequest(String requestID, String title, String description,Date dateCompleted, String location, String type){
    //Scucess
    for(Request r : this.activeRequests){
      if(r.getRequestID() == requestID){
        r.editRequest(dateCompleted,description,title,location,type);
        rd.updateRequest(r);
        return "";
      }
    }
    return requestID;
    //Fail
    //return requestID;
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
  }

  public String deleteRequest(String requestID) {
    //Scucess
    Date now = new Date();
    for(Request r : this.activeRequests){
      if(r.getRequestID() == requestID){
        r.setDateCompleted(now);
        rd.delRequest(r);
        return "";
      }
    }
    return "";
    //Fail
    //return requestID;
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node does not exists / invalid
     */
    //        dm.delNode(node_id);
  }

  public ArrayList<Request> getRequests() {
    return new ArrayList<Request>(this.activeRequests);
    /*
    Returns an ArrayList of all Node Objects in the graph
     */
  }
}
