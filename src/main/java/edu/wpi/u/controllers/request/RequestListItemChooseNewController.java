package edu.wpi.u.controllers.request;

import edu.wpi.u.App;
import edu.wpi.u.requests.MaintenanceRequest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RequestListItemChooseNewController extends AnchorPane {
    public RequestListItemContainerController parent;



    public RequestListItemChooseNewController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/request/RequestListItemChooseNew.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }




    public void handleNewMaintenanceRequest() throws IOException {
        App.newNodeType = "Maintenance";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewComputerRequest() throws IOException {
        App.newNodeType = "Computer";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewMedicineRequest() throws IOException {
        App.newNodeType = "Medicine";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewGiftRequest() throws IOException {
        App.newNodeType = "Gift";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewLaundryRequest() throws IOException {
        App.newNodeType = "Laundry";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewAudioVisualRequest() throws IOException {
        App.newNodeType = "AudioVisual";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewFloralRequest() throws IOException {
        App.newNodeType = "Floral";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewSanitationRequest() throws IOException {
        App.newNodeType = "Sanitation";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewSecurityRequest() throws IOException {
        App.newNodeType = "Security";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewReligiousRequest() throws IOException {
        App.newNodeType = "Religious";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewFoodRequest() throws IOException {
        App.newNodeType = "Food";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewLanguageRequest() throws IOException {
        App.newNodeType = "Language";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }

    public void handleCancelButton() throws IOException {
        App.newReqVBox.getChildren().clear();
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }





}
