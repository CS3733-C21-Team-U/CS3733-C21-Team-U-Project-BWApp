package edu.wpi.u.controllers.request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.requests.Comment;
import edu.wpi.u.requests.CommentType;
import edu.wpi.u.requests.SpecificRequest;
import edu.wpi.u.requests.Request;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RequestListItemExpandedController extends AnchorPane implements Initializable {
    public AnchorPane mapViewRoot;
    public RequestListItemContainerController parent;
    public Label titleLabel;
    public Label descriptionLabel;
    public Label creatorAndDateLabel;
    public Label assigneesLabel;
    public Label completeByLabel;
    @FXML public VBox commentsRoot;
    @FXML public VBox mainSpecialFieldVbox;
    @FXML private JFXTextField commentField;
    @FXML public SVGPath typeIconSVG;
    //for update

    AnchorPane mainMapPane = new AnchorPane();
    Group locationGroup = new Group();

    //    public HBox HBoxToClone;
//    public VBox specificFields;
//    public VBox VBoxToAdd;
//    public Label typeLabel;
//    @FXML Label requestDetailTitleLabel;
//    @FXML Label requestDetailCreatorLabel;
//    @FXML Label requestDetailDescriptionLabel;
//    @FXML JFXChipView requestDetailLocationChipView;
//    @FXML JFXChipView requestDetailStaffChipView;
//    @FXML Label requestDetailDateCreatedLabel;
//    @FXML Label requestDetailDate2BCompleteLabel;
//
//    //Maintenance Requests Panes
//    @FXML Label requestDetailSecurityLabel;
//    @FXML ListView commentListView;
//    @FXML StackPane requestDetailStack;
//    @FXML Pane requestDetailSecurityPane;
//    @FXML Pane requestDetailMaintenancePane;
//    @FXML Pane requestDetailLaundryPane;
//    SpecificRequest currentSpecificRequest;
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

        ImageView node = new ImageView(String.valueOf(getClass().getResource(App.mapInteractionModel.mapImageResourcePathfinding.get())));
        node.setFitWidth(430);
        node.setPreserveRatio(true);
        mainMapPane.getChildren().add(node);
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
        loadLocationsOnMap("G", mainMapPane);



        //------------------------------------------------------------------------------------
//        ImageView node = new ImageView(String.valueOf(getClass().getResource(App.mapInteractionModel.mapImageResourcePathfinding.get())));
//        node.setFitWidth(430);
//        node.setPreserveRatio(true);
//        //mainMapPane.getChildren().add(node);
//        //node.toFront();
//        miniMap = new GesturePane(node);
//        mainMapPane.setMinSize(300, 300);
//        mainMapPane.setPrefWidth(300);
//        mainMapPane.setPrefHeight(300);
//        mainMapPane.setStyle("-fx-background-color: #000000");
//        miniMap.setFitMode(GesturePane.FitMode.UNBOUNDED);
//        miniMap.setScrollMode(GesturePane.ScrollMode.ZOOM);
//        miniMap.setPrefHeight(518);
//        miniMap.centreOn(new Point2D(170, 90));
//        miniMap.zoomTo(2.5, miniMap.targetPointAtViewportCentre());
//        mapViewRoot.getChildren().add(miniMap);


        //add Listener
        parent.needUpdate.addListener((o,oldVal,newVal) -> {
            titleLabel.setText(this.parent.request.getGenericRequest().getTitle());
            descriptionLabel.setText(this.parent.request.getGenericRequest().getDescription());
            String creatorAndDateString = this.parent.request.getGenericRequest().getCreator();
            creatorAndDateString += " - ";
            creatorAndDateString += App.p.format(parent.request.getGenericRequest().getDateNeeded());
            creatorAndDateLabel.setText(creatorAndDateString);
            assigneesLabel.setText(String.join(",",parent.request.getGenericRequest().getAssignees()));
            completeByLabel.setText(App.p.format(this.parent.request.getGenericRequest().getDateNeeded()));
            generateSpecificFields();
            generateComments();
        });

        typeIconSVG.setContent(parent.getIcon(parent.request.getType()));
        titleLabel.setText(this.parent.request.getGenericRequest().getTitle());
        descriptionLabel.setText(this.parent.request.getGenericRequest().getDescription());
        String creatorAndDateString = this.parent.request.getGenericRequest().getCreator();
        creatorAndDateString += " - ";
        creatorAndDateString += App.p.format(parent.request.getGenericRequest().getDateNeeded());
        creatorAndDateLabel.setText(creatorAndDateString);
        assigneesLabel.setText(String.join(",",parent.request.getGenericRequest().getAssignees()));
        completeByLabel.setText(App.p.format(this.parent.request.getGenericRequest().getDateNeeded()));
        generateSpecificFields();
        generateComments();

    }

    //TODO : Replace with function written in NER Controller, based on current IREQUEST
    //I re-did this here, IDK what the above comment is - Kohmei
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
        Label comTimeLabel = new Label(App.p.format(comment.getTimestamp()));
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
        Comment resolveComment = new Comment("Resolve", commentField.getText(), App.userService.getActiveUser().getName(), CommentType.DEFAULT);
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
//        System.out.println("HERE, attempting delete Request " + App.lastClickedRequestNumber);
//
//        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
//        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/ModifyRequest.fxml"));
//        anchor.getChildren().clear();
//        anchor.getChildren().add(root);
        parent.switchToEdit();
    }

    public void handleCollapseButton(){
        this.parent.switchToCollapsed();
    }



    public void loadLocationsOnMap(String floor, AnchorPane pane){
        ArrayList<String> locationNodeList = this.parent.request.getGenericRequest().getLocations();
        for(String nodeIDLocation: locationNodeList){
            if(App.mapService.getNodeFromID(nodeIDLocation).getFloor().equals(floor)) {
                Circle mapLocation = new Circle();
                mapLocation.setCenterX(200);
                //(App.mapService.getNodeFromID(nodeIDLocation).getCords()[0]
                //App.mapService.getNodeFromID(nodeIDLocation).getCords()[1]
                mapLocation.setCenterX(200);
                mapLocation.setRadius(300);
                mapLocation.setStyle("-fx-fill: yellow");
                mapLocation.toFront();
                pane.getChildren().add(mapLocation);
            }
        }
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
                comTypeLabel = new Label("Update Added");
                break;
            default:
                comTypeLabel = new Label("Unknown Comment");
                break;
        }
        comTypeLabel.getStyleClass().add("headline-3");
        Label comTimeLabel = new Label(App.p.format(comment.getTimestamp()));
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
}
