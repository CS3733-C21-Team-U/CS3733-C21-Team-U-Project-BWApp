package edu.wpi.u.controllers.mapbuilder;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.exceptions.InvalidEdgeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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
    @FXML
    Label longNameErrorLabel, shortNameErrorLabel,nodeTypeErrorLabel;
    /**
     * To be run when save button in node context menu is pressed. Needs to check if a node can be saved, then saves a node.
     */
    @FXML
    public void initialize(){
        longNameErrorLabel.setVisible(false);
        shortNameErrorLabel.setVisible(false);
        nodeTypeErrorLabel.setVisible(false);

        longNameText.focusedProperty().addListener(observable -> {
            longNameErrorLabel.setVisible(false);
        });

        shortNameText.focusedProperty().addListener(observable -> {
            shortNameErrorLabel.setVisible(false);
        });

        nodeTypeDrop.focusedProperty().addListener(observable -> {
            nodeTypeErrorLabel.setVisible(false);
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
        } else if(App.mapInteractionModel.getCurrentAction().equals("MULTISELECT") && App.mapInteractionModel.nodeIDList.size() > 1){
            longNameText.setDisable(true);
            shortNameText.setDisable(true);
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
        if(App.mapInteractionModel.getCurrentAction().equals("SELECT")) {

            if(!longNameText.getText().equals("") && !shortNameText.getText().equals("") && !nodeTypeDrop.getValue().equals("")){
                Node thisNode = App.mapService.getNodeFromID(App.mapInteractionModel.getNodeID());
                App.undoRedoService.updateNode(thisNode.getNodeID(), App.mapInteractionModel.getCoords()[0], App.mapInteractionModel.getCoords()[1],getNodeType(), longNameText.getText(), shortNameText.getText());
            }else{
                if(longNameText.getText().equals("")){
                    longNameErrorLabel.setVisible(true);
                }
                if(shortNameText.getText().equals("")){
                    shortNameErrorLabel.setVisible(true);
                }
                if(nodeTypeDrop.getValue().equals("")){
                    nodeTypeErrorLabel.setVisible(true);
                }
                return;
            }

            App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
            ((Pane) App.mapInteractionModel.selectedContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedContextBox);

        } else if(App.mapInteractionModel.getCurrentAction().equals("ADDNODE")){
            App.undoRedoService.addNode(App.mapInteractionModel.getCoords()[0], App.mapInteractionModel.getCoords()[1], App.mapInteractionModel.getFloor(), App.mapInteractionModel.getBuilding(), getNodeType(),longNameText.getText(), shortNameText.getText());
            App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
            ((Pane) App.mapInteractionModel.selectedContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedContextBox);
        } else if(App.mapInteractionModel.getCurrentAction().equals("MULTISELECT")){
            for(String curNodeID : App.mapInteractionModel.nodeIDList){
                Node curNode = App.mapService.getNodeFromID(curNodeID);
                App.undoRedoService.updateNode(curNodeID,curNode.getCords()[0],curNode.getCords()[1],getNodeType(),curNode.getLongName(),curNode.getShortName());
            }
            ((Pane) App.mapInteractionModel.selectedContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedContextBox);
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
            ((Pane) App.mapInteractionModel.selectedContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedContextBox);
        } else if(App.mapInteractionModel.getCurrentAction().equals("MULTISELECT")){
            for(String curNodeID : App.mapInteractionModel.nodeIDList){
                App.undoRedoService.deleteNode(curNodeID);
            }
        } else {
            App.undoRedoService.deleteNode(App.mapInteractionModel.getNodeID());
        }
        App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
        ((Pane) App.mapInteractionModel.selectedContextBox.getParent()).getChildren().remove(App.mapInteractionModel.selectedContextBox);
    }



}
