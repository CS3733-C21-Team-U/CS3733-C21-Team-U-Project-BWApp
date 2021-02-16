package edu.wpi.u.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NodeController {

  @FXML public TextField enterNodeID;

  @FXML public TextField enterXCoo;

  @FXML public TextField enterYCoo;

  @FXML
  public void addNode() {

    if (enterNodeID.getText().equals("")) {
      System.out.println("Missing Node ID.");
      return;
    }

    if (enterXCoo.getText().equals("")) {
      System.out.println("Missing x-coordinate.");
      return;
    }

    if (enterYCoo.getText().equals("")) {
      System.out.println("Missing y-coordinate");
      return;
    }

    //        Node myNode = new Node(enterNodeID.getText(),);

  }

  @FXML
  public void editNode() {}

  @FXML
  public void deleteNode() {}
}
