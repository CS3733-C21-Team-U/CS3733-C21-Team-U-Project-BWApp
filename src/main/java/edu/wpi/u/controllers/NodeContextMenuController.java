package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.InvalidEdgeException;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.*;

public class NodeContextMenuController {
    Node node1;

    @FXML
    JFXTextField longNameText;
    @FXML
    JFXTextField shortNameText;
    @FXML
    JFXComboBox nodeTypeDrop;

    /**
     * To be run when save button in node context menu is pressed. Needs to check if a node can be saved, then saves a node.
     */
    @FXML


    public void initialize(){
        Node thisNode = App.mapService.getNodeFromID(App.mapInteractionModel.getNodeID());
        ArrayList<String> nodeAList = new ArrayList<String>();
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("WALK");
        list.add("PARK");
        list.add("LABS");

        if(App.mapInteractionModel.getCurrentAction().equals("NONE")) {
            longNameText.setText(thisNode.getLongName());
            shortNameText.setText(thisNode.getShortName());
        }

        nodeTypeDrop.setItems(list); //I'm in the middle of fixing this
    }
    @FXML
    public void handleSaveButton() throws InvalidEdgeException {
        if(App.mapInteractionModel.getCurrentAction().equals("NONE")) {
            Node thisNode = App.mapService.getNodeFromID(App.mapInteractionModel.getNodeID());
            App.undoRedoService.updateNode(thisNode.getNodeID(), thisNode.getCords()[0], thisNode.getCords()[1], longNameText.getText(), shortNameText.getText());
        } else if(App.mapInteractionModel.getCurrentAction().equals("ADDNODE")){
            App.undoRedoService.addNode(App.mapInteractionModel.getCoords()[0], App.mapInteractionModel.getCoords()[1], App.mapInteractionModel.getFloor(), App.mapInteractionModel.getBuilding(), nodeTypeDrop.getValue().toString(),longNameText.getText(), shortNameText.getText());
        }
    }
    @FXML
    public void handleDeleteButton(){
        App.undoRedoService.deleteNode(App.mapInteractionModel.getNodeID());
    }



}
