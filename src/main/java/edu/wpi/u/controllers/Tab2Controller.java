package edu.wpi.u.controllers;

import javafx.scene.control.TextField;

public class Tab2Controller {

  public TextField messageLabel;
  private MainController mainController;

  public void injectMainController(MainController mainController) {
    this.mainController = mainController;
    messageLabel.textProperty().bind(mainController.sceneData.message);
  }

  public void initialize() {
    System.out.println("Tab 2 Init");
  }
}
