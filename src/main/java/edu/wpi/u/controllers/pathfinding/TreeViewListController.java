package edu.wpi.u.controllers.pathfinding;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;

public class TreeViewListController {

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


    /**
     * TODO:
     *      1. Bring up the ui component when start or end node is pressed in scenebuilder
     *          - there should be some boolean passed in that indicates start or end node
     *          - Supposed to do this with someone
     *      2. How to expand a tree on mousePressed - Is this built in?
     *      3. How to collapse a tree on mousePressed - Is this also built in?
     *      4. How to select locations (indicated by longName)
     *          - Will use App.mapInteractionModel.setStartNode(String), and setEndNode
     *          - Figure out how to apply some function when a location is pressed
     *          - Need to know nodeID of whatever location is pressed
     */


    /**
     * JavaFX initialize function. Not entirely sure what it should do, but I just have it filling the tree with text
     */
    @FXML
    public void initialize(){
        fillAllTrees();
    }

    /**
     * Fills all TreeViews with their respective Nodes... might not need to be called by this class?
     */
    public void fillAllTrees(){

        // Creating easy access to list of full nodes
        ArrayList<Node> allNodes = App.mapService.getNodes();

        // Creating roots for each tree
        TreeItem rootConf = new TreeItem("Conference");
        TreeItem rootDept = new TreeItem("Departments");
        TreeItem rootElev = new TreeItem("Elevators");
        TreeItem rootExit = new TreeItem("Exits");
        TreeItem rootFood = new TreeItem("Food Services");
        TreeItem rootKios = new TreeItem("Kiosks");
        TreeItem rootLabs = new TreeItem("Labs");
        TreeItem rootPark = new TreeItem("Parking Options");
        TreeItem rootRest = new TreeItem("Restrooms");
        TreeItem rootServ = new TreeItem("Services");
        TreeItem rootStai = new TreeItem("Stairways");

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
        for(Node n: allNodes){
            switch(n.getNodeType()){
                case "CONF":
                    rootPark.getChildren().add(new TreeItem(n.getLongName()));
                    break;
                case "DEPT":
                    rootDept.getChildren().add(new TreeItem(n.getLongName()));
                    break;
                case "ELEV":
                    rootElev.getChildren().add(new TreeItem(n.getLongName()));
                    break;
                case "EXIT":
                    rootExit.getChildren().add(new TreeItem(n.getLongName()));
                    break;
                case "FOOD":
                    rootFood.getChildren().add(new TreeItem(n.getLongName()));
                    break;
                case "KIOS":
                    rootKios.getChildren().add(new TreeItem(n.getLongName()));
                    break;
                case "LAB":
                    rootLabs.getChildren().add(new TreeItem(n.getLongName()));
                    break;
                case "PARK":
                    rootPark.getChildren().add(new TreeItem(n.getLongName()));
                    break;
                case "REST":
                    rootRest.getChildren().add(new TreeItem(n.getLongName()));
                    break;
                case "SERV":
                    rootServ.getChildren().add(new TreeItem(n.getLongName()));
                    break;
                case "STAI":
                    rootStai.getChildren().add(new TreeItem(n.getLongName()));
                    break;
            }
        }

    }


}
