package edu.wpi.u.controllers.pathfinding;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TreeViewListController implements Initializable {

    // Creating each tree
    @FXML
    private TreeView confTree;
    @FXML
    private TreeView deptTree;
    @FXML
    private TreeView elevTree;
    @FXML
    private TreeView exitTree;
    @FXML
    private TreeView foodTree;
    @FXML
    private TreeView kiosTree;
    @FXML
    private TreeView labsTree;
    @FXML
    private TreeView parkTree;
    @FXML
    private TreeView restTree;
    @FXML
    private TreeView servTree;
    @FXML
    private TreeView staiTree;

    // Creating roots for each tree
    TreeItem rootConf = new TreeItem("Conference Rooms");
    TreeItem rootDept = new TreeItem("Departments");
    TreeItem rootElev = new TreeItem("Elevators");
    TreeItem rootExit = new TreeItem("Exits");
    TreeItem rootFood = new TreeItem("Food Services");
    TreeItem rootKios = new TreeItem("Kiosks");
    TreeItem rootLabs = new TreeItem("Labs");
    TreeItem rootPark = new TreeItem("Parking Spaces");
    TreeItem rootRest = new TreeItem("Restrooms");
    TreeItem rootServ = new TreeItem("Services");
    TreeItem rootStai = new TreeItem("Stairways");


    boolean confExpanded;
    boolean deptExpanded;
    boolean elevExpanded;
    boolean exitExpanded;
    boolean foodExpanded;
    boolean kiosExpanded;
    boolean labsExpanded;
    boolean parkExpanded;
    boolean restExpanded;
    boolean servExpanded;
    boolean staiExpanded;

    boolean isStartNode; // TODO: Talk about this


    /**
     * TODO:
     *      1. (Do last)Bring up the ui component when start or end node text input thing is pressed in pathfinding
     *          - there should be some boolean passed in that indicates start or end node
     *          - Supposed to do this with someone or have them do it
     *      2. How to invoke initialize?
     */


    /**
     * JavaFX initialize function. Not entirely sure what it should do, but I just have it filling the tree with text
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb){
        confExpanded = false;
        deptExpanded = false;
        elevExpanded = false;
        exitExpanded = false;
        foodExpanded = false;
        kiosExpanded = false;
        labsExpanded = false;
        parkExpanded = false;
        restExpanded = false;
        servExpanded = false;
        staiExpanded = false;

        isStartNode = true; // TODO: This should change depending on which search bar is clicked

        fillAllTrees();

    }

    /**
     * Fills all TreeViews with their respective Nodes... might not need to be called by this class?
     */
    private void fillAllTrees(){
        // Creating easy access to list of full nodes
        ArrayList<Node> allNodes = App.mapService.getNodes();

        // Setting the root for each tree
        confTree.setRoot(rootConf);
        deptTree.setRoot(rootDept);
        elevTree.setRoot(rootElev);
        exitTree.setRoot(rootExit);
        foodTree.setRoot(rootFood);
        kiosTree.setRoot(rootKios);
        labsTree.setRoot(rootLabs);
        parkTree.setRoot(rootPark);
        restTree.setRoot(rootRest);
        servTree.setRoot(rootServ);
        staiTree.setRoot(rootStai);

        // Putting applicable nodes into trees
        // Currently displays long name for applicable nodes
        // Make it store the Node, but display longName?
        for(Node n: allNodes){
            TreeItem temp = new TreeItem(n.getLongName());
            switch(n.getNodeType()){
                case "CONF":
                    rootConf.getChildren().add(temp);
                    temp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(isStartNode) {
                            App.mapInteractionModel.setStartNode(n.getNodeID()); // TODO: Need to close window?
                        } else App.mapInteractionModel.setEndNode(n.getNodeID());
                    });
                    break;
                case "DEPT":
                    rootDept.getChildren().add(temp);
                    temp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(isStartNode) {
                            App.mapInteractionModel.setStartNode(n.getNodeID());
                        } else App.mapInteractionModel.setEndNode(n.getNodeID());
                    });
                    break;
                case "ELEV":
                    rootElev.getChildren().add(temp);
                    temp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(isStartNode) {
                            App.mapInteractionModel.setStartNode(n.getNodeID());
                        } else App.mapInteractionModel.setEndNode(n.getNodeID());
                    });
                    break;
                case "EXIT":
                    rootExit.getChildren().add(temp);
                    temp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(isStartNode) {
                            App.mapInteractionModel.setStartNode(n.getNodeID());
                        } else App.mapInteractionModel.setEndNode(n.getNodeID());
                    });
                    break;
                case "FOOD":
                    rootFood.getChildren().add(temp);
                    temp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(isStartNode) {
                            App.mapInteractionModel.setStartNode(n.getNodeID());
                        } else App.mapInteractionModel.setEndNode(n.getNodeID());
                    });
                    break;
                case "KIOS":
                    rootKios.getChildren().add(temp);
                    temp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(isStartNode) {
                            App.mapInteractionModel.setStartNode(n.getNodeID());
                        } else App.mapInteractionModel.setEndNode(n.getNodeID());
                    });
                    break;
                case "LAB": // Todo: make sure LAB is used, not LABS in most recent AllNodes files
                    rootLabs.getChildren().add(temp);
                    temp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(isStartNode) {
                            App.mapInteractionModel.setStartNode(n.getNodeID());
                        } else App.mapInteractionModel.setEndNode(n.getNodeID());
                    });
                    break;
                case "PARK":
                    rootPark.getChildren().add(temp);
                    temp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(isStartNode) {
                            App.mapInteractionModel.setStartNode(n.getNodeID());
                        } else App.mapInteractionModel.setEndNode(n.getNodeID());
                    });
                    break;
                case "REST":
                    rootRest.getChildren().add(temp);
                    temp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(isStartNode) {
                            App.mapInteractionModel.setStartNode(n.getNodeID());
                        } else App.mapInteractionModel.setEndNode(n.getNodeID());
                    });
                    break;
                case "SERV":
                    rootServ.getChildren().add(temp);
                    temp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(isStartNode) {
                            App.mapInteractionModel.setStartNode(n.getNodeID());
                        } else App.mapInteractionModel.setEndNode(n.getNodeID());
                    });
                    break;
                case "STAI":
                    rootStai.getChildren().add(temp);
                    temp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(isStartNode) {
                            App.mapInteractionModel.setStartNode(n.getNodeID());
                        } else App.mapInteractionModel.setEndNode(n.getNodeID());
                    });
                    break;
            }
        }

    }


    /**
     * Generalized method for expanding/collapsing, handles when the actual tree is pressed
     * Will be called with the treeType/nodeType as in input from the individual methods
     */
    private void expandAndCollapse(String treeType){
        switch(treeType){
            case "CONF":
                if(confExpanded){
                    rootConf.setExpanded(false);
                    confExpanded = false;
                } else {
                    rootConf.setExpanded(true);
                    confExpanded = true;
                }
                break;
            case "DEPT":
                if(deptExpanded){
                    rootDept.setExpanded(false);
                    deptExpanded = false;
                } else {
                    rootDept.setExpanded(true);
                    deptExpanded = true;
                }
                break;
            case "ELEV":
                if(elevExpanded){
                    rootElev.setExpanded(false);
                    elevExpanded = false;
                } else {
                    rootElev.setExpanded(true);
                    elevExpanded = true;
                }
                break;
            case "EXIT":
                if(exitExpanded){
                    rootExit.setExpanded(false);
                    exitExpanded = false;
                } else {
                    rootExit.setExpanded(true);
                    exitExpanded = true;
                }
                break;
            case "FOOD":
                if(foodExpanded){
                    rootFood.setExpanded(false);
                    foodExpanded = false;
                } else {
                    rootFood.setExpanded(true);
                    foodExpanded = true;
                }
                break;
            case "KIOS":
                if(kiosExpanded){
                    rootKios.setExpanded(false);
                    kiosExpanded = false;
                } else {
                    rootKios.setExpanded(true);
                    kiosExpanded = true;
                }
                break;
            case "LAB":
                if(labsExpanded){
                    rootLabs.setExpanded(false);
                    labsExpanded = false;
                } else {
                    rootLabs.setExpanded(true);
                    labsExpanded = true;
                }
                break;
            case "PARK":
                if(parkExpanded){
                    rootPark.setExpanded(false);
                    parkExpanded = false;
                } else {
                    rootPark.setExpanded(true);
                    parkExpanded = true;
                }
                break;
            case "REST":
                if(restExpanded){
                    rootRest.setExpanded(false);
                    restExpanded = false;
                } else {
                    rootRest.setExpanded(true);
                    restExpanded = true;
                }
                break;
            case "SERV":
                if(servExpanded){
                    rootServ.setExpanded(false);
                    servExpanded = false;
                } else {
                    rootServ.setExpanded(true);
                    servExpanded = true;
                }
                break;
            case "STAI":
                if(staiExpanded){
                    rootStai.setExpanded(false);
                    staiExpanded = false;
                } else {
                    rootStai.setExpanded(true);
                    staiExpanded = true;
                }
                break;
        }
    }

    /**
     * The following methods use expandAndCollapse to handle the mouse clicks
     */
    @FXML
    public void handleConfClicked(){
        expandAndCollapse("CONF");
    }
    @FXML
    public void handleDeptClicked(){
        expandAndCollapse("DEPT");
    }
    @FXML
    public void handleElevClicked(){
        expandAndCollapse("ELEV");
    }
    @FXML
    public void handleExitClicked(){
        expandAndCollapse("EXIT");
    }
    @FXML
    public void handleFoodClicked(){
        expandAndCollapse("FOOD");
    }
    @FXML
    public void handleKiosClicked(){
        expandAndCollapse("KIOS");
    }
    @FXML
    public void handleLabsClicked(){
        expandAndCollapse("LABS");
    }
    @FXML
    public void handleParkClicked(){
        expandAndCollapse("PARK");
    }
    @FXML
    public void handleRestClicked(){
        expandAndCollapse("REST");
    }
    @FXML
    public void handleServClicked(){
        expandAndCollapse("SERV");
    }
    @FXML
    public void handleStaiClicked(){
        expandAndCollapse("STAI");
    }


}
