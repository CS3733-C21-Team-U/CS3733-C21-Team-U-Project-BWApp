package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.InvalidEdgeException;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.*;

public class NodeContextMenuController {
    Node node1;

    @FXML
    JFXTextField longNameText;
    @FXML
    JFXTextField shortNameText;
    @FXML
    JFXComboBox nodeTypeDrop;
    @FXML
    JFXButton doneButton;

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

        if(App.mapInteractionModel.getCurrentAction().equals("ADDNODE")) {
            doneButton.setText("Stop adding");
        }else{
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
        App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
    }
    @FXML
    public void handleDeleteButton() {
        if (App.mapInteractionModel.getCurrentAction().equals("ADDNODE")) {
            App.mapInteractionModel.setCurrentAction("NONE");
            ((Pane) App.mapInteractionModel.selectedNodeContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
        } else {
            App.undoRedoService.deleteNode(App.mapInteractionModel.getNodeID());
        }
        App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
    }



}
