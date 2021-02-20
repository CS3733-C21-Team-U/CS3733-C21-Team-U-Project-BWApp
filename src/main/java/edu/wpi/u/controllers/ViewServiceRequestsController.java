package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ViewServiceRequestsController {



    @FXML
    Accordion viewAccordian;

    public void initialize() throws IOException {
        //This is how you add title panes here
        TitledPane t = new TitledPane();
        viewAccordian.getPanes().add(t);

    }


    @FXML
    public void handleChangeToEditRequest(){
        App.rightDrawerRoot.set( "../views/EditServiceRequests.fxml");
    }


}
