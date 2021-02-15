package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.models.Message;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class MainController {

  public Message sceneData = new Message();


  // Super important, the name of the vairble must be "[fxid]+Controller" where fxid matches in
  // Mainveiw.fxml and Tab1.fxml

  @FXML
  private void toNewScene() throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("edu/wpi/u/views/MainPage.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  private void initialize() {
    System.out.println("Main Init");
  }

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
