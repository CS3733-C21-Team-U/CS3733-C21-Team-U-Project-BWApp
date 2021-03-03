package edu.wpi.u.models;

import edu.wpi.u.database.Database;
import edu.wpi.u.database.RequestData;
import edu.wpi.u.requests.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

public class RequestService {


  static RequestData rd = new RequestData();
  ArrayList<IRequest> activeRequests = new ArrayList<>();

  public RequestService() {
    this.activeRequests = rd.loadActiveRequests();
    for (IRequest x : this.activeRequests){

      System.out.println("Req: "+ x.getGenericRequest().getRequestID());
    }
  }

  /*
  Saves the model data into csvs and drops tables
   */

  public void loadCSVFile(String path, String tableName){
    Database.getDB().dropValues();
    Database.getDB().readCSV(path,tableName);
    //this.activeRequests = rd.loadActiveRequests();
  }

  public void saveCSVFile(String path, String tableName){
    Database.getDB().saveCSV(tableName,path , "test"); // TODO: Provide header
  }
  /*
  Make sure x & y are positive integers within the map coordinate range
  */
  public String addRequest(String description, LinkedList<String> assignee, String title, LinkedList<String> location, String type, String creator, LinkedList<Serializable> specifics) {

    IRequest result = new RequestFactory().makeRequest(type);

    Random rand = new Random();
    int requestID = rand.nextInt();
    String ID = Integer.toString(requestID);//make a random id
    // String requestID,LinkedList<String> assignee, Date dateCreated, Date dateCompleted, String description, String title, LinkedList<String> location, String type, String creator) {
    Request newRequest = new Request(ID, assignee, new Date(), null, description ,title,location, type, creator);

    result.setRequest(newRequest);
    result.setSpecificData(specifics);

    rd.addRequest(result);
    this.activeRequests.add(result);
    return "";
    //Fail
    //return requestID;
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
  }

  public void addRequest(IRequest result) {
    rd.addRequest(result);
    this.activeRequests.add(result);
  }


  public String updateRequest(String requestID, String description, LinkedList<String> assignee, String title, LinkedList<String> location,
                              Date dateGiven, String type, String creator, LinkedList<Serializable> specifics) {
   for(IRequest Ir : this.activeRequests){
     Request r = Ir.getGenericRequest();
     if(r.getRequestID() == requestID){
       /*if(dateGiven != null){ // No longer Used
         this.activeRequests.remove(r);
       }*/
       r.editRequest(dateGiven,description,title,location,type,assignee,creator);
       Ir.setRequest(r);
       Ir.setSpecificData(specifics);
       rd.updateRequest(Ir);
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

  public void updateRequest(IRequest result) {
    rd.updateRequest(result);
  }




  public String deleteRequest(String requestID) {
    //Success
    long time = System.currentTimeMillis();
    Date now = new Date(time);
    for(IRequest r : this.activeRequests){
      if(r.getGenericRequest().getRequestID() == requestID){
        r.getGenericRequest().setDateCompleted(now);
        this.activeRequests.remove(r);
        rd.resolveRequest(requestID, time);
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

  public ArrayList<IRequest> getRequests() {
    boolean debug = false;
//    if(debug){ //Adding fake requests just so we can test UI - currently it always returns a 0 length array
//      ArrayList<Request> temp = new ArrayList<Request>();
//      LinkedList<String> list = new LinkedList<String>();
//      list.add("Nothing");
//      list.add("Here");
//      Request r1 = new Request("FakeID",list ,new Date(1000), new Date(2000),"No Description","Fake Title",list, "No Type","Admin");
//      temp.add(r1);
//      return temp;
//
//    }else{
      return new ArrayList<IRequest>(this.activeRequests);
    }

  public void addRequest(String text, String text1) {
  }

    /*
    Returns an ArrayList of all Node Objects in the graph
     */
}

