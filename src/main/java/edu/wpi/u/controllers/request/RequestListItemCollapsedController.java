package edu.wpi.u.controllers.request;

import com.jfoenix.controls.JFXButton;
import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RequestListItemCollapsedController extends AnchorPane implements Initializable {

    public RequestListItemContainerController parent;

    @FXML public Button viewRequestButton;
    @FXML public Label requestItemDescriptionLabel;

    @FXML public Label requestItemTitleLabel;
    @FXML public Label requestItemDate2BCompletedLabel;
    @FXML public Label requestItemCreatorLabel;
    @FXML public Label requestItemRequestTypeLabel;
    @FXML public Label requestItemLocationLabel;
    @FXML public Label requestItemAssigneeLabel;

    @FXML public AnchorPane requestItemRoot;
    @FXML public SVGPath requestIcon;
    @FXML public JFXButton iconContainer;

    public RequestListItemCollapsedController(RequestListItemContainerController parent) throws IOException {
        this.parent = parent;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemCollapsed.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        App.isLoggedIn.addListener((o,oldVal,newVal) -> {
            showOrNottoSHow();
        });

        requestIcon.setContent(parent.getIcon(parent.request.getType()));
        setFields();

        parent.needUpdate.addListener((o,oldVal,newVal) -> {
            setFields();
        });

        App.requestService.requestType.addListener((o, oldVal, newVal)->{
            showOrNottoSHow();
        });

        App.requestService.assignedStatus.addListener((o, oldVal, newVal)->{
           showOrNottoSHow();
        });

        App.requestService.resolveStatus.addListener((o, oldVal, newVal)->{
            showOrNottoSHow();
        });

        App.requestService.checkFilters.addListener((o, oldVal, newVal)->{
            showOrNottoSHow();
        });

        parent.isResolved.addListener((o, oldVal, newVal)->{
            if(parent.request.getGenericRequest().isResolved()){
                requestItemDate2BCompletedLabel.setText("Request Resolved");
                if(App.themeString.equals("DARK")){
                    requestItemRoot.setStyle("-fx-background-color: -requestResolvedColorReal; -fx-background-radius: 7px; -fx-opacity: 0.8");
                }else{
                    requestItemRoot.setStyle("-fx-background-color: -requestResolvedColorReal; -fx-background-radius: 7px;");
                }
                iconContainer.setStyle("-fx-background-color: -primaryVariant;");

            }
        });

    }

    public void handleShowDetailButton(ActionEvent actionEvent) throws IOException {
        this.parent.switchToExpanded();
    }

    public void appear(){
        parent.switchGoneToCollapsed();
    }
    public void disappear(){
        parent.switchCollapsedToGone();
    }

    public void showOrNottoSHow(){
        //for user role
        //Patients only see requests that they are the author of
        boolean role = (!App.userService.getActiveUser().getType().equals(Role.PATIENT)) || App.userService.getActiveUser().getUserName().equals(parent.request.getGenericRequest().getCreator());

        //for requests
        boolean type = parent.request.getType().equals(App.requestService.requestType.getValue())
                || App.requestService.requestType.getValue().equals("All");

        boolean assignee = (App.requestService.assignedStatus.getValue().equals("Assigned to You")&&parent.request.getGenericRequest().getAssignees().contains(App.userService.getActiveUser().getUserName()))
        || (App.requestService.assignedStatus.getValue().equals("Unassigned")&&(parent.request.getGenericRequest().getAssignees().size()==0))
                || App.requestService.assignedStatus.getValue().equals("All");

        boolean resolved = (App.requestService.resolveStatus.getValue().equals("Active")&&!parent.request.getGenericRequest().isResolved())
                ||(App.requestService.resolveStatus.getValue().equals("Resolved")&&parent.request.getGenericRequest().isResolved())||(App.requestService.resolveStatus.getValue().equals("All"));

            if(type && assignee && resolved && role){
                appear();
            }
            else{
                disappear();
            }
    }

    private void setFields(){
        requestItemTitleLabel.setText(parent.request.getGenericRequest().getTitle());
        Timestamp t = parent.request.getGenericRequest().getDateNeeded();
        if(parent.request.getGenericRequest().isResolved()){
            requestItemDate2BCompletedLabel.setText("Request Resolved");
            requestItemTitleLabel.setText("[RESOLVED] " + parent.request.getGenericRequest().getTitle());
            if(App.themeString.equals("DARK")){
                requestItemRoot.setStyle("-fx-background-color: -requestResolvedColorReal; -fx-background-radius: 7px; -fx-opacity: 0.6");
            }else{
                requestItemRoot.setStyle("-fx-background-color: -requestResolvedColorReal; -fx-background-radius: 7px;");
            }
            iconContainer.setStyle("-fx-background-color: -primaryVariant;");
        }
        else if(t.before(new Timestamp(System.currentTimeMillis()))){
            requestItemDate2BCompletedLabel.setText("Overdue: " + t.toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ", " + t.toLocalDateTime().toLocalTime());
        }
        else{
            requestItemDate2BCompletedLabel.setText("Due: " + t.toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ", " + t.toLocalDateTime().toLocalTime());
        }
        requestItemCreatorLabel.setText(parent.request.getGenericRequest().getCreator());
        requestItemRequestTypeLabel.setText(parent.request.getType());
        int locationsSize = parent.request.getGenericRequest().getLocations().size();
        int assigneeSize = parent.request.getGenericRequest().getAssignees().size();
        if(locationsSize != 0) {
            if(locationsSize == 1){
                requestItemLocationLabel.setText(locationsSize + " Location");

            }
            else{
                requestItemLocationLabel.setText(locationsSize + " Locations");
            }
        }
        else {
            requestItemLocationLabel.setText("No Locations");
        }
        if(assigneeSize != 0) {
            if(assigneeSize == 1){
                requestItemAssigneeLabel.setText(assigneeSize + " Assignee");

            }
            else{
                requestItemAssigneeLabel.setText(assigneeSize + " Assignees");

            }
        }
        else {
            requestItemAssigneeLabel.setText("No Assignees");
        }
    }

}
