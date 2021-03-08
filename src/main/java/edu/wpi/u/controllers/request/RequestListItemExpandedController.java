package edu.wpi.u.controllers.request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.u.App;
import edu.wpi.u.requests.Comment;
import edu.wpi.u.requests.CommentType;
import edu.wpi.u.requests.SpecificRequest;
import edu.wpi.u.requests.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
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


    AnchorPane mainMapPane = new AnchorPane();

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
        //node.toFront();
        miniMap = new GesturePane(mainMapPane);
        mainMapPane.setMinSize(300, 300);
        mainMapPane.setPrefWidth(300);
        mainMapPane.setPrefHeight(300);
        mainMapPane.setStyle("-fx-background-color: #000000");
        miniMap.setFitMode(GesturePane.FitMode.UNBOUNDED);
        miniMap.setScrollMode(GesturePane.ScrollMode.ZOOM);
        miniMap.setPrefHeight(518);
        miniMap.centreOn(new Point2D(170, 90));
        miniMap.zoomTo(2.5, miniMap.targetPointAtViewportCentre());
        mapViewRoot.getChildren().add(miniMap);

        titleLabel.setText(this.parent.request.getGenericRequest().getTitle());
        descriptionLabel.setText(this.parent.request.getGenericRequest().getDescription());
        String creatorAndDateString = this.parent.request.getGenericRequest().getCreator();
        creatorAndDateString += " - ";
        creatorAndDateString += App.p.format(parent.request.getGenericRequest().getDateNeeded());
        creatorAndDateLabel.setText(creatorAndDateString);
        assigneesLabel.setText(String.join(",",parent.request.getGenericRequest().getAssignees()));
        completeByLabel.setText(App.p.format(this.parent.request.getGenericRequest().getDateNeeded()));
//        //requestDetailSecurityLabel.setText(request);
//        setSpecifics();
        generateSpecificFields();
        generateComments();
        //loadLocationsOnMap("G", mainMapPane);

    }

    //TODO : Replace with function written in NER Controller, based on current IREQUEST
    //I re-did this here, IDK what the above comment is - Kohmei
    public void generateSpecificFields(){
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
        for(int i = 1; i < this.parent.request.getGenericRequest().getComments().size(); i++){
            commentsRoot.getChildren().clear();
            //This whole block just generates the comment block instead of loading from FXML
            Comment comment = this.parent.request.getGenericRequest().getComments().get(i);
            Region comExpandRegion = new Region();
            HBox.setHgrow(comExpandRegion,Priority.ALWAYS);
            Label comTypeLabel = new Label(comment.getType().toString());
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
            iconSVG.setContent("M19.43 12.98c.04-.32.07-.64.07-.98 0-.34-.03-.66-.07-.98l2.11-1.65c.19-.15.24-.42.12-.64l-2-3.46c-.09-.16-.26-.25-.44-.25-.06 0-.12.01-.17.03l-2.49 1c-.52-.4-1.08-.73-1.69-.98l-.38-2.65C14.46 2.18 14.25 2 14 2h-4c-.25 0-.46.18-.49.42l-.38 2.65c-.61.25-1.17.59-1.69.98l-2.49-1c-.06-.02-.12-.03-.18-.03-.17 0-.34.09-.43.25l-2 3.46c-.13.22-.07.49.12.64l2.11 1.65c-.04.32-.07.65-.07.98 0 .33.03.66.07.98l-2.11 1.65c-.19.15-.24.42-.12.64l2 3.46c.09.16.26.25.44.25.06 0 .12-.01.17-.03l2.49-1c.52.4 1.08.73 1.69.98l.38 2.65c.03.24.24.42.49.42h4c.25 0 .46-.18.49-.42l.38-2.65c.61-.25 1.17-.59 1.69-.98l2.49 1c.06.02.12.03.18.03.17 0 .34-.09.43-.25l2-3.46c.12-.22.07-.49-.12-.64l-2.11-1.65zm-1.98-1.71c.04.31.05.52.05.73 0 .21-.02.43-.05.73l-.14 1.13.89.7 1.08.84-.7 1.21-1.27-.51-1.04-.42-.9.68c-.43.32-.84.56-1.25.73l-1.06.43-.16 1.13-.2 1.35h-1.4l-.19-1.35-.16-1.13-1.06-.43c-.43-.18-.83-.41-1.23-.71l-.91-.7-1.06.43-1.27.51-.7-1.21 1.08-.84.89-.7-.14-1.13c-.03-.31-.05-.54-.05-.74s.02-.43.05-.73l.14-1.13-.89-.7-1.08-.84.7-1.21 1.27.51 1.04.42.9-.68c.43-.32.84-.56 1.25-.73l1.06-.43.16-1.13.2-1.35h1.39l.19 1.35.16 1.13 1.06.43c.43.18.83.41 1.23.71l.91.7 1.06-.43 1.27-.51.7 1.21-1.07.85-.89.7.14 1.13zM12 8c-2.21 0-4 1.79-4 4s1.79 4 4 4 4-1.79 4-4-1.79-4-4-4zm0 6c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2z");
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

    public void addComment() {
        Comment newComment = new Comment("Status", commentField.getText(), App.userService.getActiveUser().getName(), CommentType.DEFAULT);
        this.parent.request.getGenericRequest().addComment(newComment);
        generateComments();
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
                mapLocation.setRadius(30);
                mapLocation.setStyle("-fx-fill: yellow");
                mapLocation.toFront();
                pane.getChildren().add(mapLocation);
            }
        }
    }

//    private void setSpecifics(){
//        switch(currentSpecificRequest.getType()) {
//            case("Maintenance") :
//                requestDetailMaintenancePane.setVisible(true);
//                break;
//            case("Laundry") :
//                requestDetailLaundryPane.setVisible(true);
//                //add stuff
//                break;
//            case("Security"):
//                requestDetailSecurityPane.setVisible(true);
//                //add stuff
//                break;
//            default:
//                System.out.println("lmao you screwed up");
//        }
//    }

}
