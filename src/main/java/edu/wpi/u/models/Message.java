package edu.wpi.u.models;

import javafx.beans.property.SimpleStringProperty;

public class Message {

  public SimpleStringProperty author;
  public SimpleStringProperty message;

  public Message() {
    message = new SimpleStringProperty();
    author = new SimpleStringProperty();
  }

  public void getDataFromDB() {
    System.out.println("I will now make a call to the DB to get some data");

    // Do some error checking

    // Save the data in a class variable
  }
}
