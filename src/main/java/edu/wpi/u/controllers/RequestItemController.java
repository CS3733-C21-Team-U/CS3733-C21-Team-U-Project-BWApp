package edu.wpi.u.controllers;

import edu.wpi.u.App;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RequestItemController {
    @FXML
    AnchorPane requestAnchor;

    @FXML
    Label descriptionLabel;

    @FXML
    Button expandCollapseButton;

    @FXML
    Button deleteRequestButton;

    @FXML
    Button editRequestButton;

    public boolean isCollapsed = true;

    @FXML
    public void handleExpandCollapseButton(){
        if(isCollapsed) {
            isCollapsed = false;
            requestAnchor.setPrefHeight(700);
            expandCollapseButton.setText("Collapse");
            descriptionLabel.setPrefHeight(requestAnchor.getPrefHeight()-140);
            deleteRequestButton.setVisible(true);
            editRequestButton.setVisible(true);
        } else{
            isCollapsed = true;
            requestAnchor.setPrefHeight(200);
            expandCollapseButton.setText("Expand");
            descriptionLabel.setPrefHeight(requestAnchor.getPrefHeight()-140);
            deleteRequestButton.setVisible(false);
            editRequestButton.setVisible(false);
        }

    }

    //Listener here for the global drawerstare variable
    @FXML
    public void handleEditRequest() throws Exception {
        App.rightDrawerRoot.set("../views/EditRequests.fxml");
    }
}
