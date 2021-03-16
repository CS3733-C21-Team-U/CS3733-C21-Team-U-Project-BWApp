package edu.wpi.u.controllers.request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.requests.Comment;
import edu.wpi.u.requests.CommentType;
import edu.wpi.u.requests.SpecificRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RequestListItemExpandedController extends AnchorPane implements Initializable {
    public AnchorPane mapViewRoot;
    public RequestListItemContainerController parent;
    public Label titleLabel;
    public Label descriptionLabel;
    public Label creatorAndDateLabel;
    public Label assigneesLabel;
    public Label completeByLabel;
    public AnchorPane noLocaitonGraphic;


    //Map stuff
    ImageView imageNode;
    public Label locationLabel;
    public int currentNode;
    AnchorPane mainMapPane = new AnchorPane();
    Group locationGroup = new Group();
//    public BorderPane locationUI;


    private String nodeID = "";
    @FXML public VBox commentsRoot;
    @FXML public VBox mainSpecialFieldVbox;
    @FXML private JFXTextField commentField;
    @FXML public SVGPath typeIconSVG;

    GesturePane miniMap;


    public RequestListItemExpandedController(RequestListItemContainerController parent) throws IOException {
        this.parent = parent;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemExpanded.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }


    @FXML
    public void initialize(URL location, ResourceBundle resources){

        mainMapPane.getChildren().add(locationGroup);
        mainMapPane.setMinSize(457,275);
        mainMapPane.toFront();
        miniMap = new GesturePane(mainMapPane);
        miniMap.setMinSize(457,285);
        miniMap.setFitMode(GesturePane.FitMode.UNBOUNDED);
        miniMap.setScrollMode(GesturePane.ScrollMode.ZOOM);
        miniMap.setPrefHeight(518);
        miniMap.centreOn(new Point2D(170, 90));
        miniMap.zoomTo(2.5, miniMap.targetPointAtViewportCentre());
        mapViewRoot.getChildren().add(miniMap);
        miniMap.toFront();
        mapViewRoot.setMinSize(457,300);
        mapViewRoot.toFront();
        locationGroup.toFront();
        locationGroup.minHeight(300);
        locationGroup.minWidth(300);

        setFields();
        //add Listener
        parent.needUpdate.addListener((o,oldVal,newVal) -> {
            setFields();
        });

        typeIconSVG.setContent(parent.getIcon(parent.request.getType()));


    }

    public void generateSpecificFields(){
        mainSpecialFieldVbox.getChildren().clear();
        for(int i = 0; i < this.parent.request.getSpecificFields().length; i++) {
            Region region = new Region();
            region.setPrefHeight(14);
            mainSpecialFieldVbox.getChildren().add(region);

            Label fieldTitle = new Label(this.parent.request.getSpecificFields()[i]);
            fieldTitle.getStyleClass().add("subtitle");
            mainSpecialFieldVbox.getChildren().add(fieldTitle);

            Label fieldLabel = new Label(this.parent.request.getSpecificData().get(i).toString());
            fieldLabel.getStyleClass().add("headline-3");
            mainSpecialFieldVbox.getChildren().add(fieldLabel);
        }
    }

    public void generateComments(){
        commentsRoot.getChildren().clear();
        generateFirstComment();
        for(int i = this.parent.request.getGenericRequest().getComments().size()-1; i >= 1; i--){
            generateCommentHelper(i);
        }
    }

    private void generateFirstComment() {
        //This whole block just generates the comment block instead of loading from FXML
        Comment comment = this.parent.request.getGenericRequest().getPrimaryComment();
        Region comExpandRegion = new Region();
        HBox.setHgrow(comExpandRegion,Priority.ALWAYS);
        Label comTypeLabel = new Label("Request Created");
        comTypeLabel.getStyleClass().add("headline-3");
        Label comTimeLabel = new Label(App.prettyTime.format(comment.getTimestamp()));
        comTimeLabel.getStyleClass().add("caption");
        HBox typeDateHBox = new HBox();
        typeDateHBox.getChildren().add(comTypeLabel);
        typeDateHBox.getChildren().add(comExpandRegion);
        typeDateHBox.getChildren().add(comTimeLabel);
        Label comTitleLabel = new Label(comment.getAuthor());
        comTitleLabel.getStyleClass().add("subtitle");
        Label comDescLabel = new Label(comment.getDescription());
        SVGPath iconSVG = new SVGPath();
        iconSVG.setContent(parent.getIcon(comment.getType().toString()));
        iconSVG.getStyleClass().add("on-primary");
        VBox textVBox = new VBox();
        HBox.setHgrow(textVBox,Priority.ALWAYS);
        textVBox.getChildren().add(typeDateHBox);
        textVBox.getChildren().add(comTitleLabel);
//        textVBox.getChildren().add(comDescLabel);
        JFXButton iconButton = new JFXButton();
        iconButton.getStyleClass().add("fab-mini");
        iconButton.setGraphic(iconSVG);
        iconButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        iconButton.setMouseTransparent(true);
        HBox mainHBox = new HBox();
        HBox.setMargin(iconButton,new Insets(10,10,10,10));
        HBox.setMargin(textVBox,new Insets(10,10,10,10));
        mainHBox.getChildren().add(iconButton);
        mainHBox.getChildren().add(textVBox);
        commentsRoot.getChildren().add(mainHBox);
    }

    public void addComment() {
      //  System.out.println("The start size is: "+this.parent.request.getGenericRequest().getComments().size());
        Comment newComment = new Comment("Status", commentField.getText(), App.userService.getActiveUser().getName(), CommentType.DEFAULT);
        App.requestService.addComment(this.parent.request, newComment);
        generateCommentHelper(this.parent.request.getGenericRequest().getComments().size()-1);
        //System.out.println("The end size is: "+this.parent.request.getGenericRequest().getComments().size());
        commentField.setText("");
    }

    public void resolve() {
        //  System.out.println("The start size is: "+this.parent.request.getGenericRequest().getComments().size());
        Comment resolveComment = new Comment("Resolve", commentField.getText(), App.userService.getActiveUser().getUserName(), CommentType.RESOLVE);
        App.requestService.resolveRequest(this.parent.request, resolveComment);
        generateCommentHelper(this.parent.request.getGenericRequest().getComments().size()-1);
        //System.out.println("The end size is: "+this.parent.request.getGenericRequest().getComments().size());
        commentField.setText("");
    }

    @FXML
    public void handleRequestDetailCancelButton() throws Exception {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/ViewRequestList.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    @FXML
    public void handleResolveRequestButton() throws IOException {
        //Resolve Request()

        SpecificRequest r = App.requestService.getRequests().get(App.lastClickedRequestNumber);
        Comment resolveComment = new Comment("Title", "RESOLVED RESOLVED RESOLVED", "Bichael", CommentType.RESOLVE, new Timestamp(System.currentTimeMillis()));
        App.requestService.resolveRequest(r, resolveComment);

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/ViewRequestList.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    @FXML
    public void handleEditRequest() throws IOException {
        parent.editRequestFillFields.set(!parent.editRequestFillFields.getValue());
        parent.switchToEdit();
    }

    public void handleCollapseButton(){
        this.parent.switchToCollapsed();
    }

    public void nextLocation(){
        System.out.println("Next Location from: "+ currentNode);
        currentNode = (currentNode+1)%parent.request.getGenericRequest().getLocations().size();
        System.out.println("Next Location : "+ currentNode);
        loadLocationsOnMap(currentNode);

    }
    public void previousLocation(){
        System.out.println("Previous Location from: "+ currentNode);
        currentNode = Math.floorMod(currentNode-1, parent.request.getGenericRequest().getLocations().size());
        System.out.println("Previous Location: "+ currentNode);
        loadLocationsOnMap(currentNode);
    }


    public void loadLocationsOnMap(int nodeNum){
        //clear
        mainMapPane.getChildren().clear();
       locationGroup.getChildren().clear();
        mainMapPane.getChildren().add(locationGroup);

        if(parent.request.getGenericRequest().getLocations().size() == 0){
            noLocaitonGraphic.setVisible(true);
            noLocaitonGraphic.setMouseTransparent(false);
            noLocaitonGraphic.toFront();
            return;
        }else{
            noLocaitonGraphic.setVisible(false);
            noLocaitonGraphic.setMouseTransparent(true);
            noLocaitonGraphic.toFront();
        }
        //this sets the map image
        //add a switch case for each floor
        Node node = App.mapService.getNodeFromID(parent.request.getGenericRequest().getLocations().get(nodeNum));
        String floor = node.getFloor();
        String resourceURL="";
        double scale = 0.1439;//set this as well to match
        switch (floor){
            case "G":
                resourceURL = "/edu/wpi/u/views/Images/FaulknerCampus.png";
                break;
            case "1":
                resourceURL = "/edu/wpi/u/views/Images/FaulknerFloor1Light.png";
                scale = 0.1753;
                break;
            case "2":
                resourceURL = "/edu/wpi/u/views/Images/FaulknerFloor2Light.png";
                scale = 0.1753;//this is as correct as it can be, moving it down slightly would make it better
                break;
            case "3":
                resourceURL = "/edu/wpi/u/views/Images/FaulknerFloor3Light.png";
                scale = 0.1753;
                break;
            case "4":
                resourceURL = "/edu/wpi/u/views/Images/FaulknerFloor4Light.png";
                scale = 0.1753;
                break;
            case "5":
                resourceURL = "/edu/wpi/u/views/Images/FaulknerFloor5Light.png";
                scale = 0.1753;
                break;
        }

        imageNode = new ImageView(resourceURL);
        imageNode.setFitWidth(430);
        imageNode.setPreserveRatio(true);
        mainMapPane.getChildren().add(imageNode);
        locationGroup.toFront();
        miniMap.toBack();

        SVGPath location = new SVGPath();
        location.setContent("M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z");
//        location.setScaleX(0.7);
//        location.setScaleY(0.7);
        DropShadow ds = new DropShadow();
//        ds.setRadius(5);
//        ds.setSpread(5);
        ds.setOffsetX(1);
        ds.setOffsetY(1);
        location.setEffect(ds);
        location.setLayoutX(((node.getCords()[0]-85)*scale));
        location.setLayoutY(((node.getCords()[1]-185)*scale));
        location.setStyle("-fx-fill: -primary");
//        location.setOpacity(0.8);
//        location.setScaleX(1.5);
//        location.setScaleY(1.5);
        location.toFront();
        locationGroup.getChildren().add(location);
        miniMap.centreOn(new Point2D(((node.getCords()[0]-85)*scale), ((node.getCords()[1]-185)*scale)));

        locationLabel.setText("Floor " + node.getFloor() + ": " + node.getLongName());
    }

    private void generateCommentHelper(int i){
        //This whole block just generates the comment block instead of loading from FXML
        Comment comment = this.parent.request.getGenericRequest().getComments().get(i);
        Region comExpandRegion = new Region();
        HBox.setHgrow(comExpandRegion,Priority.ALWAYS);
        Label comTypeLabel;
        switch (comment.getType().toString()){
            case "RESOLVE":
                comTypeLabel = new Label("Request Resolved");
                break;
            case "UPDATE":
                comTypeLabel = new Label("Request Edited");
                break;
            case "DEFAULT":
                comTypeLabel = new Label("Update addLisded");
                break;
            default:
                comTypeLabel = new Label("Unknown Comment");
                break;
        }
        comTypeLabel.getStyleClass().add("headline-3");
        Label comTimeLabel = new Label(App.prettyTime.format(comment.getTimestamp()));
        comTimeLabel.getStyleClass().add("caption");
        HBox typeDateHBox = new HBox();
        typeDateHBox.getChildren().add(comTypeLabel);
        typeDateHBox.getChildren().add(comExpandRegion);
        typeDateHBox.getChildren().add(comTimeLabel);
        Label comTitleLabel = new Label(comment.getAuthor());
        comTitleLabel.getStyleClass().add("subtitle");
        Label comDescLabel = new Label(comment.getDescription());
        SVGPath iconSVG = new SVGPath();
        iconSVG.setContent(parent.getIcon(comment.getType().toString()));
        iconSVG.getStyleClass().add("on-primary");
        VBox textVBox = new VBox();
        HBox.setHgrow(textVBox,Priority.ALWAYS);
        textVBox.getChildren().add(typeDateHBox);
        textVBox.getChildren().add(comTitleLabel);
        textVBox.getChildren().add(comDescLabel);
        JFXButton iconButton = new JFXButton();
        iconButton.getStyleClass().add("fab-mini");
        iconButton.setGraphic(iconSVG);
        iconButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        iconButton.setMouseTransparent(true);
        HBox mainHBox = new HBox();
        HBox.setMargin(iconButton,new Insets(10,10,10,10));
        HBox.setMargin(textVBox,new Insets(10,10,10,10));
        mainHBox.getChildren().add(iconButton);
        mainHBox.getChildren().add(textVBox);
        commentsRoot.getChildren().add(mainHBox);
    }


    public void handlePathfindToLocation(){
        String nodeID = parent.request.getGenericRequest().getLocations().get(currentNode);
        App.mapInteractionModel.setEndNode(nodeID);

        App.tabPaneRoot.getSelectionModel().select(0);
        App.mapInteractionModel.mapTargetNode2.set(!App.mapInteractionModel.mapTargetNode2.get());

    }

    private void setFields(){
        titleLabel.setText(this.parent.request.getGenericRequest().getTitle());
        descriptionLabel.setText(this.parent.request.getGenericRequest().getDescription());
        String creatorAndDateString = this.parent.request.getGenericRequest().getCreator() + " on " +
                parent.request.getGenericRequest().getDateCreated().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        creatorAndDateLabel.setText(creatorAndDateString);
        assigneesLabel.setText(String.join(",",parent.request.getGenericRequest().getAssignees()));
        completeByLabel.setText(parent.request.getGenericRequest().getDateNeeded().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                + ", " + parent.request.getGenericRequest().getDateNeeded().toLocalDateTime().toLocalTime());
        generateSpecificFields();
        generateComments();
        loadLocationsOnMap(0);
    }
}
