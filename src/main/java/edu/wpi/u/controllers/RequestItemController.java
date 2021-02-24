package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.models.RequestService;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.io.IOException;

public class RequestItemController {

    @FXML public Button expandCollapseButton;
    @FXML public Button editRequestButton;
    @FXML public AnchorPane requestAnchor;

    @FXML public Button deleteRequestButton;

    @FXML public Label descriptionLabel; //40
    //@FXML public TextField title;
    //@FXML public TextField location;

    public boolean isCollapsed = true;
    @FXML public Label titleLabel; //45
    @FXML public Label locationLabel; //40


    @FXML
    public void handleExpandCollapseButton(){
        if(isCollapsed) {
            isCollapsed = false;
            expandCollapseButton.setText("Collapse");
            titleLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);
            descriptionLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);
            locationLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);
        } else{
            isCollapsed = true;
            expandCollapseButton.setText("Expand");
            titleLabel.setPrefHeight(45);
            descriptionLabel.setPrefHeight(40);
            locationLabel.setPrefHeight(40);

        }
    }

    //Listener here for the global drawerstare variable
    @FXML
    public void handleChangeToEditRequest() throws Exception {
        App.rightDrawerRoot.set("/edu/wpi/u/views/EditRequest.fxml");
    }

    @FXML
    public void handleDeleteRequest() {
        App.requestService.deleteRequest(App.requestService.getRequests().get(App.getInstance().requestClicked).getRequestID());
        App.rightDrawerRoot.set("/edu/wpi/u/views/EditRequest.fxml");
        App.rightDrawerRoot.set("/edu/wpi/u/views/ViewRequest.fxml");
    }

    public void setTitle(String newTitle){
        titleLabel.setText(newTitle);
    }
    public void setLocation(String newLocation){ locationLabel.setText(newLocation); }

}
