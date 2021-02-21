package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import javafx.fxml.FXML;


public class LeftDrawerMenuController {

    @FXML
    JFXToggleNode navigationButton;
    @FXML
    JFXToggleNode serviceRequestButton;



    public void handleNavigationButton() throws Exception {
        //Switch to a new right drawer
        App.rightDrawerRoot.set( "../views/PathfindingRightPage.fxml");
    }

    public void handleServiceRequestButton() throws Exception {
        //Switch to a new right drawer
        App.rightDrawerRoot.set( "../views/ViewRequest.fxml");
    }











}
