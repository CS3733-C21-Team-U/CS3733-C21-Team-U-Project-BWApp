package edu.wpi.u.controllers.robot;

import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MobileGoToKioskController {
    public void handleMoveToKioskButton() {
        String fxmlLocation = "/edu/wpi/u/views/robot/KioskLogin.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlLocation));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }
}
