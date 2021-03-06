package edu.wpi.u.controllers.mapbuilder;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;

public class MapBuilderBaseController {




    static final Duration DURATION = Duration.millis(300);
    @FXML public SVGPath leftMenuHamburger;
    @FXML public AnchorPane mainAnchorPane;
    @FXML public JFXDrawer leftMenuDrawer;
    @FXML public JFXDrawer serviceRequestDrawer;
    public JFXToggleNode toggle1;
    public JFXToggleNode toggle2;
    public JFXToggleNode toggle3;
    public JFXToggleNode toggle4;
    public JFXToggleNode toggle5;
    public JFXToggleNode toggle6;

    AnchorPane rightServiceRequestPane;
    AnchorPane leftMenuPane;
    AnchorPane pane = new AnchorPane();
    ImageView node = new ImageView();
    Group edgeNodeGroup = new Group();
    public GesturePane map = new GesturePane(pane);

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
        pane.getChildren().add(edgeNodeGroup);

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

        map.setOnMouseDragged(e ->{
            Affine invMatrix = null;
            try {
                invMatrix = map.getAffine().createInverse();
                System.out.println("Mouse realeased, 76");
            } catch (NonInvertibleTransformException nonInvertibleTransformException) {
                nonInvertibleTransformException.printStackTrace();
            }
            Point2D realPoint = invMatrix.transform(e.getX(),e.getY());

            double x = (realPoint.getX()) + map.getLayoutX();
            double y = (realPoint.getY()) + map.getLayoutY();
            App.mapInteractionModel.setCoords(new double[]{x,y});
        });


        // Click and scroll map view functionality
        map.setOnMouseClicked(e -> {
            if(!App.mapInteractionModel.clickedOnNode){
                pane.getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
            }else{
                App.mapInteractionModel.clickedOnNode = false;
            }
            Point2D pivotOnTarget = map.targetPointAt(new Point2D(e.getX(), e.getY()))
                    .orElse(map.targetPointAtViewportCentre());
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


            // Trying add node context menu
            try {
                if (App.mapInteractionModel.getCurrentAction().equals("ADDNODE")) {
                    FXMLLoader nodeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mapbuilder/ContextMenuNode.fxml"));
                    AnchorPane contextAnchor = new AnchorPane();
                    contextAnchor = nodeContextMenu.load();
                    ContextMenuNodeController controller = nodeContextMenu.getController();
                    contextAnchor.setLayoutX(App.mapInteractionModel.getCoords()[0]);
                    contextAnchor.setLayoutY(App.mapInteractionModel.getCoords()[1]); // Careful
                    pane.getChildren().remove(App.mapInteractionModel.selectedEdgeContextBox);
                    pane.getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
                    pane.getChildren().add(contextAnchor);
                    App.mapInteractionModel.selectedNodeContextBox = contextAnchor;
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

        // Creating nodes
        generateEdges(App.mapInteractionModel.floor);
        generateNodes(App.mapInteractionModel.floor);

        App.mapInteractionModel.mapImageResource.addListener((observable, oldValue, newValue)  ->{
            pane.getChildren().remove(node);
            node = new ImageView(String.valueOf(getClass().getResource(App.mapInteractionModel.mapImageResource.get())));
            if(App.mapInteractionModel.floor.equals("G")){
                node.setFitWidth(2987);
            } else{
                node.setFitWidth(2470);
            }
            node.setPreserveRatio(true);
            pane.getChildren().add(node);
        });

        App.mapInteractionModel.editFlag.addListener((observable, oldValue, newValue)  ->{
            //update nodes and edges
            generateEdges(App.mapInteractionModel.floor);
            generateNodes(App.mapInteractionModel.floor);
        });
    } // End of initialize

    public void handleAddNodeButtonEDIT(){
        pane.getChildren().remove(App.mapInteractionModel.selectedEdgeContextBox);
        pane.getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
        if(App.mapInteractionModel.getCurrentAction().equals("NONE")){
            App.mapInteractionModel.setCurrentAction("ADDNODE");
        }else{
            App.mapInteractionModel.setCurrentAction("NONE");
        }

    }

    public void handleAddEdgeButtonEDIT(){
        pane.getChildren().remove(App.mapInteractionModel.selectedEdgeContextBox);
        pane.getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
        if(App.mapInteractionModel.getCurrentAction().equals("NONE")){
            App.mapInteractionModel.setCurrentAction("ADDEDGE");
        }else{
            App.mapInteractionModel.setCurrentAction("NONE");
        }

        App.mapInteractionModel.setEdgeID("");
        App.mapInteractionModel.clearPreviousNodeID();
    }

    /**
     * Sets the position, radius, id, fill, etc., of the node, and sets its action when clicked
     * @param n - Node that is being place
     * @throws IOException
     */
    public void placeNodesHelper(Node n) throws IOException{
            Circle node1 = new Circle();
            node1.setCenterX(n.getCords()[0]);
            node1.setCenterY(n.getCords()[1]);
            node1.setRadius(7.0);
            node1.setId(n.getNodeID());
            node1.toFront();
            node1.setStyle("-fx-fill: -error");
            node1.setVisible(true);
            node1.setOnMouseClicked(event -> {
                try {
                    App.mapInteractionModel.clickedOnNode=true;
                    handleNodeClicked(n);
                } catch (IOException  e) {
                    e.printStackTrace();
                }
            });
            node1.setOnMouseDragged(event -> {
                if(!App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
                    try {
                        handleNodeDragged(node1); // Set visual position (circle)
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                }
            });
            node1.setOnMouseReleased(event -> {
                try {
                    handleNodeDragExit(n, node1);
                } catch (Exception e) {
                    e.printStackTrace(); // Update node's actual storage
                }
            });
        edgeNodeGroup.getChildren().add(node1);
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
        edgeNodeGroup.toFront();
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
        edge.toFront();
        edge.setStyle("-fx-stroke: -error");
        edge.setVisible(true);
        edge.setOnMouseClicked(event -> {
            try {
                App.mapInteractionModel.clickedOnNode=true;
                handleEdgeClicked(ed);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        edgeNodeGroup.getChildren().add(edge);
    }

    public void generateEdges(String floor){
        edgeNodeGroup.getChildren().clear();
        App.mapService.getEdges().stream().forEach(e ->{
        try {
            if(e.getStartNode().getFloor().equals(floor) && e.getEndNode().getFloor().equals(floor)){
                placeEdgesHelper(e);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        });
        edgeNodeGroup.toFront();
    }


    /**
     * Function called when and edge is clicked on. This brings up the context menu.
     * @param e - Edge that is clicked on
     * @throws IOException
     */
    public void handleEdgeClicked(Edge e) throws IOException {
        App.mapInteractionModel.setEdgeID(e.getEdgeID());
        if(!App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
            double xdiff = e.getEndNode().getCords()[0]-e.getStartNode().getCords()[0];
            double ydiff = e.getEndNode().getCords()[1]-e.getStartNode().getCords()[1];
            System.out.println("You clicked on an edge");
            FXMLLoader edgeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mapbuilder/ContextMenuEdge.fxml"));
            AnchorPane EdgeContextAnchor = new AnchorPane();
            EdgeContextAnchor = edgeContextMenu.load();
            ContextMenuEdgeController controller = edgeContextMenu.getController();

            EdgeContextAnchor.setLayoutX(e.getStartNode().getCords()[0]+(xdiff/2));
            EdgeContextAnchor.setLayoutY(e.getStartNode().getCords()[1]+(ydiff/2));
            pane.getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
            pane.getChildren().remove(App.mapInteractionModel.selectedEdgeContextBox);
            pane.getChildren().add(EdgeContextAnchor);
            App.mapInteractionModel.selectedNodeContextBox = EdgeContextAnchor;
        }
    }

    /**
     * Function called when a node is clicked. This brings up the context menu.
     * @param n - Node that is clicked on
     * @throws IOException
     */
    public void handleNodeClicked(Node n) throws IOException {
        App.mapInteractionModel.setCoords(new double[]{n.getCords()[0], n.getCords()[1]});
        if(!App.mapInteractionModel.getCurrentAction().equals("ADDNODE") && !App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
            System.out.println("You clicked on a node");
            App.mapInteractionModel.setNodeID(n.getNodeID());
            FXMLLoader nodeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mapbuilder/ContextMenuNode.fxml"));
            AnchorPane contextAnchor = new AnchorPane();
            contextAnchor = nodeContextMenu.load();
            ContextMenuNodeController controller = nodeContextMenu.getController();
            contextAnchor.setLayoutX(n.getCords()[0]);
            contextAnchor.setLayoutY(n.getCords()[1]);
            pane.getChildren().remove(App.mapInteractionModel.selectedEdgeContextBox);
            pane.getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
            pane.getChildren().add(contextAnchor);
            App.mapInteractionModel.selectedNodeContextBox = contextAnchor;

        }else if(App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
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
                c1.setFill(Paint.valueOf("green"));
            }
            if(!App.mapInteractionModel.getPreviousNodeID().equals("")) { // Have 2nd node
                c2 = findCircleFromNode(App.mapInteractionModel.getPreviousNodeID());
                if(c2 != null) {
                    c2.toFront();
                    c2.setFill(Paint.valueOf("green"));
                }
                edgeNodeGroup.getChildren().remove(edge);
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
                    edge.setStroke(Paint.valueOf("green"));
                    edge.setVisible(true);
                    edgeNodeGroup.getChildren().add(edge);
                }
                // Spawning context menu
                FXMLLoader edgeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mapbuilder/ContextMenuEdge.fxml"));
                AnchorPane EdgeContextAnchor = new AnchorPane();
                EdgeContextAnchor = edgeContextMenu.load();
                ContextMenuEdgeController controller = edgeContextMenu.getController();

                if(c2 != null) {
                    EdgeContextAnchor.setLayoutX(edge.getLayoutX() + (xdiff / 2));
                    EdgeContextAnchor.setLayoutY(edge.getLayoutY() + (ydiff / 2));
                }else{
                    EdgeContextAnchor.setLayoutX(c1.getCenterX());
                    EdgeContextAnchor.setLayoutY(c1.getCenterY());
                }
                pane.getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
                pane.getChildren().remove(App.mapInteractionModel.selectedEdgeContextBox);
                pane.getChildren().add(EdgeContextAnchor);
                App.mapInteractionModel.selectedNodeContextBox = EdgeContextAnchor;
            }
        }
    }

    public Circle findCircleFromNode(String nodeID){
        for(javafx.scene.Node n: edgeNodeGroup.getChildren()){
            if(n.getId().equals(nodeID)){
                return (Circle)n;
            }
        }
        //if failed to find maybe on other floor
        return null;
    }

    public javafx.scene.Node findTempLine(){
        for(javafx.scene.Node n: edgeNodeGroup.getChildren()){
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
            pane.getChildren().remove(App.mapInteractionModel.selectedEdgeContextBox); // Removing previous context menus when dragging
            pane.getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
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
            loadNewMapAndGenerateHelper("G", "/edu/wpi/u/views/Images/FaulknerCampus.png");
            node.setFitWidth(2987);
            edgeNodeGroup.toFront();
        }else{
            toggle1.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 1 and reloads the correct nodes
     */
    public void handleFloor1Button(){
        if(!App.mapInteractionModel.floor.equals("1")) {
            loadNewMapAndGenerateHelper("1", "/edu/wpi/u/views/Images/FaulknerFloor1Light.png");
            edgeNodeGroup.toFront();
        }else{
            toggle2.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 2 and reloads the correct nodes
     */
    public void handleFloor2Button(){
        if(!App.mapInteractionModel.floor.equals("2")) {
            loadNewMapAndGenerateHelper("2", "/edu/wpi/u/views/Images/FaulknerFloor2Light.png");
            edgeNodeGroup.toFront();
        }else{
            toggle3.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 3 and reloads the correct nodes
     */
    public void handleFloor3Button(){
        if(!App.mapInteractionModel.floor.equals("3")) {
            loadNewMapAndGenerateHelper("3", "/edu/wpi/u/views/Images/FaulknerFloor3Light.png");
            edgeNodeGroup.toFront();
        }else{
            toggle4.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 4 and reloads the correct nodes
     */
    public void handleFloor4Button(){
        if(!App.mapInteractionModel.floor.equals("4")) {
            loadNewMapAndGenerateHelper("4", "/edu/wpi/u/views/Images/FaulknerFloor4Light.png");
            edgeNodeGroup.toFront();
        }else{
            toggle5.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 5 and reloads the correct nodes
     */
    public void handleFloor5Button(){
        if(!App.mapInteractionModel.floor.equals("5")) {
            loadNewMapAndGenerateHelper("5", "/edu/wpi/u/views/Images/FaulknerFloor5Light.png");
            edgeNodeGroup.toFront();
        }else{
            toggle6.setSelected(true);
        }
    }












}