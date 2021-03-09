package edu.wpi.u.controllers.request;

import com.jfoenix.controls.JFXChipView;
import edu.wpi.u.App;
import edu.wpi.u.requests.SpecificRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RequestListItemCollapsedController extends AnchorPane implements Initializable {

    public RequestListItemContainerController parent;

    //@FXML public Button expandCollapseButton;     DNE
    //@FXML public Button editRequestButton;        DNE
    //@FXML public AnchorPane requestItemAnchor;    DNE

    //@FXML public Button deleteRequestButton;      DNE

    @FXML public Button viewRequestButton;
    @FXML public Label requestItemDescriptionLabel;
    //@FXML public TextField title;
    //@FXML public TextField location;

    //public boolean isCollapsed = true;
    @FXML public Label requestItemTitleLabel;
//    @FXML public JFXChipView requestItemLocationChipView;
    @FXML public Label requestItemDate2BCompletedLabel;
    @FXML public Label requestItemCreatorLabel;
    @FXML public Label requestItemRequestTypeLabel;
    @FXML public AnchorPane requestItemRoot;
//    @FXML public SVGPath requestIcon;

    public RequestListItemCollapsedController(RequestListItemContainerController parent) throws IOException {
        this.parent = parent;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemCollapsed.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        requestItemTitleLabel.setText(parent.request.getGenericRequest().getTitle());
        requestItemDate2BCompletedLabel.setText(App.p.format(parent.request.getGenericRequest().getDateNeeded()));
        requestItemCreatorLabel.setText(parent.request.getGenericRequest().getCreator());
        requestItemRequestTypeLabel.setText(parent.request.getType());

        parent.needUpdate.addListener((o,oldVal,newVal) -> {
            requestItemTitleLabel.setText(parent.request.getGenericRequest().getTitle());
            requestItemDate2BCompletedLabel.setText(App.p.format(parent.request.getGenericRequest().getDateNeeded()));
            requestItemCreatorLabel.setText(parent.request.getGenericRequest().getCreator());
            requestItemRequestTypeLabel.setText(parent.request.getType());
        });

        App.requestService.requestType.addListener((o, oldVal, newVal)->{
            showOrNottoSHow();
//           if(parent.request.getType().equals(App.requestService.requestType.getValue()) || App.requestService.requestType.getValue().equals("All")){
//               appear();
//               System.out.println("I am a"+parent.request.getType()+"not a "+ App.requestService.requestType + "APPEAR");
//           }
//           else{
//               disappear();
//               System.out.println("I am a"+parent.request.getType()+"not a"+App.requestService.requestType+"DISAPPEAR");
//           }
        });

        App.requestService.assignedStatus.addListener((o, oldVal, newVal)->{
           showOrNottoSHow();
        });

        App.requestService.resolveStatus.addListener((o, oldVal, newVal)->{
            showOrNottoSHow();
        });


        //System.out.println("MYINFO: " +requestItemRoot.getPrefHeight()+" "+" "+requestItemRoot.getMaxHeight()+ requestItemRoot.isVisible()+ " " + requestItemRoot.getOpacity());


    }

    public void handleShowDetailButton(ActionEvent actionEvent) throws IOException {
        this.parent.switchToExpanded();
    }

    public void appear(){
        requestItemRoot.setOpacity(1.0);
    }
    public void disappear(){
        requestItemRoot.setOpacity(0.0);

    }

    public void showOrNottoSHow(){
        //for requests
        boolean type = parent.request.getType().equals(App.requestService.requestType.getValue())
                || App.requestService.requestType.getValue().equals("All");

        boolean assignee = (App.requestService.assignedStatus.getValue().equals("Assigned to You")&&parent.request.getGenericRequest().getAssignees().contains(App.userService.getActiveUser().getUserName()))
        || (App.requestService.assignedStatus.getValue().equals("Unassigned")&&(parent.request.getGenericRequest().getAssignees().size()==0))
                || App.requestService.assignedStatus.getValue().equals("All");

        boolean resolved = (App.requestService.resolveStatus.getValue().equals("Active")&&!parent.request.getGenericRequest().isResolved())
                ||(App.requestService.resolveStatus.getValue().equals("Resolved")&&parent.request.getGenericRequest().isResolved())||(App.requestService.resolveStatus.getValue().equals("All"));

            if(type && assignee && resolved){
                appear();
            }
            else{
                disappear();
            }
        //is my type = , OR is type all
        //if all - show, if unassigned is assignees empty, if assigned does assignees contain my username or not?
        //if resolved, am I resolved, if unresolved am I not resolved
    }

//    //Listener here for the global drawerstare variable
//    @FXML
//    public void handleChangeToEditRequest() throws Exception {
//        App.lastClickedRequestNumber = myID;
//        App.rightDrawerRoot.set("/edu/wpi/u/views/EditRequest.fxml");
//    }
//
//    @FXML
//    public void handleDeleteRequest() {
//        App.requestService.deleteRequest(myRequestID);
//        App.rightDrawerRoot.set("/edu/wpi/u/views/EditRequest.fxml"); //Fake - Just to refresh
//        App.rightDrawerRoot.set("/edu/wpi/u/views/Oldfxml/ViewRequest.fxml");
//    }
//
//    public void setTitle(String newTitle){
//        requestitleLabel.setText(newTitle);
//    }
//    public void setLocation(String newLocation){ locationLabel.setText(newLocation); }

}
