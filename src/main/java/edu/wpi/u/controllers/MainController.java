package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.models.Message;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MainController {

  public Message sceneData = new Message();

  // Super important, the name of the vairble must be "[fxid]+Controller" where fxid matches in
  // Mainveiw.fxml and Tab1.fxml
  @FXML private Tab1Controller tab1Controller;
  @FXML private Tab2Controller tab2Controller;
  @FXML private Tab3Controller tab3Controller;

  @FXML
  private void toNewScene() throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewView.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  private void initialize() {
    System.out.println("Main Init");
    tab1Controller.injectMainController(this);
    tab2Controller.injectMainController(this);
    tab3Controller.injectMainController(this);
  }
}
