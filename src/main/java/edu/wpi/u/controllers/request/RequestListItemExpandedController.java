package edu.wpi.u.controllers.request;

import com.jfoenix.controls.JFXChipView;
import edu.wpi.u.App;
import edu.wpi.u.requests.SpecificRequest;
import edu.wpi.u.requests.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;

import java.io.IOException;

public class RequestListItemExpandedController {
    public HBox HBoxToClone;
    public VBox specificFields;
    public VBox VBoxToAdd;
    public Label typeLabel;
    @FXML Label requestDetailTitleLabel;
    @FXML Label requestDetailCreatorLabel;
    @FXML Label requestDetailDescriptionLabel;
    @FXML JFXChipView requestDetailLocationChipView;
    @FXML JFXChipView requestDetailStaffChipView;
    @FXML Label requestDetailDateCreatedLabel;
    @FXML Label requestDetailDate2BCompleteLabel;

    //Maintenance Requests Panes
    @FXML Label requestDetailSecurityLabel;
    @FXML ListView commentListView;
    @FXML StackPane requestDetailStack;
    @FXML Pane requestDetailSecurityPane;
    @FXML Pane requestDetailMaintenancePane;
    @FXML Pane requestDetailLaundryPane;
    SpecificRequest currentSpecificRequest;



    @FXML
    public void initialize() throws IOException{
        currentSpecificRequest = App.requestService.getRequests().get(App.lastClickedRequestNumber);
        Request request = currentSpecificRequest.getGenericRequest();
        requestDetailTitleLabel.setText("Request Title: "+ request.getTitle());
        requestDetailCreatorLabel.setText("Created By: Administrator");
        //requestDetailCreatorLabel.setText("Created By: " + request.getCreator());
        requestDetailDescriptionLabel.setText(request.getDescription());
        //requestDetailLocationChipView.setText(request.getLocation());
      //  requestDetailStaffChipView.setText(request.getStaff());
        System.out.println(request.getDateCreated().toString());
        requestDetailDateCreatedLabel.setText("Date Created: "+ request.getDateCreated().toString());
        //System.out.println(request.getDateNeeded());
        requestDetailDate2BCompleteLabel.setText("Date to Complete By: NA");
        typeLabel.setText("Type: " + currentSpecificRequest.getType());
        //requestDetailSecurityLabel.setText(request);
//        setSpecifics();
        generateSpecificFields();

    }

    //TODO : Replace with function written in NER Controller, based on current IREQUEST
    public void generateSpecificFields(){
       //Generate Labels ONLY, using fields/values of Irequest, no error checking needed
        //NO text fields, just for display
        for(int i = 0; i < currentSpecificRequest.getSpecificFields().length; i++) {

            Label entryData= new Label(currentSpecificRequest.getSpecificFields()[i] +": "+ currentSpecificRequest.getSpecificData().get(i).toString());
            entryData.setStyle("-fx-font-size: 34");
            VBoxToAdd.getChildren().add(entryData);
        }

    }

    public void handleCommentButton() {
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
        App.requestService.resolveRequest(r);

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/ViewRequestList.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    @FXML
    public void handleEditRequestButton() throws IOException {
        System.out.println("HERE, attempting delete Request " + App.lastClickedRequestNumber);

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/request/ModifyRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    private void setSpecifics(){
        switch(currentSpecificRequest.getType()) {
            case("Maintenance") :
                requestDetailMaintenancePane.setVisible(true);
                break;
            case("Laundry") :
                requestDetailLaundryPane.setVisible(true);
                //add stuff
                break;
            case("Security"):
                requestDetailSecurityPane.setVisible(true);
                //add stuff
                break;
            default:
                System.out.println("lmao you screwed up");
        }
    }
}
