package edu.wpi.u.models;

public class Message {

  public String message;
  public String author;

  public Message() {
    message = null;
    author = null;
  }

  public void getDataFromDB() {
    System.out.println("I will now make a call to the DB to get some data");

    // Do some error checking

    // Save the data in a class variable
  }
}
