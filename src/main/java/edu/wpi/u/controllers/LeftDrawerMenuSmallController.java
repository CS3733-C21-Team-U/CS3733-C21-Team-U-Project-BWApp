package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class LeftDrawerMenuSmallController {


    @FXML public JFXToggleNode toggle1;
    @FXML public JFXToggleNode toggle2;
    @FXML public JFXToggleNode toggle3;
    @FXML public JFXToggleNode toggle4;

    @FXML
    public void initialize() throws IOException{
        toggleButton(App.leftMenuScreenNum);
    }

    private void toggleButton(int screen){
        switch(screen) {
            case 1:
                toggle1.setSelected(true); break;
            case 2:
                toggle2.setSelected(true); break;
            case 3:
                toggle3.setSelected(true); break;
            case 4:
                toggle4.setSelected(true); break;
        }
    }


    public void handleChangeToPathPlanning(ActionEvent actionEvent) {
        App.rightDrawerRoot.set( "/edu/wpi/u/views/PathfindingRightPage.fxml");
        App.leftMenuScreenNum = 1;
    }

    public void handleChangeToRequests(ActionEvent actionEvent) {
        App.rightDrawerRoot.set( "/edu/wpi/u/views/ViewRequest.fxml");
        App.leftMenuScreenNum = 2;
    }


    public void handleChangeToAdmin(ActionEvent actionEvent) {
        App.rightDrawerRoot.set( "/edu/wpi/u/views/AdminTools.fxml");
        App.leftMenuScreenNum = 3;
    }

    public void handleChangeToSettings(ActionEvent actionEvent) {
        App.rightDrawerRoot.set( "/edu/wpi/u/views/Settings.fxml");
        App.leftMenuScreenNum = 4;
    }

    public void handleExit(ActionEvent actionEvent) {
        App.getInstance().stop();
    }
}