package edu.wpi.u.models;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.requests.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class RequestService {


  public static void main (String[] args){

  }

  public RequestService() {
    System.out.println("Constrctor for Request SERVICE");
  }

  /*
  Saves the model data into csvs and drops tables
   */
  public void saveAndExitDB(){


  }


  /*
  Make sure x & y are positive integers within the map coordinate range
  */
  public String addRequest(String requestID, String title, String description, Date dateCreated, Date dateCompleted, ArrayList<String> staffList) {
    //Scucess
    return "";
    //Fail
    //return requestID;
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
  }

  public String updateRequest(String requestID, String title, String description, Date dateCreated, Date dateCompleted, ArrayList<String> staffList){
    //Scucess
    return "";
    //Fail
    //return requestID;

    // gm needs to write
    /*
    Check if valid node_id
    Return "" is a success
    Return node_id if node already exists / invalid
     */
  }

  public String deleteRequest(String requestID) {
    //Scucess
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
    Request request1 = new Request("request1","test1  ","first floor");
    ArrayList<Request> list1 = new ArrayList<Request>();
    list1.add(request1);
    //return new ArrayList<Request>();
    return list1;
    /*
    Returns an ArrayList of all Node Objects in the graph
     */
  }
}
