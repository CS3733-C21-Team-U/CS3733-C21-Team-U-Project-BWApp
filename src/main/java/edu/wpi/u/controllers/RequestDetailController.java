package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Stack;

public class RequestDetailController {
    @FXML Label requestDetailTitleLabel;
    @FXML Label requestDetailCreatorLabel;
    @FXML Label requestDetailDescriptionLabel;
    @FXML JFXChipView requestDetailLocationChipView;
    @FXML JFXChipView requestDetailStaffChipView;
    @FXML Label requestDetailDateCreatedLabel;
    @FXML Label requestDetailDate2BCompleteLabel;
    @FXML Label requestDetailSecurityLabel;
    @FXML ListView commentListView;
    @FXML StackPane requestDetailStack;
    @FXML Pane requestDetailSecurityPane;
    @FXML Pane requestDetailMaintenancePane;
    @FXML Pane requestDetailLaundryPane;
  
//    requestDetailTitleLabel.setText(request.getTitle());
//    requestDetailCreatorLabel.setText(request.getCreator());
//    requestDetailDescriptionLabel.setText(request.getDecripition());
//    requestDetailLocationChipView.setText(request.getLocation());
//    requestDetailStaffChipView.setText(request.getStaff());
//    requestDetailDateCreatedLabel.setText(request.getDateCreated());
//    requestDetailDate2BCompleteLabel.setText(request.getDate2BComplete());
//    requestDetailSecurityLabel.setText(request.getLanguage());

    public void handleCommentButton() {
    }

    public void handleRequestDetailCancelButton() throws Exception {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewViewRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    public void handleResolveRequestButton() {
    }

    public void handleEditRequestButton() {
    }
}
