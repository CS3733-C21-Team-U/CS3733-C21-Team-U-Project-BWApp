package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ViewRequestController {





    public void initialize() throws IOException {
        //This is how you add title panes here


    }


    @FXML
    public void handleChangeToEditRequest(){
        App.rightDrawerRoot.set( "../views/EditRequests.fxml");
    }


}
