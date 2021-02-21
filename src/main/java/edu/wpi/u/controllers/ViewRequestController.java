package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ViewRequestController {

    @FXML
    VBox requestList;



    public void initialize() throws IOException {
        //This is how you add title panes here
        AnchorPane request;
        request = FXMLLoader.load(getClass().getResource("../views/RequestItem.fxml"));

        requestList.getChildren().add(request);

    }


    @FXML
    public void handleChangeToEditRequest(){
        //Switch to a new right drawer
        App.rightDrawerRoot.set( "../views/EditRequest.fxml");

    }


}
