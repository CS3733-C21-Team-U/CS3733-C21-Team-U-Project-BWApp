package edu.wpi.u.models;

import edu.wpi.u.database.Database;
import edu.wpi.u.database.RequestData;
import edu.wpi.u.requests.*;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


public class RequestService {


  static RequestData rd = new RequestData();
  ArrayList<SpecificRequest> activeRequests = new ArrayList<>();

  public SimpleStringProperty requestType = new SimpleStringProperty("All");//all request types
  public SimpleStringProperty resolveStatus= new SimpleStringProperty("All");//resolved, active, both
  public SimpleStringProperty assignedStatus= new SimpleStringProperty("All");//assignedToYou, unAssigned, all

  public RequestService() {
    this.activeRequests = rd.loadActiveRequests();
    for (SpecificRequest x : this.activeRequests){

      System.out.println("Req: "+ x.getGenericRequest().getRequestID());
    }
  }

  public ArrayList<String> getAssignees(String requestID){
    return rd.getAssignees(requestID);
  }

  public ArrayList<String> getLocations(String requestID){
    return rd.getLocations(requestID);
  }

  public void setAssignees(String requestID, ArrayList<String> assignees){
    rd.updAssignees(requestID, assignees);
  }

  public void setLocations(String requestID, ArrayList<String> locations){
    rd.updLocations(requestID, locations);
  }

  public void loadCSVFile(String path, String tableName){
    Database.getDB().dropValues(tableName);
    Database.getDB().readCSV(path,tableName);
    //this.activeRequests = rd.loadActiveRequests();
  }

  public void saveCSVFile(String path, String tableName){
    Database.getDB().saveCSV(tableName,path , "test"); // TODO: Provide header
  }

  public void addRequest(SpecificRequest result) {
    rd.addRequest(result);
    this.activeRequests.add(result);
  }

  public void updateRequest(SpecificRequest result) {
    rd.updateRequest(result);
  }

  public void resolveRequest(SpecificRequest result, Comment resolveComment) {
    result.getGenericRequest().resolveRequest(resolveComment);
    this.activeRequests.remove(result);
    rd.resolveRequest(result.getGenericRequest().getRequestID(),resolveComment);
  }

  public void addComment(SpecificRequest specificRequest, Comment comment){
    specificRequest.getGenericRequest().addComment(comment);
    rd.addCommentToRequest(specificRequest.getGenericRequest().getRequestID(), comment);
  }

  public ArrayList<SpecificRequest> getRequests() {
      return this.activeRequests;
    }

}

