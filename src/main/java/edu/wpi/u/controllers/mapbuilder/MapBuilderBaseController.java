package edu.wpi.u.controllers.mapbuilder;

import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import javafx.animation.Interpolator;
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
    private JFXToggleNode selectButton, multiSelectButton, addNodeButton, addEdgeButton, alineButton;

    AnchorPane pane = new AnchorPane();
    ImageView node = new ImageView();
    Group nodesAndEdges = new Group();
    public GesturePane map = new GesturePane(pane);
    String selectedColor = "green";
    /**
     * Initializes the admin map screen with map zoom, and all node and edge placement
     * @throws IOException
     */
    public void initialize() throws Exception {
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
        selectButton.setTooltip(new Tooltip("Select"));
        multiSelectButton.setTooltip(new Tooltip("Select Multiple"));
        addNodeButton.setTooltip(new Tooltip("Add Node"));
        addEdgeButton.setTooltip(new Tooltip("Add Edge"));
        alineButton.setTooltip(new Tooltip("Aline Selected Nodes"));
        //handle converting converting clicks on the screen into map space


        map.setOnMouseDragged(e ->{//mouse dragging listener---------------------------------------------
            setMouseCoordinates(e);
        });
        map.setOnMouseDragReleased(e ->{//mouse dragging ending listener---------------------------------------------
            setMouseCoordinates(e);
        });


        map.setOnMouseClicked(e -> {//mouse clicking listener -----------------------------------------------
            if(findCircleFromNode(App.mapInteractionModel.getPreviousNodeID()) != null){ //to prevent a null pointer when the previous node isn't being displayed
                findCircleFromNode(App.mapInteractionModel.getPreviousNodeID()).setFill(Paint.valueOf("B00020"));
            }//this makes sure any nodes that were previously clicked are changes back to the original red color

            Point2D pivotOnTarget = map.targetPointAt(new Point2D(e.getX(), e.getY()))
                    .orElse(map.targetPointAtViewportCentre());

            setMouseCoordinates(e);//this sets the mouse coordinates


            // Trying add node context menu
            try {
                if (App.mapInteractionModel.getCurrentAction().equals("ADDNODE")) {
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
            curNode.setRadius(7.0);
            curNode.setId(n.getNodeID());
            curNode.setStyle("-fx-fill: -error");
            curNode.setVisible(true);
            //setting mouse events for the drawn circle
            curNode.setOnMouseClicked(event -> {
                try {
                    handleNodeClicked(n);
                } catch (IOException  e) {
                    e.printStackTrace();
                }
            });
            curNode.setOnMouseDragged(event -> {
                if(!App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
                    try {
                        handleNodeDragged(curNode); // Set visual position (circle)
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                }
            });
            curNode.setOnMouseDragExited(event -> {
                if(!App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
                    try {
                        handleNodeDragExit(App.mapService.getNodeFromID(curNode.getId()), curNode); // Set visual position (circle)
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

//            curNode.setOnMouseReleased(event -> {
//                try {
//                    handleNodeDragExit(n, curNode);
//                } catch (Exception e) {
//                    e.printStackTrace(); // Update node's actual storage
//                }
//            });
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
        edge.setStyle("-fx-stroke: -error");
        edge.setVisible(true);
        edge.setOnMouseClicked(event -> {
            try {
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
                for(javafx.scene.Node n : nodesAndEdges.getChildren()){
                    if(n.getId().equals(e.getEdgeID())){
                        Line edge = (Line) n;
                        edge.setStroke(Paint.valueOf("Green"));
                    }
                }
                //find the old edge and reset its color
                for(javafx.scene.Node n : nodesAndEdges.getChildren()){
                    if(n.getId().equals(App.mapInteractionModel.previusEdgeID)){
                        Line edge = (Line) n;
                        edge.setStroke(Paint.valueOf(errorColor));
                    }
                }
                EdgeContextAnchor.setLayoutX(e.getStartNode().getCords()[0]+(xdiff/2));
                EdgeContextAnchor.setLayoutY(e.getStartNode().getCords()[1]+(ydiff/2));
                pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
                pane.getChildren().remove(App.mapInteractionModel.selectedEdgeContextBox);
                pane.getChildren().add(EdgeContextAnchor);
                App.mapInteractionModel.selectedContextBox = EdgeContextAnchor;
                break;
        }
    }

    /**
     * Function called when a node is clicked. This brings up the context menu.
     * @param n - Node that is clicked on
     * @throws IOException
     */
    String errorColor = "B00020";
    public void handleNodeClicked(Node n) throws IOException {
        if(findCircleFromNode(App.mapInteractionModel.getPreviousNodeID()) != null){ //to prevent a null pointer when the previous node isn't being displayed
            findCircleFromNode(App.mapInteractionModel.getPreviousNodeID()).setFill(Paint.valueOf("B00020"));
        }

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
                    pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
                    Circle drawnNode = findCircleFromNode(n.getNodeID());
                    drawnNode.setFill(Paint.valueOf("green"));
                    Circle removedNode = findCircleFromNode(App.mapInteractionModel.deselectedNodeID);
                    if(removedNode != null){
                        removedNode.setFill(Paint.valueOf(errorColor));
                    }
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
                App.mapInteractionModel.resetNodeIDList();
                FXMLLoader nodeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mapbuilder/ContextMenuNode.fxml"));
                AnchorPane contextAnchor;
                contextAnchor = nodeContextMenu.load();
                contextAnchor.setLayoutX(n.getCords()[0]);
                contextAnchor.setLayoutY(n.getCords()[1]);
                pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
                pane.getChildren().add(contextAnchor);
                App.mapInteractionModel.selectedContextBox = contextAnchor;
                makeContextMenu(n, n.getCords()[0], n.getCords()[1]);//The creation of the context menu for the node
                Circle clickedCircle = findCircleFromNode(n.getNodeID());
                clickedCircle.setFill(Paint.valueOf(selectedColor));
                Circle previousCircle = findCircleFromNode(App.mapInteractionModel.getPreviousNodeID());
                if(previousCircle != null){ //to prevent a null pointer when the previous node isn't being displayed
                    previousCircle.setFill(Paint.valueOf("B00020"));
                }
                break;
            case "ADDEDGE"://TODO fix to remove the need to refresh everything to draw correctly
                javafx.scene.Node tempEdge = findTempLine();
                if(tempEdge != null){
                    pane.getChildren().remove(tempEdge);
                    generateEdges(App.mapInteractionModel.floor);
                    generateNodes(App.mapInteractionModel.floor);
                }
                App.mapInteractionModel.setNodeID(n.getNodeID());
                Circle c1 = new Circle();
                Circle c2 = new Circle();
                Line edge = new Line();
                if(!App.mapInteractionModel.getNodeID().equals("")) { // Have 1st node
                    c1 = findCircleFromNode(n.getNodeID());
                    c1.toFront();
                    c1.setFill(Paint.valueOf(selectedColor));
                }
                if(!App.mapInteractionModel.getPreviousNodeID().equals("")) { // Have 2nd node
                    c2 = findCircleFromNode(App.mapInteractionModel.getPreviousNodeID());
                    if(c2 != null) {
                        c2.toFront();
                        c2.setFill(Paint.valueOf(selectedColor));
                    }
                    nodesAndEdges.getChildren().remove(edge);
                    // Create physical Edge
                    double oldx = App.mapService.getNodeFromID(App.mapInteractionModel.getNodeID()).getCords()[0];
                    double oldy = App.mapService.getNodeFromID(App.mapInteractionModel.getNodeID()).getCords()[1];

                    double xdiff = 0;
                    double ydiff = 0;

                    if(c2 != null) {
                        xdiff = c2.getCenterX() - oldx;
                        ydiff = c2.getCenterY() - oldy;
                        edge.setLayoutX(c1.getCenterX());
                        edge.setStartX(0);
                        edge.setLayoutY(c1.getCenterY());
                        edge.setStartY(0);
                        edge.setEndX(xdiff);
                        edge.setEndY(ydiff);
                        edge.setId("tempedge");
                        edge.setStrokeWidth(3.0);
                        edge.toFront();
                        edge.setStroke(Paint.valueOf(selectedColor));
                        edge.setVisible(true);
                        nodesAndEdges.getChildren().add(edge);
                    }
                    if(c2 != null) {
                        makeContextMenu(edge, edge.getLayoutX() + (xdiff / 2), edge.getLayoutY() + (ydiff / 2));//Creation of the Edge context menu
                    }else{
                        makeContextMenu(edge, c1.getCenterX(), c1.getCenterY());//Creation of the Edge context menu
                    }
                }
                break;
            case "ALINE":
                //TODO add align logic
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
        for(javafx.scene.Node n: nodesAndEdges.getChildren()){
            if(n.getId().equals("tempedge")){
                return n;
            }
        }
        //if failed to find maybe on other floor
        return null;
    }


    /**
     * Need to: Handle when node is dragged by updating coordinates on screen connecting edges. Coordinates saved in other helper function
     * @param node1 -
     */
    public void handleNodeDragged(Circle node1) {
            map.gestureEnabledProperty().set(false); // Disabling map zoom
            pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
            // Update coordinates of node
            node1.setCenterX(App.mapInteractionModel.getCoords()[0]);
            node1.setCenterY(App.mapInteractionModel.getCoords()[1]);
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
    }

    public void handleRedoButton() throws Exception{
        App.undoRedoService.redo();
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
            App.mapInteractionModel.setCurrentAction("ALINE");
        }else{
            alineButton.setSelected(true);
        }
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