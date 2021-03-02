package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXChipView;
import edu.wpi.u.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.IOException;

public class RequestItemController {

    //@FXML public Button expandCollapseButton;     DNE
    //@FXML public Button editRequestButton;        DNE
    //@FXML public AnchorPane requestItemAnchor;    DNE

    //@FXML public Button deleteRequestButton;      DNE

    @FXML public Button viewRequestButton;
    @FXML public Label requestItemDescriptionLabel;
    //@FXML public TextField title;
    //@FXML public TextField location;

    //public boolean isCollapsed = true;
    @FXML public Label requestItemTitleLabel;
    @FXML public JFXChipView requestItemLocationChipView;
    @FXML public Label requestItemDate2BCompletedLabel;
    @FXML public Label requestItemCreatorLabel;
    @FXML public Label requestItemRequestTypeLabel;

    public String myRequestID;
    public Integer myID;

    @FXML
    public void initialize() throws IOException {
        myID = App.lastClickedRequestNumber;
    }

    @FXML public void handleViewRequestInDetailButton() throws Exception {
        App.lastClickedRequestNumber = myID;
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ViewRequestInDetail.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }


    /*
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
        App.lastClickedRequestNumber = myID;
        App.rightDrawerRoot.set("/edu/wpi/u/views/EditRequest.fxml");
    }

    @FXML
    public void handleDeleteRequest() {
        App.requestService.deleteRequest(myRequestID);
        App.rightDrawerRoot.set("/edu/wpi/u/views/EditRequest.fxml"); //Fake - Just to refresh
        App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/ViewRequest.fxml");
    }

    public void setTitle(String newTitle){
        requestitleLabel.setText(newTitle);
    }
    public void setLocation(String newLocation){ locationLabel.setText(newLocation); }
     */
}
