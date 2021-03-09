package edu.wpi.u.controllers.mapbuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.InvalidEdgeException;
import edu.wpi.u.users.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ContextMenuEdgeController {
    Node node1;

    @FXML
    JFXComboBox edgeComboBox;
    @FXML
    JFXButton deleteButton;
    @FXML
    Label permissionErrorLabel;

    @FXML
    public void initialize() {
        permissionErrorLabel.setVisible(false);

        edgeComboBox.focusedProperty().addListener(observable -> {
            permissionErrorLabel.setVisible(false);
        });

        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Admin only");
        list.add("All Employees");
        list.add("Everyone");
        if(App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
            deleteButton.setText("Cancel");
            edgeComboBox.setValue("Everyone");
        } else {
            Edge thisEdge = App.mapService.getEdgeFromID(App.mapInteractionModel.getEdgeID());
            if(thisEdge.getUserPermissions().equals(Role.DEFAULT)){
                edgeComboBox.setValue("Everyone");
            } else if(thisEdge.getUserPermissions().equals(Role.DOCTOR)){
                edgeComboBox.setValue("All Employees");
            }else{
                edgeComboBox.setValue("Admin only");
            }
        }

        edgeComboBox.setItems(list);
    }

    /**
     * To be run when save button for edge context menu is pressed. Needs to check if a new edge can be saved, then it saved one
     */
    @FXML
    public void handleSaveButton() throws InvalidEdgeException {

        if(!edgeComboBox.getSelectionModel().isEmpty()){
            if(App.mapInteractionModel.getCurrentAction().equals("NONE")) {
                Edge thisEdge = App.mapService.getEdgeFromID(App.mapInteractionModel.getEdgeID());
                App.undoRedoService.updateEdge(thisEdge.getEdgeID(), getEdgePermissionType());
            } else if(App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
                App.undoRedoService.addEdge(App.mapInteractionModel.getPreviousNodeID(), App.mapInteractionModel.getNodeID(),getEdgePermissionType());
                App.mapInteractionModel.setEdgeID("");
                App.mapInteractionModel.clearPreviousNodeID();
            }
            App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
            ((Pane) App.mapInteractionModel.selectedContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedContextBox);
        }else {
            permissionErrorLabel.setVisible(true);
            MapBuilderBaseController.shake(edgeComboBox);
        }

    }

    public Role getEdgePermissionType(){
        switch (edgeComboBox.getValue().toString()){
            case "Admin only":
                return Role.ADMIN;
            case "All Employees":
                return Role.DOCTOR;
            case "Everyone":
                return Role.DEFAULT;
            default:
                return null;
        }

    }

    @FXML
    public void handleDeleteButton() {
        if (App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")) {
            App.mapInteractionModel.setCurrentAction("ADDEDGE");
        }else {
            App.undoRedoService.deleteEdge(App.mapInteractionModel.getEdgeID());
        }
        App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
        ((Pane) App.mapInteractionModel.selectedContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedContextBox);

    }
}
