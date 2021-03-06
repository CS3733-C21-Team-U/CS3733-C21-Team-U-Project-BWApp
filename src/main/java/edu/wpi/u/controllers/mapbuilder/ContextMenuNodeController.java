package edu.wpi.u.controllers.mapbuilder;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
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

public class ContextMenuNodeController {
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
        // Client side error handling for long name
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        longNameText.getValidators().add(validator);
        longNameText.focusedProperty().addListener((o, oldVal, newVal)-> {
            if(!newVal){
                longNameText.validate();
            }
        });
        // Client side error handling for short name
        validator.setMessage("Input Required");
        shortNameText.getValidators().add(validator);
        shortNameText.focusedProperty().addListener((o, oldVal, newVal)-> {
            if(!newVal){
                shortNameText.validate();
            }
        });
        // Client side error handling for dropdown menu
        validator.setMessage("Input Required");
        nodeTypeDrop.getValidators().add(validator);
        nodeTypeDrop.focusedProperty().addListener((o, oldVal, newVal)-> {
            if(!newVal){
                nodeTypeDrop.validate();
            }
        });


        Node thisNode = App.mapService.getNodeFromID(App.mapInteractionModel.getNodeID());
        ArrayList<String> nodeAList = new ArrayList<String>();
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Conference Room");//CONF
        list.add("Department");//DEPT
        list.add("Elevator");//ELEV
        list.add("Exit");//EXIT
        list.add("Food");//FOOD
        list.add("Hallway");//HALL
        list.add("Kiosk");//KIOS
        list.add("Laboratory");//LAB
        list.add("Parking Lot");//PARK
        list.add("Restroom");//REST
        list.add("Service Area");//SERV
        list.add("Staircase");//STAI
        list.add("Walkway");//WALK

        if(App.mapInteractionModel.getCurrentAction().equals("ADDNODE")) {
            doneButton.setText("Stop adding");
        }else{
            longNameText.setText(thisNode.getLongName());
            shortNameText.setText(thisNode.getShortName());
            switch(thisNode.getNodeType()){
                case "CONF":
                    nodeTypeDrop.setValue("Conference Room");
                    break;
                case "DEPT":
                    nodeTypeDrop.setValue("Department");
                    break;
                case "ELEV":
                    nodeTypeDrop.setValue("Elevator");
                    break;
                case "EXIT":
                    nodeTypeDrop.setValue("Exit");
                    break;
                case "FOOD":
                    nodeTypeDrop.setValue("Food");
                    break;
                case "HALL":
                    nodeTypeDrop.setValue("Hallway");
                    break;
                case "KIOS":
                    nodeTypeDrop.setValue("Kiosk");
                    break;
                case "LAB":
                    nodeTypeDrop.setValue("Laboratory");
                    break;
                case "PARK":
                    nodeTypeDrop.setValue("Parking Lot");
                    break;
                case "REST":
                    nodeTypeDrop.setValue("Restroom");
                    break;
                case "SERV":
                    nodeTypeDrop.setValue("Service Area");
                    break;
                case "STAI":
                    nodeTypeDrop.setValue("Staircase");
                    break;
                case "WALK":
                    nodeTypeDrop.setValue("Walkway");
                    break;

            }
        }

        nodeTypeDrop.setItems(list); //I'm in the middle of fixing this


    }
    @FXML
    public void handleSaveButton() throws InvalidEdgeException {
        if(App.mapInteractionModel.getCurrentAction().equals("NONE")) {

            if(!longNameText.getText().equals("") && !shortNameText.getText().equals("") && !nodeTypeDrop.getValue().equals("")){
                Node thisNode = App.mapService.getNodeFromID(App.mapInteractionModel.getNodeID());
                App.undoRedoService.updateNode(thisNode.getNodeID(), App.mapInteractionModel.getCoords()[0], App.mapInteractionModel.getCoords()[1],getNodeType(), longNameText.getText(), shortNameText.getText());
            }

            App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
            ((Pane) App.mapInteractionModel.selectedNodeContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);

        } else if(App.mapInteractionModel.getCurrentAction().equals("ADDNODE")){
            App.undoRedoService.addNode(App.mapInteractionModel.getCoords()[0], App.mapInteractionModel.getCoords()[1], App.mapInteractionModel.getFloor(), App.mapInteractionModel.getBuilding(), getNodeType(),longNameText.getText(), shortNameText.getText());
            App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
            ((Pane) App.mapInteractionModel.selectedNodeContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
        }


    }

    public String getNodeType(){
        switch(nodeTypeDrop.getValue().toString()){
            case "Conference Room":
                return "CONF";
            case "Department":
                return "DEPT";
            case "Elevator":
                return "ELEV";
            case "Exit":
                return "EXIT";
            case "Food":
                return "FOOD";
            case "Hallway":
                return "HALL";
            case "Kiosk":
                return "KIOS";
            case "Laboratory":
                return "LAB";
            case "Parking Lot":
                return "PARK";
            case "Restroom":
                return "REST";
            case "Service Area":
                return "SERV";
            case "Staircase":
                return "STAI";
            case "Walkway":
                return "WALK";
        }
        return "";
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
        ((Pane) App.mapInteractionModel.selectedNodeContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
    }



}
