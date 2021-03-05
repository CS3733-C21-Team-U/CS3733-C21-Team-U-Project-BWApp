package edu.wpi.u.models;

import edu.wpi.u.database.Database;
import edu.wpi.u.database.RequestData;
import edu.wpi.u.requests.*;
import java.util.ArrayList;
import java.util.Date;


public class RequestService {


  static RequestData rd = new RequestData();
  ArrayList<IRequest> activeRequests = new ArrayList<>();

  public RequestService() {
    this.activeRequests = rd.loadActiveRequests();
    for (IRequest x : this.activeRequests){

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

  public void addRequest(IRequest result) {
    rd.addRequest(result);
    this.activeRequests.add(result);
  }

  public void updateRequest(IRequest result) {
    rd.updateRequest(result);
  }

  public void resolveRequest(IRequest result) {
    long time = System.currentTimeMillis();
    Date now = new Date(time);
    result.getGenericRequest().setDateCompleted(now);
    this.activeRequests.remove(result);
    rd.resolveRequest(result.getGenericRequest().getRequestID(), time);
  }

  public ArrayList<IRequest> getRequests() {
      return this.activeRequests;
    }

}

