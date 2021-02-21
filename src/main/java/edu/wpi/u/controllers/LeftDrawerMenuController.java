package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.event.ActionEvent;

public class LeftDrawerMenuController {

    public void handleChangeToPathPlanning(ActionEvent actionEvent) {
        App.rightDrawerRoot.set( "../views/PathPlanning.fxml");
    }

    public void handleChangeToRequests(ActionEvent actionEvent) {
        App.rightDrawerRoot.set( "../views/ViewRequests.fxml");
    }

    public void handleChangeToAdmin(ActionEvent actionEvent) {
        App.rightDrawerRoot.set( "../views/AdminTools.fxml");
    }
}
