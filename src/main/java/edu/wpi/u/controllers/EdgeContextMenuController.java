package edu.wpi.u.controllers;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.InvalidEdgeException;
import edu.wpi.u.users.StaffType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.Observable;

public class EdgeContextMenuController {
    Node node1;

    @FXML
    JFXComboBox edgeComboBox;

    @FXML
    public void initialize() {
        Edge thisEdge = App.mapService.getEdgeFromID(App.mapInteractionModel.getEdgeID());
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("PATIENT");
        list.add("EMPLOYEE");

        edgeComboBox.setItems(list);
    }

    /**
     * To be run when save button for edge context menu is pressed. Needs to check if a new edge can be saved, then it saved one
     */
    @FXML
    public void handleSaveButton() throws InvalidEdgeException {
        ArrayList<StaffType> userTypes = new ArrayList<StaffType>();
        userTypes.add(StaffType.valueOf(edgeComboBox.getValue().toString()));
        System.out.println("DEBUG: " + edgeComboBox.getValue().toString());
        if(App.mapInteractionModel.getCurrentAction().equals("NONE")) {
            Edge thisEdge = App.mapService.getEdgeFromID(App.mapInteractionModel.getEdgeID());
            App.undoRedoService.updateEdge(thisEdge.getEdgeID(), thisEdge.getUserPermissions());
        } else if(App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
            App.undoRedoService.addEdge(App.mapInteractionModel.getPreviousNodeID(), App.mapInteractionModel.getNodeID(),userTypes);
        }
        userTypes.clear();
        //System.out.println(App.mapInteractionModel.getCoords()[0] + " " + App.mapInteractionModel.getCoords()[1]);
        App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
    }
    @FXML
    public void handleDeleteButton(){
        App.undoRedoService.deleteEdge(App.mapInteractionModel.getEdgeID());
        App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
    }
}
