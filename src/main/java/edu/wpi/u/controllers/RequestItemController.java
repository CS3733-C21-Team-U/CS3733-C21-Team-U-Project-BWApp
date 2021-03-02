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

    @FXML public void handleViewRequestInDetailButton() throws Exception {
        //App.lastClickedRequestNumber = myID;
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ViewRequestInDetail.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }
}
