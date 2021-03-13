package edu.wpi.u.controllers.mobile;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleNode;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.algorithms.getEdgesTest;
import edu.wpi.u.controllers.mapbuilder.ContextMenuNodeController;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
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
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.util.LinkedList;

public class MobilePathFindingBaseController {


    public GesturePane map;

    static final Duration DURATION = Duration.millis(300);
    @FXML public AnchorPane mainAnchorPane;
    @FXML public JFXToggleNode floorG;
    @FXML public JFXToggleNode floor1;
    @FXML public JFXToggleNode floor2;
    @FXML public JFXToggleNode floor3;
    @FXML public JFXToggleNode floor4;
    @FXML public JFXToggleNode floor5;
    public JFXButton nextButton;


    AnchorPane rightServiceRequestPane;
    AnchorPane leftMenuPane;
    AnchorPane pane = new AnchorPane();
    ImageView node = new ImageView();
    Group edgeNodeGroup = new Group();
    Group pathPreviewGroup = new Group();
    Group pathBorder = new Group();
    Group pathFill = new Group();
    Group pathArrow = new Group();

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
        pane.getChildren().add(pathPreviewGroup);
        pane.getChildren().add(pathBorder);
        pane.getChildren().add(pathFill);
        pane.getChildren().add(pathArrow);

        map = new GesturePane(pane);
        map.setMinScale(0.3);
        map.setMaxScale(2);
        map.centreOn(new Point2D(700, 4000));
        map.zoomTo(0.1, map.targetPointAtViewportCentre());

        map.setPrefWidth(412);
        map.setPrefHeight(712);
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
                    pane.getChildren().remove(App.mapInteractionModel.selectedContextBox);
                    pane.getChildren().add(contextAnchor);
                    App.mapInteractionModel.selectedContextBox = contextAnchor;
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
            clearMapItems();
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
            generateEdges(App.mapInteractionModel.floorPathfinding);
            generateNodes(App.mapInteractionModel.floorPathfinding);
        });

        App.mapInteractionModel.pathPreviewFlag.addListener((observable, oldValue, newValue)  ->{
            pathPreviewGroup.getChildren().clear();
            generatePathPreview(App.mapInteractionModel.floorPathfinding);
            setMapItemsOrder();
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
        node1.setStyle("-fx-fill: #6200ee");
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
    }

    /**
     * sets the proper order for generated objects on the map
     * @author Charles Kittler (cvkittler)
     */
    private void setMapItemsOrder(){
        pathPreviewGroup.toFront();
        edgeNodeGroup.toFront();
        pathBorder.toFront();
        pathFill.toFront();
        pathArrow.toFront();
    }

    private void clearMapItems(){
        edgeNodeGroup.getChildren().clear();
        pathPreviewGroup.getChildren().clear();
        pathBorder.getChildren().clear();
        pathFill.getChildren().clear();
        pathArrow.getChildren().clear();
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
    }



    /**
     * Sets the x vector, y vector, and other positional fields of the edge, and sets its action when clicked
     * @param ed - Edge that is clicked (variable named e is reserved for the exception thrown)
     */
    String outlineColor = "b187ed";
    String fillColor = "fffff5";
    public void placeEdgesHelper(Edge ed, boolean forward){
        double xdiff = ed.getEndNode().getCords()[0]-ed.getStartNode().getCords()[0];
        double ydiff = ed.getEndNode().getCords()[1]-ed.getStartNode().getCords()[1];
        Line fill = new Line(), backround = new Line();
        backround.setLayoutX(ed.getStartNode().getCords()[0]);
        backround.setStartX(0);
        backround.setLayoutY(ed.getStartNode().getCords()[1]);
        backround.setStartY(0);
        backround.setEndX(xdiff);
        backround.setEndY(ydiff);
        backround.setId(ed.getEdgeID()+"_back");
        backround.setStrokeWidth(20);
        backround.setStroke(Paint.valueOf(outlineColor));
        backround.setVisible(true);
        backround.setStrokeLineCap(StrokeLineCap.ROUND);
        pathBorder.getChildren().add(backround);
        fill.setLayoutX(ed.getStartNode().getCords()[0]);
        fill.setStartX(0);
        fill.setLayoutY(ed.getStartNode().getCords()[1]);
        fill.setStartY(0);
        fill.setEndX(xdiff);
        fill.setEndY(ydiff);
        fill.setId(ed.getEdgeID()+"_fill");
        fill.setStrokeWidth(10);
        fill.setStroke(Paint.valueOf(fillColor));
        fill.setVisible(true);
        fill.setStrokeLineCap(StrokeLineCap.ROUND);
        fill.getStrokeDashArray().addAll(1d, 20d);
        pathFill.getChildren().add(fill);
        start(fill, forward);
    }

    public void start(Line fill, boolean forward) {

        final double maxOffset = fill.getStrokeDashArray().stream().reduce(0d, (a, b) -> a + b);
        Timeline timeline = new Timeline();
        if(forward) {
            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(fill.strokeDashOffsetProperty(), 0, Interpolator.LINEAR)),
                    new KeyFrame(Duration.millis(2000), new KeyValue(fill.strokeDashOffsetProperty(), maxOffset, Interpolator.LINEAR)));
        }else{
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(2000), new KeyValue(fill.strokeDashOffsetProperty(), 0, Interpolator.LINEAR)),
                    new KeyFrame(Duration.ZERO, new KeyValue(fill.strokeDashOffsetProperty(), maxOffset, Interpolator.LINEAR)));
        }
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
        previewEdge.setId(ed.getEdgeID() + "_preview");
        previewEdge.setStrokeWidth(7);
        previewEdge.setStroke(Paint.valueOf("644491"));
        previewEdge.setVisible(true);
        pathPreviewGroup.getChildren().add(previewEdge);
    }

    public void generateEdges(String floor){
        LinkedList<Edge> edgePath = getEdgesTest.EdgesFollowed(App.mapInteractionModel.path);
        for(int i = 0;  i < edgePath.size(); i++){
            if(i != edgePath.size() - 1){
                if(edgePath.get(i).getStartNode().equals(edgePath.get(i + 1).getStartNode()) || edgePath.get(i).getStartNode().equals(edgePath.get(i + 1).getEndNode())){
                    if(floor.equals(edgePath.get(i).getStartNode().getFloor()) && floor.equals(edgePath.get(i).getEndNode().getFloor())){
                        placeEdgesHelper(edgePath.get(i), true);
                    }
                }else{
                    if(floor.equals(edgePath.get(i).getStartNode().getFloor()) && floor.equals(edgePath.get(i).getEndNode().getFloor())){
                        placeEdgesHelper(edgePath.get(i), false);
                    }
                }
            }else{
                if(edgePath.get(i).getStartNode().equals(edgePath.get(i - 1).getStartNode()) || edgePath.get(i).getStartNode().equals(edgePath.get(i - 1).getEndNode())){
                    if(floor.equals(edgePath.get(i).getStartNode().getFloor()) && floor.equals(edgePath.get(i).getEndNode().getFloor())){
                        placeEdgesHelper(edgePath.get(i), false);
                    }
                }else{
                    if(floor.equals(edgePath.get(i).getStartNode().getFloor()) && floor.equals(edgePath.get(i).getEndNode().getFloor())){
                        placeEdgesHelper(edgePath.get(i), true);
                    }
                }
            }

        }

        setMapItemsOrder();
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
        setMapItemsOrder();
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
            clearMapItems();
            App.mapInteractionModel.floorPathfinding = "G";
            loadNewMapAndGenerateHelper("G", "/edu/wpi/u/views/Images/FaulknerCampus.png");
            node.setFitWidth(2987);
            generateEdges("G");
            setMapItemsOrder();
        }else{
            floorG.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 1 and reloads the correct nodes
     */
    public void handleFloor1Button(){
        if(!App.mapInteractionModel.floorPathfinding.equals("1")) {
            clearMapItems();
            App.mapInteractionModel.floorPathfinding = "1";
            loadNewMapAndGenerateHelper("1", "/edu/wpi/u/views/Images/FaulknerFloor1Light.png");
            generateEdges("1");
            setMapItemsOrder();
        }else{
            floor1.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 2 and reloads the correct nodes
     */
    public void handleFloor2Button(){
        if(!App.mapInteractionModel.floorPathfinding.equals("2")) {
            clearMapItems();
            App.mapInteractionModel.floorPathfinding = "2";
            loadNewMapAndGenerateHelper("2", "/edu/wpi/u/views/Images/FaulknerFloor2Light.png");
            generateEdges("2");
            setMapItemsOrder();
        }else{
            floor2.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 3 and reloads the correct nodes
     */
    public void handleFloor3Button(){
        if(!App.mapInteractionModel.floorPathfinding.equals("3")) {
            clearMapItems();
            App.mapInteractionModel.floorPathfinding = "3";
            loadNewMapAndGenerateHelper("3", "/edu/wpi/u/views/Images/FaulknerFloor3Light.png");
            generateEdges("3");
            setMapItemsOrder();
        }else{
            floor3.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 4 and reloads the correct nodes
     */
    public void handleFloor4Button(){
        if(!App.mapInteractionModel.floorPathfinding.equals("4")) {
            clearMapItems();
            App.mapInteractionModel.floorPathfinding = "4";
            loadNewMapAndGenerateHelper("4", "/edu/wpi/u/views/Images/FaulknerFloor4Light.png");
            generateEdges("4");
            setMapItemsOrder();
        }else{
            floor4.setSelected(true);
        }
    }


    /**
     * This is what changes the map displayed to floor 5 and reloads the correct nodes
     */
    public void handleFloor5Button(){
        if(!App.mapInteractionModel.floorPathfinding.equals("5")) {
            clearMapItems();
            App.mapInteractionModel.floorPathfinding = "5";
            loadNewMapAndGenerateHelper("5", "/edu/wpi/u/views/Images/FaulknerFloor5Light.png");
            generateEdges("5");
            setMapItemsOrder();
        }else{
            floor5.setSelected(true);
        }
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mobile/MobileEmbenedGoogleMaps.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        MobileContainerController.getInstance().getMobileRoot().getChildren().clear();
        MobileContainerController.getInstance().getMobileRoot().getChildren().add(fxmlLoader.getRoot());
    }

    public void handleNext(ActionEvent actionEvent) {
        App.mobileUpdateDestinationField.setValue(!App.mobileUpdateDestinationField.getValue());
    }

    public void handleSaveParking(ActionEvent actionEvent) {
        App.mobileUpdateParkingSpot.setValue(!App.mobileUpdateParkingSpot.getValue());
    }
}