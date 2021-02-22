package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.GraphService;

import java.time.LocalDateTime;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.util.Date;

import java.util.ArrayList;

public class NewRequestController {

    @FXML
    public TextField titleTextField;
    @FXML
    public TextField descriptionTextField;
    @FXML
    public TextField nodeTextField;
    @FXML
    public TextField assigneeTextField;
    @FXML
    public TextField contactTextField;
    @FXML
    public SplitMenuButton serviceTypeMenu;
    @FXML
    public Button submitRequestButton;
    @FXML
    public Button assigneeButton;
    @FXML
    public ListView assigneeList;
    @FXML
    public Button cancelButton;

    public String exampleID;

    public Date start = new Date();

    public Date end;

    private ArrayList<String> assignee = new ArrayList<String>();

    public void handleAssigneeList(){
        assignee.add(assigneeTextField.getText());
        assigneeList.getItems().add(assigneeTextField.getText());
        assigneeTextField.setText("");
        //System.out.println("call");

    }

    public void handleSubmitRequestButton(){
    App.requestService.addRequest(exampleID, titleTextField.getText(), descriptionTextField.getText(), start, end, assignee);

    }

    public void handleLeaveAdd(){
        App.rightDrawerRoot.set( "../views/ViewRequest.fxml");
    }

}
