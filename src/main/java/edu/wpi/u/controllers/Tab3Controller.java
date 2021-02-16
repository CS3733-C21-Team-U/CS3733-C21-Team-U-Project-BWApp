package edu.wpi.u.controllers;

import javafx.scene.control.TextField;

public class Tab3Controller {

  public TextField authorLabel;
  private MainController mainController;

  public void injectMainController(MainController mainController) {
    this.mainController = mainController;
    authorLabel.textProperty().bind(mainController.sceneData.author);
  }

  public void initialize() {
    System.out.println("Tab 3 Init");
  }
}
