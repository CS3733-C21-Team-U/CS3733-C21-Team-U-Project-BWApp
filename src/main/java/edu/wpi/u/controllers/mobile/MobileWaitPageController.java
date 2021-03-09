package edu.wpi.u.controllers.mobile;

import com.jfoenix.controls.JFXButton;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MobileWaitPageController {
    public JFXButton pathFindButton;

    public void enablePathFinding(){
        pathFindButton.setStyle("-fx-opacity: 1");
        pathFindButton.setDisable(false);
    }

    public void handlePathFInd(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mobile/MobilePathfindingBase.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }
}
