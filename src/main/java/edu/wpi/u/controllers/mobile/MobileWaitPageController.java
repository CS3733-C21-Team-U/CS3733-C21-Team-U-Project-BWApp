package edu.wpi.u.controllers.mobile;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MobileWaitPageController implements Initializable {
    public JFXButton pathFindButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handlePathFInd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mobile/MobilePathfindingBase.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        MobileContainerController.getInstance().getMobileRoot().getChildren().clear();
        MobileContainerController.getInstance().getMobileRoot().getChildren().add(fxmlLoader.getRoot());
    }

    public void handleDebug() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mobile/MobilePathfindingBase.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        MobileContainerController.getInstance().getMobileRoot().getChildren().clear();
        MobileContainerController.getInstance().getMobileRoot().getChildren().add(fxmlLoader.getRoot());
    }

    public void handleReturnLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mobile/MobileUserLoginScreen.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        MobileContainerController.getInstance().getMobileRoot().getChildren().clear();
        MobileContainerController.getInstance().getMobileRoot().getChildren().add(fxmlLoader.getRoot());
    }




}
