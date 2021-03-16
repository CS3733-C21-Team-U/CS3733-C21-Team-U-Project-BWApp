package edu.wpi.u.controllers.mapbuilder;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.util.LinkedList;

public class MapBuilderBaseController {

    static final Duration DURATION = Duration.millis(300);
    @FXML public AnchorPane mainAnchorPane;
    public JFXToggleNode toggle1;
    public JFXToggleNode toggle2;
    public JFXToggleNode toggle3;
    public JFXToggleNode toggle4;
    public JFXToggleNode toggle5;
    public JFXToggleNode toggle6;

    @FXML
    public JFXToggleNode selectButton, multiSelectButton, addNodeButton, addEdgeButton, alineButton;
    public JFXButton redoButton,undoButton;

    AnchorPane pane = new AnchorPane();
    ImageView node = new ImageView();
    Group nodesAndEdges = new Group();
    public GesturePane map = new GesturePane(pane);
    String selectedColor = "6200ee";
    String errorColor = "8862bf";

    boolean clickedOnSomethingFlag = false;
    /**
     * Initializes the admin map screen with map zoom, and all node and edge placement
     * @throws IOException
     */
    public void initialize() throws Exception {
        switch (App.themeString){
            case "PURPLE":
                selectedColor = "6200ee";
                errorColor = "8862bf";
                break;
            case "DARK":
                selectedColor = "bb86fc";
                errorColor = "8d52d9";
                break;
            case "YELLOW":
                selectedColor = "B00020";
                errorColor = "be4b19";
                break;
            case "BLUE":
                selectedColor = "5785d4";
                errorColor = "00348c";
                break;
        }
        App.mapService.loadStuff();
        // Loading the map
        node = new ImageView(String.valueOf(getClass().getResource(App.mapInteractionModel.mapImageResource.get())));
        node.setFitWidth(2987);
        node.setPreserveRatio(true);
        pane.getChildren().add(node);
        pane.getChildren().add(nodesAndEdges);

        map.setMinScale(0.3);
        map.setMaxScale(2);
        map.centreOn(new Point2D(700, 4000));
        map.zoomTo(0.5, map.targetPointAtViewportCentre());

        map.setPrefWidth(1920);
        map.setPrefHeight(1000);
        map.setFitMode(GesturePane.FitMode.UNBOUNDED);
        map.setScrollMode(GesturePane.ScrollMode.ZOOM);

        mainAnchorPane.getChildren().add(map);
        map.toBack();

        //setup tooltips
        selectButton.setTooltip(new Tooltip("Select Tool"));
        multiSelectButton.setTooltip(new Tooltip("Multi-Select Tool"));
        addNodeButton.setTooltip(new Tooltip("Add Node Tool"));
        addEdgeButton.setTooltip(new Tooltip("Add Edge Tool"));
        alineButton.setTooltip(new Tooltip("Align Selected Nodes Tool"));
        redoButton.setTooltip(new Tooltip("Redo Edit"));
        undoButton.setTooltip(new Tooltip("Undo Edit"));

        toggle2.setTooltip(new Tooltip("Floor 1"));
        toggle3.setTooltip(new Tooltip("Floor 2"));
        toggle4.setTooltip(new Tooltip("Floor 3"));
        toggle5.setTooltip(new Tooltip("Floor 4"));
        toggle6.setTooltip(new Tooltip("Floor 5"));
        toggle1.setTooltip(new Tooltip("Floor G"));
        //handle converting converting clicks on the screen into map space


        map.setOnMouseDragged(e ->{//mouse dragging listener---------------------------------------------
            setMouseCoordinates(e);
        });
        map.setOnMouseDragReleased(e ->{//mouse dragging ending listener---------------------------------------------
            setMouseCoordinates(e);
        });
        map.setOnMouseReleased(e -> {
            if(!clickedOnSomethingFlag){
                pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
                nodesAndEdges.getChildren().clear();
                generateEdges(App.mapInteractionModel.floor);
                generateNodes(App.mapInteractionModel.floor);
                LinkedList<String> nodesToReset = App.mapInteractionModel.resetNodeIDList();
            }else{
                clickedOnSomethingFlag = false;
            }
        });
        map.setOnMouseClicked(e -> {//mouse clicking listener -----------------------------------------------
            clickedOnSomethingFlag = false;
//            if(findCircleFromNode(App.mapInteractionModel.getPreviousNodeID()) != null){ //to prevent a null pointer when the previous node isn't being displayed
//                findCircleFromNode(App.mapInteractionModel.getPreviousNodeID()).setFill(Paint.valueOf(errorColor));
//            }//this makes sure any nodes that were previously clicked are changes back to the original red color
            setMouseCoordinates(e);
            Point2D pivotOnTarget = map.targetPointAt(new Point2D(e.getX(), e.getY()))
                    .orElse(map.targetPointAtViewportCentre());

            setMouseCoordinates(e);//this sets the mouse coordinates
            // Trying add node context menu
            try {
                if (App.mapInteractionModel.getCurrentAction().equals("ADDNODE") && !clickedOnSomethingFlag) {
                    Node n = new Node();//just an empty node to make the context menu appear correctly
                    makeContextMenu(n, App.mapInteractionModel.getCoords()[0], App.mapInteractionModel.getCoords()[1] );
                }else{

                }
            } catch(IOException ex){
                ex.printStackTrace();
            }

            // Map zooming stuff
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                // increment of scale makes more sense exponentially instead of linearly
                map.animate(DURATION)
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomBy(map.getCurrentScale(), pivotOnTarget);
            } else if (e.getButton() == MouseButton.SECONDARY && e.getClickCount() == 1) {
                map.animate(DURATION)
                        .interpolateWith(Interpolator.EASE_BOTH)
                        .zoomTo(map.getMinScale(), pivotOnTarget);
            }
        });

        // Creating nodes and edges
        generateEdges(App.mapInteractionModel.floor);
        generateNodes(App.mapInteractionModel.floor);


        App.mapInteractionModel.mapImageResource.addListener((observable, oldValue, newValue)  ->{//this is setting the listener for changing the maps when the floor is changed
            pane.getChildren().remove(node);
            node = new ImageView(String.valueOf(getClass().getResource(App.mapInteractionModel.mapImageResource.get())));
            if(App.mapInteractionModel.floor.equals("G")){
                node.setFitWidth(2987);//this is the correct width for the ground floor map
            } else{
                node.setFitWidth(2470);//this is the correct width for the regular floor map
            }
            node.setPreserveRatio(true);
            pane.getChildren().add(node);//adding the new image to the GesturePane
        });

        App.mapInteractionModel.editFlag.addListener((observable, oldValue, newValue)  ->{
            //update nodes and edges when an edit is made
            generateEdges(App.mapInteractionModel.floor);
            generateNodes(App.mapInteractionModel.floor);
        });
    } // End of initialize-------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Sets the position, radius, id, fill, etc., of the node, and sets its action when clicked
     * @param n - Node that is being place
     * @throws IOException
     */
    public void placeNodesHelper(Node n) throws IOException{
            Circle curNode = new Circle();
            curNode.setCenterX(n.getCords()[0]);
            curNode.setCenterY(n.getCords()[1]);
            curNode.setRadius(9);
            curNode.setId(n.getNodeID());
            curNode.setStroke(Paint.valueOf(errorColor));
            curNode.setFill(Paint.valueOf(errorColor));
            curNode.setVisible(true);
            //setting mouse events for the drawn circle
            curNode.setOnMouseClicked(event -> {
                System.out.println("CLICKED NODE (Line 182)");
                clickedOnSomethingFlag = true;
            });
            curNode.setOnMouseDragged(event -> {
                if(App.mapInteractionModel.getCurrentAction().equals("SELECT")){
                    try {
                        clickedOnSomethingFlag = true;
                        handleNodeDragged(curNode); // Set visual position (circle)
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                }
            });
            curNode.setOnMouseDragExited(event -> {
                if(!App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
                    try {
                        clickedOnSomethingFlag = true;
                        handleNodeDragExit(App.mapService.getNodeFromID(curNode.getId()), curNode); // Set visual position (circle)
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            //to make sure you can move the map
            curNode.setOnMouseReleased(event -> {
                map.gestureEnabledProperty().set(true);
                try {
                    handleNodeClicked(n);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(App.mapInteractionModel.getCurrentAction().equals("SELECT")){
                    //make the gesture pane
                }
            });
        nodesAndEdges.getChildren().add(curNode);
    }

    /**
     * This generates the visible nodes that go on a particular floor. This is outside of
     * the initialize function because we need to generate nodes on the new floor changes as well
     * @param floor this is the string representing the floor of a node ("g", "1", "2"...)
     */
    public void generateNodes(String floor){
        App.mapService.getNodes().stream().forEach(n -> {
            try {
                if(n.getFloor().equals(floor)){
                    placeNodesHelper(n);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        nodesAndEdges.toFront();
    }

    /**
     * makes it so the edges attached to the node given are updated graphicly with the
     * node as its dragged
     * SHOULD BE CALLED WHILE DRAGGING A NODE
     * @param nodeOnMap the node that is being dragged
     */
    private void moveEdgesWithNode(Circle nodeOnMap){
        Node n = App.mapService.getNodeFromID(nodeOnMap.getId());
        for (Edge e : n.getEdges()) {
                if (n.getNodeID().equals(e.getEndNode().getNodeID())) {
                    //if the given node is end node of the edge
                    double xdiff = nodeOnMap.getCenterX() - e.getStartNode().getCords()[0];
                    double ydiff = nodeOnMap.getCenterY() - e.getStartNode().getCords()[1];
                    Line edgeOnMap = new Line();
                    for (javafx.scene.Node curMapNode : nodesAndEdges.getChildren()) {
                        if (curMapNode.getId().equals(e.getEdgeID())) {
                            edgeOnMap = (Line) curMapNode;
                            break;
                        }
                    }
                    edgeOnMap.setEndX(xdiff);
                    edgeOnMap.setEndY(ydiff);
                } else {
                    //if the given node is the start node of the edge
                    double xdiff = e.getEndNode().getCords()[0] - nodeOnMap.getCenterX();
                    double ydiff = e.getEndNode().getCords()[1] - nodeOnMap.getCenterY();
                    Line edgeOnMap = new Line();
                    for (javafx.scene.Node curMapNode : nodesAndEdges.getChildren()) {
                        if (curMapNode.getId().equals(e.getEdgeID())) {
                            edgeOnMap = (Line) curMapNode;
                            break;
                        }
                    }
                    edgeOnMap.setLayoutX(nodeOnMap.getCenterX());
                    edgeOnMap.setLayoutY(nodeOnMap.getCenterY());
                    edgeOnMap.setEndX(xdiff);
                    edgeOnMap.setEndY(ydiff);
                }
            }

    }

    /**
     * Sets the x vector, y vector, and other positional fields of the edge, and sets its action when clicked
     * @param ed - Edge that is clicked (variable named e is reserved for the exception thrown)
     */
    public void placeEdgesHelper(Edge ed){
        double xdiff = ed.getEndNode().getCords()[0]-ed.getStartNode().getCords()[0];
        double ydiff = ed.getEndNode().getCords()[1]-ed.getStartNode().getCords()[1];
        Line edge = new Line();
        edge.setLayoutX(ed.getStartNode().getCords()[0]);
        edge.setStartX(0);
        edge.setLayoutY(ed.getStartNode().getCords()[1]);
        edge.setStartY(0);
        edge.setEndX(xdiff);
        edge.setEndY(ydiff);
        edge.setId(ed.getEdgeID());
        edge.setStrokeWidth(7);
        edge.setStroke(Paint.valueOf(errorColor));
        edge.setVisible(true);
        edge.setOnMouseClicked(event -> {
            clickedOnSomethingFlag = true;
            System.out.println("Clicked on an Edge (Edge 300)");
        });
        edge.setOnMouseReleased(event -> {
            try {
                clickedOnSomethingFlag = true;
                handleEdgeClicked(ed);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        nodesAndEdges.getChildren().add(edge);
    }

    /**
     * generates the edges on the give floor
     * @param floor the floor to generate edges for
     */
    public void generateEdges(String floor){
        nodesAndEdges.getChildren().clear();
        App.mapService.getEdges().stream().forEach(e ->{
        try {
            if(e.getStartNode().getFloor().equals(floor) && e.getEndNode().getFloor().equals(floor)){
                placeEdgesHelper(e);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        });
        nodesAndEdges.toFront();
    }


    /**
     * Function called when and edge is clicked on. This brings up the context menu.
     * @param e - Edge that is clicked on
     * @throws IOException
     */
    public void handleEdgeClicked(Edge e) throws IOException {
        clickedOnSomethingFlag = true;
        App.mapInteractionModel.setEdgeID(e.getEdgeID());
        switch (App.mapInteractionModel.getCurrentAction()){
            case "ADDEDGE":
                break;
            case "SELECT":
                double xdiff = e.getEndNode().getCords()[0]-e.getStartNode().getCords()[0];
                double ydiff = e.getEndNode().getCords()[1]-e.getStartNode().getCords()[1];
                FXMLLoader edgeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mapbuilder/ContextMenuEdge.fxml"));
                AnchorPane EdgeContextAnchor;
                EdgeContextAnchor = edgeContextMenu.load();
                Circle previousCircle = findCircleFromNode(App.mapInteractionModel.getNodeID());
                if(previousCircle != null){ //to prevent a null pointer when the previous node isn't being displayed
                    previousCircle.setFill(Paint.valueOf(errorColor));
                }
                //find and set current edge to green
                ((Line)nodesAndEdges.lookup( "#" + e.getEdgeID())).setStroke(Paint.valueOf(selectedColor));

                EdgeContextAnchor.setLayoutX(e.getStartNode().getCords()[0]+(xdiff/2));
                EdgeContextAnchor.setLayoutY(e.getStartNode().getCords()[1]+(ydiff/2));
                pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
                pane.getChildren().add(EdgeContextAnchor);
                App.mapInteractionModel.selectedContextBox = EdgeContextAnchor;
                //find the old edge and reset its color
                Line oldEdge = ((Line)nodesAndEdges.lookup( "#" + App.mapInteractionModel.previusEdgeID));
                if (oldEdge != null){
                    oldEdge.setStroke(Paint.valueOf(errorColor));
                }
                break;
        }
        clickedOnSomethingFlag = true;
    }

    /**
     * Function called when a node is clicked. This brings up the context menu.
     * @param n - Node that is clicked on
     * @throws IOException
     */
    public void handleNodeClicked(Node n) throws IOException {
        clickedOnSomethingFlag = true;
        //find the old edge and reset its color
        for(javafx.scene.Node node : nodesAndEdges.getChildren()){
            if(node.getId().equals(App.mapInteractionModel.getEdgeID())){
                Line edge = (Line) node;
                edge.setStroke(Paint.valueOf(errorColor));
            }
        }

        App.mapInteractionModel.setCoords(new double[]{n.getCords()[0], n.getCords()[1]});

        switch (App.mapInteractionModel.getCurrentAction()){
            case "MULTISELECT":
                App.mapInteractionModel.setNodeID(n.getNodeID());
                // if its the first click treat it like it was a normal select (fall through)
                if(App.mapInteractionModel.nodeIDList.size() > 1){
                    //remove old context menu
                    pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
                    //reset a nodes color if it was toggled
                    Circle removedNode = findCircleFromNode(App.mapInteractionModel.toggledNodeID);
                    if(removedNode != null){
                        removedNode.setFill(Paint.valueOf(errorColor));
                    }
                    //highlight all the selected nodes
                    for (String node: App.mapInteractionModel.nodeIDList){
                        Circle drawnNode = (Circle) nodesAndEdges.lookup("#" + node);
                        drawnNode.setFill(Paint.valueOf(selectedColor));
                    }
                    Circle drawnNode = (Circle) nodesAndEdges.lookup("#" + n.getNodeID());
                    drawnNode.setFill(Paint.valueOf(selectedColor));
                    //spawn context menu
                    AnchorPane contextAnchor;
                    FXMLLoader nodeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mapbuilder/ContextMenuNode.fxml"));
                    contextAnchor = nodeContextMenu.load();
                    contextAnchor.setLayoutX(n.getCords()[0]);
                    contextAnchor.setLayoutY(n.getCords()[1]);
                    pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
                    pane.getChildren().add(contextAnchor);
                    App.mapInteractionModel.selectedContextBox = contextAnchor;
                    break;
                }
            case "SELECT":
                App.mapInteractionModel.setNodeID(n.getNodeID());
                //update the last node/edges clicked on
                Circle lastCircle = findCircleFromNode(App.mapInteractionModel.deselectedNodeID);
                if(lastCircle != null){
                    lastCircle.setCenterX(App.mapService.getNodeFromID(lastCircle.getId()).getCords()[0]);
                    lastCircle.setCenterY(App.mapService.getNodeFromID(lastCircle.getId()).getCords()[1]);
                    moveEdgesWithNode(lastCircle);
                }


                //deal with the multi-select queue
                if(!App.mapInteractionModel.getCurrentAction().equals("MULTISELECT")) {
                    LinkedList<String> nodesToReset = App.mapInteractionModel.resetNodeIDList();
                    for (String curNodeID : nodesToReset) {
                        Circle drawnNode = findCircleFromNode(curNodeID);
                        drawnNode.setFill(Paint.valueOf(errorColor));
                    }
                }
                //deal with the current node clicked on
                Circle curCircle = findCircleFromNode(n.getNodeID());
                moveEdgesWithNode(curCircle);
                makeContextMenu(n, curCircle.getCenterX(), curCircle.getCenterY());//The creation of the context menu for the node
                Circle clickedCircle = findCircleFromNode(n.getNodeID());
                clickedCircle.setFill(Paint.valueOf(selectedColor));
                Circle previousCircle = findCircleFromNode(App.mapInteractionModel.getPreviousNodeID());
                if(previousCircle != null){ //to prevent a null pointer when the previous node isn't being displayed
                    previousCircle.setFill(Paint.valueOf(errorColor));
                    Node previousNode = App.mapService.getNodeFromID(App.mapInteractionModel.getPreviousNodeID());
                    previousCircle.setCenterX(previousNode.getCords()[0]);
                    previousCircle.setCenterY(previousNode.getCords()[1]);
                }
                break;
            case "ADDEDGE"://TODO fix to remove the need to refresh everything to draw correctly
                //remove old temp line
                javafx.scene.Node oldTempEdge = findTempLine();
                if(oldTempEdge != null){
                    nodesAndEdges.getChildren().remove(oldTempEdge);
                }
                //update the node that was clicked on
                App.mapInteractionModel.setNodeID(n.getNodeID());
                //set all nodes to be the red because the selected ones will be set to green next
                for (javafx.scene.Node node : nodesAndEdges.getChildren() ) {
                    if(node.getClass().getSimpleName().equals("Circle")){
                        Circle circle1 = (Circle)node;
                        circle1.setFill(Paint.valueOf(errorColor));
                    }
                }
                //node selection logic
                Circle c1 = findCircleFromNode(n.getNodeID());
                Circle c2 = findCircleFromNode(App.mapInteractionModel.deselectedNodeID);
                Line tempEdge = new Line();

                //set the most recent clicked node color
                c1.setFill(Paint.valueOf(selectedColor));

                //if the previse node is on the current floor if yes draw edge
                if(App.mapInteractionModel.getPreviousNodeID() != null) {
                    if (App.mapService.getNodeFromID(App.mapInteractionModel.getPreviousNodeID()).getFloor().equals(App.mapInteractionModel.floor)) {
                        c2.toFront();
                        c2.setFill(Paint.valueOf(selectedColor));
                        // Create drawn Edge
                        double oldx = App.mapService.getNodeFromID(App.mapInteractionModel.getNodeID()).getCords()[0];
                        double oldy = App.mapService.getNodeFromID(App.mapInteractionModel.getNodeID()).getCords()[1];
                        double xdiff = 0;
                        double ydiff = 0;
                        xdiff = c2.getCenterX() - oldx;
                        ydiff = c2.getCenterY() - oldy;
                        tempEdge.setLayoutX(c1.getCenterX());
                        tempEdge.setStartX(0);
                        tempEdge.setLayoutY(c1.getCenterY());
                        tempEdge.setStartY(0);
                        tempEdge.setEndX(xdiff);
                        tempEdge.setEndY(ydiff);
                        tempEdge.setId("tempedge");
                        tempEdge.setStrokeWidth(3.0);
                        tempEdge.toFront();
                        tempEdge.setStroke(Paint.valueOf(selectedColor));
                        tempEdge.setVisible(true);
                        nodesAndEdges.getChildren().add(tempEdge);
                        makeContextMenu(tempEdge, tempEdge.getLayoutX() + (xdiff / 2), tempEdge.getLayoutY() + (ydiff / 2));//Creation of the Edge context menu
                    } else {
                        makeContextMenu(tempEdge, c1.getCenterX(), c1.getCenterY());//Creation of the Edge context menu
                    }
                }
                break;
            case "ALINE":
                //highlight all the selected nodes
                for (String node: App.mapInteractionModel.nodeIDList){
                    Circle drawnNode = (Circle) nodesAndEdges.lookup("#" + node);
                    drawnNode.setFill(Paint.valueOf(selectedColor));
                }
                if(App.mapInteractionModel.aline.equals("XCOORD")){
                    App.undoRedoService.updateNode(n.getNodeID(),App.mapInteractionModel.alineValue,n.getCords()[1],n.getNodeType(),n.getLongName(),n.getShortName());
                }else if(App.mapInteractionModel.aline.equals("YCOORD")){
                    App.undoRedoService.updateNode(n.getNodeID(),n.getCords()[0],App.mapInteractionModel.alineValue,n.getNodeType(),n.getLongName(),n.getShortName());
                }
                nodesAndEdges.getChildren().clear();
                generateEdges(App.mapInteractionModel.floor);
                generateNodes(App.mapInteractionModel.floor);
                break;
        }
        if(App.mapInteractionModel.getCurrentAction().equals("SELECT")){

        }else if(App.mapInteractionModel.getCurrentAction().equals("MULTISELECT")){

        }
        //TODO rewrite but better
        else if(App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){

        }
    }

    public Circle findCircleFromNode(String nodeID){
        for(javafx.scene.Node n: nodesAndEdges.getChildren()){
            if(n.getId().equals(nodeID)){
                return (Circle)n;
            }
        }
        //if failed to find maybe on other floor
        return null;
    }

    public javafx.scene.Node findTempLine(){
        return nodesAndEdges.lookup("#tempedge");
    }


    /**
     * Need to: Handle when node is dragged by updating coordinates on screen connecting edges. Coordinates saved in other helper function
     * @param node1 -
     */
    public void handleNodeDragged(Circle node1) {
            // Disabling map zoom and remove context menus
            map.gestureEnabledProperty().set(false);
            pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
            //set last dragged node and edges to their saved value
            Circle lastNode = (Circle) nodesAndEdges.lookup("#"+ App.mapInteractionModel.toggledNodeID);
            Node node2 = App.mapService.getNodeFromID(App.mapInteractionModel.toggledNodeID);
            lastNode.setCenterX(node2.getCords()[0]);
            lastNode.setCenterY(node2.getCords()[1]);
            moveEdgesWithNode(lastNode);
            // Update coordinates of node
            node1.setCenterX(App.mapInteractionModel.getCoords()[0]);
            node1.setCenterY(App.mapInteractionModel.getCoords()[1]);
            moveEdgesWithNode(node1);
    }

    /**
     * Saves node's coordinates whenever dragging ceases
     * @param n - Node being dragged
     */
    public void handleNodeDragExit(Node n, Circle node1) throws IOException {
        map.gestureEnabledProperty().set(true);
        handleNodeClicked(n);
    }

    @FXML
    public void handleUndoButton() throws Exception{
        App.undoRedoService.undo();
        pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
        //highlight all the selected nodes
        for (String node: App.mapInteractionModel.nodeIDList){
            Circle drawnNode = (Circle) nodesAndEdges.lookup("#" + node);
            if(drawnNode != null){
                drawnNode.setFill(Paint.valueOf(selectedColor));
            }
        }
    }

    public void handleRedoButton() throws Exception{
        App.undoRedoService.redo();
        pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
        //highlight all the selected nodes
        for (String node: App.mapInteractionModel.nodeIDList){
            Circle drawnNode = (Circle) nodesAndEdges.lookup("#" + node);
            if(drawnNode != null){
                drawnNode.setFill(Paint.valueOf(selectedColor));
            }
        }
    }


    /**
     * This is a helper function for the floor buttons.
     * It reloads the correct image onto the GesturePane and
     * loads the correct nodes onto the screen after deleting the old ones
     * @param floor this is the floor G, 1, 2, 3, 4, or 5 as a string
     * @param resource this a path that points to the correct floor map from the base package (/edu/wpi/u...)
     */
    public void loadNewMapAndGenerateHelper(String floor, String resource){
        App.mapInteractionModel.floor = floor;
        App.mapInteractionModel.mapImageResource.set(resource);
        generateEdges(floor);
        generateNodes(floor);
    }

    /**
     * This is what changes the map displayed to floor G and reloads the correct nodes
     */
    public void handleFloorGButton(){
        if(!App.mapInteractionModel.floor.equals("G")) {
            pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
            loadNewMapAndGenerateHelper("G", "/edu/wpi/u/views/Images/FaulknerCampus.png");
            node.setFitWidth(2987);
            nodesAndEdges.toFront();
        }else{
            toggle1.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 1 and reloads the correct nodes
     */
    public void handleFloor1Button(){
        if(!App.mapInteractionModel.floor.equals("1")) {
            pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
            loadNewMapAndGenerateHelper("1", "/edu/wpi/u/views/Images/FaulknerFloor1Light.png");
            nodesAndEdges.toFront();
        }else{
            toggle2.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 2 and reloads the correct nodes
     */
    public void handleFloor2Button(){
        if(!App.mapInteractionModel.floor.equals("2")) {
            pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
            loadNewMapAndGenerateHelper("2", "/edu/wpi/u/views/Images/FaulknerFloor2Light.png");
            nodesAndEdges.toFront();
        }else{
            toggle3.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 3 and reloads the correct nodes
     */
    public void handleFloor3Button(){
        if(!App.mapInteractionModel.floor.equals("3")) {
            pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
            loadNewMapAndGenerateHelper("3", "/edu/wpi/u/views/Images/FaulknerFloor3Light.png");
            nodesAndEdges.toFront();
        }else{
            toggle4.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 4 and reloads the correct nodes
     */
    public void handleFloor4Button(){
        if(!App.mapInteractionModel.floor.equals("4")) {
            pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
            loadNewMapAndGenerateHelper("4", "/edu/wpi/u/views/Images/FaulknerFloor4Light.png");
            nodesAndEdges.toFront();
        }else{
            toggle5.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 5 and reloads the correct nodes
     */
    public void handleFloor5Button(){
        if(!App.mapInteractionModel.floor.equals("5")) {
            pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
            loadNewMapAndGenerateHelper("5", "/edu/wpi/u/views/Images/FaulknerFloor5Light.png");
            nodesAndEdges.toFront();
        }else{
            toggle6.setSelected(true);
        }
    }

    public void handleSelectButton() {
        LinkedList<String> nodesToReset = App.mapInteractionModel.resetNodeIDList();
        for(String curNodeID : nodesToReset){
            Circle drawnNode = findCircleFromNode(curNodeID);
            drawnNode.setFill(Paint.valueOf(errorColor));
        }
        pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
        if(!App.mapInteractionModel.getCurrentAction().equals("SELECT")){
            App.mapInteractionModel.setCurrentAction("SELECT");
        }else{
            selectButton.setSelected(true);
        }
    }


    public void handleMultiSelectButton() {
        //deselect the current selected node
        Circle selectedCircle = (Circle) nodesAndEdges.lookup("#" + App.mapInteractionModel.nodeID);
        if(selectedCircle != null) {
            selectedCircle.setFill(Paint.valueOf(errorColor));
        }
        //highlight all the selected nodes
        for (String node: App.mapInteractionModel.nodeIDList){
            Circle drawnNode = (Circle) nodesAndEdges.lookup("#" + node);
            if(drawnNode != null){
                drawnNode.setFill(Paint.valueOf(selectedColor));
            }
        }
        pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
        if(!App.mapInteractionModel.getCurrentAction().equals("MULTISELECT")){
            App.mapInteractionModel.setCurrentAction("MULTISELECT");
        }else{
            multiSelectButton.setSelected(true);
        }
    }

    public void handleAlineButton() {
        pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
        if(!App.mapInteractionModel.getCurrentAction().equals("ALINE")){
            if(App.mapInteractionModel.getCurrentAction().equals("MULTISELECT")){
                if(App.mapInteractionModel.nodeIDList.size() > 1) {
                    alineFromMultiSelect();
                    App.mapInteractionModel.setCurrentAction("ALINE");
                }else{
                    shake(alineButton);
                    multiSelectButton.setSelected(true);
                }
            }else{
                shake(alineButton);
                selectButton.setSelected(true);
            }

        }else{
            alineButton.setSelected(true);
        }
    }

    /**
     * animates a shake on the javaFX node passed in
     * @author Charles Kittler (cvkittler)
     * @param node the object to shake
     */
    public static void shake(javafx.scene.Node node){
        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis(0), new KeyValue(node.translateXProperty(), 0, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(100), new KeyValue(node.translateXProperty(), -10, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), 10, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(300), new KeyValue(node.translateXProperty(), -10, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(400), new KeyValue(node.translateXProperty(), 10, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(500), new KeyValue(node.translateXProperty(), -10, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(600), new KeyValue(node.translateXProperty(), 10, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(700), new KeyValue(node.translateXProperty(), -10, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(800), new KeyValue(node.translateXProperty(), 10, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(900), new KeyValue(node.translateXProperty(), -10, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(1000), new KeyValue(node.translateXProperty(), 0, Interpolator.LINEAR))
        );
        animation.setCycleCount(1);
        animation.play();
    }

    /**
     * gets the selected nodes from map interaction model
     * computes the average x and y variance and depending on which is smaller
     * it sets all the selected nodes to average cord with the smaller variance
     * @author Charles Kittler (cvkittler)
     */
    public void alineFromMultiSelect(){
        double totalX = 0.0, totalY = 0.0;
        for ( String curNodeId:App.mapInteractionModel.nodeIDList){
            Node curNode = App.mapService.getNodeFromID(curNodeId);
            totalX += curNode.getCords()[0];
            totalY += curNode.getCords()[1];
        }
        double avgX = totalX / App.mapInteractionModel.nodeIDList.size();
        double avgY = totalY / App.mapInteractionModel.nodeIDList.size();

        double varianceX = 0.0, varianceY = 0.0;
        for ( String curNodeId:App.mapInteractionModel.nodeIDList){
            Node curNode = App.mapService.getNodeFromID(curNodeId);
            varianceX += Math.abs(curNode.getCords()[0] - avgX);
            varianceY += Math.abs(curNode.getCords()[1] - avgY);
        }
        double avgVarianceX = varianceX / App.mapInteractionModel.nodeIDList.size();
        double avgVarianceY = varianceY / App.mapInteractionModel.nodeIDList.size();

        if(avgVarianceX < avgVarianceY){
            //aline to x coord
            for(String curNodeId:App.mapInteractionModel.nodeIDList){
                Node curNode = App.mapService.getNodeFromID(curNodeId);
                App.undoRedoService.updateNode(curNodeId,avgX,curNode.getCords()[1],curNode.getNodeType(),curNode.getLongName(),curNode.getShortName());
            }
            App.mapInteractionModel.aline = "XCOORD";
            App.mapInteractionModel.alineValue = avgX;
        }else{
            //aline to y coord
            for(String curNodeId:App.mapInteractionModel.nodeIDList){
                Node curNode = App.mapService.getNodeFromID(curNodeId);
                App.undoRedoService.updateNode(curNodeId,curNode.getCords()[0],avgY,curNode.getNodeType(),curNode.getLongName(),curNode.getShortName());
            }
            App.mapInteractionModel.aline = "YCOORD";
            App.mapInteractionModel.alineValue = avgY;
        }
        App.mapInteractionModel.editFlag.set(String.valueOf(Math.random()));
    }


    public void handleAddNodeButtonEDIT(){
        LinkedList<String> nodesToReset = App.mapInteractionModel.resetNodeIDList();
        for(String curNodeID : nodesToReset){
            Circle drawnNode = findCircleFromNode(curNodeID);
            drawnNode.setFill(Paint.valueOf(errorColor));
        }
        pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
        if(!App.mapInteractionModel.getCurrentAction().equals("ADDNODE")){
            App.mapInteractionModel.setCurrentAction("ADDNODE");
        }else{
            addNodeButton.setSelected(true);
        }

    }

    public void handleAddEdgeButtonEDIT(){
        LinkedList<String> nodesToReset = App.mapInteractionModel.resetNodeIDList();
        for(String curNodeID : nodesToReset){
            Circle drawnNode = findCircleFromNode(curNodeID);
            drawnNode.setFill(Paint.valueOf(errorColor));
        }
        pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
        if(!App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
            App.mapInteractionModel.setCurrentAction("ADDEDGE");
        }else{
            addEdgeButton.setSelected(true);
        }

        App.mapInteractionModel.setEdgeID("");
        App.mapInteractionModel.clearPreviousNodeID();
    }


    /**
     * This is a helper to make the context menu for a node or an edge.
     * @param NE this in a Node or an Edge. This isn't necessarily used to make the context menu, it is just needed to know if it is a node or an edge menu
     * @param x this is the x coordinate of the context menu
     * @param y this is the y coordinate of the context menu
     * @throws IOException this is just the usual thing. Have never seen it throw this exception but whatever....
     */
    public void makeContextMenu(Object NE, double x, double y ) throws IOException {
        AnchorPane contextAnchor;
        if(NE.getClass() == Node.class){
            FXMLLoader nodeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mapbuilder/ContextMenuNode.fxml"));
            contextAnchor = nodeContextMenu.load();
        } else {
            FXMLLoader edgeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mapbuilder/ContextMenuEdge.fxml"));
            contextAnchor = edgeContextMenu.load();
        }
        contextAnchor.setLayoutX(x);
        contextAnchor.setLayoutY(y);
        pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
        pane.getChildren().add(contextAnchor);
        App.mapInteractionModel.selectedContextBox = contextAnchor;

    }


    /**
     * This sets the mouse coordinates
     * @param e this is the event. this has to be passed in to do the scaling stuff
     */
    public void setMouseCoordinates(MouseEvent e){
        Affine invMatrix = null;
        try {
            invMatrix = map.getAffine().createInverse();
        } catch (NonInvertibleTransformException nonInvertibleTransformException) {
            nonInvertibleTransformException.printStackTrace();
        }
        Point2D realPoint = invMatrix.transform(e.getX(),e.getY());

        double x = (realPoint.getX()) + map.getLayoutX();
        double y = (realPoint.getY()) + map.getLayoutY();
        App.mapInteractionModel.setCoords(new double[]{x,y});
    }
}