package edu.wpi.u.controllers;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.*;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.PathNotFoundException;
import edu.wpi.u.models.GraphService;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class PathfindingRightPageController {

    @FXML
    JFXToggleNode textEntryButton;
    @FXML
    JFXToggleNode listEntryButton;

    @FXML
    Button findPathButton;

    @FXML
    TextField startTextField;
    @FXML
    TextField endTextField;
    @FXML
    ChoiceBox startDropField;
    @FXML
    ChoiceBox endDropField;

    @FXML
    JFXDrawer errorDrawer;

    @FXML
    ToggleGroup textList;


    //This initialize function mostly fills in the correct nodes to the drop-down menu
    public void initialize() throws IOException {
        ArrayList<Node> L = App.graphService.getNodes();//This gets the list of all the nodes
        ArrayList<String> nodeIDs = new ArrayList<String>(); //Instantiating a new ArrayList for the NodeID's
        for(Node N: L){//This fills up the new ArrayList<String> with the node ID's so we can display those
            nodeIDs.add(N.getNodeID());
        }
        ObservableList<String> oList = FXCollections.observableList(nodeIDs);
        startDropField.setItems(oList); //This sets the observablelist that just got created to the stuff thats in the dropdown
        endDropField.setItems(oList);




        FXMLLoader errorMessageLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/ErrorMessage.fxml"));
        AnchorPane error = errorMessageLoader.load();
        ErrorMessageController controller = errorMessageLoader.getController();
        controller.errorMessage.setText("Plaese Input Valid Nodes");
        errorDrawer.setSidePane(error);


    }

    public void handleFindPathButton(){

            if (listEntryButton.isSelected()) {
                if (startDropField.getValue() == null || endDropField.getValue() == null) {
                    errorDrawer.open();
                } else {
                    try {
                        App.PathHandling.setSVGPath(App.graphService.aStar(String.valueOf(startDropField.valueProperty().getValue()), String.valueOf(endDropField.valueProperty().getValue())));
                    } catch(PathNotFoundException p) {
                        errorDrawer.open();
                    }
                    }
            } else {
                if (startTextField.getText().equals("") || endTextField.getText().equals("")) {
                    errorDrawer.open();
                } else {
                    System.out.println("SENDING THE LIST TO PATHHANDLING");
                    try {
                        App.PathHandling.setSVGPath(App.graphService.aStar(startTextField.getText(), endTextField.getText()));
                    } catch(PathNotFoundException p) {
                        errorDrawer.open();
                    }
                }
            }

    }

    public void handleListEntryButton(){
        startTextField.setVisible(false);
        endTextField.setVisible(false);
        startDropField.setVisible(true);
        endDropField.setVisible(true);
    }
    public void handleTextEntryButton(){
        startDropField.setVisible(false);
        endDropField.setVisible(false);
        startTextField.setVisible(true);
        endTextField.setVisible(true);
    }

    public void handleErrorMessageClear(){
        errorDrawer.close();
    }


}
