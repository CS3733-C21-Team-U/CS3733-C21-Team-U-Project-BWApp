package edu.wpi.u.controllers;

public class Tab1Controller {

  private MainController mainController;

  public void injectMainController(MainController mainController) {
    this.mainController = mainController;
  }

  public void initialize() {}
}
