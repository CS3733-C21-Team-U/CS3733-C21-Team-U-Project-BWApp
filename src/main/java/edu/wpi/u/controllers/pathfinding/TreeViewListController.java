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
    private TreeItem rootConf = new TreeItem("Conference Rooms");
    private TreeItem rootDept = new TreeItem("Departments");
    private TreeItem rootElev = new TreeItem("Elevators");
    private TreeItem rootExit = new TreeItem("Entrances and Exits");
    private TreeItem rootFood = new TreeItem("Food Services");
    private TreeItem rootKios = new TreeItem("Kiosks");
    private TreeItem rootLabs = new TreeItem("Labs");
    private TreeItem rootPark = new TreeItem("Parking Spaces");
    private TreeItem rootRest = new TreeItem("Restrooms");
    private TreeItem rootServ = new TreeItem("Services");
    private TreeItem rootStai = new TreeItem("Stairways");

    private boolean confExpanded;
    private boolean deptExpanded;
    private boolean elevExpanded;
    private boolean exitExpanded;
    private boolean foodExpanded;
    private boolean kiosExpanded;
    private boolean labsExpanded;
    private boolean parkExpanded;
    private boolean restExpanded;
    private boolean servExpanded;
    private boolean staiExpanded;

    private boolean isStartNode = true;

    private HashMap<String,String> longToID;

    /**
     * JavaFX initialize function. Not entirely sure what it should do, but I just have it filling the tree with text
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb){
        //System.out.println("initialized");
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

        App.mapInteractionModel.mapTargetNode2.addListener(e -> {
            isStartNode = true;
        });

        App.mapInteractionModel.mapTargetNode.addListener(e -> {
            isStartNode = false;
        });

        longToID = new HashMap<>();
        fillAllTrees();

        // A little bit janky solution that allows the TreeItems to be clickable because TreeItem.addEventHandler doesn't work
        // Basically it adds a listener to each TreeItem in the tree, and when clicked it calls the method handleMouseClicked, which sets start/end node
        confTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)confTree.getSelectionModel().getSelectedItem()).getValue())));
        deptTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)deptTree.getSelectionModel().getSelectedItem()).getValue())));
        elevTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)elevTree.getSelectionModel().getSelectedItem()).getValue())));
        exitTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)exitTree.getSelectionModel().getSelectedItem()).getValue())));
        foodTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)foodTree.getSelectionModel().getSelectedItem()).getValue())));
        kiosTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)kiosTree.getSelectionModel().getSelectedItem()).getValue())));
        labsTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)labsTree.getSelectionModel().getSelectedItem()).getValue())));
        parkTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)parkTree.getSelectionModel().getSelectedItem()).getValue())));
        restTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)restTree.getSelectionModel().getSelectedItem()).getValue())));
        servTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)servTree.getSelectionModel().getSelectedItem()).getValue())));
        staiTree.getSelectionModel().selectedItemProperty().addListener(e -> handleMouseClicked(longToID.get((String)((TreeItem)staiTree.getSelectionModel().getSelectedItem()).getValue())));

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
                case "ELEV":
                    rootElev.getChildren().add(temp);
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
                    rootRest.getChildren().add(temp);
                    longToID.put(n.getLongName(),n.getNodeID());
                    break;
                case "SERV":
                    rootServ.getChildren().add(temp);
                    longToID.put(n.getLongName(),n.getNodeID());
                    break;
                case "STAI":
                    rootStai.getChildren().add(temp);
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
        if(App.mapService.getNodes().contains(App.mapService.getNodeFromID(value))) {
            if(isStartNode) {
                System.out.println("Clicked " + value + " as new start value");
                App.mapInteractionModel.setStartNode(value);
            } else {
                System.out.println("Clicked " + value + " as new end value");
                App.mapInteractionModel.setEndNode(value);
            }
        }
        // TODO: Close TreeViewListController
    }


    /**
     * Generalized method for expanding/collapsing, handles when the actual tree is pressed
     * Will be called with the treeType/nodeType as in input from the individual methods
     */
    private void expandAndCollapse(String treeType){
        confTree.setPrefSize(350.0,24.0);
        switch(treeType){
            case "CONF":
                confTree.setPrefSize(350.0, 144);
                if(confExpanded){
                    rootConf.setExpanded(false);
                    System.out.println("Conferences Collapsed");
                    confExpanded = false;
                } else {
                    rootConf.setExpanded(true);
                    System.out.println("Conferences Expanded");
                    confExpanded = true;
                }
                break;
            case "DEPT":
                if(deptExpanded){
                    rootDept.setExpanded(false);
                    System.out.println("Departments Collapsed");
                    deptExpanded = false;
                } else {
                    rootDept.setExpanded(true);
                    System.out.println("Departments Expanded");
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
