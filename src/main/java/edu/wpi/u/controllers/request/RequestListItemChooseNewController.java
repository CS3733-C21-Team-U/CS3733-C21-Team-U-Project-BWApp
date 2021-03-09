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
        App.newNodeType = "Security";
        App.newReqVBox.getChildren().clear();
        App.newReqVBox.getChildren().add(new RequestListItemNewController());
        App.VBoxChanged.set(!App.VBoxChanged.get());
    }
    public void handleNewComputerRequest(){
        App.newNodeType = "Computer";
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
    }
    public void handleNewMedicineRequest(){
        App.newNodeType = "Medicine";
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
    }
    public void handleNewGiftRequest(){
        App.newNodeType = "Gift";
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
    }
    public void handleNewLaundryRequest(){
        App.newNodeType = "Laundry";
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
    }
    public void handleNewAudioVisualRequest(){
        App.newNodeType = "AudioVisual";
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
    }
    public void handleNewFloralRequest(){
        App.newNodeType = "AudioVisual";
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
    }
    public void handleNewSanitationRequest(){
        App.newNodeType = "AudioVisual";
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
    }
    public void handleNewSecurityRequest(){
        App.newNodeType = "Security";
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
    }
    public void handleNewReligiousRequest(){
        App.newNodeType = "Religious";
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
    }
    public void handleNewFoodRequest(){
        App.newNodeType = "Food";
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
    }
    public void handleNewLanguageRequest(){
        App.newNodeType = "Language";
        this.parent.needUpdate.set(!this.parent.needUpdate.get());
    }





}
