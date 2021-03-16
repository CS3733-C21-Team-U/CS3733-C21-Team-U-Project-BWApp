package edu.wpi.u.controllers.pathfinding;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TreeViewListController implements Initializable {

    // Creating each tree
    @FXML
    private TreeView confTree;
    @FXML
    private TreeView deptTree;
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

    // Creating roots for each tree, these are actual TreeItems, while representing the root of the TreeView
    private TreeItem rootConf = new TreeItem("Conference Rooms");
    private TreeItem rootDept = new TreeItem("Departments");
    private TreeItem rootExit = new TreeItem("Entrances and Exits");
    private TreeItem rootFood = new TreeItem("Food Services");
    private TreeItem rootKios = new TreeItem("Kiosks");
    private TreeItem rootLabs = new TreeItem("Labs");
    private TreeItem rootPark = new TreeItem("Parking Spaces");
    private TreeItem rootRest = new TreeItem("Restrooms");
    private TreeItem rootServ = new TreeItem("Services");

    // Booleans to store if a tree is expanded or not
    private boolean confExpanded;
    private boolean deptExpanded;
    private boolean exitExpanded;
    private boolean foodExpanded;
    private boolean kiosExpanded;
    private boolean labsExpanded;
    private boolean parkExpanded;
    private boolean restExpanded;
    private boolean servExpanded;

    // Used to decide if start or end node is selected
    private boolean isStartNode = true;

    // Hashmap to store long name to nodeID for each node added to an item
    // This is needed because a TreeItem can only store one value, which also happens to be what text is being shown
    private HashMap<String,String> longToID;

    /**
     * JavaFX initialize function, which:
     * - Sets variables
     * - Adds a listener for start/end boxes
     * - Fills trees with their Items
     * - Adds listeners to each item
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb){
        //System.out.println("initialized");
        confExpanded = false;
        deptExpanded = false;
        exitExpanded = false;
        foodExpanded = false;
        kiosExpanded = false;
        labsExpanded = false;
        parkExpanded = false;
        restExpanded = false;
        servExpanded = false;

        // Setting listener to check if start or end node will be set
        App.mapInteractionModel.currentTargetNode.addListener((observable, oldVal, newVal) ->{
            if(newVal.equals("START")){
                isStartNode = true;
            }else{
                isStartNode = false;
            }
        });

        longToID = new HashMap<>();
        fillAllTrees();

        // Not my favorite solution, but this is what allows each item in each tree to be clickable
        // Basically it adds a listener to each TreeItem in the tree, and when clicked it calls the method handleMouseClicked, which sets start/end node
        confTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)confTree.getSelectionModel().getSelectedItem()).getValue())));
        deptTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)deptTree.getSelectionModel().getSelectedItem()).getValue())));
        exitTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)exitTree.getSelectionModel().getSelectedItem()).getValue())));
        foodTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)foodTree.getSelectionModel().getSelectedItem()).getValue())));
        kiosTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)kiosTree.getSelectionModel().getSelectedItem()).getValue())));
        labsTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)labsTree.getSelectionModel().getSelectedItem()).getValue())));
        parkTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)parkTree.getSelectionModel().getSelectedItem()).getValue())));
        restTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)restTree.getSelectionModel().getSelectedItem()).getValue())));
        servTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)servTree.getSelectionModel().getSelectedItem()).getValue())));
    }

    /**
     * Fills all TreeViews with their respective Nodes
     */
    private void fillAllTrees(){
        // Creating easy access to list of full nodes
        ArrayList<Node> allNodes = App.mapService.getNodes();

        // Setting the root for each tree
        confTree.setRoot(rootConf);
        deptTree.setRoot(rootDept);
        exitTree.setRoot(rootExit);
        foodTree.setRoot(rootFood);
        kiosTree.setRoot(rootKios);
        labsTree.setRoot(rootLabs);
        parkTree.setRoot(rootPark);
        restTree.setRoot(rootRest);
        servTree.setRoot(rootServ);

        // Putting applicable nodes into trees
        // Currently displays long name for applicable nodes
        for(Node n: allNodes){
            TreeItem temp = new TreeItem(n.getLongName());
            switch(n.getNodeType()){
                case "CONF":
                    rootConf.getChildren().add(temp);
                    longToID.put(n.getLongName(),n.getNodeID());
                    break;
                case "DEPT":
                    rootDept.getChildren().add(temp);
                    longToID.put(n.getLongName(),n.getNodeID());
                    break;
                case "EXIT":
                    rootExit.getChildren().add(temp);
                    longToID.put(n.getLongName(),n.getNodeID());
                    break;
                case "FOOD":
                    rootFood.getChildren().add(temp);
                    longToID.put(n.getLongName(),n.getNodeID());
                    break;
                case "KIOS":
                    rootKios.getChildren().add(temp);
                    longToID.put(n.getLongName(),n.getNodeID());
                    break;
                case "LAB":
                    rootLabs.getChildren().add(temp);
                    longToID.put(n.getLongName(),n.getNodeID());
                    break;
                case "PARK":
                    rootPark.getChildren().add(temp);
                    longToID.put(n.getLongName(),n.getNodeID());
                    break;
                case "REST":
                    n.setLongName(n.getLongName() + " floor " + n.getFloor());
                    temp.setValue(n.getLongName());
                    rootRest.getChildren().add(temp);
                    longToID.put(n.getLongName(),n.getNodeID());
                    break;
                case "SERV":
                    rootServ.getChildren().add(temp);
                    longToID.put(n.getLongName(),n.getNodeID());
                    break;
            }
        }

    }

    /**
     * Handles a mouse click on any TreeItem (including root sadly)
     * Only uses values of nodes that actually exist in mapService as a result
     * @param value
     */
    private void handleMouseClicked(String value){

        if(App.mapService.getNodes().contains(App.mapService.getNodeFromID(value))) { // If the item is a node, not a collapsable Item
            collapseAllTrees();
            if(isStartNode) {
                App.mapInteractionModel.setStartNode(value); // Add to start
            } else {
                App.mapInteractionModel.setEndNode(value); // Add to end
            }
        }

        // Removing the ugly grey highlights after an Item is selected
        confTree.getSelectionModel().select(-1);
        deptTree.getSelectionModel().select(-1);
        exitTree.getSelectionModel().select(-1);
        foodTree.getSelectionModel().select(-1);
        kiosTree.getSelectionModel().select(-1);
        labsTree.getSelectionModel().select(-1);
        parkTree.getSelectionModel().select(-1);
        restTree.getSelectionModel().select(-1);
        servTree.getSelectionModel().select(-1);
    }

    /**
     * Generalized method for expanding/collapsing, handles when the actual tree is pressed
     * Will be called with the treeType/nodeType as in input from the individual methods
     */
    private void expandAndCollapse(String treeType){

        collapseAllTrees();

        switch(treeType){
            case "CONF":
                if(confExpanded){
                    confTree.setPrefSize(350.0, 32.0);
                    rootConf.setExpanded(false);
                    System.out.println("Conferences Collapsed");
                    confExpanded = false;
                } else {
                    confTree.setPrefSize(350.0, 144.0);
                    rootConf.setExpanded(true);
                    System.out.println("Conferences Expanded");
                    confExpanded = true;
                }
                break;
            case "DEPT":
                if(deptExpanded){
                    deptTree.setPrefSize(350.0, 32.0);
                    rootDept.setExpanded(false);
                    System.out.println("Departments Collapsed");
                    deptExpanded = false;
                } else {
                    deptTree.setPrefSize(350.0, 144.0);
                    rootDept.setExpanded(true);
                    System.out.println("Departments Expanded");
                    deptExpanded = true;
                }
                break;
            case "EXIT":
                if(exitExpanded){
                    exitTree.setPrefSize(350.0, 32.0);
                    rootExit.setExpanded(false);
                    exitExpanded = false;
                } else {
                    exitTree.setPrefSize(350.0, 144.0);
                    rootExit.setExpanded(true);
                    exitExpanded = true;
                }
                break;
            case "FOOD":
                if(foodExpanded){
                    foodTree.setPrefSize(350.0, 32.0);
                    rootFood.setExpanded(false);
                    foodExpanded = false;
                } else {
                    foodTree.setPrefSize(350.0, 144.0);
                    rootFood.setExpanded(true);
                    foodExpanded = true;
                }
                break;
            case "KIOS":
                if(kiosExpanded){
                    kiosTree.setPrefSize(350.0, 32.0);
                    rootKios.setExpanded(false);
                    kiosExpanded = false;
                } else {
                    kiosTree.setPrefSize(350.0, 144.0);
                    rootKios.setExpanded(true);
                    kiosExpanded = true;
                }
                break;
            case "LABS":
                if(labsExpanded){
                    labsTree.setPrefSize(350.0, 32.0);
                    rootLabs.setExpanded(false);
                    labsExpanded = false;
                } else {
                    labsTree.setPrefSize(350.0, 144.0);
                    rootLabs.setExpanded(true);
                    labsExpanded = true;
                }
                break;
            case "PARK":
                if(parkExpanded){
                    parkTree.setPrefSize(350.0, 32.0);
                    rootPark.setExpanded(false);
                    parkExpanded = false;
                } else {
                    parkTree.setPrefSize(350.0, 144.0);
                    rootPark.setExpanded(true);
                    parkExpanded = true;
                }
                break;
            case "REST":
                if(restExpanded){
                    restTree.setPrefSize(350.0, 32.0);
                    rootRest.setExpanded(false);
                    restExpanded = false;
                } else {
                    restTree.setPrefSize(350.0, 144.0);
                    rootRest.setExpanded(true);
                    restExpanded = true;
                }
                break;
            case "SERV":
                if(servExpanded){
                    servTree.setPrefSize(350.0, 32.0);
                    rootServ.setExpanded(false);
                    servExpanded = false;
                } else {
                    servTree.setPrefSize(350.0, 144.0);
                    rootServ.setExpanded(true);
                    servExpanded = true;
                }
                break;
        }
    }

    /**
     * Collapses all trees and sets their size to be smaller
     */
    public void collapseAllTrees(){
        confTree.setPrefSize(350.0,32.0);
        deptTree.setPrefSize(350.0,32.0);
        exitTree.setPrefSize(350.0,32.0);
        foodTree.setPrefSize(350.0,32.0);
        kiosTree.setPrefSize(350.0,32.0);
        labsTree.setPrefSize(350.0,32.0);
        parkTree.setPrefSize(350.0,32.0);
        restTree.setPrefSize(350.0,32.0);
        servTree.setPrefSize(350.0,32.0);

        rootConf.setExpanded(false);
        rootDept.setExpanded(false);
        rootExit.setExpanded(false);
        rootFood.setExpanded(false);
        rootKios.setExpanded(false);
        rootLabs.setExpanded(false);
        rootPark.setExpanded(false);
        rootRest.setExpanded(false);
        rootServ.setExpanded(false);

        confExpanded = false;
        deptExpanded = false;
        exitExpanded = false;
        foodExpanded = false;
        kiosExpanded = false;
        labsExpanded = false;
        parkExpanded = false;
        restExpanded = false;
        servExpanded = false;

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



}
