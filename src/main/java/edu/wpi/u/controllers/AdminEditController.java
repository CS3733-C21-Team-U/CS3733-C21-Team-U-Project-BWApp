package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXDrawer;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
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

public class AdminEditController {


    public GesturePane map;

    static final Duration DURATION = Duration.millis(300);
    @FXML public SVGPath leftMenuHamburger;
    @FXML public AnchorPane mainAnchorPane;
    @FXML public JFXDrawer leftMenuDrawer;
    @FXML public JFXDrawer serviceRequestDrawer;

    AnchorPane rightServiceRequestPane;
    AnchorPane leftMenuPane;
    AnchorPane pane = new AnchorPane();

    /**
     * Initializes the admin map screen with map zoom, and all node and edge placement
     * @throws IOException
     */
    public void initialize() throws Exception {
        App.mapService.loadStuff();
        // Loading the map
        ImageView node = new ImageView(String.valueOf(getClass().getResource(App.mapInteractionModel.mapImageResource.get())));
        node.setFitWidth(2987);
        node.setPreserveRatio(true);
        pane.getChildren().add(node);

        map = new GesturePane(pane);
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

        // Click and scroll map view functionality
        map.setOnMouseClicked(e -> {
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
            try {
                if (App.mapInteractionModel.getCurrentAction().equals("ADDNODE")) {
                    FXMLLoader nodeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NodeContextMenu.fxml"));
                    AnchorPane contextAnchor = new AnchorPane();
                    contextAnchor = nodeContextMenu.load();
                    NodeContextMenuController controller = nodeContextMenu.getController();
                    contextAnchor.setLayoutX(App.mapInteractionModel.getCoords()[0]);
                    contextAnchor.setLayoutY(App.mapInteractionModel.getCoords()[1]);
                    pane.getChildren().remove(App.mapInteractionModel.selectedEdgeContextBox);
                    pane.getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
                    pane.getChildren().add(contextAnchor);
                    App.mapInteractionModel.selectedNodeContextBox = contextAnchor;
                }else{

                }
            } catch(IOException ex){
                ex.printStackTrace();
            }

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
        generateNodes(App.mapInteractionModel.floor);
        generateEdges(App.mapInteractionModel.floor);

        App.mapInteractionModel.mapImageResource.addListener((observable, oldValue, newValue)  ->{
            pane.getChildren().remove(node);
            ImageView newNode = new ImageView(String.valueOf(getClass().getResource(App.mapInteractionModel.mapImageResource.get())));
            node.setFitWidth(1786);
            node.setPreserveRatio(true);
            pane.getChildren().add(newNode);
            pane.getChildren().removeAll(App.mapInteractionModel.nodeIDList);
            pane.getChildren().removeAll(App.mapInteractionModel.edgeIDList);
        });




    } // End of initialize

    @FXML
    public void handleAddNodeButton(){
        App.mapInteractionModel.setCurrentAction("ADDNODE");
    }

    @FXML
    public void handleAddEdgeButton(){
        App.mapInteractionModel.setCurrentAction("ADDEDGE");
    }

    /**
     * Sets the position, radius, id, fill, etc., of the node, and sets its action when clicked
     * @param n - Node that is being place
     * @throws IOException
     */
    public void placeNodesHelper(Node n) throws IOException{
            Circle node = new Circle();
            node.setCenterX(n.getCords()[0]);
            node.setCenterY(n.getCords()[1]);
            node.setRadius(7.0);
            node.setId(n.getNodeID());
            App.mapInteractionModel.nodeIDList.add(n.getNodeID());
            node.toFront();
            node.setFill(Paint.valueOf("Black"));
            node.setVisible(true);
            node.setOnMouseClicked(event -> {
                try {
                    handleNodeClicked(n);
                } catch (IOException  e) {
                    e.printStackTrace();
                }
            });


        pane.getChildren().add(node);
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
                }else{
                    System.out.println(n.getFloor());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        App.mapInteractionModel.edgeIDList.add(ed.getEdgeID());
        edge.setStrokeWidth(3.0);
        edge.toFront();
        edge.setFill(Paint.valueOf("Black"));
        edge.setVisible(true);

        edge.setOnMouseClicked(event -> {
            try {
                handleEdgeClicked(ed);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        pane.getChildren().add(edge);

    }



    public void generateEdges(String floor){
        System.out.println("We're outside the function");
        System.out.println(App.mapService.getEdges());
        System.out.println("That's all folks");
        App.mapService.getEdges().stream().forEach(e ->{
            System.out.println("We're in the function");
        try {
            if(e.getStartNode().getFloor().equals(floor) && e.getEndNode().getFloor().equals(floor)){
                System.out.println("The edge is a valid edge for printing");
                placeEdgesHelper(e);

            } else{
                System.out.println(e.getStartNode().getFloor());
                System.out.println("The edge is NOT a valid edge for printing");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        });
    }



    /**
     * Function called when and edge is clicked on. This brings up the context menu.
     * @param e - Edge that is clicked on
     * @throws IOException
     */
    public void handleEdgeClicked(Edge e) throws IOException {
        if(!App.mapInteractionModel.getCurrentAction().equals("ADDNODE")){
            double xdiff = e.getEndNode().getCords()[0]-e.getStartNode().getCords()[0];
            double ydiff = e.getEndNode().getCords()[1]-e.getStartNode().getCords()[1];
            System.out.println("You clicked on an edge");
            FXMLLoader edgeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/EdgeContextMenu.fxml"));
            AnchorPane EdgeContextAnchor = new AnchorPane();
            EdgeContextAnchor = edgeContextMenu.load();
            EdgeContextMenuController controller = edgeContextMenu.getController();

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
        if(!App.mapInteractionModel.getCurrentAction().equals("ADDEDGE")){
            System.out.println("You clicked on a node");
            App.mapInteractionModel.setNodeID(n.getNodeID());
            FXMLLoader nodeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/NodeContextMenu.fxml"));
            AnchorPane contextAnchor = new AnchorPane();
            contextAnchor = nodeContextMenu.load();
            NodeContextMenuController controller = nodeContextMenu.getController();
            contextAnchor.setLayoutX(n.getCords()[0]);
            contextAnchor.setLayoutY(n.getCords()[1]);
            pane.getChildren().remove(App.mapInteractionModel.selectedEdgeContextBox);
            pane.getChildren().remove(App.mapInteractionModel.selectedNodeContextBox);
            pane.getChildren().add(contextAnchor);
            App.mapInteractionModel.selectedNodeContextBox = contextAnchor;
        }
    }
    @FXML
    public void handleUndoButton() throws Exception{
        App.undoRedoService.undo();
    }

    @FXML
    public void handleRedoButton() throws Exception{
        App.undoRedoService.redo();
    }

}