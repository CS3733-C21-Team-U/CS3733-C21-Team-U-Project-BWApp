package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.models.GraphService;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.*;

public class PathfindingRightPageController {

    @FXML
    Button entryTypeButton;
    @FXML
    Button findPathButton;
    @FXML
    JFXTextField startTextField;
    @FXML
    JFXTextField endTextField;
    @FXML
    JFXComboBox startDropField;
    @FXML
    JFXComboBox endDropField;

    GraphService gs = new GraphService();

    ObservableList<Node> oList;


    //This initialize function mostly fills in the correct nodes to the drop-down menu
    public void initialize() throws IOException {
        ArrayList<Node> L = gs.getNodes();//This gets the list of all the nodes
        ArrayList<String> nodeIDs = new ArrayList<String>(); //Instantiating a new ArrayList for the NodeID's
        for(Node N: L){//This fills up the new ArrayList<String> with the node ID's so we can display those
            nodeIDs.add(N.getNodeID());
        }
        ObservableList<String> oList = FXCollections.observableList(nodeIDs);
        startDropField.setItems(oList); //This sets the observablelist that just got created to the stuff thats in the dropdown
        endDropField.setItems(oList);

    }




    /*  This toggles the list/text entry button and shows/hides teh applicable fields
        In the future, it may be smart to select the node in the drop-down that was
        typed in the text entry and vice-versa. Only if the typed node is valid, obviously.
    */
    public void handleEntryTypeButton() throws Exception {
        if(entryTypeButton.getText().equalsIgnoreCase("List Entry")) {//If the current button is List Entry
            entryTypeButton.setText("Text Entry");
            startTextField.setVisible(false);
            endTextField.setVisible(false);
            startDropField.setVisible(true);
            endDropField.setVisible(true);
        } else{//If the current button is Text Entry
            entryTypeButton.setText("List Entry");
            startTextField.setVisible(true);
            endTextField.setVisible(true);
            startDropField.setVisible(false);
            endDropField.setVisible(false);
        }
    }
}
