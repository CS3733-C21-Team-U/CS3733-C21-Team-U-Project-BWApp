package edu.wpi.u.controllers.pathfinding;

import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.algorithms.getEdgesTest;
import edu.wpi.u.controllers.mapbuilder.ContextMenuNodeController;
import javafx.animation.Interpolator;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;

public class PathfindingBaseController {


    public GesturePane map;

    static final Duration DURATION = Duration.millis(300);
    @FXML public AnchorPane mainAnchorPane;
    @FXML public JFXToggleNode floorG;
    @FXML public JFXToggleNode floor1;
    @FXML public JFXToggleNode floor2;
    @FXML public JFXToggleNode floor3;
    @FXML public JFXToggleNode floor4;
    @FXML public JFXToggleNode floor5;


    AnchorPane rightServiceRequestPane;
    AnchorPane leftMenuPane;
    AnchorPane pane = new AnchorPane();
    ImageView node = new ImageView();
    Group edgeNodeGroup = new Group();
    Group pathPreviewGroup = new Group();

    /**
     * Initializes the admin map screen with map zoom, and all node and edge placement
     * @throws IOException
     */
    public void initialize() throws Exception {
        floorG.setSelected(true);
        App.mapService.loadStuff();
        // Loading the map
        node = new ImageView(String.valueOf(getClass().getResource(App.mapInteractionModel.mapImageResourcePathfinding.get())));
        node.setFitWidth(2987);
        node.setPreserveRatio(true);
        pane.getChildren().add(node);
        pane.getChildren().add(edgeNodeGroup);
        edgeNodeGroup.toFront();

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
                    FXMLLoader nodeContextMenu = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mapbuilder/ContextMenuNode.fxml"));
                    AnchorPane contextAnchor = new AnchorPane();
                    contextAnchor = nodeContextMenu.load();
                    ContextMenuNodeController controller = nodeContextMenu.getController();
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
        generateNodes(App.mapInteractionModel.floorPathfinding);
        App.mapInteractionModel.mapImageResourcePathfinding.addListener((observable, oldValue, newValue)  ->{
            pane.getChildren().remove(node);
            node = new ImageView(String.valueOf(getClass().getResource(App.mapInteractionModel.mapImageResourcePathfinding.get())));
            if(App.mapInteractionModel.floorPathfinding.equals("G")){
                node.setFitWidth(2987);
            } else{
                node.setFitWidth(2470);
            }
            node.setPreserveRatio(true);
            pane.getChildren().add(node);
        });

        App.mapInteractionModel.pathFlag.addListener((observable, oldValue, newValue)  ->{
            edgeNodeGroup.getChildren().clear();
            String floorStart = App.mapInteractionModel.path.get(0).getFloor();
            String floorEnd = App.mapInteractionModel.path.get(App.mapInteractionModel.path.size()-1).getFloor();
            switch (floorStart){
                case "G":
                    floorG.setSelected(true);
                    handleFloorGButton();
                    break;
                case "1":
                    floor1.setSelected(true);
                    handleFloor1Button();
                    break;
                case "2":
                    floor2.setSelected(true);
                    handleFloor2Button();
                    break;
                case "3":
                    floor3.setSelected(true);
                    handleFloor3Button();
                    break;
                case "4":
                    floor4.setSelected(true);
                    handleFloor4Button();
                    break;
                case "5":
                    floor5.setSelected(true);
                    handleFloor5Button();
                    break;
            }
            generateEdges(floorStart);
            generateNodes(App.mapInteractionModel.floorPathfinding);
        });

        App.mapInteractionModel.pathPreviewFlag.addListener((observable, oldValue, newValue)  ->{
            pathPreviewGroup.getChildren().clear();
            generatePathPreview(App.mapInteractionModel.floor);
            pathPreviewGroup.toFront();
        });

    } // End of initialize

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
            node1.setStyle("-fx-fill: -error");
            node1.setVisible(true);
            node1.setOnMousePressed(event -> {
                try {
                    handleNodeClicked(n);
                } catch (IOException  e) {
                    e.printStackTrace();
                }
            });

            node1.setOnMouseEntered(event -> {
                App.mapInteractionModel.nodeIDForHover.setValue(n.getNodeID());
            });
        edgeNodeGroup.getChildren().add(node1);
        node1.toFront();
    }


    /**
     * This generates the visible nodes that go on a particular floor. This is outside of
     * the initialize function because we need to generate nodes on the new floor changes as well
     * @param floor this is the string representing the floor of a node ("g", "1", "2"...)
     */
    public void generateNodes(String floor){
        App.mapService.getNodes().stream().forEach(n -> {
            try {
                if(n.getFloor().equals(floor) && !n.getNodeType().equals("WALK") && !n.getNodeType().equals("HALL")){
                    placeNodesHelper(n);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        edgeNodeGroup.toFront();
        pathPreviewGroup.toFront();
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
                handleEdgeClicked(ed);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        edgeNodeGroup.getChildren().add(edge);
    }



    /**
     * Sets the x vector, y vector, and other positional fields of the edge, and sets its action when clicked
     * @param ed - Edge that is clicked (variable named e is reserved for the exception thrown)
     */
    public void placePathPreview(Edge ed){
        double xdiff = ed.getEndNode().getCords()[0]-ed.getStartNode().getCords()[0];
        double ydiff = ed.getEndNode().getCords()[1]-ed.getStartNode().getCords()[1];
        Line previewEdge = new Line();
        previewEdge.setLayoutX(ed.getStartNode().getCords()[0]);
        previewEdge.setStartX(0);
        previewEdge.setLayoutY(ed.getStartNode().getCords()[1]);
        previewEdge.setStartY(0);
        previewEdge.setEndX(xdiff);
        previewEdge.setEndY(ydiff);
        previewEdge.setId(ed.getEdgeID()+ "_preview");
        previewEdge.setStrokeWidth(7);
        previewEdge.toFront();
        previewEdge.setStroke(Paint.valueOf("green"));
        previewEdge.setVisible(true);
        previewEdge.setOnMouseClicked(event -> {
            try {
                handleEdgeClicked(ed);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        pathPreviewGroup.getChildren().add(previewEdge);
    }

    public void generateEdges(String floor){
        getEdgesTest.EdgesFollowed(App.mapInteractionModel.path).stream().forEach(e ->{
        try {
            if(e.getStartNode().getFloor().equals(floor) && e.getEndNode().getFloor().equals(floor)){
                placeEdgesHelper(e);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        });
        pathPreviewGroup.toFront();
        edgeNodeGroup.toFront();

    }

    public void generatePathPreview(String floor){
        getEdgesTest.EdgesFollowed(App.mapInteractionModel.pathPreview).stream().forEach(e ->{
            try {
                if(e.getStartNode().getFloor().equals(floor) && e.getEndNode().getFloor().equals(floor)){
                    placePathPreview(e);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        pathPreviewGroup.toFront();
        edgeNodeGroup.toFront();
    }

    /**
     * Function called when and edge is clicked on. This brings up the context menu.
     * @param e - Edge that is clicked on
     * @throws IOException
     */
    public void handleEdgeClicked(Edge e) throws IOException {
    }



    /**
     * Function called when a node is clicked. This brings up the context menu.
     * @param n - Node that is clicked on
     * @throws IOException
     */
    public void handleNodeClicked(Node n) throws IOException {
            System.out.println("You clicked on a node");
            App.mapInteractionModel.setNodeID(n.getNodeID());
    }

    /**
     * This is a helper function for the floor buttons.
     * It reloads the correct image onto the GesturePane and
     * loads the correct nodes onto the screen after deleting the old ones
     * @param floor this is the floor G, 1, 2, 3, 4, or 5 as a string
     * @param resource this a path that points to the correct floor map from the base package (/edu/wpi/u...)
     */
    public void loadNewMapAndGenerateHelper(String floor, String resource){
        App.mapInteractionModel.floorPathfinding = floor;
        App.mapInteractionModel.mapImageResourcePathfinding.set(resource);
        generateNodes(floor); //Since this is just the pathfinding page, we don't want to generate all the nodes and edges
    }

    /**
     * This is what changes the map displayed to floor G and reloads the correct nodes
     */
    public void handleFloorGButton(){
        if(!App.mapInteractionModel.floorPathfinding.equals("G")) {
            edgeNodeGroup.getChildren().clear();
            pathPreviewGroup.getChildren().clear();
            loadNewMapAndGenerateHelper("G", "/edu/wpi/u/views/Images/FaulknerCampus.png");
            node.setFitWidth(2987);
            generateEdges("G");
            edgeNodeGroup.toFront();

        }else{
            floorG.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 1 and reloads the correct nodes
     */
    public void handleFloor1Button(){
        if(!App.mapInteractionModel.floorPathfinding.equals("1")) {
            edgeNodeGroup.getChildren().clear();
            loadNewMapAndGenerateHelper("1", "/edu/wpi/u/views/Images/FaulknerFloor1Light.png");
            generateEdges("1");
            edgeNodeGroup.toFront();
        }else{
            floor1.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 2 and reloads the correct nodes
     */
    public void handleFloor2Button(){
        if(!App.mapInteractionModel.floorPathfinding.equals("2")) {
            edgeNodeGroup.getChildren().clear();
            loadNewMapAndGenerateHelper("2", "/edu/wpi/u/views/Images/FaulknerFloor2Light.png");
            generateEdges("2");
            edgeNodeGroup.toFront();
        }else{
            floor2.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 3 and reloads the correct nodes
     */
    public void handleFloor3Button(){
        if(!App.mapInteractionModel.floorPathfinding.equals("3")) {
            edgeNodeGroup.getChildren().clear();
            loadNewMapAndGenerateHelper("3", "/edu/wpi/u/views/Images/FaulknerFloor3Light.png");
            generateEdges("3");
            edgeNodeGroup.toFront();
        }else{
            floor3.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 4 and reloads the correct nodes
     */
    public void handleFloor4Button(){
        if(!App.mapInteractionModel.floorPathfinding.equals("4")) {
            edgeNodeGroup.getChildren().clear();
            loadNewMapAndGenerateHelper("4", "/edu/wpi/u/views/Images/FaulknerFloor4Light.png");
            generateEdges("4");
            edgeNodeGroup.toFront();
        }else{
            floor4.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 5 and reloads the correct nodes
     */
    public void handleFloor5Button(){
        if(!App.mapInteractionModel.floorPathfinding.equals("5")) {
            edgeNodeGroup.getChildren().clear();
            loadNewMapAndGenerateHelper("5", "/edu/wpi/u/views/Images/FaulknerFloor5Light.png");
            generateEdges("5");
            edgeNodeGroup.toFront();
        }else{
            floor5.setSelected(true);
        }
    }










}