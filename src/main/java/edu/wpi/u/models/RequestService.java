package edu.wpi.u.models;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.database.RequestData;
import edu.wpi.u.requests.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

public class RequestService {


  static RequestData rd = new RequestData();
  ArrayList<Request> activeRequests = new ArrayList<>();

  public RequestService() {
    this.activeRequests = rd.loadActiveRequests();
  }

  /*
  Saves the model data into csvs and drops tables
   */

  public void loadCSVFile(String path, String tableName){
    rd.dropValues();
    rd.readCSV(path,tableName);
    this.activeRequests = rd.loadActiveRequests();
  }

  public void saveCSVFile(String path, String tableName){
    rd.dropValues();
    rd.saveCSV(path,tableName, "test"); // TODO: Provide header
  }
  /*
  Make sure x & y are positive integers within the map coordinate range
  */
  public String addRequest(String description, LinkedList<String> assignee, String title, LinkedList<String> location, String type, String creator) {
    //Scucess
    Random rand = new Random();
    int requestID = rand.nextInt();
    String ID = Integer.toString(requestID);//make a random id
    // String requestID,LinkedList<String> assignee, Date dateCreated, Date dateCompleted, String description, String title, LinkedList<String> location, String type, String creator) {
    Request newRequest = new Request(ID, null, new Date(), new Date(), description ,title,null, type, creator);
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

  public String updateRequest(String requestID, String title, String description,Date dateCompleted, LinkedList<String> location, String type, LinkedList<String> assignee, String creator){
    //Scucess
    for(Request r : this.activeRequests){
      if(r.getRequestID() == requestID){
        r.editRequest(dateCompleted,description,title,location,type,assignee,creator);
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
