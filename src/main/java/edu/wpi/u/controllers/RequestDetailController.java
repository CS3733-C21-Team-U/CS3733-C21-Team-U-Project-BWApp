package edu.wpi.u.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import edu.wpi.u.App;
import edu.wpi.u.requests.IRequest;
import edu.wpi.u.requests.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Stack;

public class RequestDetailController {
    @FXML Label requestDetailTitleLabel;
    @FXML Label requestDetailCreatorLabel;
    @FXML Label requestDetailDescriptionLabel;
    @FXML Label requestDetailTypeLabel;
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
    @FXML Label requestDetailMaintenanceLabel;
    @FXML Label requestDetailLaundryLabel;
    IRequest currentIRequest;

    /**
     * Sets All Labels to Correspond to A Specific Request
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException{
        currentIRequest = App.requestService.getRequests().get(App.lastClickedRequestNumber);
        Request request = currentIRequest.getGenericRequest();
        requestDetailTitleLabel.setText(request.getTitle());
        requestDetailDescriptionLabel.setText(request.getDescription());
        requestDetailTypeLabel.setText(request.getType());
        requestDetailCreatorLabel.setText(request.getCreator());
        requestDetailDateCreatedLabel.setText(request.getDateCreated().toString());
        requestDetailDate2BCompleteLabel.setText(request.getDateNeeded().toString());
        requestDetailLocationChipView.getChips().addAll(request.getLocation());
        requestDetailStaffChipView.getChips().addAll(request.getAssignee());
        setSpecifics();

    }

    /**
     * Allows User to Add Comments to A Request
     */
    public void handleCommentButton() {}

    /**
     * Sends User Back to NewViewRequest
     * @throws Exception
     */
    @FXML
    public void handleRequestDetailCancelButton() throws Exception {
        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewViewRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    /**
     * Resolves Request and Takes the User Back to NewViewRequest
     * @throws IOException
     */
    @FXML
    public void handleResolveRequestButton() throws IOException {
        //Resolve Request()

        IRequest r = App.requestService.getRequests().get(App.lastClickedRequestNumber);
        App.requestService.deleteRequest(r.getGenericRequest().getRequestID());

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewViewRequest.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    /**
     * Takes User to Edit Request Page
     * @throws IOException
     */
    @FXML
    public void handleEditRequestButton() throws IOException {
        System.out.println("HERE, attempting delete Request " + App.lastClickedRequestNumber);

        AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/NewRequestEdit.fxml"));
        anchor.getChildren().clear();
        anchor.getChildren().add(root);
    }

    private void setSpecifics(){
        switch(currentIRequest.getGenericRequest().getType()) {
            case("Maintenance") :
                requestDetailMaintenancePane.setVisible(true);
                //Placeholder
                requestDetailMaintenanceLabel.setText("Maintenance Issue");
                break;
            case("Laundry") :
                requestDetailLaundryPane.setVisible(true);
                //Placeholder
                requestDetailLaundryLabel.setText("Laundry Issue");
                break;
            case("Security"):
                requestDetailSecurityPane.setVisible(true);
                //Placeholder
                requestDetailSecurityLabel.setText("Security Issue");
                break;
            default:
                System.out.println("lmao you screwed up");
        }
    }
}
