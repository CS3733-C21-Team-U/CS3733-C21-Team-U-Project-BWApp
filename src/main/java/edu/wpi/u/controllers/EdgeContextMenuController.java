package edu.wpi.u.controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.InvalidEdgeException;
import edu.wpi.u.users.StaffType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Observable;

public class EdgeContextMenuController {
    Node node1;

    @FXML
    JFXComboBox edgeComboBox;
    @FXML
    JFXButton deleteButton;

    @FXML
    public void initialize() {
        // Client side error handling for combo box
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        edgeComboBox.getValidators().add(validator);
        edgeComboBox.focusedProperty().addListener((o, oldVal, newVal)-> {
            if(!newVal){
                edgeComboBox.validate();
            }
        });

        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Admin only");
        list.add("All Employees");
        list.add("Everyone");
        if(App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
            deleteButton.setText("Stop adding");
        }

        edgeComboBox.setItems(list);
    }

    /**
     * To be run when save button for edge context menu is pressed. Needs to check if a new edge can be saved, then it saved one
     */
    @FXML
    public void handleSaveButton() throws InvalidEdgeException {
        ArrayList<StaffType> userTypes = new ArrayList<>();
        userTypes.add(getEdgePermissionType());
        if(App.mapInteractionModel.getCurrentAction().equals("NONE")) {
            Edge thisEdge = App.mapService.getEdgeFromID(App.mapInteractionModel.getEdgeID());
            App.undoRedoService.updateEdge(thisEdge.getEdgeID(), thisEdge.getUserPermissions());
        } else if(App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
            App.undoRedoService.addEdge(App.mapInteractionModel.getPreviousNodeID(), App.mapInteractionModel.getNodeID(),userTypes);
        }
        userTypes.clear();
        App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
        ((Pane) App.mapInteractionModel.selectedNodeContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
    }

    public StaffType getEdgePermissionType(){
        switch (edgeComboBox.getValue().toString()){
            case "Admin only":
                return StaffType.ADMIN;
            case "All Employees":
                return StaffType.DOCTOR;
            default:
                return StaffType.DEFUALT;
        }

    }

    @FXML
    public void handleDeleteButton() {
        if (App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")) {
            App.mapInteractionModel.setCurrentAction("NONE");
            App.undoRedoService.deleteEdge(App.mapInteractionModel.getEdgeID());
        }else {
            App.undoRedoService.deleteEdge(App.mapInteractionModel.getEdgeID());
        }
        App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
        ((Pane) App.mapInteractionModel.selectedNodeContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);

    }
}
