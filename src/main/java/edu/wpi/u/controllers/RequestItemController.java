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
import javafx.scene.text.Text;

import java.io.IOException;

public class RequestItemController {


    @FXML public Button expandCollapseButton;
    @FXML public Button editRequestButton;
    @FXML public AnchorPane requestAnchor;

    @FXML public Button deleteRequestButton;

    @FXML public Label descriptionLabel;
    //@FXML public TextField title;
    //@FXML public TextField location;

    public boolean isCollapsed = true;
    @FXML public Label titleLabel;
    @FXML public Label locationLabel;


    @FXML
    public void handleExpandCollapseButton(){
        if(isCollapsed) {
            isCollapsed = false;
            requestAnchor.setPrefHeight(700);
            expandCollapseButton.setText("Collapse");
            descriptionLabel.setPrefHeight(requestAnchor.getPrefHeight()-140);
//            deleteRequestButton.setVisible(true);
//            editRequestButton.setVisible(true);
        } else{
            isCollapsed = true;
            requestAnchor.setPrefHeight(200);
            expandCollapseButton.setText("Expand");
            descriptionLabel.setPrefHeight(requestAnchor.getPrefHeight()-140);
//            deleteRequestButton.setVisible(false);
//            editRequestButton.setVisible(false);
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
    }

    public void setTitle(String newTitle){
        titleLabel.setText(newTitle);
    }
    public void setLocation(String newLocation){ locationLabel.setText(newLocation); }

}
