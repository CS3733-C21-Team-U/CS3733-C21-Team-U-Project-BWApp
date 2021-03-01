package edu.wpi.u.controllers;

import com.jfoenix.controls.*;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.PathNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
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

    ErrorMessageController controller;

    //This initialize function mostly fills in the correct nodes to the drop-down menu
    public void initialize() throws IOException {
        ArrayList<Node> L = App.mapService.getNodes();//This gets the list of all the nodes
        ArrayList<String> nodeIDs = new ArrayList<String>(); //Instantiating a new ArrayList for the NodeID's
        for(Node N: L){//This fills up the new ArrayList<String> with the node ID's so we can display those
            nodeIDs.add(N.getNodeID());
        }
        ObservableList<String> oList = FXCollections.observableList(nodeIDs);
        startDropField.setItems(oList); //This sets the observablelist that just got created to the stuff thats in the dropdown
        endDropField.setItems(oList);




        FXMLLoader errorMessageLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/ErrorMessage.fxml"));
        AnchorPane error = errorMessageLoader.load();
        controller = errorMessageLoader.getController();
        controller.errorMessage.setText("Please Input Valid Nodes");
        errorDrawer.setSidePane(error);


    }

    public void handleFindPathButton(){

            if (listEntryButton.isSelected()) {
                if (startDropField.getValue() == null || endDropField.getValue() == null) {
                    controller.errorMessage.setText("Please input two valid Nodes!");
                    errorDrawer.open();
                } else {
                    try {
                        App.PathHandling.setSVGPath(App.mapService.aStar(String.valueOf(startDropField.valueProperty().getValue()), String.valueOf(endDropField.valueProperty().getValue())));
                        errorDrawer.close();
                    } catch(PathNotFoundException p) {
                        controller.errorMessage.setText(p.description);
                        errorDrawer.open();
                    }

                    }
            } else {
                if (startTextField.getText().equals("") || endTextField.getText().equals("")) {
                    controller.errorMessage.setText("Please input two valid Nodes!");
                    errorDrawer.open();
                } else {
                    System.out.println("SENDING THE LIST TO PATHHANDLING");
                    try {
                        App.PathHandling.setSVGPath(App.mapService.aStar(startTextField.getText(), endTextField.getText()));
                        errorDrawer.close();
                    } catch(PathNotFoundException p) {
                        errorDrawer.open();
                    }
                }
            }

    }

    public void handleListEntryButton(){
        if(!listEntryButton.isSelected()){
            listEntryButton.setSelected(true);
        }
        startTextField.setVisible(false);
        endTextField.setVisible(false);
        startDropField.setVisible(true);
        endDropField.setVisible(true);
    }
    public void handleTextEntryButton(){
        if(!textEntryButton.isSelected()){
            textEntryButton.setSelected(true);
        }
        startDropField.setVisible(false);
        endDropField.setVisible(false);
        startTextField.setVisible(true);
        endTextField.setVisible(true);
    }

    public void handleErrorMessageClear(){
        errorDrawer.close();
    }


}
