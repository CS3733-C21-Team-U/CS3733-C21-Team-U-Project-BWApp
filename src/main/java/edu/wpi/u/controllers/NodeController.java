package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

  @FXML
  public void buttonPressMain() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/MainPage.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void buttonPressNode() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/EditNodeLily.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void buttonPressEdge() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/EditEdgeNick.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  public void buttonPressAS() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/AStarTyler.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

}
