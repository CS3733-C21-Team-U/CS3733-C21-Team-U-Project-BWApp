package edu.wpi.u.controllers;

public class Tab2Controller {

  private MainController mainController;

  public void injectMainController(MainController mainController) {
    this.mainController = mainController;
  }

  public void initialize() {}
}
