package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Stack;

public class RequestDetailController {
    @FXML public Label requestDetailMaintenanceLabel;
    @FXML public Label requestDetailLaundryLabel;
    @FXML public StackPane requestDetailStack;
    @FXML public Pane requestDetailSecurityPane;
    @FXML public Label requestDetailSecurityLabel;
    @FXML public Pane requestDetailMaintenancePane;
    @FXML public Pane requestDetailLaundryPane;
    @FXML public JFXListView commentListView;
    @FXML public JFXChipView requestDetailStaffChipView;
    @FXML public JFXChipView requestDetailLocationChipView;
    @FXML public Label requestDetailDateCreatedLabel;
    @FXML public Label requestDetailDate2BCompleteLabel;
    @FXML public Label requestDetailDescriptionLabel;
    @FXML public Label requestDetailTitleLabel;
    @FXML public Label requestDetailCreatorLabel;

    public void handleCommentButton(ActionEvent actionEvent) {
    }

    public void handleRequestDetailCancelButton(ActionEvent actionEvent) {
    }

    public void handleResolveRequestButton(ActionEvent actionEvent) {
    }

    public void handleEditRequestButton(ActionEvent actionEvent) {
    }


//    @FXML Label requestDetailTitleLabel;
//    @FXML Label requestDetailCreatorLabel;
//    @FXML Label requestDetailDescriptionLabel;
//    @FXML JFXChipView requestDetailLocationChipView;
//    @FXML JFXChipView requestDetailStaffChipView;
//    @FXML Label requestDetailDateCreatedLabel;
//    @FXML Label requestDetailDate2BCompleteLabel;
//    @FXML Label requestDetailSecurityLabel;
//    @FXML ListView commentListView;
//    @FXML StackPane requestDetailStack;
//    @FXML Pane requestDetailSecurityPane;
//    @FXML Pane requestDetailMaintenancePane;
//    @FXML Pane requestDetailLaundryPane;


}
