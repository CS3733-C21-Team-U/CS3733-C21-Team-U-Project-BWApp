package edu.wpi.u.models;

import edu.wpi.u.database.Database;
import edu.wpi.u.database.RequestData;
import edu.wpi.u.requests.*;
import java.util.ArrayList;
import java.util.Date;


public class RequestService {


  static RequestData rd = new RequestData();
  ArrayList<SpecificRequest> activeRequests = new ArrayList<>();

  public RequestService() {
    this.activeRequests = rd.loadActiveRequests();
    for (SpecificRequest x : this.activeRequests){

      System.out.println("Req: "+ x.getGenericRequest().getRequestID());
    }
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

  public void resolveRequest(SpecificRequest result) {
    long time = System.currentTimeMillis();
    Date now = new Date(time);
    result.getGenericRequest().setDateCompleted(now);
    this.activeRequests.remove(result);
    rd.resolveRequest(result.getGenericRequest().getRequestID(), time);
  }

  public ArrayList<SpecificRequest> getRequests() {
      return this.activeRequests;
    }

}

