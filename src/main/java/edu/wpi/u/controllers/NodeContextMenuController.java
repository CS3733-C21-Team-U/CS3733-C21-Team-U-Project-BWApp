package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import javafx.beans.InvalidationListener;
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
    @FXML


    public void initialize(){
        ArrayList<String> nodeAList = new ArrayList<String>();
        nodeAList.add("WALK");
        nodeAList.add("PARK");
        nodeAList.add("LABS");


    //nodeTypeDrop.setItems(nodeAList); I'm in the middle of fixing this
    }

    public void handleSaveButton(){
        // App.mapService.updateNode() TODO: Use Charlie's undo/redo stuff
    }



}
