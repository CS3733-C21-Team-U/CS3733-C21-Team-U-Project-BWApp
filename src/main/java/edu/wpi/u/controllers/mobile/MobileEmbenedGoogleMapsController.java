package edu.wpi.u.controllers.mobile;

import com.jfoenix.controls.JFXButton;
import edu.wpi.u.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.web.WebView;

import java.io.IOException;

public class MobileEmbenedGoogleMapsController {
    public WebView googleMapsEmbedded;
    public JFXButton nextButton1;
    String URL = "https://www.google.com/maps/embed/v1/directions" +
            "?key=AIzaSyCttPdnsrVIlOvwMDTAbnaPbC1HtmGfcMs" +
            "&origin=42.27371483220032,-71.8086443997036"+
            "&destination=1153+Centre+St+Jamaica+Plain+MA+02130" +
            "&avoid=tolls|highways";
    public void initialize(){
        googleMapsEmbedded.getEngine().loadContent("<iframe width='280' height='425' src='" + URL + "' />");
    }

    public void handleNext1() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/robot/MobileGoToKiosk.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        MobileContainerController.getInstance().getMobileRoot().getChildren().clear();
        MobileContainerController.getInstance().getMobileRoot().getChildren().add(fxmlLoader.getRoot());
    }

    public void handleReturn() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/mobile/MobileUserLoginScreen.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        MobileContainerController.getInstance().getMobileRoot().getChildren().clear();
        MobileContainerController.getInstance().getMobileRoot().getChildren().add(fxmlLoader.getRoot());
    }



    //42.3016707215711, -71.1274086336297

}
